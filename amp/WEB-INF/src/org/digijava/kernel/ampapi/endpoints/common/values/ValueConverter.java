package org.digijava.kernel.ampapi.endpoints.common.values;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.digijava.kernel.ampapi.discriminators.DiscriminationConfigurer;
import org.digijava.kernel.ampapi.endpoints.activity.InterchangeUtils;
import org.digijava.kernel.ampapi.endpoints.activity.field.APIField;
import org.digijava.kernel.ampapi.endpoints.resource.ResourceType;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.module.aim.dbentity.ApprovalStatus;

/**
 * @author Nadejda Mandrescu
 */
public class ValueConverter {
    private static final Logger logger = Logger.getLogger(ValueConverter.class);

    private Map<Class<? extends DiscriminationConfigurer>, DiscriminationConfigurer> discriminatorConfigurerCache =
            new HashMap<>();

    /**
     * Used to restore the value of the discrimination field.
     */
    public void configureDiscriminationField(Object obj, APIField fieldDef) {
        if (fieldDef.getDiscriminationConfigurer() != null) {
            DiscriminationConfigurer configurer = discriminatorConfigurerCache.computeIfAbsent(
                    fieldDef.getDiscriminationConfigurer(), this::newConfigurer);
            configurer.configure(obj, fieldDef.getDiscriminatorField(), fieldDef.getDiscriminatorValue());
        }
    }

    private DiscriminationConfigurer newConfigurer(Class<? extends DiscriminationConfigurer> configurer) {
        try {
            return configurer.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate discriminator configurer " + configurer, e);
        }
    }

    /**
     * Handles integer -> long and similar conversions, since exact type is not guaranteed during deserialisation 
     * @param value
     * @param type
     * @return
     */
    public Object toSimpleTypeValue(Object value, Class<?> type) {
        if (value == null || type.isAssignableFrom(value.getClass())) {
            return value;
        }
        try {
            Method valueOf = type.getMethod("valueOf", String.class);
            return valueOf.invoke(type, value.toString());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            logger.error("Could not automatically convert the value. The deserializer configuration may be missing.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates an instance of a type
     * @param concreteType
     * @return
     */
    public Object getNewInstance(Class<?> concreteType) {
        try {
            return instantiate(findConcreteType(concreteType));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // this class can work with plain lists
    private Class findConcreteType(Class type) {
        if (SortedSet.class.isAssignableFrom(type)) {
            return TreeSet.class;
        } else if (Set.class.isAssignableFrom(type)) {
            return HashSet.class;
        } else if (Collection.class.isAssignableFrom(type)) {
            return ArrayList.class;
        } else {
            return type;
        }
    }

    public Object instantiate(Class type) {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Object getObjectById(Class<?> entityClass, Object id) {
        if (Collection.class.isAssignableFrom(entityClass)) {
            throw new RuntimeException("Can't handle a collection of ID-linked objects yet!");
        }
        if (ApprovalStatus.class.isAssignableFrom(entityClass)) {
            return ApprovalStatus.fromId((Integer) id);
        } else if (ResourceType.class.isAssignableFrom(entityClass)) {
            return ResourceType.fromId((Integer) id);
        } else if (InterchangeUtils.isSimpleType(entityClass)) {
            return ConvertUtils.convert(id, entityClass);
        } else {
            return PersistenceManager.getSession().get(entityClass.getName(), Long.valueOf(id.toString()));
        }
    }

}
