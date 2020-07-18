package tr.edu.metu.ceng.clustering.kmeans.db.bean;

import java.util.Date;

public class Measurement {
	int stationId;
	String stationName;
	int year;
	int month;
	int day;
	Date temporal;
	Double measurementVal;

	public Measurement(int stationId, String stationName, int year, int month, int day, Date temporal,
			Double measurementVal) {
		super();
		this.stationId = stationId;
		this.stationName = stationName;
		this.year = year;
		this.month = month;
		this.day = day;
		this.temporal = temporal;
		this.measurementVal = measurementVal;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public Date getTemporal() {
		return temporal;
	}

	public void setTemporal(Date temporal) {
		this.temporal = temporal;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Double getMeasurementVal() {
		return measurementVal;
	}

	public void setMeasurementVal(Double measurementVal) {
		this.measurementVal = measurementVal;
	}

}
