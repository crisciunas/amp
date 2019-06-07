package org.digijava.kernel.validators.resource;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.*;

import java.util.Set;

import org.digijava.kernel.ampapi.endpoints.activity.field.APIField;
import org.digijava.kernel.ampapi.endpoints.resource.AmpResource;
import org.digijava.kernel.ampapi.endpoints.resource.ResourceErrors;
import org.digijava.kernel.ampapi.endpoints.resource.ResourceType;
import org.digijava.kernel.validation.ConstraintViolation;
import org.digijava.kernel.validation.Validator;
import org.digijava.kernel.validators.activity.ValidatorMatchers;
import org.digijava.kernel.validators.ValidatorUtil;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Octavian Ciubotaru
 */
public class ResourceRequiredValidatorTest {

    private static APIField resourceField;

    @BeforeClass
    public static void setUp() {
        resourceField = ValidatorUtil.getMetaData(AmpResource.class);
    }

    @Test
    public void testNullResourceType() {
        AmpResource resource = new AmpResource();

        Set<ConstraintViolation> violations = getConstraintViolations(resource);

        assertThat(violations, emptyIterable());
    }

    @Test
    public void testMissingWebLink() {
        AmpResource resource = new AmpResource();
        resource.setResourceType(ResourceType.LINK);

        Set<ConstraintViolation> violations = getConstraintViolations(resource);

        assertThat(violations, contains(constraint("web_link")));
    }

    @Test
    public void testValidWebLink() {
        AmpResource resource = new AmpResource();
        resource.setResourceType(ResourceType.LINK);
        resource.setWebLink("https://abc.com/");

        Set<ConstraintViolation> violations = getConstraintViolations(resource);

        assertThat(violations, emptyIterable());
    }

    @Test
    public void testMissingFileName() {
        AmpResource resource = new AmpResource();
        resource.setResourceType(ResourceType.FILE);

        Set<ConstraintViolation> violations = getConstraintViolations(resource);

        assertThat(violations, contains(constraint("file_name")));
    }

    @Test
    public void testValidFileName() {
        AmpResource resource = new AmpResource();
        resource.setResourceType(ResourceType.FILE);
        resource.setFileName("file.pdf");

        Set<ConstraintViolation> violations = getConstraintViolations(resource);

        assertThat(violations, emptyIterable());
    }

    private Matcher<ConstraintViolation> constraint(String path) {
        return ValidatorMatchers.violationFor(ResourceRequiredValidator.class, path, anything(),
                ResourceErrors.FIELD_REQUIRED);
    }

    private Set<ConstraintViolation> getConstraintViolations(AmpResource resource) {
        Validator validator = new Validator();
        return validator.validate(resourceField, resource);
    }
}