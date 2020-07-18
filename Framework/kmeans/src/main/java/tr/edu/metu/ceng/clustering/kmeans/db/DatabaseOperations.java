package tr.edu.metu.ceng.clustering.kmeans.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import tr.edu.metu.ceng.clustering.kmeans.db.bean.Measurement;
import tr.edu.metu.ceng.clustering.kmeans.db.bean.Station;

public class DatabaseOperations {

	public static void main(String[] argv) {

		try {
			System.out.println("Data generation starts at " + (new Date()));

			System.out.println("Data generation ends at " + (new Date()));
			//conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;
		try {
			Class.forName(DatabaseConstants.DB_DRIVER);
			dbConnection = DriverManager.getConnection(DatabaseConstants.DB_CONNECTION, DatabaseConstants.DB_USER,
					DatabaseConstants.DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		// System.out.println("Opened database successfully");
		return dbConnection;

	}

	public static List<Station> fetchAllStations() throws SQLException {
		List<Station> resultList = new ArrayList<Station>();
		Connection dbConnection = null;
		Statement stmt = null;
		try {
			dbConnection = getDBConnection();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM meteorological_station3 WHERE id not in(SELECT distinct station_id FROM "+ DatabaseConstants.ORGINAL_DATA_TABLE + ");");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("station_name");

				// System.out.println("STATION_ID = " + id);
				// System.out.println("STATION_NAME = " + name);

				// System.out.println("--------------");
				resultList.add(new Station(id, name));
			}
			rs.close();
			stmt.close();
			dbConnection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("fetchAllStations-Operation done successfully");
		return resultList;
	}
	
	public static List<Measurement> fetchMeasurementByStation(int stationId, Date ptemporal) throws SQLException {
		List<Measurement> resultList = new ArrayList<Measurement>();
		Connection dbConnection = null;
		Statement stmt = null;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dbConnection = getDBConnection();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + DatabaseConstants.ORGINAL_DATA_TABLE + " WHERE station_id=" + stationId
					+ " AND temporal='" + format1.format(ptemporal) + "';");

			while (rs.next()) {
				int id = rs.getInt("station_id");
				String name = rs.getString("station_name");
				int year = rs.getInt("year");
				int month = rs.getInt("month");
				int day = rs.getInt("day");
				Date temporal = rs.getDate("temporal");
				Double measurement = rs.getDouble(DatabaseConstants.ORGINAL_DATA_TABLE_DATA_COLOUMN);

				// System.out.println("STATION_ID = " + id);
				// System.out.println("STATION_NAME = " + name);

				// System.out.println("--------------");
				resultList.add(new Measurement(id, name, year, month, day, temporal, measurement));
			}
			rs.close();
			stmt.close();
			//dbConnection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		// System.out.println("fetchMeasurementByStation-Operation done
		// successfully-station id:"+stationId+" temporal:"+ptemporal);
		return resultList;
	}

	public static double[] getMeasurementValues(String databaseTableName, String tableColoumn, int year) {
		//List<Measurement> resultList = new ArrayList<Measurement>();
		List<Double> resultList = new ArrayList<Double>();
		Connection dbConnection = null;
		Statement stmt = null;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dbConnection = getDBConnection();
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT distinct("+tableColoumn+") FROM " + databaseTableName + " WHERE year=" + year + " AND station_id=18384;");
			//ResultSet rs = stmt.executeQuery("SELECT "+tableColoumn+" FROM " + databaseTableName + " WHERE year=" + year);

			while (rs.next()) {
//				int id = rs.getInt("station_id");
//				String name = rs.getString("station_name");
//				int year = rs.getInt("year");
//				int month = rs.getInt("month");
//				int day = rs.getInt("day");
//				Date temporal = rs.getDate("temporal");
				Double measurement = rs.getDouble(tableColoumn);

				resultList.add(measurement);
				//resultList.add(new Measurement(id, name, year, month, day, temporal, measurement));
			}
			rs.close();
			stmt.close();
			dbConnection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Double[] ds = resultList.toArray(new Double[resultList.size()]);
		double[] d = ArrayUtils.toPrimitive(ds);
		
		return d;
	}

}