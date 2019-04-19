package org.digijava.kernel.ampapi.endpoints.activity.field;

import static java.util.stream.Collectors.toList;
import static org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants.RequiredValidation.ALWAYS;
import static org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants.RequiredValidation.NONE;
import static org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants.RequiredValidation.SUBMIT;
import static org.digijava.kernel.util.SiteUtils.DEFAULT_SITE_ID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.dgfoundation.amp.nireports.ImmutablePair;
import org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants;
import org.digijava.kernel.ampapi.endpoints.activity.DiscriminatedFieldAccessor;
import org.digijava.kernel.ampapi.endpoints.activity.FEContext;
import org.digijava.kernel.ampapi.endpoints.activity.FMService;
import org.digijava.kernel.ampapi.endpoints.activity.InterchangeDependencyResolver;
import org.digijava.kernel.ampapi.endpoints.activity.InterchangeUtils;
import org.digijava.kernel.ampapi.endpoints.activity.SimpleFieldAccessor;
import org.digijava.kernel.ampapi.endpoints.common.TranslatorService;
import org.digijava.kernel.ampapi.endpoints.common.field.FieldMap;
import org.digijava.kernel.ampapi.filters.AmpOfflineModeHolder;
import org.digijava.kernel.entity.Message;
import org.digijava.kernel.persistence.WorkerException;
import org.digijava.module.aim.annotations.interchange.Interchangeable;
import org.digijava.module.aim.annotations.interchange.InterchangeableDiscriminator;
import org.digijava.module.aim.annotations.interchange.InterchangeableId;
import org.digijava.module.aim.annotations.interchange.PossibleValues;
import org.digijava.module.aim.annotations.interchange.Validators;
import org.digijava.module.aim.dbentity.AmpActivityProgram;

/**
 * Enumerate & describe all fields of an object used for import / export in API.
 *
 * @author acartaleanu, Octavian Ciubotaru
 */
public class FieldsEnumerator {

    private static final Logger LOGGER = Logger.getLogger(FieldsEnumerator.class);

    private FieldInfoProvider fieldInfoProvider;

    private FMService fmService;

    private TranslatorService translatorService;

    private InterchangeDependencyResolver interchangeDependencyResolver;

    private Function<String, Boolean> allowMultiplePrograms;

    /**
     * Fields Enumerator
     */
    public FieldsEnumerator(FieldInfoProvider fieldInfoProvider, FMService fmService,
            TranslatorService translatorService, Function<String, Boolean> allowMultiplePrograms) {
        this.fieldInfoProvider = fieldInfoProvider;
        this.fmService = fmService;
        this.translatorService = translatorService;
        interchangeDependencyResolver = new InterchangeDependencyResolver(fmService);
        this.allowMultiplePrograms = allowMultiplePrograms;
    }

