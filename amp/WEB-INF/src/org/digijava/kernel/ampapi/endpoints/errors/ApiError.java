package org.digijava.kernel.ampapi.endpoints.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;

import org.apache.commons.lang.StringUtils;
import org.dgfoundation.amp.algo.AlgoUtils;
import org.digijava.kernel.ampapi.endpoints.activity.InterchangeEndpoints;
import org.digijava.kernel.ampapi.endpoints.common.AmpConfiguration;
import org.digijava.kernel.ampapi.endpoints.common.EndpointUtils;
import org.digijava.kernel.ampapi.endpoints.contact.ContactEndpoint;
import org.digijava.kernel.ampapi.endpoints.currency.Currencies;
import org.digijava.kernel.ampapi.endpoints.dashboards.EndPoints;
import org.digijava.kernel.ampapi.endpoints.gis.GisEndPoints;
import org.digijava.kernel.ampapi.endpoints.indicator.IndicatorEndPoints;
import org.digijava.kernel.ampapi.endpoints.performance.PerformanceRulesEndpoint;
import org.digijava.kernel.ampapi.endpoints.reports.Reports;
import org.digijava.kernel.ampapi.endpoints.security.Security;
import org.digijava.kernel.ampapi.endpoints.settings.SettingsDefinitionsEndpoint;
import org.digijava.kernel.translator.TranslatorWorker;
import org.json.JSONObject;
import org.json.XML;

/**
 * Defines API Error Utility class for manipulating ApiErrorMessage objects
 * @author Viorel Chihai
 */

public class ApiError {

    public final static int GENERAL_ERROR_CODE = 0;
    public final static int GENERIC_HANDLED_ERROR_CODE = 0;
    public final static int GENERIC_UNHANDLED_ERROR_CODE = 1;

    public final static String API_ERROR_PATTERN = "%02d%02d";

    public final static String UNKOWN_ERROR = "Unknown Error";

    /**
     *  Stores the mapping between the component and it's Id (C).
     *  Component id 0 is reserved for all errors that are not tied to any component.
     */
    private static Map<String, Integer> componentIdClassMap;

    public static void configureComponentClassToIdMap() {
        Map<String, Integer> source = new HashMap<String, Integer>() {{
            put(InterchangeEndpoints.class.getName(), 1);
            put(Security.class.getName(), 2);
            put(Reports.class.getName(), 3);
            put(Currencies.class.getName(), 4);
            put(EndPoints.class.getName(), 5);
            put(IndicatorEndPoints.class.getName(), 6);
            put(GisEndPoints.class.getName(), 7);
            put(SettingsDefinitionsEndpoint.class.getName(), 8);
            put(AmpConfiguration.class.getName(), 9);
            put(ContactEndpoint.class.getName(), 10);
            put(PerformanceRulesEndpoint.class.getName(), 11);
        }};
        Set<Integer> usedIds = new TreeSet<>();
        // Component id 0 is reserved for all errors that are not tied to any component.
        usedIds.add(0);
        source.forEach((cn, id) -> {
            if (usedIds.contains(id)) {
                throw new RuntimeException("Class '" + cn + "' cannot be mapped to id=" + id + ". Id already in use.");
            }
            usedIds.add(id);
        });
        componentIdClassMap = Collections.unmodifiableMap(source);
    }

    private final static Set<String> COMPONENTS_WITH_NEW_ERROR_FORMAT = new HashSet<>(
            Collections.singletonList(InterchangeEndpoints.class.getName()));

    private static final ApiError FORMATTER = new ApiError(false);
    private static final ApiError FORMATTER_AND_STATUS_REPORTER = new ApiError(true);

    private boolean isUpdateResponseStatus;

    public ApiError(boolean isUpdateResponseStatus) {
        this.isUpdateResponseStatus = isUpdateResponseStatus;
    }

    /**
     * Returns an ApiErrorResponse object with a single error message
     * generic 0 error code with one error in the list.
     * @param errorMessage
     * @return the json of the error. E.g.: {0: [“Generic error 1”]}
     */
    public static ApiErrorResponse toError(String errorMessage) {
        processErrorResponseStatus();
        return format(errorMessage);
    };

    /**
     * Builds a ApiErrorResponse with all errors stored in this group.
     * @param errorsGroup
     * @return
     * @see {@link #toError(Collection)}
     */
    public static ApiErrorResponse toError(ApiEMGroup errorsGroup) {
        return toError(errorsGroup.getAllErrors());
    }

