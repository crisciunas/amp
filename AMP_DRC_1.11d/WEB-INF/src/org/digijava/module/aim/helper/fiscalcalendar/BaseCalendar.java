package org.digijava.module.aim.helper.fiscalcalendar;

import java.util.Collection;
import java.util.Vector;

import org.digijava.module.aim.dbentity.AmpFiscalCalendar;

public class BaseCalendar {
	private String name;
	private String value;

	public static final BaseCalendar BASE_GREGORIAN = new BaseCalendar("Gregorian", "GREG-CAL");
	public static final BaseCalendar BASE_ETHIOPIAN = new BaseCalendar("Ethiopian ", "ETH-CAL");

	public static Collection<BaseCalendar> calendarList = null;

	// singleton base calendar list
	static {
		calendarList = new Vector<BaseCalendar>();
		calendarList.add(BASE_GREGORIAN);
		calendarList.add(BASE_ETHIOPIAN);

	}

	public static Collection getCalendarList() {
		return calendarList;
	}

	public static BaseCalendar getBaseCalendar(String key) {
		for (BaseCalendar base : calendarList) {
			if (base.getValue().equalsIgnoreCase(key)) {
				return base;
			}
		}
		return BASE_GREGORIAN;
	}

	public BaseCalendar(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getTrnName() {
		String key = "aim:calendar:basecalender:" + value.toLowerCase().replaceAll(" ", "");
		return key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



}