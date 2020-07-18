package tr.edu.metu.data.analysis;

public class KuraklikData {
	String station_id;
	String latitude;
	String longitude;
	String altitude_m;
	String year;
	String month;
	String rainfall;
	String relative_humidity;
	String sunshine_hour;
	String temperature;
	Double aylikKuraklikOrani;

	public KuraklikData(String station_id, String latitude, String longitude, String altitude_m, String year,
			String month, String rainfall, String relative_humidity, String sunshine_hour, String temperature) {
		super();
		this.station_id = station_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude_m = altitude_m;
		this.year = year;
		this.month = month;
		this.rainfall = rainfall;
		this.relative_humidity = relative_humidity;
		this.sunshine_hour = sunshine_hour;
		this.temperature = temperature;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
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

	public Double getAylikKuraklikOrani() {
		return aylikKuraklikOrani;
	}

	public void setAylikKuraklikOrani(Double aylikKuraklikOrani) {
		this.aylikKuraklikOrani = aylikKuraklikOrani;
	}

}
