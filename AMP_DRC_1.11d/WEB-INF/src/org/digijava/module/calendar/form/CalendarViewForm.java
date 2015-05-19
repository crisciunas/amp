package org.digijava.module.calendar.form;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.digijava.kernel.user.*;
import org.digijava.module.calendar.entity.*;

public class CalendarViewForm
    extends ActionForm {
    private String siteId;
    private String instanceId;
    private User user;
    private GregorianCalendar currentDate;
    private DateBreakDown currentDateBreakDown;
    private GregorianCalendar baseDate;
    private DateBreakDown baseDateBreakDown;
    private GregorianCalendar startDate;
    private DateBreakDown startDateBreakDown;
    private String customViewStartDate;
    private GregorianCalendar endDate;
    private DateBreakDown endDateBreakDown;
    private String customViewEndDate;
    private int timestamp;
    private List calendarTypes;
    private int selectedCalendarType;
    private DateNavigator dateNavigator;
    private String view;
    private EventsFilter filter;
    private boolean filterInUse;
    private Collection ampCalendarGraphs;
  private Boolean resetFilter;

  public GregorianCalendar getCurrentDate() {
        return currentDate;
    }

    public DateBreakDown getCurrentDateBreakDown() {
        return currentDateBreakDown;
    }

    public DateNavigator getDateNavigator() {
        return dateNavigator;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public DateBreakDown getEndDateBreakDown() {
        return endDateBreakDown;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getSiteId() {
        return siteId;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public DateBreakDown getStartDateBreakDown() {
        return startDateBreakDown;
    }

    public User getUser() {
        return user;
    }

    public String getView() {
        return view;
    }

    public DateBreakDown getBaseDateBreakDown() {
        return baseDateBreakDown;
    }

    public GregorianCalendar getBaseDate() {
        return baseDate;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public EventsFilter getFilter() {
        if (filter == null) {
            filter = new EventsFilter();
        }
        return filter;
    }

    public boolean isFilterInUse() {
        return filterInUse;
    }

    public List getCalendarTypes() {
        return calendarTypes;
    }

    public int getSelectedCalendarType() {
        return selectedCalendarType;
    }

    public String getCustomViewStartDate() {
        return customViewStartDate;
    }

    public String getCustomViewEndDate() {
        return customViewEndDate;
    }

    public Collection getAmpCalendarGraphs() {
        return ampCalendarGraphs;
    }

  public Boolean getResetFilter() {
    return resetFilter;
  }

  public void setView(String view) {
        this.view = view;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStartDateBreakDown(DateBreakDown startDateBreakDown) {
        this.startDateBreakDown = startDateBreakDown;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setEndDateBreakDown(DateBreakDown endDateBreakDown) {
        this.endDateBreakDown = endDateBreakDown;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public void setDateNavigator(DateNavigator dateNavigator) {
        this.dateNavigator = dateNavigator;
    }

    public void setCurrentDateBreakDown(DateBreakDown currentDateBreakDown) {
        this.currentDateBreakDown = currentDateBreakDown;
    }

    public void setCurrentDate(GregorianCalendar currentDate) {
        this.currentDate = currentDate;
    }

    public void setBaseDateBreakDown(DateBreakDown baseDateBreakDown) {
        this.baseDateBreakDown = baseDateBreakDown;
    }

    public void setBaseDate(GregorianCalendar baseDate) {
        this.baseDate = baseDate;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setFilter(EventsFilter filter) {
        this.filter = filter;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setFilterInUse(boolean filterInUse) {
        this.filterInUse = filterInUse;
    }

    public void setCalendarTypes(List calendarTypes) {
        this.calendarTypes = calendarTypes;
    }

    public void setSelectedCalendarType(int selectedCalendarType) {
        this.selectedCalendarType = selectedCalendarType;
    }

    public void setCustomViewStartDate(String customViewStartDate) {
        this.customViewStartDate = customViewStartDate;
    }

    public void setCustomViewEndDate(String customViewEndDate) {
        this.customViewEndDate = customViewEndDate;
    }

    public void setAmpCalendarGraphs(Collection ampCalendarGraphs) {
        this.ampCalendarGraphs = ampCalendarGraphs;
    }

  public void setResetFilter(Boolean resetFilter) {
    this.resetFilter = resetFilter;
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    /* if(filter!=null){
       this.filter.setShowPublicEvents(false);
     }
    */
  }


}
