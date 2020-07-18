package com.myFuzzyProject;

public class PredictedData {
	String preDate;
	String stationId;
	String value;

	public PredictedData(String preDate, String stationId, String value) {
		super();
		this.preDate = preDate;
		this.stationId = stationId;
		this.value = value;
	}

	public String getPreDate() {
		return preDate;
	}

	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
