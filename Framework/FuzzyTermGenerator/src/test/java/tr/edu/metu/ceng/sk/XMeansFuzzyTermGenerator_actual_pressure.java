
package tr.edu.metu.ceng.sk;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.xerial.util.log.Logger;

import tr.edu.metu.ceng.sk.EuclidDistanceMetric2D;
import tr.edu.metu.ceng.sk.XMeans;
import tr.edu.metu.ceng.sk.XMeans.ClusterInfo;

public class XMeansFuzzyTermGenerator_actual_pressure {
	
	//private String FILE_NAME = "temperature2.txt";//"cloudiness.txt"; //"rainfall.txt";//"relative_humidity.txt";//"sunshine_hour.txt";//"temperature.txt";//"vapor_pressure.txt";//"wind_speed.txt";
	//private String MET_TYPE = "temperature[";//"cloudiness["; //"rainfall[";//"relative-humidity[";//"sunshine-hour[";//"vapor-pressure[";//"wind-speed["; 
	
	//private String[] clusterLabels={"very-low]", "very-high]", "above-normal]", "below-normal]", "extreme]","low]","high]","normal]"};//wind_speed
	//private String[] clusterLabels={"very-high]", "very-low]", "extreme]", "above-normal]", "normal]","low]","below-normal]","high]"};//vapor_pressure
	//private String[] clusterLabels={"very-hot]", "boiling]", "high]", "hot]", "cold]", "nearly-cold]", "normal]", "low]"}; //temperature
    //private String[] clusterLabels={"low]", "high]", "luminous]", "normal]", "nearly-dark]", "very-high]", "dark]", "overmuch]"};//sunshine_hour
    //private String[] clusterLabels={"high]", "very-high]", "very-low]", "overmuch]", "normal]", "nearly-dry]", "flood]", "low]"};//relative_humidity
    //private String[] clusterLabels={"nearly-dry]", "normal]", "very-high]", "overmuch]", "flood]", "very-low]", "high]", "low]"};//rainfall
	//private String[] clusterLabels={"Fair]", "Partly-Sunny]", "Sunny]", "Partly-Cloudy]", "Overcast]", "Mostly-Cloudy]", "Mostly-Sunny]", "Broken]"};//cloudiness
    //private String[] clusterLabels={"normal", "very-high", "high", "low"};//actual_pressure
    
    //Map<String, Integer> clusterLabelsMap = new HashMap<String, Integer>();
	
    private static Logger _logger = Logger.getLogger(XMeansFuzzyTermGenerator_actual_pressure.class);
    private boolean PRINT_DETAIL = true;
    
    @Test
    public void faithful() throws Exception {

    
    
    	String[] clusterLabelsActualPressure={"low", "normal", "high", "very-low", "extreme", "very-high"};//actual_pressure
    	String[] clusterLabelsCloudiness={"Mostly-Cloudy", "Partly-Sunny", "Partly-Cloudy", "Mostly-Sunny", "Overcast", "Broken", "Sunny", "Fair"};//cloudiness
    	String[] clusterLabelsRainfall={"high", "very-low", "flood", "very-high", "nearly-dry", "low", "overmuch", "normal"};//rainfall
    	String[] clusterLabelsHumidity= {"much", "high", "normal", "less"};//{"very-low", "nearly-dry", "flood", "normal", "overmuch", "very-high", "low", "high"};//relative_humidity
    	String[] clusterLabelsSunShine={"normal", "high", "dark", "overmuch", "luminous", "very-high", "low", "nearly-dark"};//sunshine_hour
    	//String[] clusterLabelsTemperature={"low", "very-hot", "cold", "very-cold", "hot", "normal", "high", "boiling"}; //temperature
    	String[] clusterLabelsTemperature={"cold", "boiling", "normal", "hot"}; //temperature
    	String[] clusterLabelsVaporPressure={"above-normal", "extreme", "high", "very-low", "normal","below-normal","very-high","low"};//vapor_pressure
    	String[] clusterLabelsWindSpeed={"very-high", "high", "below-normal", "very-low", "extreme","above-normal","normal","low"};//wind_speed
    	
    	String[] clusterLabelsLatitude={"center-s", "near-south", "so-south", "south", "near-north", "center-n", "north", "so-north"};//latitude
    	String[] clusterLabelsLongitude={"center-e", "near-east", "east", "so-east", "center-w", "near-west", "so-west", "west"};//longitude
    	
    	
    	String[] clusterLabelsAltitude={"very-high", "near-peak", "high", "normal", "peak", "so-peak", "nearly-sea-level", "sea-level"};//altitude
    	
    	printFclHeader();
    	
    	
    	executeXMeans("actual_pressure3.txt", "actual_pressure", clusterLabelsActualPressure);
//    	executeXMeans("cloudiness2.txt", "cloudiness", clusterLabelsCloudiness);
//    	executeXMeans("rainfall2.txt", "rainfall", clusterLabelsRainfall);
//    	executeXMeans("relative_humidity3.txt", "relative_humidity", clusterLabelsHumidity);
//    	executeXMeans("sunshine_hour2.txt", "sunshine_hour", clusterLabelsSunShine);
//    	executeXMeans("temperature3.txt", "temperature", clusterLabelsTemperature);
//    	executeXMeans("vapor_pressure2.txt", "vapor_pressure", clusterLabelsVaporPressure);
//   	executeXMeans("wind_speed2.txt", "wind_speed", clusterLabelsWindSpeed);
//    	executeXMeans("latitude.txt", "latitude", clusterLabelsLatitude);
//    	executeXMeans("longitude.txt", "longitude", clusterLabelsLongitude);
//    	executeXMeans("altitude.txt", "altitude", clusterLabelsAltitude);

    }
    
