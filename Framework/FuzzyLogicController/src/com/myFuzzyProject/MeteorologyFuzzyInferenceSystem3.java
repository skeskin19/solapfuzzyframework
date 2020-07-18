package com.myFuzzyProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.myFuzzyProject.bean.MetDataBean;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MeteorologyFuzzyInferenceSystem3 {
	private static final String FILE_NAME = "meteorological-data-all.csv";
	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) throws Exception {
		List<MetDataBean> records = loadMeteorologicalData();
		
		String filename = "meteorology_5.fcl";
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock functionBlock = fis.getFunctionBlock("meteorology");

		// Set inputs
		functionBlock.setVariable("actual_pressure_in", Double.parseDouble(records.get(0).getActual_pressure()));
		functionBlock.setVariable("cloudiness_in", Double.parseDouble(records.get(0).getCloudiness()));
		functionBlock.setVariable("rainfall_in", Double.parseDouble(records.get(0).getRainfall()));
		functionBlock.setVariable("relative_humidity_in", Double.parseDouble(records.get(0).getRelative_humidity()));
		functionBlock.setVariable("sunshine_hour_in", Double.parseDouble(records.get(0).getSunshine_hour()));
		functionBlock.setVariable("vapor_pressure_in", Double.parseDouble(records.get(0).getVapor_pressure()));
		functionBlock.setVariable("wind_speed_in", Double.parseDouble(records.get(0).getWind_speed()));
		
		/*
		// Evaluate
		functionBlock.evaluate();

		// Show output variable's chart
		functionBlock.getVariable("actual_pressure_out").defuzzify();
		functionBlock.getVariable("temperature_out").defuzzify();

		Variable predictedActPres = functionBlock.getVariable("actual_pressure_out");
		System.out.println(functionBlock);
		System.out.println("predictedActPres: " + predictedActPres.toString());
		System.out.println("low_out-value: " + predictedActPres.getValue());
		System.out.println("low_out-membership: " + predictedActPres.getMembership("low_out"));
		System.out.println("normal_out-membership: " + predictedActPres.getMembership("normal_out"));
		System.out.println("high_out-membership: " + predictedActPres.getMembership("high_out"));
		System.out.println("very_high_out-membership: " + predictedActPres.getMembership("very_high_out"));
		
		System.out.println("=====================================");
		
		*/
		
		
		// Evaluate
		functionBlock.evaluate();
		
		functionBlock.getVariable("temperature_out").defuzzify();
		System.out.println("actual -> temperature: " + records.get(0).getTemperature());
		System.out.println("predicred -> temperature_out: " + functionBlock.getVariable("temperature_out").getValue());
		
		Variable predictedHot = functionBlock.getVariable("temperature_out");
		System.out.println("predictedHot: " + predictedHot.toString());
		System.out.println("boiling_out-value: " + predictedHot.getValue());
		System.out.println("boiling_out-membership: " + predictedHot.getMembership("boiling_out"));
		System.out.println("very_hot_out-membership: " + predictedHot.getMembership("very_hot_out"));
		System.out.println("hot_out-membership: " + predictedHot.getMembership("hot_out"));
		System.out.println("high_out-membership: " + predictedHot.getMembership("high_out"));
		JFuzzyChart.get().chart(predictedHot, predictedHot.getDefuzzifier(), true);
		
		// Show each rule (and degree of support)
		System.out.println("Rule blocks--------------------------");
		HashMap<String, RuleBlock> ruleBloks = fis.getFunctionBlock("meteorology").getRuleBlocks();
		Set<String> keys = ruleBloks.keySet();
		for (String ruleName : keys) {
			System.out.println("Rule :" + ruleName);
			for (Rule r : fis.getFunctionBlock("meteorology").getFuzzyRuleBlock(ruleName))
				System.out.println(r);
		}
		System.out.println("Sorted rule blocks-------------------");
		List<RuleBlock> sortedRuleBlocks = fis.getFunctionBlock("meteorology").ruleBlocksSorted();
		for(RuleBlock rb:sortedRuleBlocks) {
			for(Rule r:rb.getRules()){
				System.out.println(r);
			}
		}
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