    /**
     * describes a field in a complex JSON structure
     * see the wiki for details, too many options to be listed here
     *
     * @param field field to be described
     * @param context current context
     * @return field definition
     */
    protected APIField describeField(Field field, FEContext context) {
        Interchangeable interchangeable = context.getIntchStack().peek();
        String fieldTitle = FieldMap.underscorify(interchangeable.fieldTitle());

        APIField apiField = new APIField();
        apiField.setFieldName(fieldTitle);

        apiField.setId(field.isAnnotationPresent(InterchangeableId.class));

        // for discriminated case we can override the type here
        Class<?> type = InterchangeUtils.getClassOfField(field);
        FieldType fieldType = null;
        Class<?> elementType = null;
        if (interchangeable.pickIdOnly()) {
            fieldType = InterchangeableClassMapper.getCustomMapping(java.lang.Long.class);
        } else if (InterchangeUtils.isCollection(field)) {
            elementType = getType(field, context);
            if (interchangeable.multipleValues()) {
                fieldType = FieldType.LIST;
                if (InterchangeUtils.isSimpleType(elementType)) {
                    type = field.getClass();
                }
            }
        } else if (field.getType().equals(java.util.Date.class)) {
            fieldType = InterchangeUtils.isTimestampField(field) ? FieldType.TIMESTAMP : FieldType.DATE;
        }

        APIType apiType = new APIType(type, fieldType, elementType);
        apiField.setApiType(apiType);
        boolean isCollection = apiType.getFieldType().isList();

        if (apiField.isId()
                && (apiType.getFieldType() == FieldType.OBJECT || apiType.getFieldType() == FieldType.LIST)) {
            throw new RuntimeException("Id must use primitive data type.");
        }

        apiField.setPossibleValuesProviderClass(InterchangeUtils.getPossibleValuesProvider(field));

        String label = getLabelOf(interchangeable);
        apiField.setFieldLabel(InterchangeUtils.mapToBean(getTranslationsForLabel(label)));
        apiField.setRequired(getRequiredValue(context, fieldTitle));
        apiField.setImportable(interchangeable.importable());

        if (interchangeable.percentageConstraint()) {
            apiField.setPercentage(true);
        }
        List<String> actualDependencies =
                interchangeDependencyResolver.getActualDependencies(interchangeable.dependencies());
        if (actualDependencies != null) {
            apiField.setDependencies(actualDependencies);
        }

        apiField.setFieldNameInternal(field.getName());

        /* list type */

        apiField.setIdOnly(hasPossibleValues(field, interchangeable));

        if (!InterchangeUtils.isSimpleType(field.getType())) {
            if (!interchangeable.pickIdOnly()) {
                Class<?> clazz = isCollection ? elementType : type;
                apiField.setChildren(getAllAvailableFields(clazz, context));
            }
            if (isCollection) {
                apiField.setMultipleValues(!hasMaxSizeValidatorEnabled(field, context));
                if (interchangeable.sizeLimit() > 1) {
                    apiField.setSizeLimit(interchangeable.sizeLimit());
                }
                if (hasPercentageValidatorEnabled(context)) {
                    apiField.setPercentageConstraint(getPercentageConstraint(field, context));
                }
                String uniqueConstraint = getUniqueConstraint(apiField, field, context);
                if (hasTreeCollectionValidatorEnabled(context)) {
                    apiField.setTreeCollectionConstraint(true);
                    apiField.setUniqueConstraint(uniqueConstraint);
                } else if (hasUniqueValidatorEnabled(context)) {
                    apiField.setUniqueConstraint(uniqueConstraint);
                }
            }
        }

        // only String fields should clarify if they are translatable or not
        if (java.lang.String.class.equals(field.getType())) {
            apiField.setTranslatable(fieldInfoProvider.isTranslatable(field));
        }
        if (ActivityEPConstants.TYPE_VARCHAR.equals(fieldInfoProvider.getType(field))) {
            apiField.setFieldLength(fieldInfoProvider.getMaxLength(field));
        }
        if (StringUtils.isNotBlank(interchangeable.regexPattern())) {
            apiField.setRegexPattern(interchangeable.regexPattern());
        }
        if (StringUtils.isNotEmpty(interchangeable.discriminatorOption())) {
            apiField.setDiscriminatorValue(interchangeable.discriminatorOption());
        }

        if (!AmpOfflineModeHolder.isAmpOfflineMode()) {
            apiField.setRequired(ActivityEPConstants.FIELD_ALWAYS_REQUIRED);
            apiField.setImportable(true);
        }

        if (apiField.getApiType().getFieldType() == FieldType.LIST
                && apiField.getApiType().getItemType() == FieldType.OBJECT) {
            List<APIField> idFields = apiField.getChildren().stream()
                    .filter(APIField::isId)
                    .limit(2)
                    .collect(toList());
            if (idFields.isEmpty()) {
                throw new RuntimeException("Id field is missing: " + apiField);
            }
            if (idFields.size() > 1) {
                throw new RuntimeException("Only one id field is expected.");
            }
            apiField.setIdChild(idFields.get(0));
        }

        return apiField;
    }

    private boolean hasPossibleValues(Field field, Interchangeable interchangeable) {
        return interchangeable.pickIdOnly() || field.isAnnotationPresent(PossibleValues.class);
    }

    private Class<?> getType(Field field, FEContext context) {
        if (!context.getIntchStack().isEmpty()) {
            Interchangeable interchangeable = context.getIntchStack().peek();
            Class<?> type = interchangeable.type();
            if (type != Interchangeable.DefaultType.class) {
                return type;
            }
        }
        return InterchangeUtils.getClassOfField(field);
    }

    private String getLabelOf(Interchangeable interchangeable) {
        String label;
        if (interchangeable.label().equals(ActivityEPConstants.FIELD_TITLE)) {
            label = interchangeable.fieldTitle();
        } else {
            label = interchangeable.label();
        }
        return label;
    }

    public List<APIField> getAllAvailableFields(Class<?> clazz) {
        return getAllAvailableFields(clazz, new FEContext());
    }

