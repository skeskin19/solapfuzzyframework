package tr.edu.metu.ceng.clustering.kmeans.db;

public class DatabaseConstants {
	public static final String DB_DRIVER = "org.postgresql.Driver";
	public static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5433/simple_geofoodmart";
	public static final String DB_USER = "test";
	public static final String DB_PASSWORD = "test";
	
	public static final int DATA_TYPE__AVG_ACTUAL_PRESSURE = 1; 
	public static final int DATA_TYPE__AVG_CLOUDINESS = 2; 
	public static final int DATA_TYPE__RAINFALL_MANUEL = 3; 
	public static final int DATA_TYPE__RAINFALL_OMGI = 4; 
	public static final int DATA_TYPE__RELATIVE_HUMIDITY = 5; 
	public static final int DATA_TYPE__SUNSHINE = 6; 
	public static final int DATA_TYPE__TEMPERATURE = 7; 
	public static final int DATA_TYPE__VAPOR_PRESSURE = 8; 
	public static final int DATA_TYPE__WIND_DIRECTION_SPEED = 9; 
	public static final int DATA_TYPE__WIND_SPEED = 10; 
	//--------tables
	
	/**
	_3met_data_avg_actual_pressure - actual_pressure - ok		
	_3met_data_avg_cloudiness - cloudiness - ok
	_3met_data_rainfall_manuel - rainfall - ok	
	_3met_data_rainfall_omgi - rainfall
	_3met_data_relative_humidity - 	relative_humidity - ok
	_3met_data_sunshine - sunshine_hour - ok
	_3met_data_temperature - temperature - ok
	_3met_data_vapor_pressure - pressure - ok
	_3met_data_wind_direction_speed - wind_direction_speed, wind_direction, wind_speed
	_3met_data_wind_speed - wind_speed - ok
	 */
	public static final String ORGINAL_DATA_TABLE = "met_data_rainfall_omgi";
	public static final String ORGINAL_DATA_TABLE_DATA_COLOUMN = "rainfall";	
	public static final String TARGET_DATA_TABLE = "_3met_data_rainfall_omgi";
	
	public static String getDatabaseTableColoumn(int key) {
		String tableColoumn = null;
		switch (key) {
		case DATA_TYPE__AVG_ACTUAL_PRESSURE:
			tableColoumn = "actual_pressure"; 
			break;
		case DATA_TYPE__AVG_CLOUDINESS:
			tableColoumn = "cloudiness"; 
			break;
		case DATA_TYPE__RAINFALL_MANUEL:
			tableColoumn = "rainfall"; 
			break;
		case DATA_TYPE__RAINFALL_OMGI:
			tableColoumn = "rainfall"; 
			break;
		case DATA_TYPE__RELATIVE_HUMIDITY:
			tableColoumn = "relative_humidity"; 
			break;
		case DATA_TYPE__SUNSHINE:
			tableColoumn = "sunshine_hour"; 
			break;
		case DATA_TYPE__TEMPERATURE:
			tableColoumn = "temperature"; 
			break;
		case DATA_TYPE__VAPOR_PRESSURE:
			tableColoumn = "pressure"; 
			break;
		case DATA_TYPE__WIND_DIRECTION_SPEED:
			tableColoumn = "wind_speed"; 
			break;
		case DATA_TYPE__WIND_SPEED:
			tableColoumn = "wind_speed"; 
			break;
			
		default:
			break;
		}
		return tableColoumn;
	}
	
	public static String getDatabaseTableName(int key) {
		String tableName = null;
		switch (key) {
		case DATA_TYPE__AVG_ACTUAL_PRESSURE:
			tableName = "_3met_data_avg_actual_pressure"; 
			break;
		case DATA_TYPE__AVG_CLOUDINESS:
			tableName = "_3met_data_avg_cloudiness"; 
			break;
		case DATA_TYPE__RAINFALL_MANUEL:
			tableName = "_3met_data_rainfall_manuel"; 
			break;
		case DATA_TYPE__RAINFALL_OMGI:
			tableName = "_3met_data_rainfall_omgi"; 
			break;
		case DATA_TYPE__RELATIVE_HUMIDITY:
			tableName = "_3met_data_relative_humidity"; 
			break;
		case DATA_TYPE__SUNSHINE:
			tableName = "_3met_data_sunshine"; 
			break;
		case DATA_TYPE__TEMPERATURE:
			tableName = "_3met_data_temperature"; 
			break;
		case DATA_TYPE__VAPOR_PRESSURE:
			tableName = "_3met_data_vapor_pressure"; 
			break;
		case DATA_TYPE__WIND_DIRECTION_SPEED:
			tableName = "_3met_data_wind_direction_speed"; 
			break;
		case DATA_TYPE__WIND_SPEED:
			tableName = "_3met_data_wind_speed"; 
			break;
			
		default:
			break;
		}
		return tableName;
	}
	
	

}
