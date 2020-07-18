package sk.util;

public class FCMUtil {
	
	/**
	 * 	0/10 clear / sunny
		1/10 fair (often saved for high wispy cirrus)
		2/10 to 3/10 mostly sunny
		4/10 to 6/10 partly cloudy
		7/10 to 8/10 mostly cloudy
		9/10 broken
		10/10 cloudy / overcast
	 */
	
	public static String[] avgpressureLabels={ "low", "normal", "high", "very-high" };
	public static String[] cloudnessLabels={"Sunny", "Fair", "Partly-Sunny", "Mostly-Sunny", "Partly-Cloudy", "Mostly-Cloudy", "Broken", "Overcast"};
	public static String[] rainfallLabels={"nearly-dry", "very-low", "low", "normal", "high"}; //, "very-high", "overmuch", "flood"
	public static String[] humidityLabels={"nearly-dry", "very-low", "low", "normal", "high", "very-high", "overmuch", "flood"};
	public static String[] sunshineLabels={"dark", "nearly-dark", "low", "normal", "high", "very-high", "overmuch", "luminous"};
	public static String[] vaporpressureLabels={ "very-low", "low", "above-normal", "normal", "below-normal", "high", "very-high", "extreme" };
	public static String[] temperatureLabels = { "cold", "normal", "hot", "boiling" }; //{ "cold", "nearly-cold", "low", "normal", "high", "hot", "very-hot", "boiling" };
	public static String[] windspeedLabels={ "very-low", "low", "above-normal", "normal", "below-normal", "high", "very-high", "extreme" }; //"very-low", "low", "above-normal", "normal", "below-normal", "high", "very-high", "extreme"
	
	public static String AVG_PRESSURE_QUERY = "SELECT station_id, station_name, year, month, day, temporal, actual_pressure as metValue FROM public.met_data_avg_actual_pressure";
	public static String CLOUDINESS_QUERY = "SELECT station_id, station_name, year, month, day, temporal, cloudiness as metValue FROM public.met_data_avg_cloudiness";
	public static String RAINFALL_MANUEL_QUERY = "SELECT station_id, station_name, year, month, day, temporal, rainfall as metValue FROM public.met_data_rainfall_manuel";
	public static String RAINFALL_OMGI_QUERY = "SELECT station_id, station_name, year, month, day, temporal, rainfall as metValue FROM public.met_data_rainfall_omgi";
	public static String HUMIDITY_QUERY = "SELECT station_id, station_name, year, month, day, temporal, relative_humidity as metValue FROM public.met_data_relative_humidity";
	public static String SUNSHINE_QUERY = "SELECT station_id, station_name, year, month, day, temporal, sunshine_hour as metValue FROM public.met_data_sunshine";
	public static String VAPOR_PRESSURE_QUERY = "SELECT station_id, station_name, year, month, day, temporal, pressure as metValue FROM public.met_data_vapor_pressure";
	public static String TEMPERATURE_QUERY = "SELECT station_id, station_name, year, month, day, temporal, temperature as metValue FROM public.met_data_temperature";
	public static String WIND_DIRECTION_SPEED_QUERY = "SELECT station_id, station_name, year, month, day, temporal, wind_direction as metValue FROM public.met_data_wind_direction_speed";
	public static String WIND_SPEED_QUERY = "SELECT station_id, station_name, year, month, day, temporal, wind_speed as metValue FROM public.met_data_wind_speed";

}