    /**
     * Returns an ApiErrorResponse object with list of error messages and updates the response status
     * list of error messages => generic 0 error code with the given list of errors
     * list of custom errors, where integer will be validated to be between 0 and 99
     * @param errorMessages a collection of objects of type String or ApiErrorMessage
     * @return the JSON object of the error. E.g.: {0: [“Generic error 1”, “Generic error 2”], 135: [“Forbidden fields have been configured: sector_id”], 123: [“Generic error 1”]}
     */
    public static ApiErrorResponse toError(Collection<?> errorMessages) {
        processErrorResponseStatus();
        return format(errorMessages);
    }
    
    /**
     * Returns an ApiErrorResponse object with a single error message. Generic 0 error code with one error in the list.
     * Updates the response status.
     *
     * @param apiErrorMessage ApiErrorMessage object
     * @return the json of the error. E.g.: {"0102": ["Generic error 1"]}
     */
    public static ApiErrorResponse toError(ApiErrorMessage apiErrorMessage) {
        processErrorResponseStatus();
        return format(apiErrorMessage);
    }
    
    public static ApiErrorResponse toError(ApiErrorMessage apiErrorMessage, Throwable e) {
        processErrorResponseStatus();
        return format(apiErrorMessage, e);
    }
    
    public static Map<String, Collection<Object>> formatNoWrap(Collection<?> messages) {
        return format(messages).getErrors();
    }

    public static ApiErrorResponse format(Collection<?> errorMessages) {
        Map<String, Collection<Object>> errors = new HashMap<>();

        if (errorMessages != null && errorMessages.size() > 0) {
            if (errorMessages.iterator().next() instanceof String) {
                String generalErrorCode = String.format(API_ERROR_PATTERN, GENERAL_ERROR_CODE, GENERIC_HANDLED_ERROR_CODE);
                errors.put(generalErrorCode, (Collection<Object>) errorMessages);
            } else if (errorMessages.iterator().next() instanceof ApiErrorMessage) {
                int componentId = getErrorComponentId();
                for(Object errorMessage : errorMessages) {
                    ApiErrorMessage apiError = (ApiErrorMessage) errorMessage;
                    String errorId = getErrorId(componentId, apiError.id);

                    if (errors.get(errorId) == null) {
                        errors.put(errorId, new ArrayList<>());
                    }

                    errors.get(errorId).add(getErrorText(apiError));
                }
            }
        }

        return new ApiErrorResponse(errors);
    };

    public static ApiErrorResponse format(ApiErrorMessage apiErrorMessage, Throwable e) {
        Map<String, Collection<Object>> errors = new HashMap<>();
        errors.put(getErrorId(getErrorComponentIdFromException(e), apiErrorMessage.id),
                Arrays.asList(getErrorText(apiErrorMessage)));

        return new ApiErrorResponse(errors);
    };

    private static ApiErrorResponse format(ApiErrorMessage apiErrorMessage) {
        Map<String, Collection<Object>> errors = new HashMap<>();
        errors.put(getErrorId(getErrorComponentId(), apiErrorMessage.id), Arrays.asList(getErrorText(apiErrorMessage)));
        
        return new ApiErrorResponse(errors);
    };
    
    private static ApiErrorResponse format(String errorMessage) {
        Map<String, Collection<Object>> errors = new HashMap<>();
        errors.put(String.format(API_ERROR_PATTERN, GENERAL_ERROR_CODE, GENERIC_HANDLED_ERROR_CODE),
                Arrays.asList(errorMessage));
        
        return new ApiErrorResponse(errors);
    }

