package com.myFuzzyProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.myFuzzyProject.bean.MetDataBean;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class MeteorologyFuzzyInferenceSystem4_relative_humidity {
	private static final String FILE_NAME = "meteorological-data-relative-humidity.csv";
	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		
		DataHolder.fillRelativeHumidityDataMap2();
		List<MetDataBean> records = loadMeteorologicalData();
		
		String filename = "meteorology_7_s005_c05.fcl";//meteorology_6
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		System.out.println("Day-Month-Year\tActual-Data\tPredicted-Data\tRamdomForest-Prediction\tDiff\tDiff-ratio");
		double totalActual = 0.0;
		double totalDiff = 0.0;
		int totalPredicted = 0;
		int printResultType = 2;
		for(MetDataBean mb:records) {
			// Get default function block
			FunctionBlock functionBlock = fis.getFunctionBlock("meteorology");
	
			// Set inputs
			functionBlock.setVariable("actual_pressure_in", Double.parseDouble(mb.getActual_pressure()));
			functionBlock.setVariable("temperature_in", Double.parseDouble(mb.getTemperature()));
			functionBlock.setVariable("cloudiness_in", Double.parseDouble(mb.getCloudiness()));
			functionBlock.setVariable("rainfall_in", Double.parseDouble(mb.getRainfall()));
			functionBlock.setVariable("relative_humidity_in", Double.parseDouble(mb.getRelative_humidity()));
			functionBlock.setVariable("sunshine_hour_in", Double.parseDouble(mb.getSunshine_hour()));
			functionBlock.setVariable("vapor_pressure_in", Double.parseDouble(mb.getVapor_pressure()));
			functionBlock.setVariable("wind_speed_in", Double.parseDouble(mb.getWind_speed()));
			
			// Evaluate
			functionBlock.evaluate();
			
			double actualValue = Double.parseDouble(mb.getActual_2016());
			
			functionBlock.getVariable("relative_humidity_out").defuzzify();
			double predictedDefuzzifiedData = functionBlock.getVariable("relative_humidity_out").getValue();
			double diff = Math.abs(actualValue-predictedDefuzzifiedData);
			double ratio = (diff/actualValue) * 100;
			String randomForestPrediction = DataHolder.getPredictedValue(mb.getYear()+"-"+mb.getMonth()+"-"+ mb.getDay());
			
			//if(randomForestPrediction==null && predictedTemperature!=0.0)
			System.out.println(mb.getDay()+"-"+mb.getMonth()+"-"+mb.getYear()+"\t"+actualValue+"\t"+predictedDefuzzifiedData+"\t"+randomForestPrediction+"\t"+diff+"\t"+ratio);
			
			if(printResultType==1)
			if(randomForestPrediction!=null) {
				totalDiff = totalDiff+Math.abs(actualValue-Double.parseDouble(randomForestPrediction));
				totalActual = totalActual+actualValue;
				totalPredicted++;
			}
			
			if(printResultType==2)
			if(predictedDefuzzifiedData!=0.0) {
				totalDiff = totalDiff+diff;
				totalActual = totalActual+actualValue;
				totalPredicted++;
			}
			
			if(printResultType==3)
			if(predictedDefuzzifiedData!=0.0 && randomForestPrediction!=null) {
				totalDiff = totalDiff+diff;
				totalActual = totalActual+actualValue;
				totalPredicted++;
			}
			
			if(printResultType==4)
			if(predictedDefuzzifiedData!=0.0 && randomForestPrediction!=null) {
				totalDiff = totalDiff+Math.abs(actualValue-Double.parseDouble(randomForestPrediction));
				totalActual = totalActual+actualValue;
				totalPredicted++;
			}
			
			
			//System.out.println("actual -> temperature: " + mb.getTemperature());
			//System.out.println("predicred -> temperature_out: " + functionBlock.getVariable("temperature_out").getValue());
		}
		
		if(printResultType==1) System.out.println("Random Forest results for random forest points!");
		if(printResultType==2) System.out.println("Fuzzy Association Rule results for all poinst!");
		if(printResultType==3) System.out.println("Fuzzy Association Rule results for random forest points!");
		if(printResultType==4) System.out.println("Random Forest results for random forest points!");
		
		System.out.println("Mean Absolute Error:"+" Total Diff:"+totalDiff+" Number of Predicted:"+totalPredicted+" [(totalDiff/totalPredicted)] =>"+(totalDiff/totalPredicted)+" degrees.");
		System.out.println("mean absolute percentage error (MAPE): mape = 100 * (errors / test_labels) =>"+(100*(totalDiff/totalActual)));
		System.out.println("accuracy = 100 - np.mean(mape)=>"+(100 - ((100*(totalDiff/totalActual))) )+" %");
		
		long endTime = System.currentTimeMillis();
		System.out.println("================execution time================");
		long execTime = (endTime-startTime);
		
		System.out.println("execution time:"+execTime+" ms | "+execTime/1000 + " sec.");
	}

	private static List<MetDataBean> loadMeteorologicalData() {
		List<MetDataBean> records = new ArrayList<MetDataBean>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        records.add(new MetDataBean(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11]));
		    }
		} catch (Exception e) {
			System.out.println("Error on reading file:"+FILE_NAME);
		}
		return records;
	}

}
