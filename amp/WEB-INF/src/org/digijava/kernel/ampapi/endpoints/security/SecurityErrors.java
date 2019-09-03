/**
 * 
 */
package org.digijava.kernel.ampapi.endpoints.security;

import org.digijava.kernel.ampapi.endpoints.errors.ApiErrorMessage;

/**
 * Defines security specific errors
 * 
 * @author Nadejda Mandrescu
 */
public class SecurityErrors {
    
    /** Action exists, but it is not allowed, e.g. to edit an activity that current user has no edit rights for */
    public static final ApiErrorMessage NOT_ALLOWED = new ApiErrorMessage(4, "Not allowed");
    /** Invalid Request */
    public static final ApiErrorMessage INVALID_REQUEST = new ApiErrorMessage(7, "Invalid request");
    /** User Banned */
    public static final ApiErrorMessage USER_BANNED = new ApiErrorMessage(8, "User Banned");
    /** User is not part of any team */
    public static final ApiErrorMessage NO_TEAM = new ApiErrorMessage(9, "User is not part of any team");

    public static final ApiErrorMessage INVALID_TEAM = new ApiErrorMessage(10, "Invalid Team");

    public static final ApiErrorMessage INVALID_USER_PASSWORD = new ApiErrorMessage(11, "Username or password are invalid");

    public static final ApiErrorMessage NOT_AUTHENTICATED = new ApiErrorMessage(14, "Not authenticated");

}
