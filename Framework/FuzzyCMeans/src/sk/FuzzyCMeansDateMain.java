package sk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import sk.util.FCMUtil;
import tr.edu.metu.ceng.sk.fuzzy.Centroid;
import tr.edu.metu.ceng.sk.fuzzy.FuzzyDemoPanel;

public class FuzzyCMeansDateMain {

	
	FuzzyDemoPanel fd = null;
	private static DecimalFormat df2 = new DecimalFormat("#.#"); //#.##
	private static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
	
	public static void main(String[] args) {
		FuzzyCMeansDateMain mn = new FuzzyCMeansDateMain();
		df2.setRoundingMode(RoundingMode.DOWN);
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		df2.setDecimalFormatSymbols(otherSymbols);
		mn.createOnlyFuzzyCMeansSet();
	}
	
	public void createOnlyFuzzyCMeansSet() {
		Date dt = new Date();
		Long startTime = dt.getTime();
		fd = new FuzzyDemoPanel(false);
		
        System.out.println("read average values....");
        //read average values
        List<String> averagesList = readAverageFile(1);
       
        for (String avg:averagesList) {
        	
        	System.out.println(avg+" "+getSeason(avg));
        	
        	
        	
        	//fd.addPoint(Double.parseDouble(avg));
        }
        /**
        String[] seasonLabels={ "winter", "spring", "summer", "fall" };
        
		fd.applyFuzzyCMeansAlgorithm(seasonLabels.length);
		
		System.out.println("Get Calculated Fuzzy Values.......");
		System.out.println("-------------------------------------------------------------------------------------");
		//fd.printGeneratedFuzzyValues();
		
		HashMap<Double, double[]> calculatesValues = fd.getCalculatedValues(seasonLabels);
		
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("print fuzzy values:");
		printFuzzyValues(calculatesValues, seasonLabels);
		System.out.println("DrawingExercise.createFuzzyCMeansSet =>...write to database...!!!");
		//JDBCStatementSelect.insertFuzzyValues(calculatesValues);
		System.out.println("insertion is completed!");
		
		System.out.println("-------------average values FUZZY set-1----------------------------------");
		printAverageValuesFuzzy(averagesList, calculatesValues, seasonLabels);
		System.out.println("-------------average values FUZZY set-2----------------------------------");
		
		
		printClusters();
		**/
		Date dt2 = new Date();
		Long endTime = dt2.getTime();
		
		System.out.println("Operation Time:"+ (endTime-startTime) + " msecs = " + ((endTime-startTime)/(1000*60)) + " minutes.");
	}

	private String getSeason(String dtStr) {
		/**
		 * 	fall	923 - 1221
			winter	1221 - 0321
			spring	0321 - 0621
			summer	0621 - 923
		 */
		int dtVal = Integer.parseInt(dtStr);
		if(dtVal>=923 && dtVal < 1221)
			return "fall";
		else if(dtVal>=1221 || dtVal < 321)
			return "winter";
		else if(dtVal>=321 && dtVal < 621)
			return "spring";
		else if(dtVal>=621 && dtVal < 923)
			return "summer";
		return null;
	}

	private void printAverageValuesFuzzy(List<String> averagesList, HashMap<Double, double[]> calculatedValues, String[] clusterLabels) {
		for(String avg:averagesList) {
			double[] fuzzyVal = calculatedValues.get(Double.parseDouble(avg));
			System.out.print(avg + "\t"); 
			System.out.print("season"+" ["); 
			System.out.print(getMaxClusterLabel(fuzzyVal, clusterLabels));
			System.out.print("]\n");
		}
		
	}

	private void printClusters() {
		for(Centroid c: fd.getClusters()) {
    		System.out.println("cluster: "+c.getCentroidName()+" c.m_x="+c.getX());
    	}
		
	}

