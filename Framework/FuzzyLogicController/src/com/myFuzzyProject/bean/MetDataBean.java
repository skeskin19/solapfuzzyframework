package com.myFuzzyProject.bean;

public class MetDataBean {
	String dbId;
	String stationId;
	String year;
	String month;
	String day;
	String actual_pressure;
	String cloudiness;
	String rainfall;
	String relative_humidity;
	String sunshine_hour;
	String temperature;
	String vapor_pressure;
	String wind_speed;
	String latitude;
	String longitude;
	String altitude_m;
	String actual_2016;
	//d_id	station_id	latitude	longitude	altitude_m	year	month	day	actual_pressure	cloudiness	rainfall	relative_humidity	
		//sunshine_hour	temperature	vapor_pressure	wind_speed
	public MetDataBean(String dbId, String stationId, String latitude, String longitude, String altitude_m, String year, String month, String day, String actual_pressure, String cloudiness,
			String rainfall, String relative_humidity, String sunshine_hour, String temperature, String vapor_pressure,
			String wind_speed, String actual_2016) {
		super();
		this.dbId = dbId;
		this.stationId = stationId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude_m = altitude_m;
		this.year = year;
		this.month = month;
		this.day = day;
		this.actual_pressure = actual_pressure;
		this.cloudiness = cloudiness;
		this.rainfall = rainfall;
		this.relative_humidity = relative_humidity;
		this.sunshine_hour = sunshine_hour;
		this.temperature = temperature;
		this.vapor_pressure = vapor_pressure;
		this.wind_speed = wind_speed;
		this.actual_2016 = actual_2016;
	}
	
	public MetDataBean(String stationId, String latitude, String longitude, String altitude_m, String year, String month, String day, String actual_pressure, String cloudiness,
			String rainfall, String relative_humidity, String sunshine_hour, String temperature, String vapor_pressure,
			String wind_speed, String actual_2016) {
		super();
		this.stationId = stationId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude_m = altitude_m;
		this.year = year;
		this.month = month;
		this.day = day;
		this.actual_pressure = actual_pressure;
		this.cloudiness = cloudiness;
		this.rainfall = rainfall;
		this.relative_humidity = relative_humidity;
		this.sunshine_hour = sunshine_hour;
		this.temperature = temperature;
		this.vapor_pressure = vapor_pressure;
		this.wind_speed = wind_speed;
		this.actual_2016 = actual_2016;
	}
	
	public MetDataBean(String year, String month, String day, String actual_pressure, String cloudiness,
			String rainfall, String relative_humidity, String sunshine_hour, String temperature, String vapor_pressure,
			String wind_speed, String actual_2016) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.actual_pressure = actual_pressure;
		this.cloudiness = cloudiness;
		this.rainfall = rainfall;
		this.relative_humidity = relative_humidity;
		this.sunshine_hour = sunshine_hour;
		this.temperature = temperature;
		this.vapor_pressure = vapor_pressure;
		this.wind_speed = wind_speed;
		this.actual_2016 = actual_2016;
	}

	public String getDbId() {
		return dbId;
	}

	public void setDbId(String dbId) {
		this.dbId = dbId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
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

	public String getActual_pressure() {
		return actual_pressure;
	}

	public void setActual_pressure(String actual_pressure) {
		this.actual_pressure = actual_pressure;
	}

	public String getCloudiness() {
		return cloudiness;
	}

	public void setCloudiness(String cloudiness) {
		this.cloudiness = cloudiness;
	}

	public String getRainfall() {
		return rainfall;
	}

	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}

	public String getRelative_humidity() {
		return relative_humidity;
	}

	public void setRelative_humidity(String relative_humidity) {
		this.relative_humidity = relative_humidity;
	}

	public String getSunshine_hour() {
		return sunshine_hour;
	}

	public void setSunshine_hour(String sunshine_hour) {
		this.sunshine_hour = sunshine_hour;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getVapor_pressure() {
		return vapor_pressure;
	}

	public void setVapor_pressure(String vapor_pressure) {
		this.vapor_pressure = vapor_pressure;
	}

	public String getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(String wind_speed) {
		this.wind_speed = wind_speed;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude_m() {
		return altitude_m;
	}

	public void setAltitude_m(String altitude_m) {
		this.altitude_m = altitude_m;
	}

	public String getActual_2016() {
		return actual_2016;
	}

	public void setActual_2016(String actual_2016) {
		this.actual_2016 = actual_2016;
	}

}
