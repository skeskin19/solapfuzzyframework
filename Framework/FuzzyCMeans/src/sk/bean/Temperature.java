package sk.bean;

import java.io.Serializable;

public class Temperature implements Serializable {
	private String station_id;
	private String station_name;
	private String year;
	private String month;
	private String day;
	private String temporal;
	private String temperature;

	public Temperature(String station_id, String station_name, String year, String month, String day, String temporal,
			String temperature) {
		super();
		this.station_id = station_id;
		this.station_name = station_name;
		this.year = year;
		this.month = month;
		this.day = day;
		this.temporal = temporal;
		this.temperature = temperature;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTemporal() {
		return temporal;
	}

	public void setTemporal(String temporal) {
		this.temporal = temporal;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

}
