/**
 *
 */
package org.digijava.kernel.ampapi.endpoints.activity;

import org.digijava.kernel.ampapi.endpoints.errors.ApiErrorMessage;

/**
 * Defines errors used by Activity API. Please define concrete errors, normally is an invalid request input. <br>
 * Anything that is out of control, should be re-thrown and it will be handled by API Exception Filter.
 *
 * @author Nadejda Mandrescu
 */
public class ActivityErrors {
    
    public static final ApiErrorMessage UNIQUE_ACTIVITY_TITLE = new ApiErrorMessage(2, 0,
            "Activity title should be unique");
    
    public static final ApiErrorMessage SAVE_AS_DRAFT_FM_DISABLED = new ApiErrorMessage(2, 1,
            "Activity cannot be saved as draft, \"save as draft\" is disabled in FM");
    
    public static final ApiErrorMessage UPDATE_ID_MISMATCH = new ApiErrorMessage(2, 2, "Request project ids mismatch");
    
    public static final ApiErrorMessage UPDATE_ID_IS_OLD = new ApiErrorMessage(2, 3,
            "Update request for older activity id. Please provide the latest");
    
    public static final ApiErrorMessage ACTIVITY_IS_BEING_EDITED =
            new ApiErrorMessage(2, 4, "Current activity is being edited by:");
    
    public static final ApiErrorMessage ACTIVITY_NOT_LOADED = new ApiErrorMessage(2, 5, "Cannot load the activity");
    
    public static final ApiErrorMessage ACTIVITY_IS_STALE = new ApiErrorMessage(2, 6, "The activity is stale");
    
    public static final ApiErrorMessage FUNDING_PLEDGE_ORG_GROUP_MISMATCH = new ApiErrorMessage(2, 7,
            "The organization group of the pledge doesn't match with funding donor organization group");
    
    public static final ApiErrorMessage ACTIVITY_NOT_FOUND = new ApiErrorMessage(2, 8,
            "Activity not found");
    
}
