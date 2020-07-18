package sk.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import sk.bean.MeteorologicData;
import sk.util.FCMUtil;

public class JDBCStatementSelect {

	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/simple_geofoodmart";
	private static final String DB_USER = "test";
	private static final String DB_PASSWORD = "test";

	public static void main(String[] argv) {

		try {

			//selectRecordsFromDbStationTable();
			List<MeteorologicData> list = selectRecordsFromDbTable(FCMUtil.TEMPERATURE_QUERY);
			System.out.println("Number of elements: "+ list.size());
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	private static void selectRecordsFromDbStationTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = "SELECT id, station_name, latitude, longitude, altitude_m, city_id, town_name, geom FROM public.meteorological_station2";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String id = rs.getString("id");
				String station_name = rs.getString("station_name");
				String latitude = rs.getString("latitude");
				String longitude = rs.getString("longitude");
				String city_id = rs.getString("city_id");
				String town_name = rs.getString("town_name");
				String geom = rs.getString("geom");
				
				System.out.println("row : { " + "id:"+id + ", station_name:"+station_name + ", latitude:"+latitude + ", longitude:"+longitude + ", city_id:"+city_id + ", town_name:"+town_name + ", geom:"+geom + " }" );
				

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	
	public static List<MeteorologicData> selectRecordsFromDbTable(String selectTableSQL) throws SQLException {

		List<MeteorologicData> list = new ArrayList<MeteorologicData>();
		Connection dbConnection = null;
		Statement statement = null;

		//String selectTableSQL = "SELECT station_id, station_name, year, month, day, temporal, temperature as metValue FROM public.met_data_temperature";
		
		//String selectTableSQL = "SELECT station_id, station_name, year, month, day, temporal, temperature FROM public.met_data_temperature where year=2016 and station_id=18275";

		//String selectTableSQL = "SELECT station_id, station_name, year, month, day, temporal, sunshine_hour as temperature FROM public.met_data_sunshine where year=2016";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String station_id = rs.getString("station_id");
				String station_name = rs.getString("station_name");
				String year = rs.getString("year");
				String month = rs.getString("month");
				String day = rs.getString("day");
				String temporal = rs.getString("temporal");
				String temperature = rs.getString("metValue");
				
				MeteorologicData tmp = new MeteorologicData(station_id, station_name, year, month, day, temporal, temperature);
				list.add(tmp);
				System.out.println("row : { " + "id:"+station_id + ", station_name:"+station_name + ", year:"+year + ", month:"+month + ", day:"+day + ", temporal:"+temporal + ", temperature:"+temperature + " }" );
				

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return list;

	}

	public static void insertFuzzyValues(HashMap<Double, double[]> calculatedValues) {
		System.out.println("----------insertFuzzyValues---------------------");
		Set<Double> keyVals = calculatedValues.keySet();
		System.out.println(keyVals.size() + " number of rows will be inserted at "+ (new Date()));
		try {
			Connection dbConnection = null;
			PreparedStatement preparedStatement = null;

			String insertTableSQL = "INSERT INTO public.temperature_fuzzy_values(temperature, cold_fuzzy_membership, normal_fuzzy_membership, hot_fuzzy_membership) "
					+ " VALUES (?, ?, ?, ?)";

			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			try {
				int i=0;
				
				for (Double val : keyVals) {

					// insertFuzzyRow(val, calculatedValues.get(val));
					double[] fuzzyVal = calculatedValues.get(val);

					preparedStatement.setDouble(1, val);
					preparedStatement.setDouble(2, fuzzyVal[0]);
					preparedStatement.setDouble(3, fuzzyVal[1]);
					preparedStatement.setDouble(4, fuzzyVal[2]);

					preparedStatement.addBatch();
					
					if (i % 100 == 0 || i == keyVals.size()) {
						preparedStatement.executeBatch(); // Execute every 1000 items.
						dbConnection.setAutoCommit(false);
						dbConnection.commit();
						dbConnection.setAutoCommit(true);
						System.out.println("100 rows inserted at " + (new Date()));
					}

				}
				

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertFuzzyRow(Double val, double[] fuzzyVal) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO public.temperature_fuzzy_values(temperature, cold_fuzzy_membership, normal_fuzzy_membership, hot_fuzzy_membership) "
				+ " VALUES (?, ?, ?, ?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setDouble(1, val);
			preparedStatement.setDouble(2, fuzzyVal[0]);
			preparedStatement.setDouble(3, fuzzyVal[1]);
			preparedStatement.setDouble(4, fuzzyVal[2]);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
	}

}