	private void printFuzzyValues(HashMap<Double, double[]> calculatedValues, String[] clusterLabels) {
		Set<Double> keyVals = calculatedValues.keySet();
		System.out.println("Cluster Labels:"+clusterLabels);
		for (Double val : keyVals) {

			// insertFuzzyRow(val, calculatedValues.get(val));
			double[] fuzzyVal = calculatedValues.get(val);
			
			double max = getMax(fuzzyVal);

			System.out.print(val + " => max [ " + getMaxClusterLabel(fuzzyVal, clusterLabels) + " ] ");
			for (int i = 0; i < fuzzyVal.length; ++i)
				System.out.print(fuzzyVal[i] + "; ");
			System.out.print("\n");
		}
	}
	
	private double getMax(double[] array) {
		double max = array[0];
	      for (int j = 1; j < array.length; j++) {
	          if (Double.isNaN(array[j])) {
	              return Double.NaN;
	          }
	          if (array[j] > max) {
	              max = array[j];
	          }
	      }
	  
	      return max;
	}
	
	private String getMaxClusterLabel(double[] array, String[] clusterLabels) {
		double max = array[0];
		int ind=0;
	      for (int j = 1; j < array.length; j++) {
	          if (array[j] > max) {
	              max = array[j];
	              ind=j;
	          }
	      }
	  
	      return clusterLabels[ind]+"("+df2.format(max)+")";
	}
	
	private String getQueryTableString(int queryTableInd){
		switch (queryTableInd) {
		case 1:
			return FCMUtil.AVG_PRESSURE_QUERY;
		case 2:
			return FCMUtil.CLOUDINESS_QUERY;
		case 3:
			return FCMUtil.RAINFALL_MANUEL_QUERY;
		case 4:
			return FCMUtil.RAINFALL_OMGI_QUERY;
		case 5:
			return FCMUtil.HUMIDITY_QUERY;
		case 6:
			return FCMUtil.SUNSHINE_QUERY;
		case 7:
			return FCMUtil.VAPOR_PRESSURE_QUERY;
		case 8:
			return FCMUtil.TEMPERATURE_QUERY;
		case 9:
			return FCMUtil.WIND_DIRECTION_SPEED_QUERY;
		case 10:
			return FCMUtil.WIND_SPEED_QUERY;
		default:
			return "NO";
		}
	}
	
	private String[] getClusterLabelList(int queryTableInd){
		switch (queryTableInd) {
		case 1:
			return FCMUtil.avgpressureLabels;
		case 2:
			return FCMUtil.cloudnessLabels;
		case 3:
			return FCMUtil.rainfallLabels;
		case 4:
			return FCMUtil.rainfallLabels;
		case 5:
			return FCMUtil.humidityLabels;
		case 6:
			return FCMUtil.sunshineLabels;
		case 7:
			return FCMUtil.vaporpressureLabels;
		case 8:
			return FCMUtil.temperatureLabels;
		case 9:
			return FCMUtil.windspeedLabels;
		case 10:
			return FCMUtil.windspeedLabels;
		default:
			return null;
		}
	}
	
	private List<String> readAverageFile(int queryTableInd) {
		List<String> resultList = new ArrayList<String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader("dates.txt");//getAverageFileName(queryTableInd)
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
	
	private String getAverageFileName(int queryTableInd){
		switch (queryTableInd) {
		case 1:
			return "actual_pressure.txt";
		case 2:
			return "cloudiness.txt";
		case 3:
			return "rainfall.txt";
		case 4:
			return "rainfall.txt";
		case 5:
			return "relative_humidity.txt";
		case 6:
			return "sunshine_hour.txt";
		case 7:
			return "vapor_pressure.txt";
		case 8:
			return "temperature.txt";
		case 9:
			return "wind_speed.txt";
		case 10:
			return "wind_speed.txt";
		default:
			return null;
		}
	}
	
	private String getFuzzyPrefixType(int queryTableInd){
		switch (queryTableInd) {
		case 1:
			return "actual-pressure";
		case 2:
			return "cloudiness";
		case 3:
			return "rainfall";
		case 4:
			return "rainfall";
		case 5:
			return "relative-humidity";
		case 6:
			return "sunshine-hour";
		case 7:
			return "vapor-pressure";
		case 8:
			return "temperature";
		case 9:
			return "wind-speed";
		case 10:
			return "wind-speed";
		default:
			return null;
		}
	}
}
