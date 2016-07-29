package org.digijava.kernel.ampapi.endpoints.gis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.node.POJONode;
import org.codehaus.jackson.node.TextNode;
import org.dgfoundation.amp.newreports.AmountsUnits;
import org.digijava.kernel.ampapi.endpoints.common.EndpointUtils;
import org.digijava.kernel.ampapi.endpoints.common.TranslationUtil;
import org.digijava.kernel.ampapi.endpoints.dto.gis.IndicatorLayers;
import org.digijava.kernel.ampapi.endpoints.gis.services.ActivityLocationExporter;
import org.digijava.kernel.ampapi.endpoints.gis.services.ActivityService;
import org.digijava.kernel.ampapi.endpoints.gis.services.ActivityStructuresExporter;
import org.digijava.kernel.ampapi.endpoints.gis.services.GapAnalysis;
import org.digijava.kernel.ampapi.endpoints.gis.services.LocationService;
import org.digijava.kernel.ampapi.endpoints.indicator.IndicatorEPConstants;
import org.digijava.kernel.ampapi.endpoints.indicator.IndicatorUtils;
import org.digijava.kernel.ampapi.endpoints.reports.ReportsUtil;
import org.digijava.kernel.ampapi.endpoints.util.ApiMethod;
import org.digijava.kernel.ampapi.endpoints.util.AvailableMethod;
import org.digijava.kernel.ampapi.endpoints.util.JsonBean;
import org.digijava.kernel.ampapi.exception.AmpApiException;
import org.digijava.kernel.ampapi.helpers.geojson.FeatureCollectionGeoJSON;
import org.digijava.kernel.ampapi.helpers.geojson.FeatureGeoJSON;
import org.digijava.kernel.ampapi.helpers.geojson.PointGeoJSON;
import org.digijava.kernel.ampapi.helpers.geojson.objects.ClusteredPoints;
import org.digijava.kernel.ampapi.postgis.util.QueryUtil;
import org.digijava.module.aim.dbentity.AmpActivityVersion;
import org.digijava.module.aim.dbentity.AmpIndicatorColor;
import org.digijava.module.aim.dbentity.AmpIndicatorLayer;
import org.digijava.module.aim.dbentity.AmpStructure;
import org.digijava.module.aim.helper.FormatHelper;
import org.digijava.module.aim.helper.GlobalSettingsConstants;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.esrigis.dbentity.AmpApiState;
import org.digijava.module.esrigis.dbentity.AmpMapConfig;
import org.digijava.module.esrigis.helpers.DbHelper;
import org.digijava.module.esrigis.helpers.MapConstants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Class that holds entrypoing for GIS api methods
 * 
 * @author ddimunzio@developmentgateway.org jdeanquin@developmentgateway.org
 * 
 */
@Path("gis")
public class GisEndPoints {
	private static final Logger logger = Logger.getLogger(GisEndPoints.class);

	@Context
	private HttpServletRequest httpRequest;
	private String BOUNDARY_PATH = "src" + System.getProperty("file.separator") + "main"
			+ System.getProperty("file.separator") + "resources" + System.getProperty("file.separator") + "gis"
			+ System.getProperty("file.separator") + "boundaries" 
			+ System.getProperty("file.separator");

		
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<AvailableMethod> getAvailableFilters() {
		return EndpointUtils.getAvailableMethods(GisEndPoints.class.getName());
	}	

