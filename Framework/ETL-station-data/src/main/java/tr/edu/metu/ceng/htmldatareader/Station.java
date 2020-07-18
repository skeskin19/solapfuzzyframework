package tr.edu.metu.ceng.htmldatareader;

public class Station {
	String id;
	String station_name;
	String latitude;
	String longitude;
	String altitude_m;
	String city_id;
	String town_name;

	public Station(String id, String station_name, String latitude, String longitude, String altitude_m, String city_id,
			String town_name) {
		super();
		this.id = id;
		this.station_name = station_name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude_m = altitude_m;
		this.city_id = city_id;
		this.town_name = town_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
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

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getTown_name() {
		return town_name;
	}

	public void setTown_name(String town_name) {
		this.town_name = town_name;
	}

	public String toString() {
		return id + ", " + station_name + ", " + latitude + ", " + longitude + ", " + altitude_m + ", " + city_id + ", "
				+ town_name;
	}

}
