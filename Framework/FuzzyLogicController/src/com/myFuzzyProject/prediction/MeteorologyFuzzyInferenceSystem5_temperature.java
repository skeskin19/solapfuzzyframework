package com.myFuzzyProject.prediction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myFuzzyProject.bean.MetDataBean;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MeteorologyFuzzyInferenceSystem5_temperature {
	private static final String FCL_FILE_NAME = "meteorology-26052020.fcl";
	private static final String TEST_DATA_FILE_NAME_CRISP = "met-data-test-02052020-for-association.csv";
	private static final String TEST_DATA_FILE_NAME_FUZZY = "met-data-test-02052020-for-association-fuzzy-2.csv";
	private static final String COMMA_DELIMITER = ",";
	private static final String PREDICATION_TYPE = "temperature";
	
	public static void main(String[] args) throws Exception {
		List<MetDataBean> testRecordsCrisp = loadMeteorologicalTestDataCrisp();
		Map<String, MetDataBean> testRecordsFuzzy = loadMeteorologicalTestDataFuzzy();
		
		
		
		FIS fis = FIS.load(FCL_FILE_NAME, true);
		fis.debug = true;

		if (fis == null) {
			System.err.println("Can't load file: '" + FCL_FILE_NAME + "'");
			System.exit(1);
		}
		System.out.println(System.getProperty("user.home"));
		long startTime = System.currentTimeMillis();
		
		System.out.println(" DB Id \t" + " Predicted Value \t" + " Actual Value \t" + " Success ");
		System.out.println("-------\t" + "-----------------\t" + "--------------\t" + "---------");
		long successCnt = 0;
		for(MetDataBean mb:testRecordsCrisp) {
			// Get default function block
			FunctionBlock functionBlock = fis.getFunctionBlock("meteorology");
	
			// Set inputs
			functionBlock.setVariable("latitude_in", Double.parseDouble(mb.getLatitude()));
			functionBlock.setVariable("longitude_in", Double.parseDouble(mb.getLongitude()));
			functionBlock.setVariable("altitude_in", Double.parseDouble(mb.getAltitude_m()));
			functionBlock.setVariable("month_in", Double.parseDouble(mb.getMonth()));
			functionBlock.setVariable("day_in", Double.parseDouble(mb.getDay()));
			
			if(!"actual_pressure".equals(PREDICATION_TYPE))
				functionBlock.setVariable("actual_pressure_in", Double.parseDouble(mb.getActual_pressure()));
			if(!"cloudiness".equals(PREDICATION_TYPE))
				functionBlock.setVariable("cloudiness_in", Double.parseDouble(mb.getCloudiness()));
			if(!"rainfall".equals(PREDICATION_TYPE))
				functionBlock.setVariable("rainfall_in", Double.parseDouble(mb.getRainfall()));
			if(!"relative_humidity".equals(PREDICATION_TYPE))
				functionBlock.setVariable("relative_humidity_in", Double.parseDouble(mb.getRelative_humidity()));
			if(!"sunshine_hour".equals(PREDICATION_TYPE))
				functionBlock.setVariable("sunshine_hour_in", Double.parseDouble(mb.getSunshine_hour()));
			if(!"vapor_pressure".equals(PREDICATION_TYPE))
				functionBlock.setVariable("vapor_pressure_in", Double.parseDouble(mb.getVapor_pressure()));
			if(!"wind_speed".equals(PREDICATION_TYPE))
				functionBlock.setVariable("wind_speed_in", Double.parseDouble(mb.getWind_speed()));
			if(!"temperature".equals(PREDICATION_TYPE))
				functionBlock.setVariable("temperature_in", Double.parseDouble(mb.getTemperature()));
			
			// Evaluate
			functionBlock.evaluate();
			
			Variable defuzz = functionBlock.getVariable(PREDICATION_TYPE+"_out");
			
			System.out.println("---"+mb.getDbId()+" --- predicted crisp: "+functionBlock.getVariable(PREDICATION_TYPE+"_out").getValue()+" --- actual crisp: "+mb.getTemperature());
			
			Map<String, LinguisticTerm> linguistedTerms = defuzz.getLinguisticTerms();
			
		}
		System.out.println("Total Number of test \t: " + testRecordsCrisp.size());
		System.out.println("Total Number of success \t: " + successCnt);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Start time:"+startTime+" End time:"+endTime+" operation takes "+ ((endTime-startTime)/(1000*60))+" minutes.");
	}

	private static List<MetDataBean> loadMeteorologicalTestDataCrisp() {
		List<MetDataBean> records = new ArrayList<MetDataBean>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(TEST_DATA_FILE_NAME_CRISP))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        //d_id	station_id	latitude	longitude	altitude_m	year	month	day	actual_pressure	cloudiness	rainfall	relative_humidity	sunshine_hour	temperature	vapor_pressure	wind_speed
		        records.add(new MetDataBean(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12], values[13], values[14], values[15], null));
		    }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on reading file:"+TEST_DATA_FILE_NAME_CRISP);
		}
		return records;
	}
	
	private static Map<String, MetDataBean> loadMeteorologicalTestDataFuzzy() {
		Map<String, MetDataBean> records = new HashMap<String, MetDataBean>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(TEST_DATA_FILE_NAME_FUZZY))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        //db_id	latitude	longitude	altitude	month	day	actual_pressure	cloudiness	rainfall	relative_humidity	sunshine_hour	temperature	vapor_pressure	wind_speed
		        records.put(values[0], new MetDataBean(values[0], null, values[1], values[2], values[3], null, values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11], values[12], values[13], null));
		    }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on reading file:"+TEST_DATA_FILE_NAME_FUZZY);
		}
		return records;
	}
}