    /**
     * Describes each @Interchangeable field of a class
     *
     * @param clazz the class to be described
     * @param context current context
     * @return field definitions
     */
    private List<APIField> getAllAvailableFields(Class<?> clazz, FEContext context) {
        List<APIField> result = new ArrayList<>();
        //StopWatch.next("Descending into", false, clazz.getName());
        for (Field field : InterchangeUtils.getFieldsAnnotatedWith(clazz,
                Interchangeable.class, InterchangeableDiscriminator.class)) {
            Interchangeable interchangeable = field.getAnnotation(Interchangeable.class);
            if (interchangeable != null) {
                context.getIntchStack().push(interchangeable);
                if (isFieldVisible(context)) {
                    APIField descr = describeField(field, context);
                    descr.setFieldAccessor(new SimpleFieldAccessor(field.getName()));
                    result.add(descr);
                }
                context.getIntchStack().pop();
            }
            InterchangeableDiscriminator discriminator = field.getAnnotation(InterchangeableDiscriminator.class);
            if (discriminator != null) {
                Interchangeable[] settings = discriminator.settings();
                for (int i = 0; i < settings.length; i++) {
                    context.getDiscriminationInfoStack().push(getDiscriminationInfo(field, settings[i]));
                    context.getIntchStack().push(settings[i]);
                    if (isFieldVisible(context)) {
                        APIField descr = describeField(field, context);
                        descr.setDiscriminatorField(discriminator.discriminatorField());
                        descr.setDiscriminationConfigurer(discriminator.configurer());
                        descr.setFieldAccessor(new DiscriminatedFieldAccessor(new SimpleFieldAccessor(field.getName()),
                                discriminator.discriminatorField(), settings[i].discriminatorOption()));
                        result.add(descr);
                    }
                    context.getIntchStack().pop();
                    context.getDiscriminationInfoStack().pop();
                }
            }
        }
        return result;
    }

    private ImmutablePair<Class<?>, Object> getDiscriminationInfo(Field field, Interchangeable interchangeable) {
        Class<?> classOfField = InterchangeUtils.getClassOfField(field);
        String value = interchangeable.discriminatorOption();
        return new ImmutablePair<>(classOfField, value);
    }

