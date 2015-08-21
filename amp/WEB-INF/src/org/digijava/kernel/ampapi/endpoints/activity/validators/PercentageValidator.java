/**
 * 
 */
package org.digijava.kernel.ampapi.endpoints.activity.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants;
import org.digijava.kernel.ampapi.endpoints.activity.ActivityErrors;
import org.digijava.kernel.ampapi.endpoints.activity.ActivityImporter;
import org.digijava.kernel.ampapi.endpoints.errors.ApiErrorMessage;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;

/**
 * Validates that the sum of percentages is equal to 100
 * 
 * @author acartaleanu
 */
public class PercentageValidator extends InputValidator {

	@Override
	public ApiErrorMessage getErrorMessage() {
		return ActivityErrors.FIELD_PERCENTAGE_SUM_BAD;
	}

	@Override
	public boolean isValid(ActivityImporter importer, Map<String, Object> newFieldParent, 
			Map<String, Object> oldFieldParent, JsonBean fieldDescription, String fieldPath) {
		boolean isValid = true;
		String fieldName = (String) fieldDescription.get(ActivityEPConstants.FIELD_NAME);
		String percentageField = fieldDescription.getString(ActivityEPConstants.PERCENTAGE_CONSTRAINT);
		if (StringUtils.isNotBlank(percentageField)) {
			// get Collection with values to be summed into percentages
			Collection<Map<String, Object>> fieldValue = (Collection<Map<String, Object>>) newFieldParent.get(fieldName);
			
			//Set that will hold the values that have to be summed up
			Set<Object> idValuesSet = new HashSet<Object>();
			if (fieldValue != null && fieldValue.size() > 1) {
				double result = calculatePercentagesSum(fieldValue, percentageField);
				//if the result isn't equal to 100, it's invalid
				if (Math.abs(result - 100.0) > 0.0001 )
					isValid = false;
			}
		}
		return isValid;
	}

	
	private double calculatePercentagesSum(Collection<Map<String, Object>> fieldValues, String fieldPathToPercentage) {
		double result = 0.0;
		for (Map<String, Object> child : fieldValues) {
			if (child.get(fieldPathToPercentage) != null) {
				Number val = (Number) child.get(fieldPathToPercentage);
				result += val.doubleValue();
			}
		}
		return result;
	}
}