/*
 *  AmpApplicationSettings.java
 *  @Author Priyajith C
 *  Created: 18-Aug-2004
 */

package org.digijava.module.aim.dbentity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class AmpApplicationSettings implements Serializable {

	@JsonProperty("id")
	private Long ampAppSettingsId;

	private AmpTeam team;

	@JsonProperty("default-records-per-page")
	private Integer defaultRecordsPerPage;

	@JsonProperty("report-start-year")
	private Integer reportStartYear;

	@JsonProperty("report-end-year")
	private Integer reportEndYear;
	
	private AmpCurrency currency;

	@JsonProperty("fiscal-calendar")
	private AmpFiscalCalendar fiscalCalendar;

	private String language;
	
	private String validation;

	@JsonProperty("show-all-countries")
    private Boolean showAllCountries = false;

	@JsonProperty("default-team-report")
	private AmpReports defaultTeamReport;

	@JsonProperty("default-reports-per-page")
	private Integer defaultReportsPerPage;

	@JsonProperty("allow-add-team-res")
    private Integer allowAddTeamRes;

	@JsonProperty("allow-share-team-res")
    private Integer allowShareTeamRes; //across the workspaces

	@JsonProperty("allow-publishing-resources")
    private Integer allowPublishingResources;

	@JsonProperty("number-of-pages-to-display")
    private Integer numberOfPagesToDisplay;
    
    public Boolean getShowAllCountries() {
        return showAllCountries;
    }

    public void setShowAllCountries(Boolean showAllCountries) {
    	if (showAllCountries == null)
    		return;
    	this.showAllCountries = showAllCountries;
    }

    public AmpReports getDefaultTeamReport() {
		return defaultTeamReport;
	}

	public void setDefaultTeamReport(AmpReports defaultTeamReport) {
		this.defaultTeamReport = defaultTeamReport;
	}

	public Long getAmpAppSettingsId() {
		return this.ampAppSettingsId;
	}

	public void setAmpAppSettingsId(Long ampAppSettingsId) {
		this.ampAppSettingsId = ampAppSettingsId;
	}

	public AmpTeam getTeam() {
		return this.team;
	}

	public void setTeam(AmpTeam team) {
		this.team = team;
	}

	public Integer getDefaultRecordsPerPage() {
		return this.defaultRecordsPerPage;
	}

	public void setDefaultRecordsPerPage(Integer defaultRecordsPerPage) {
		this.defaultRecordsPerPage = defaultRecordsPerPage;
	}

	public AmpCurrency getCurrency() {
		return this.currency;
	}

	public void setCurrency(AmpCurrency currency) {
		this.currency = currency;
	}

	public AmpFiscalCalendar getFiscalCalendar() {
		return this.fiscalCalendar;
	}

	public void setFiscalCalendar(AmpFiscalCalendar fiscalCalendar) {
		this.fiscalCalendar = fiscalCalendar;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

    public Integer getDefaultReportsPerPage() {
      return defaultReportsPerPage;
    }

    public void setDefaultReportsPerPage(Integer  defaultReportsPerPage) {
      this.defaultReportsPerPage = defaultReportsPerPage;
    }

	public Integer getReportStartYear() {
	    return reportStartYear;
	}

	public void setReportStartYear(Integer reportStartYear) {
	    this.reportStartYear = reportStartYear;
	}

	public Integer getReportEndYear() {
	    return reportEndYear;
	}

	public void setReportEndYear(Integer reportEndYear) {
	    this.reportEndYear = reportEndYear;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	/**
	 * @return the allowAddTeamRes
	 */
	public Integer getAllowAddTeamRes() {
		return allowAddTeamRes;
	}

	/**
	 * @param allowAddTeamRes the allowAddTeamRes to set
	 */
	public void setAllowAddTeamRes(Integer allowAddTeamRes) {
		this.allowAddTeamRes = allowAddTeamRes;
	}

	public Integer getAllowShareTeamRes() {
		return allowShareTeamRes;
	}

	public void setAllowShareTeamRes(Integer allowShareTeamRes) {
		this.allowShareTeamRes = allowShareTeamRes;
	}

	public Integer getAllowPublishingResources() {
		return allowPublishingResources;
	}

	public void setAllowPublishingResources(Integer allowPublishingResources) {
		this.allowPublishingResources = allowPublishingResources;
	}

	public Integer getNumberOfPagesToDisplay() {
		return numberOfPagesToDisplay;
	}

	public void setNumberOfPagesToDisplay(Integer numberOfPagesToDisplay) {
		this.numberOfPagesToDisplay = numberOfPagesToDisplay;
	}

}
