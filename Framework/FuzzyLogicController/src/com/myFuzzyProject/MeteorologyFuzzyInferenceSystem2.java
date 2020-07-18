package com.myFuzzyProject;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MeteorologyFuzzyInferenceSystem2 {
	public static void main(String[] args) throws Exception {
		String filename = "meteorology_4.fcl";
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock functionBlock = fis.getFunctionBlock(null);

		//plot all variables in the FIZ (each LinguisticTerm in each Variable in the FUNCTION_BLOCK)
		JFuzzyChart.get().chart(functionBlock);
		
		// Set inputs
		functionBlock.setVariable("sunshine_hour_in", 9.8);
		
		functionBlock.setVariable("cloudiness_in", 1.4);
		functionBlock.setVariable("relative_humidity_in", 52.2);
		functionBlock.setVariable("vapor_pressure_in", 16.3);

		// Evaluate
		functionBlock.evaluate();
		
		//JFuzzyChart.get().chart(functionBlock.getVariable("sunshine_hour_in"), functionBlock.getVariable("sunshine_hour_in").getDefuzzifier(), true);

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
		
		JFuzzyChart.get().chart(predictedActPres, predictedActPres.getDefuzzifier(), true);
		
		System.out.println("=====================================");
		
		Variable predictedHot = functionBlock.getVariable("temperature_out");
		System.out.println("predictedHot: " + predictedHot.toString());
		System.out.println("boiling_out-value: " + predictedHot.getValue());
		System.out.println("boiling_out-membership: " + predictedHot.getMembership("boiling_out"));
		System.out.println("very_hot_out-membership: " + predictedHot.getMembership("very_hot_out"));
		System.out.println("hot_out-membership: " + predictedHot.getMembership("hot_out"));
		System.out.println("high_out-membership: " + predictedHot.getMembership("high_out"));

		JFuzzyChart.get().chart(predictedHot, predictedHot.getDefuzzifier(), true);

		// Print ruleSet
		//System.out.println(fis);

		// Show each rule (and degree of support)
		System.out.println("Rule No1");
		for (Rule r : fis.getFunctionBlock("meteorology").getFuzzyRuleBlock("No1").getRules())
			System.out.println(r);
		
		System.out.println("Rule No2");
		for (Rule r : fis.getFunctionBlock("meteorology").getFuzzyRuleBlock("No2").getRules())
			System.out.println(r);

	}

}