    /**
     * Sets "Internal Server Error" marker if the marker was absent or 200 OK
     * Does not override custom Response marker status if one has been set
     * See AMP-20522 for details
     */
    private static void processErrorResponseStatus() {
        Integer responseMarker = EndpointUtils.getResponseStatusMarker();
        if (responseMarker == null || responseMarker == HttpServletResponse.SC_OK) {
            EndpointUtils.setResponseStatusMarker(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Builds full error code, that can be used for logging.
     * Please refer to {@link #toError(ApiErrorMessage)} and its overloaded alternatives
     * to generate a correct Amp API Error response.
     * @param error API Message error reference
     * @return full error code
     */
    public static String getErrorCode(ApiErrorMessage error) {
        return getErrorId(getErrorComponentId(), error.id);
    }

    /**
     * Returns the id of the ApiErrorMessage object. A lookup for the class code will be made on the stacktrace .
     * @param componentId component id
     * @param errorId error id
     * @return the id of the error
     */
    private static String getErrorId(int componentId, int errorId) {
        if (componentId != 0 && errorId < 100) {
            return String.format(API_ERROR_PATTERN, componentId, errorId);
        } else {
            return String.format(API_ERROR_PATTERN, GENERAL_ERROR_CODE, errorId);
        }
    }

    private static Integer getErrorComponentId() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return getErrorComponentIdFromStackTrace(stackTrace);
    }

    private static Integer getErrorComponentIdFromException(Throwable e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        return getErrorComponentIdFromStackTrace(stackTrace);
    }

    private static Integer getErrorComponentIdFromStackTrace(StackTraceElement[] stackTrace) {
        if (componentIdClassMap == null) {
            configureComponentClassToIdMap();
        }
        for (StackTraceElement st : stackTrace) {
            if (componentIdClassMap.containsKey(st.getClassName())) {
                return componentIdClassMap.get(st.getClassName());
            }
        }
        return GENERAL_ERROR_CODE;
    }

    /**
     * Determine if this call is being executed from a REST Endpoint / component that supports new error format.
     *
     * @return true if this component supports new error format
     */
    private static boolean isNewErrorFormat() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement st : stackTrace) {
            if (COMPONENTS_WITH_NEW_ERROR_FORMAT.contains(st.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert ApiErrorMessage to an error object. Some components use expanded errors while others use flattened.
     * See {@link #isNewErrorFormat()}.
     *
     * @param apiErrorMessage object
     * @see ApiErrorMessage
     * @return an object describing the error
     */
    private static Object getErrorText(ApiErrorMessage apiErrorMessage) {
        if (isNewErrorFormat()) {
            return getExpandedError(apiErrorMessage);
        } else {
            return getFlattenedError(apiErrorMessage);
        }
    }

    /**
     * Extract the expanded error message from ApiErrorMessage. This format is handy for post processing in UI layers.
     *
     * @param apiErrorMessage the error
     * @return expanded error message
     */
    private static Map<String, Set<String>> getExpandedError(ApiErrorMessage apiErrorMessage) {
        String errorText = TranslatorWorker.translateText(apiErrorMessage.description);
        if (!StringUtils.isBlank(apiErrorMessage.prefix)) {
            errorText += " " + TranslatorWorker.translateText(apiErrorMessage.prefix);
        }
    
        Map<String, Set<String>> error = new HashMap<>();
        error.put(errorText, apiErrorMessage.values);
        return error;
    }

    /**
     * Extract flattened error message from ApiErrorMessage.
     *
     * @param apiErrorMessage the error
     * @return the message represented by this error object
     */
    private static String getFlattenedError(ApiErrorMessage apiErrorMessage) {
        String errorText = "(" + TranslatorWorker.translateText(apiErrorMessage.description) + ")";
        if (!StringUtils.isBlank(apiErrorMessage.prefix)) {
            errorText += " " + TranslatorWorker.translateText(apiErrorMessage.prefix);
        }

        if (apiErrorMessage.values != null) {
            // no translation of the value, it must come translated if translation is needed for it
            errorText += " " + Joiner.on(", ").join(apiErrorMessage.values);
        }

        return errorText;
    }

    /**
     *
     * @param errorResponse
     * @return convert a error into a well-formed, element-normal XML string
     */
    public static String toXmlErrorString(ApiErrorResponse errorResponse) {
        Map<String, Collection<Object>> errorResponseMap = errorResponse.getErrors();
        List<Map<String, Object>> errors = new ArrayList<>();
    
        for (String key : errorResponseMap.keySet()) {
            Map<String, Object> err = new HashMap<>();
            Collection<Object> error = errorResponseMap.get(key);
            err.put("code", key);
            err.put("value", error);
            errors.add(err);
        }
    
        Map<String, List<Map<String, Object>>> errorsMap = new HashMap<>();
        errorsMap.put("error", errors);
        Map<String, Map<String, List<Map<String, Object>>>> responseErrorBean = new HashMap<>();
        responseErrorBean.put("errors", errorsMap);
    
        try {
            JSONObject o = new JSONObject(new ObjectMapper().writer().writeValueAsString(responseErrorBean));
            return XML.toString(o);
        } catch (Exception e) {
            throw AlgoUtils.translateException(e);
        }
    }
}
