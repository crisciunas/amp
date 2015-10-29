/**
 * 
 */
package org.digijava.kernel.ampapi.endpoints.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

import org.apache.log4j.Logger;
import org.dgfoundation.amp.newreports.GeneratedReport;
import org.dgfoundation.amp.newreports.ReportAreaImpl;
import org.dgfoundation.amp.newreports.ReportEnvironment;
import org.dgfoundation.amp.newreports.ReportSpecification;
import org.dgfoundation.amp.reports.mondrian.MondrianReportGenerator;
import org.dgfoundation.amp.visibility.data.ColumnsVisibility;
import org.digijava.kernel.ampapi.endpoints.util.ApiMethod;
import org.digijava.kernel.ampapi.endpoints.util.AvailableMethod;
import org.digijava.kernel.ampapi.endpoints.util.GisUtil;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;
import org.digijava.kernel.ampapi.exception.AmpApiException;
import org.digijava.kernel.ampapi.postgis.util.QueryUtil;
import org.digijava.kernel.exception.DgException;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.kernel.request.TLSUtils;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.dbentity.AmpApplicationSettings;
import org.digijava.module.aim.dbentity.AmpCurrency;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.FormatHelper;
import org.digijava.module.aim.helper.GlobalSettingsConstants;
import org.digijava.module.aim.helper.TeamMember;
import org.digijava.module.aim.util.CurrencyUtil;
import org.digijava.module.aim.util.DbUtil;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.esrigis.dbentity.AmpApiState;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

/**
 * Common utility methods for all endpoints
 * @author Nadejda Mandrescu
 */
public class EndpointUtils {
	protected static final Logger logger = Logger.getLogger(EndpointUtils.class);

	/**
	 * @return current user application settings 
	 */
	public static AmpApplicationSettings getAppSettings() {
		HttpServletRequest request = TLSUtils.getRequest();
		if (request != null) {
			TeamMember tm = (TeamMember) request.getSession().getAttribute(Constants.CURRENT_MEMBER);
			if (tm != null && tm.getTeamId() != null) {
				return DbUtil.getTeamAppSettings(tm.getTeamId());
			}
		}
		return null;
	}
	
	/**
	 * @return default currency code for public or logged in user
	 */
	public static String getDefaultCurrencyCode() {
		return getDefaultCurrency(null).getCurrencyCode();
	}

	/**
	 * @return default currency id for public or logged in user
	 */
	public static Long getDefaultCurrencyId(AmpApplicationSettings appSettings) {
		return getDefaultCurrency(appSettings).getAmpCurrencyId();
	}

	/**
	 * @return default currency entity for public or logged in user
	 */
	public static AmpCurrency getDefaultCurrency(AmpApplicationSettings appSettings) {
		if (appSettings == null)
			appSettings = getAppSettings();
		if(appSettings != null && appSettings.getCurrency() != null)
			return appSettings.getCurrency();
		return CurrencyUtil.getDefaultCurrency();

	}

	/**
	 * @return default calendar id for public or logged in user
	 */
	public static String getDefaultCalendarId() {
		AmpApplicationSettings appSettings = getAppSettings();
		if(appSettings != null)
			return String.valueOf(appSettings.getFiscalCalendar().getIdentifier());
		return String.valueOf(DbUtil.getBaseFiscalCalendar());
	}
	
	/**
	 * @return report default START year selection
	 */
	public static String getDefaultReportStartYear() {
		AmpApplicationSettings appSettings = getAppSettings();
		if(appSettings != null && appSettings.getReportStartYear()!=null && appSettings.getReportStartYear() > 0)
			return String.valueOf(appSettings.getReportStartYear());
		return FeaturesUtil.getGlobalSettingValue(Constants.GlobalSettings.START_YEAR_DEFAULT_VALUE);
	} 
	
	/**
	 * @return report default END year selection
	 */
	public static String getDefaultReportEndYear() {
		AmpApplicationSettings appSettings = getAppSettings();
		if(appSettings != null &&appSettings.getReportEndYear()!=null&& appSettings.getReportEndYear() > 0)
			return String.valueOf(appSettings.getReportEndYear());
		return FeaturesUtil.getGlobalSettingValue(Constants.GlobalSettings.END_YEAR_DEFAULT_VALUE);
	}
	
	/**
	 * Generates a report based on a given specification
	 * 
	 * @param spec - report specification
	 * @return GeneratedReport that stores all report info and report output
	 */
	public static GeneratedReport runReport(ReportSpecification spec) {
		return runReport(spec, ReportAreaImpl.class);
	}
	