    /**
     * Picks available translations for a label.
     *
     * @param label the label to be translated
     * @return a map from the ISO2 code -> translation in said text
     */
    private Map<String, String> getTranslationsForLabel(String label) {
        Map<String, String> translations = new HashMap<>();
        try {
            Collection<Message> messages = translatorService.getAllTranslationOfBody(label, DEFAULT_SITE_ID);
            for (Message m : messages) {
                translations.put(m.getLocale(), m.getMessage());
            }
            if (translations.isEmpty()) {
                translations.put("EN", label);
            }
        } catch (WorkerException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return translations;
    }

    /**
     * Find nested field with a percentage constraint.
     *
     * @param field field to check
     * @param context current context
     * @return name of the field with percentage constraint
     */
    private String getPercentageConstraint(Field field, FEContext context) {
        Class<?> genericClass = InterchangeUtils.getGenericClass(field);
        Field[] fields = FieldUtils.getFieldsWithAnnotation(genericClass, Interchangeable.class);
        for (Field f : fields) {
            Interchangeable interchangeable = f.getAnnotation(Interchangeable.class);
            if (isVisible(interchangeable.fmPath(), context) && interchangeable.percentageConstraint()) {
                return FieldMap.underscorify(interchangeable.fieldTitle());
            }
        }

        return null;
    }

    /**
     * Describes each @Interchangeable field of a class
     */
    private String getUniqueConstraint(APIField apiField, Field field, FEContext context) {
        if (apiField.getApiType().isSimpleItemType()) {
            Interchangeable interchangeable = context.getIntchStack().peek();
            return interchangeable.uniqueConstraint() ? apiField.getFieldName() : null;
        }
        Class<?> genericClass = InterchangeUtils.getGenericClass(field);
        Field[] fields = FieldUtils.getFieldsWithAnnotation(genericClass, Interchangeable.class);
        for (Field f : fields) {
            Interchangeable interchangeable = f.getAnnotation(Interchangeable.class);
            if (isVisible(interchangeable.fmPath(), context) && interchangeable.uniqueConstraint()) {
                return FieldMap.underscorify(interchangeable.fieldTitle());
            }
        }

        return null;
    }

    /**
     * Return all fields paths that match the field filter.
     */
    public List<String> findFieldPaths(Predicate<APIField> fieldFilter, List<APIField> fields) {
        return findFieldPaths(fieldFilter, fields, "");
    }

    private List<String> findFieldPaths(Predicate<APIField> fieldFilter, List<APIField> fields, String prefix) {
        List<String> paths = new ArrayList<>();
        for (APIField f : fields) {
            if (fieldFilter.test(f)) {
                paths.add(prefix + f.getFieldName());
            }
            paths.addAll(findFieldPaths(fieldFilter, f.getChildren(), prefix + f.getFieldName() + "~"));
        }
        return paths;
    }

    /**
     * Gets the field required value.
     *
     * @param context current context
     * @param fieldTitle the field to get its required value
     * @return String with Y|ND|N, where Y (yes) = always required, ND=for draft status=false,
     * N (no) = not required. .
     */
    private String getRequiredValue(FEContext context, String fieldTitle) {
        Interchangeable fieldIntch = context.getIntchStack().peek();

        ActivityEPConstants.RequiredValidation required = fieldIntch.required();
        String requiredFmPath = fieldIntch.requiredFmPath();

        if (StringUtils.isNotBlank(requiredFmPath)) {
            if (isRequiredVisible(requiredFmPath, context)) {
                required = required == NONE ? SUBMIT : required;
            } else {
                required = NONE;
            }
        }

        if (required == ALWAYS) {
            return ActivityEPConstants.FIELD_ALWAYS_REQUIRED;
        } else if (required == SUBMIT || hasRequiredValidatorEnabled(context)) {
            return ActivityEPConstants.FIELD_NON_DRAFT_REQUIRED;
        }

        return ActivityEPConstants.FIELD_NOT_REQUIRED;
    }

    /**
     * Determine if the field contains unique validator
     *
     * @param context current context
     * @return boolean if the field contains unique validator
     */
    private boolean hasUniqueValidatorEnabled(FEContext context) {
        return hasValidatorEnabled(context, ActivityEPConstants.UNIQUE_VALIDATOR_NAME);
    }

    /**
     * Determine if the field contains tree collection validator
     *
     * @param context current context
     * @return boolean if the field contains tree collection validator
     */
    private boolean hasTreeCollectionValidatorEnabled(FEContext context) {
        return hasValidatorEnabled(context, ActivityEPConstants.TREE_COLLECTION_VALIDATOR_NAME);
    }

    /**
     * Determine if the field contains maxsize validator
     *
     * @param context current context
     * @return boolean if the field contains maxsize validator
     */
    private boolean hasMaxSizeValidatorEnabled(Field field, FEContext context) {
        if (AmpActivityProgram.class.equals(InterchangeUtils.getGenericClass(field))) {
            return allowMultiplePrograms.apply(context.getIntchStack().peek().discriminatorOption());

        } else {
            return hasValidatorEnabled(context, ActivityEPConstants.MAX_SIZE_VALIDATOR_NAME);
        }
    }

    /**
     * Determine if the field contains required validator
     *
     * @param context current context
     * @return boolean if the field contains required validator
     */
    private boolean hasRequiredValidatorEnabled(FEContext context) {
        return hasValidatorEnabled(context, ActivityEPConstants.MIN_SIZE_VALIDATOR_NAME);
    }

    /**
     * Determine if the field contains percentage validator
     *
     * @param context current context
     * @return boolean if the field contains percentage validator
     */
    private boolean hasPercentageValidatorEnabled(FEContext context) {
        return hasValidatorEnabled(context, ActivityEPConstants.PERCENTAGE_VALIDATOR_NAME);
    }

    /**
     * Determine if the field contains a certain validator
     *
     * @param context current context
     * @param validatorName the name of the validator (unique, maxSize, minSize, percentage, treeCollection)
     * @return boolean if the field contains unique validator
     */
    private boolean hasValidatorEnabled(FEContext context, String validatorName) {
        boolean isEnabled = false;
        Interchangeable interchangeable = context.getIntchStack().peek();
        Validators validators = interchangeable.validators();

        String validatorFmPath = "";

        if (ActivityEPConstants.UNIQUE_VALIDATOR_NAME.equals(validatorName)) {
            validatorFmPath = validators.unique();
        } else if (ActivityEPConstants.MAX_SIZE_VALIDATOR_NAME.equals(validatorName)) {
            validatorFmPath = validators.maxSize();
        } else if (ActivityEPConstants.MIN_SIZE_VALIDATOR_NAME.equals(validatorName)) {
            validatorFmPath = validators.minSize();
        } else if (ActivityEPConstants.PERCENTAGE_VALIDATOR_NAME.equals(validatorName)) {
            validatorFmPath = validators.percentage();
        } else if (ActivityEPConstants.TREE_COLLECTION_VALIDATOR_NAME.equals(validatorName)) {
            validatorFmPath = validators.treeCollection();
        }

        if (StringUtils.isNotBlank(validatorFmPath)) {
            isEnabled = isVisible(validatorFmPath, context);
        }

        return isEnabled;
    }

    private boolean isFieldVisible(FEContext context) {
        Interchangeable interchangeable = context.getIntchStack().peek();

        return isVisible(interchangeable.fmPath(), context);
    }

    protected boolean isRequiredVisible(String fmPath, FEContext context) {
        Interchangeable peek = context.getIntchStack().pop();
        boolean isVisible = fmService.isVisible(fmPath, context.getIntchStack());
        context.getIntchStack().push(peek);

        return isVisible;
    }

    protected boolean isVisible(String fmPath, FEContext context) {
        Interchangeable interchangeable = context.getIntchStack().peek();
        String fieldTitle = FieldMap.underscorify(interchangeable.fieldTitle());

        if (!AmpOfflineModeHolder.isAmpOfflineMode()) {
            return true;
        } else {
            return fmService.isVisible(fmPath, context.getIntchStack());
        }
    }

}