	/**
	 * Returns Aggregate ADM info by ADM Level
	 * 
	 * @param filter
	 *            adminLevel to filter, the json should look like {
	 *            "FiltersParams":
	 *            {"params":[{"filterName":"adminLevel","filterValue"
	 *            :["Region"]}] } }
	 * 
	 *            Available regions
	 * @return
	 * @throws AmpApiException 
	 */
	@POST
	@Path("/cluster")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "ClusterPointsByAdmin")
	public final FeatureCollectionGeoJSON getClusteredPointsByAdm(
			final JsonBean config) throws AmpApiException {

		List<ClusteredPoints> c = LocationService.getClusteredPoints(config);
		FeatureCollectionGeoJSON result = new FeatureCollectionGeoJSON();
		for (ClusteredPoints clusteredPoints : c) {
		    if (!clusteredPoints.getLon().equalsIgnoreCase("") && !clusteredPoints.getLat().equalsIgnoreCase("")){ 
		    result.features.add(getPoint(new Double(clusteredPoints.getLon()),
					new Double(clusteredPoints.getLat()),
					clusteredPoints.getActivityids(),
					clusteredPoints.getAdmin()));
		    }
		}

		return result;
	}

	/**
	 * Returns Aggregate ADM info by ADM Level
	 * 
	 * @param filter
	 *            adminLevel to filter, the json should look like {
	 *            "FiltersParams":
	 *            {"params":[{"filterName":"adminLevel","filterValue"
	 *            :["Region"]}] } }
	 * 
	 *            Available regions
	 * @return
	 * @throws AmpApiException 
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/structures")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "Structures")
	public final FeatureCollectionGeoJSON getProjectSites(final JsonBean config,@QueryParam("startFrom") Integer startFrom,
			@QueryParam("size")Integer size) throws AmpApiException {
		FeatureCollectionGeoJSON f = new FeatureCollectionGeoJSON();
		List<AmpStructure> al = LocationService.getStructures( config);
		int start = 0;
		int end = al.size() -1;
		if (startFrom!=null && size!=null && startFrom < al.size()) {
			start = startFrom.intValue();
			if (al.size()>(startFrom + size)) {
				end = startFrom + size;
			}
		}
  		
      for (;start <= end;start++) {
    	AmpStructure structure = al.get(start);
		  try {
				FeatureGeoJSON fgj = new FeatureGeoJSON();
				PointGeoJSON pg = new PointGeoJSON();
				pg.coordinates
						.add(Double.parseDouble(structure.getLongitude() == null ? "0" : structure.getLongitude()));
				pg.coordinates.add(Double.parseDouble(structure.getLatitude() == null ? "0" : structure.getLatitude()));
				fgj.id = structure.getAmpStructureId().toString();
				fgj.properties.put("title", new TextNode(structure.getTitle()));
				if (structure.getDescription() != null && !structure.getDescription().trim().equals("")) {
					fgj.properties.put("description", new TextNode(structure.getDescription()));
				}
				Set<AmpActivityVersion> av = structure.getActivities();
				List<Long> actIds = new ArrayList<Long>();

				for (AmpActivityVersion ampActivity : av) {
					actIds.add(ampActivity.getAmpActivityId());
				}

				fgj.properties.put("activity", new POJONode(actIds));
				fgj.geometry = pg;

				f.features.add(fgj);
			} catch (NumberFormatException e) {
				logger.warn("Couldn't get parse latitude/longitude for structure with latitude: "
						+ structure.getLatitude() + " longitude: " + structure.getLongitude() + " and title: "
						+ structure.getTitle());
			}
		}
		return f;
	}

	@POST
	@Path("/saved-maps")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "SaveMap")
	public JsonBean savedMaps(final JsonBean pMap) {
		return EndpointUtils.saveApiState(pMap,"G");
	}



	@GET
	@Path("/saved-maps/{mapId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "MapById")
	public JsonBean savedMaps(@PathParam("mapId") Long mapId) {
		return EndpointUtils.getApiState(mapId);

	}




	@GET
	@Path("/saved-maps")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "MapList")
	public List<JsonBean> savedMaps() {
		String type="G";
		return EndpointUtils.getApiStateList(type);
	}



	@GET
	@Path("/indicator-layers")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "IndicatorLayers")
	public List<IndicatorLayers> getIndicatorLayers() {
		List<IndicatorLayers> indicatorLayers = new ArrayList<IndicatorLayers>();
		List<AmpMapConfig> mapsConfigs = DbHelper.getMaps();
		for (AmpMapConfig ampMapConfig : mapsConfigs) {
			IndicatorLayers i = new IndicatorLayers();
			i.setId(ampMapConfig.getId());
			i.setTitle(ampMapConfig.getConfigName());
			i.setLink(ampMapConfig.getMapUrl());
			String type = MapConstants.mapTypeNames.get(ampMapConfig
					.getMapType());
			i.setType(type);
			indicatorLayers.add(i);
		}
		return indicatorLayers;
	}

	/**
	* Config sample <br/>
	* {  												<br/>
    *  "columnFilters":{ 								<br/>  
    *   "National Planning Objectives Level 1 Id":[ 	<br/>  
    *     1,2,3,4 										<br/>
    *   ], 												<br/>
    *  "Primary Sector Sub-Sector Id":[ 				<br/>  
    *     11,22,32,43 									<br/>
    *   ] 												<br/>
    * }, 												<br/>
    * "otherFilters":{  								<br/>
    *   "date":{  										<br/>
    *      "start":"1967-01-01",						<br/>
    *      "end":"2015-12-31"							<br/>
    *  },												<br/>
    *  "keyWord":"some activity"						<br/>
    * },												<br/>
    * “settings” :  {									<br/>
    * 		"0" : [“Actual Commitments”, “Actual Disbursements”],	<br/>
    * 		"1" : “USD”,								<br/>
    * 		"2" : “123”									<br/>
    * 	}												<br/>
    * }													<br/>
	*/
	@POST
	@Path("/activities")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "ActivitiesNewLists")
	public JsonBean getActivitiesNew(JsonBean config, @QueryParam("start") Integer page,@QueryParam("size") Integer pageSize) {
		logger.error(String.format("Requesting %s pagesize from %s page", pageSize, page));
		try {
			Integer reqNumber = (pageSize == null) || (page == null) ? null : pageSize * page; 
			return ActivityService.getActivities(config, null, reqNumber, pageSize);
		}catch(AmpApiException ex){
			throw new WebApplicationException(ex);
		}	
	}

	/**
	 * return activity by id in the format /activities/12,15,16
	 * @param config {@link #getActivitiesNew  Json config}
	 * @param activityIds comma separated list of ids
	 * @return
	 * @see #getActivitiesNew
	 */
	@POST
	@Path("/activities/{activityId}") //once its done remove the New
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "ActivitiesById")
	public JsonBean getActivities(JsonBean config, @PathParam("activityId") PathSegment activityIds) {
		try {
			return ActivityService.getActivities(config,
					Arrays.asList(activityIds.getPath().split("\\s*,\\s*")),
					null, null);
		} catch (AmpApiException ex) {
			throw new WebApplicationException(ex); 
		}
	}
	
	
	private FeatureGeoJSON getPoint(Double lat, Double lon,
			List<Long> activityid, String adm) {
		FeatureGeoJSON fgj = new FeatureGeoJSON();
		PointGeoJSON pg = new PointGeoJSON();
		pg.coordinates.add(lat);
		pg.coordinates.add(lon);

		fgj.properties.put("activityid", new POJONode(activityid));
		fgj.properties.put("admName", new TextNode(adm));

		fgj.geometry = pg;
		return fgj;
	}


	
	@POST
	@Path("/locationstotals/{admlevel}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "locationstotals")	
	public JsonBean getAdminLevelsTotals(JsonBean filters, @PathParam ("admlevel") String admlevel){
		LocationService ls = new LocationService();
		// this Service was resetting the amount units so far (used by this EP only), now changed its interface to allow other "users" to not reset it
		return ls.getTotals(admlevel, filters, AmountsUnits.AMOUNTS_OPTION_UNITS);
	}
	
	
	@GET
	@Path("/indicators")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "IndicatorsList")
	public List<JsonBean> getIndicators(@QueryParam("admLevel") String admLevel) {
		List<AmpIndicatorLayer> indicators;
		if (admLevel !=null) {
			indicators = QueryUtil.getIndicatorByCategoryValue(admLevel);
			return generateIndicatorJson(indicators, false);
		}
		else {
			indicators = QueryUtil.getIndicatorLayers();
			return generateIndicatorJson(indicators, true);

		}
	}
	
	// TODO: remove this one once GIS migrated to POST method
	@GET
	@Path("/indicators/{indicatorId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "IndicatorById")	
	public JsonBean getIndicatorsById(@PathParam ("indicatorId") Long indicatorId){
	    return getIndicatorsById(new JsonBean(), indicatorId, false);
	    //return getIndicatorsById(new JsonBean(), indicatorId, true);
	}
	
	@POST
	@Path("/indicators/{indicatorId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "IndicatorById")
	public JsonBean getIndicatorsById(JsonBean input, @PathParam ("indicatorId") Long indicatorId, 
	        @QueryParam("gapAnalysis") @DefaultValue("false") Boolean isGapAnalysis) {
	    return IndicatorUtils.getIndicatorsAndLocationValues(indicatorId, input, isGapAnalysis);
	}
	
	private List<JsonBean> generateIndicatorJson (List<AmpIndicatorLayer> indicators,boolean includeAdmLevel) {
		List<JsonBean> indicatorsJson = new ArrayList<JsonBean>();
		GapAnalysis gapAnalysis = new GapAnalysis();

        for (AmpIndicatorLayer indicator : indicators) {
			JsonBean json = new JsonBean();
            json.set(IndicatorEPConstants.ID, indicator.getId());
            json.set(IndicatorEPConstants.NAME, TranslationUtil.getTranslatableFieldValue(IndicatorEPConstants.NAME, indicator.getName(), indicator.getId()));
            json.set(IndicatorEPConstants.DESCRIPTION, TranslationUtil.getTranslatableFieldValue(IndicatorEPConstants.DESCRIPTION, indicator.getDescription(), indicator.getId()));
            json.set(IndicatorEPConstants.UNIT, TranslationUtil.getTranslatableFieldValue(IndicatorEPConstants.UNIT, indicator.getUnit(), indicator.getId()));
			json.set(IndicatorEPConstants.ID, indicator.getId());
			if (includeAdmLevel) {
				json.set(IndicatorEPConstants.ADM_LEVEL_ID, indicator.getAdmLevel().getLabel());
			}
            json.set(IndicatorEPConstants.NUMBER_OF_CLASSES, indicator.getNumberOfClasses());
            json.set(IndicatorEPConstants.ACCESS_TYPE_ID, indicator.getAccessType().getValue());
            json.set(IndicatorEPConstants.INDICATOR_TYPE_ID, indicator.getIndicatorType() == null ? null : indicator.getIndicatorType().getId());
            json.set(IndicatorEPConstants.CAN_DO_GAP_ANALYSIS, gapAnalysis.canDoGapAnalysis(indicator));
            
            json.set(IndicatorEPConstants.CREATED_ON, FormatHelper.formatDate(indicator.getCreatedOn()));
            json.set(IndicatorEPConstants.UPDATED_ON, FormatHelper.formatDate(indicator.getUpdatedOn()));

            if (indicator.getCreatedBy() != null) {
                json.set(IndicatorEPConstants.CREATE_BY, indicator.getCreatedBy().getUser().getEmail());
            }
			List<JsonBean> colors = new ArrayList<JsonBean>();
			List<AmpIndicatorColor> colorList = new ArrayList<AmpIndicatorColor>(indicator.getColorRamp());
			Collections.sort(colorList, new Comparator<AmpIndicatorColor>() {
				@Override
				public int compare(AmpIndicatorColor o1, AmpIndicatorColor o2) {
					return o1.getPayload().compareTo(o2.getPayload());
				}
			});
			for (AmpIndicatorColor color : colorList) {
				JsonBean colorJson = new JsonBean();
				colorJson.set("color", color.getColor());
				colorJson.set("order", color.getPayload());
				colors.add(colorJson);
			}
			json.set("colorRamp", colors);
			indicatorsJson.add(json);
		}
		return indicatorsJson;
	}
	
	/**
	 * Export map id from current filters
	 * 
	 * @param webResponse
	 * @param mapId
	 * @param exportType type 1 to export activity locations
	 *                   type 2 to export activity Structures
	 * @return
	 * @throws AmpApiException 
	 */
	@GET
	@Path("/export-map/")
	@Produces("application/vnd.ms-excel")
	@ApiMethod(ui = false, id = "MapExport")
	 public StreamingOutput getExportMap(@Context HttpServletResponse webResponse,@QueryParam("mapId") Long mapId,@DefaultValue("1") @QueryParam("exportType") Long exportType) throws AmpApiException
    {
		final HSSFWorkbook wb;
		String name="";
		JsonBean filter=null;
		if(mapId!=null){
			AmpApiState map = EndpointUtils.getSavedMap(mapId);
			filter = JsonBean.getJsonBeanFromString(map.getStateBlob());
		}
		//since it comes from a saved map and it has another structure we will use the linkedhash map thats inside
		//the Json bean to filter
		LinkedHashMap<String, Object> filters=(LinkedHashMap<String, Object>)filter.get("filters");
		if(exportType==1){
			name="map-export-project-sites.xls";
			wb = new ActivityStructuresExporter(filters).export(name);
		}else{
			name="map-export-administrative-Locations.xls";
			wb = new ActivityLocationExporter(filters).export(name);
		}
		webResponse.setHeader("Content-Disposition","attachment; filename=" + name);

        StreamingOutput streamOutput = new StreamingOutput(){
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                	wb.write(output);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            }
        };
        return streamOutput;
      }
	

	@GET
	@Path("/clusters")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "ClusterLevels")
	public List<JsonBean> getClusterLevels() {
 		List<AmpCategoryValue> values = QueryUtil.getClusterLevels();
 		List<JsonBean> levelsJson = new ArrayList<JsonBean>();
 		JSONArray boundaries = getBoundaries();
 		for (AmpCategoryValue value : values) {
			if(hasMapBounderies(value, boundaries)) {
				JsonBean json = new JsonBean();
				json.set("id", value.getId());
				json.set("title", value.getLabel());
				json.set("adminLevel", "adm-" + value.getIndex());
				levelsJson.add(json);
			}
 		}
 		return levelsJson;
 	}
 	
	private boolean hasMapBounderies(AmpCategoryValue value, JSONArray boundaries) {
		for (Object object : boundaries) {
			if (object instanceof JSONObject && ("adm-"+value.getIndex()).equals(((JSONObject) object).get("id"))) {
				return true;
			}			
		}
		return false;
	}
	
	/**
	 * Return the last updated activities
	 * 
	 * @param limit
	 *             the number of activities to include
	 * @param columns
	 *            the name of extra columns to include on the report. The
	 *            report already includes: project title, date of update and
	 *            donor names
	 * @return JSONObject, with the last updated activities
	 */
	@POST
	@Path("/last-updated")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "LastUpdatedActivities")
	public JSONObject getLastUpdated(@DefaultValue("10") @QueryParam("limit") Integer limit,
			@QueryParam("columns") String columns, JsonBean config) {
		List<String> extraColumns = new ArrayList<String>();
		if (columns != null) {
			StringTokenizer tokenizer = new StringTokenizer(columns, ",");
			while (tokenizer.hasMoreTokens()) {
				extraColumns.add(tokenizer.nextToken());
			}

		}
		return ActivityService.getLastUpdatedActivities(extraColumns, limit,config);
	}
	

	@GET
	@Path("/boundaries")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public JSONArray getBoundaries() {
		String countryIso = FeaturesUtil.getGlobalSettingValue(GlobalSettingsConstants.DEFAULT_COUNTRY);
		String path = httpRequest.getServletContext().getRealPath("/") + BOUNDARY_PATH + countryIso.toUpperCase()
				+ System.getProperty("file.separator") + "list.json";
		JSONArray json = null;
		try {
			InputStream is = 
	                new FileInputStream(path);
	        String jsonTxt = IOUtils.toString( is );
	        json = (JSONArray) JSONSerializer.toJSON(jsonTxt);  
			
		} catch (IOException e) {
			logger.warn("couldn't read file " + path, e);
		}
		return json;
	}
	
	@GET
	@Path("/report/{report_config_id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@ApiMethod(ui = false, id = "reportExport")
	public JsonBean getLastUpdated(@PathParam("report_config_id") String reportConfigId) {
		return ReportsUtil.getApiState(reportConfigId);
	}

}