    private void printFclHeader() {
    	System.out.println("FUNCTION_BLOCK meteorology");
    	
    	System.out.println("");
    	
    	System.out.println("VAR_INPUT");
    	System.out.println("\tactual_pressure_in : REAL;");
    	System.out.println("\tcloudiness_in : REAL;");
    	System.out.println("\trainfall_in : REAL;");
    	System.out.println("\trelative_humidity_in : REAL;");
    	System.out.println("\tsunshine_hour_in : REAL;");
    	System.out.println("\ttemperature_in : REAL;");
    	System.out.println("\tvapor_pressure_in : REAL;");
    	System.out.println("\twind_speed_in : REAL;");
    	System.out.println("END_VAR");
    	
    	System.out.println("");
    	
    	System.out.println("VAR_OUTPUT");
    	System.out.println("\tactual_pressure_out : REAL;");
    	System.out.println("\tcloudiness_out : REAL;");
    	System.out.println("\trainfall_out : REAL;");
    	System.out.println("\trelative_humidity_out : REAL;");
    	System.out.println("\tsunshine_hour_out : REAL;");
    	System.out.println("\ttemperature_out : REAL;");
    	System.out.println("\tvapor_pressure_out : REAL;");
    	System.out.println("\twind_speed_out : REAL;");
    	System.out.println("END_VAR");
    	System.out.println("");
	}