	/**
	 * Generates a report based on a given specification and wraps the output
	 * into the specified class
	 * 
	 * @param spec report specification
	 * @param clazz any class that extends {@link ReportAreaImpl}
	 * @return GeneratedReport that stores all report info and report output
	 */
	public static GeneratedReport runReport(ReportSpecification spec, Class<? extends ReportAreaImpl> clazz) {
		MondrianReportGenerator generator = new MondrianReportGenerator(clazz, 
				ReportEnvironment.buildFor(TLSUtils.getRequest()));
		GeneratedReport report = null;
		try {
			report = generator.executeReport(spec);
		} catch (Exception e) {
			logger.error("error running report", e);
		}
		return report;
	}
	
	/**
	 * Retrieves the value associated to the specified key if available 
	 * or returns the default
	 * 
	 * @param formParams
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static <T> T getSingleValue(JsonBean formParams, String key, T defaultValue) {
		if (formParams.get(key) != null)
			return (T) formParams.get(key);
		return defaultValue;
	}
	
	
	public static List<JsonBean> getApiStateList(String type) {
		List<JsonBean> maps = new ArrayList<JsonBean>();

		try {
			List<AmpApiState> l = QueryUtil.getMapList(type);
			for (AmpApiState map : l) {
				maps.add(getJsonBeanFromApiState(map, Boolean.FALSE));
			}
			return maps;
		} catch (DgException e) {
			logger.error("Cannot get maps list", e);
			throw new WebApplicationException(e);
		}
	}
	private static JsonBean getJsonBeanFromApiState(AmpApiState map, Boolean getBlob) {
		JsonBean jMap = new JsonBean();

		jMap.set("id", map.getId());
		jMap.set("title", map.getTitle());
		jMap.set("description", map.getDescription());
		if (getBlob) {
			jMap.set("stateBlob", map.getStateBlob());
		}
		jMap.set("created", GisUtil.formatDate(map.getCreatedDate()));
		if(map.getLastAccesedDate()!=null){
			jMap.set("lastAccess", GisUtil.formatDate(map.getLastAccesedDate()));
		}
		return jMap;
	}	
	
	public static AmpApiState getSavedMap(Long mapId) throws AmpApiException {
		Session s = PersistenceManager.getSession();
		AmpApiState map = (AmpApiState) s.load(AmpApiState.class, mapId);
		map.setLastAccesedDate(new Date());
		s.merge(map);
		return map;
	}

	public static JsonBean getApiState(Long mapId) {
		JsonBean jMap = null;
		try {
			AmpApiState map = getSavedMap(mapId);
			jMap = getJsonBeanFromApiState(map, Boolean.TRUE);
		} catch (ObjectNotFoundException e) {
			jMap = new JsonBean();
		} catch (AmpApiException e) {
			logger.error("cannot get map by id " + mapId, e);
			throw new WebApplicationException(e);
		}
		return jMap;
	}	
	
	public static JsonBean saveApiState(final JsonBean pMap,String type) {
		Date creationDate = new Date();
		JsonBean mapId = new JsonBean();

		AmpApiState map = new AmpApiState();
		map.setTitle(pMap.getString("title"));
		map.setDescription(pMap.getString("description"));
		map.setStateBlob(pMap.getString("stateBlob"));
		map.setCreatedDate(creationDate);
		map.setUpdatedDate(creationDate);
		map.setLastAccesedDate(creationDate);
		map.setType(type);
		try {
			Session s = PersistenceManager.getSession();
			s.save(map);
			s.flush();
			mapId.set("mapId", map.getId());
		} catch (Exception e) {
			logger.error("Cannot Save map", e);
			throw new WebApplicationException(e);
		}
		return mapId;
	}
	
	public static List<AvailableMethod> getAvailableMethods(String className) {
		return getAvailableMethods(className, false);
	}
	
	public static String getEndpointMethod(Method method) {
		if (method.getAnnotation(javax.ws.rs.POST.class) != null) {
			return "POST";
		};

		if (method.getAnnotation(javax.ws.rs.GET.class) != null) {
			return "GET";
		}
		
		if (method.getAnnotation(javax.ws.rs.PUT.class) != null) {
			return "PUT";
		}
		
		if (method.getAnnotation(javax.ws.rs.DELETE.class) != null) {
			return "DELETE";
		}
		
		throw new RuntimeException("method " + method.getName() + " of class " + method.getDeclaringClass().getName() + " does not have a POST/GET/PUT/DELETE annotation!");
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 */
	public static List<AvailableMethod> getAvailableMethods(String className, boolean includeColumn){
		List<AvailableMethod> availableFilters = new ArrayList<AvailableMethod>(); 
		try {
			Set<String> visibleColumns = ColumnsVisibility.getVisibleColumns();
			Class<?> c = Class.forName(className);
			
			javax.ws.rs.Path p = c.getAnnotation(javax.ws.rs.Path.class);
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				ApiMethod apiAnnotation = method.getAnnotation(ApiMethod.class);
				if (apiAnnotation != null) {
					final String[] columns = apiAnnotation.columns();
					
					boolean isVisibleColumn = false; 
					for(String column:columns){ 
						if (EPConstants.NA.equals(column) || visibleColumns.contains(column)) {
							isVisibleColumn=true;
							break;
						}
					}
					if (isVisibleColumn) {
						//then we have to add it to the filters list
						javax.ws.rs.Path methodPath = method.getAnnotation(javax.ws.rs.Path.class);
						AvailableMethod filter = new AvailableMethod();
						//the name should be translatable
						if (apiAnnotation.name() != null && !apiAnnotation.name().equals("")){
							filter.setName(TranslatorWorker.translateText(apiAnnotation.name()));
						}
						
						String endpoint = "/rest/" + p.value();
						
						if (methodPath != null){
							endpoint += methodPath.value();
						}
						filter.setEndpoint(endpoint);
						filter.setUi(apiAnnotation.ui());
						filter.setId(apiAnnotation.id());
						filter.setTab(apiAnnotation.tab());
						filter.setFilterType(apiAnnotation.filterType());
						if (includeColumn) {
							if (apiAnnotation.columns().length > 1) { //this should not be empty since it has a default value
								//If we have more than one column we column to NA (as it was before)
								filter.setColumn(EPConstants.NA);
							}else{
								//if we have only one we set the first element 
								filter.setColumn(apiAnnotation.columns()[0]);
							}
							
							filter.setColumns(apiAnnotation.columns());
						}
						//we check the method exposed
						filter.setMethod(getEndpointMethod(method));
						//special check if the method should be added
						boolean shouldCheck = false;
						boolean result = false;
						if (apiAnnotation.visibilityCheck().length() > 0) {
							shouldCheck = true;
							try {
								Method shouldAddApiMethod = c.getMethod(apiAnnotation.visibilityCheck(), null);
								shouldAddApiMethod.setAccessible(true);
								 result =(Boolean) shouldAddApiMethod.invoke(c.newInstance(), null);
							} catch (NoSuchMethodException | SecurityException | IllegalAccessException
									| IllegalArgumentException | InvocationTargetException e) {
							} catch (InstantiationException e) {
								logger.error(e);
							}
						}
						if (shouldCheck) {
							if (result) {
								availableFilters.add(filter);
								shouldCheck = false;
							}
						} else {
							availableFilters.add(filter);
						}
					}
				}
			}
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException("cannot retrieve methods list", e);
		}
		return availableFilters;
	}
	
	
	/**
	 * Used to set locale to US in order to avoid number formatting issues
	 * Now issues don't seem to appear, whichever the locale is, so picks the format that's in the settings
	 * @return
	 */
	public static DecimalFormat getDecimalFormat(){
		DecimalFormat defaultFormat = FormatHelper.getDecimalFormat();
		return defaultFormat;
	}

    /**
     * Sets the request attribute marker to be used by ApiResponseFilter
     * to set the response status before sending to client
     *
     * @param status HTTP response status
     */
    public static void setResponseStatusMarker(Integer status) {
        TLSUtils.getRequest().setAttribute(EPConstants.RESPONSE_STATUS, status);
    }

    /**
     * Returns HTTP Response status attribute from the request or null if none has been set
     */
    public static Integer getResponseStatusMarker() {
        return (Integer) TLSUtils.getRequest().getAttribute(EPConstants.RESPONSE_STATUS);
    }

    /**
     * Returns Map of markers from request to be set as response headers in ApiResponseFilter
     * before sending to client
     * Returns null if headers marker has not been set into request
     *
     * Please not that this is readonly method and map's modification is not possible through it
     * All modifications should be done via <code>addResponseHeaderMarker<code/> method
     */
    public static Map<String, String> getResponseHeaderMarkers() {
        Map<String, String> headerMarkers = (Map<String, String>)TLSUtils.getRequest()
                .getAttribute(EPConstants.RESPONSE_HEADERS_MAP);
        if (headerMarkers != null) {
            return Collections.unmodifiableMap(headerMarkers);
        } else {
            return null;
        }
    }

    /**
     * Adds the request attribute marker to be used by ApiResponseFilter
     * to add the response header before sending to client
     * @param headerName
     * @param headerValue
     */
    public static void addResponseHeaderMarker(String headerName, String headerValue) {
        Map<String, String> responseHeadersMap =
                (Map<String, String>)TLSUtils.getRequest().getAttribute(EPConstants.RESPONSE_HEADERS_MAP);
        if (responseHeadersMap == null) {
            responseHeadersMap = new HashMap<String, String>();
            TLSUtils.getRequest().setAttribute(EPConstants.RESPONSE_HEADERS_MAP, responseHeadersMap);
        }
        responseHeadersMap.put(headerName, headerValue);
    }

    /**
     * Cleans up all markers in case the request will be further processed
     */
    public static void cleanUpResponseMarkers() {
        TLSUtils.getRequest().removeAttribute(EPConstants.RESPONSE_STATUS);
        TLSUtils.getRequest().removeAttribute(EPConstants.RESPONSE_HEADERS_MAP);
    }
}
