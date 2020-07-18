package tr.edu.metu.ceng.clustering.kmeans.db.bean;

public class Station {
	int stationId;
	String stationName;
	double distanceToRef;
	double distanceWeight;

	public Station(int stationId, String stationName) {
		super();
		this.stationId = stationId;
		this.stationName = stationName;
	}

	public Station(int id, String name, double distance) {
		super();
		this.stationId = id;
		this.stationName = name;
		this.distanceToRef = distance;
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

	public double getDistanceToRef() {
		return distanceToRef;
	}

	public void setDistanceToRef(double distanceToRef) {
		this.distanceToRef = distanceToRef;
	}

	public double getDistanceWeight() {
		return distanceWeight;
	}

	public void setDistanceWeight(double distanceWeight) {
		this.distanceWeight = distanceWeight;
	}

}