	public void executeXMeans(String fileName, String metDataType, String[] clusterLabels) throws Exception {
        List<Point2D> input = new ArrayList<Point2D>();

        Map<String, Integer> clusterLabelsMap = new HashMap<String, Integer>();
        
        // read the faithful data set
//        BufferedReader faithful = FileResource.open(XMeansTest.class, "faithful.txt");
        try {
//            for (String line; (line = faithful.readLine()) != null;) {
//                String[] c = line.split("\\s+");
//                input.add(new Point2D.Double(Double.parseDouble(c[0]), Double.parseDouble(c[1])));
//            }
        	
        	List<String> dataList = readFile(fileName);
        	
        	/**
        	 * testdata
        	 * 
            for (int i=0;i<100;i++) {
                input.add(new Point2D.Double(Double.parseDouble(i+""), Double.parseDouble("0")));
            }
        	 **/
			for (String dt : dataList) {
				if (!"".equals(dt))
					input.add(new Point2D.Double(Double.parseDouble(dt), Double.parseDouble("0")));
			}
        	 
            XMeans<Point2D> xmeans = new XMeans<Point2D>(new EuclidDistanceMetric2D());
            ClusterInfo<Point2D> result = xmeans.execute(input, clusterLabels.length);
            System.out.println("clusterLabels.length:"+clusterLabels.length);
            if(PRINT_DETAIL)
            	_logger.info("number of clusters: " + result.K);
           
            int[] clusterDataCount = new int[result.K];
            PrintWriter writer = new PrintWriter(metDataType+"-out-fcm.txt", "UTF-8");
            System.out.println("=========================print data "+metDataType+" start==============================================");
            for (int i = 0; i < input.size(); ++i) {
                Point2D p = input.get(i);
                int clusterID = result.clusterAssignment[i];
                //System.out.println(String.format("%f\t%d\t%s", p.getX(), clusterID, metDataType+"<"+clusterLabels[clusterID]+">"));
                writer.println(String.format("%f\t%d\t%s", p.getX(), clusterID, metDataType+"<"+clusterLabels[clusterID]+">"));
                clusterDataCount[clusterID]++;
            }
            writer.close();
            System.out.println("=========================print data "+metDataType+" end==============================================");
			if (PRINT_DETAIL) {
				System.out.println("---------number of elements in each cluster--------");
				for (int i = 0; i < clusterDataCount.length; ++i) {
					System.out.println("cluster " + i + " has " + clusterDataCount[i] + " elements.");
				}
				System.out.println("---------cluster centroids----------");
				for (Point2D p : result.centroid) {
					System.out.println(String.format("%f\t%f\t%d", p.getX(), p.getY(), -1));
				}
			}
            //---------triangular form------------
			if(PRINT_DETAIL)
				System.out.println("---------triangular form------------");
            double[]  clusterPoints = new double[result.K];
            double[]  clusterTrianglePoints = new double[result.K+2];
            int i=0;
            for (Point2D p : result.centroid) {
            	if(PRINT_DETAIL)
            		System.out.println(String.format("%f\t%f\t%d", p.getX(), p.getY(), -1));
                clusterPoints[i]=p.getX();
                clusterLabelsMap.put(Double.toString(p.getX()), i);
                i++;
            }
            java.util.Arrays.sort(clusterPoints);
            double startP=clusterPoints[0]-(clusterPoints[1]-clusterPoints[0]);
            
            clusterTrianglePoints[0]=startP;
            
            for(int k=1;k<=clusterPoints.length;++k)
            	clusterTrianglePoints[k]=clusterPoints[k-1];
            
            double endP=clusterPoints[clusterPoints.length-1]+(clusterPoints[clusterPoints.length-1]-clusterPoints[clusterPoints.length-2]);
            
            clusterTrianglePoints[clusterTrianglePoints.length-1]=endP;
            
			if (PRINT_DETAIL) {
				for (int m = 0; m < clusterTrianglePoints.length; ++m) {
					System.out.println(m + ".point of line:" + clusterTrianglePoints[m]);
					// System.out.println(m+".cluster triangle:"+ "(" +
					// clusterTrianglePoints[0]+", 0) ("+
					// clusterTrianglePoints[m+1]+", 1)
					// ("+clusterTrianglePoints[clusterTrianglePoints.length-1]+",0)");

				}
			}
            
			System.out.println("");
            System.out.println("FUZZIFY "+metDataType+"_in");
            for(int m=1; m<clusterTrianglePoints.length-1;++m) {
            	//System.out.println(m+".cluster:"+clusterTrianglePoints[m]);
            	//System.out.println(m+".cluster triangle: "+ clusterLabels[clusterLabelsMap.get(Double.toString(clusterTrianglePoints[m])).intValue()] + " := (" + clusterTrianglePoints[0]+", 0)  ("+ clusterTrianglePoints[m]+", 1)  ("+clusterTrianglePoints[clusterTrianglePoints.length-1]+", 0)");
            	System.out.println("\tTERM "+clusterLabels[clusterLabelsMap.get(Double.toString(clusterTrianglePoints[m])).intValue()].replaceAll("-", "_") + "_in := (" + clusterTrianglePoints[m-1]+", 0)  ("+ clusterTrianglePoints[m]+", 1)  ("+clusterTrianglePoints[m+1]+", 0);");
            
            }
            System.out.println("END_FUZZIFY");
            
            System.out.println("");
            
            System.out.println("DEFUZZIFY "+metDataType+"_out");
            for(int m=1; m<clusterTrianglePoints.length-1;++m) {
            	//System.out.println(m+".cluster:"+clusterTrianglePoints[m]);
            	//System.out.println(m+".cluster triangle: "+ clusterLabels[clusterLabelsMap.get(Double.toString(clusterTrianglePoints[m])).intValue()] + " := (" + clusterTrianglePoints[0]+", 0)  ("+ clusterTrianglePoints[m]+", 1)  ("+clusterTrianglePoints[clusterTrianglePoints.length-1]+", 0)");
            	System.out.println("\tTERM "+clusterLabels[clusterLabelsMap.get(Double.toString(clusterTrianglePoints[m])).intValue()].replaceAll("-", "_") + "_out := (" + clusterTrianglePoints[m-1]+", 0)  ("+ clusterTrianglePoints[m]+", 1)  ("+clusterTrianglePoints[m+1]+", 0);");
            
            }
            System.out.println("\tMETHOD : COG;");
            System.out.println("\tDEFAULT := 0;");
            System.out.println("END_DEFUZZIFY");
            
            //---------triangular form------------
        }
        finally {
//            if (faithful != null)
//                faithful.close();
        }
    }

	private List<String> readFile(String fileName) {
		List<String> resultList = new ArrayList<String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				resultList.add(sCurrentLine);
				//System.out.println(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		return resultList;

	}

}
