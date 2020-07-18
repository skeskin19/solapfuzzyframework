package com.myFuzzyProject;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MeteorologyClass_v3_rainfall {
	public static void main(String[] args) throws Exception {
		/**
		 * RULE 1 : IF relative_humidity_in IS overmuch_in AND temperature_in IS low_in THEN rainfall_out IS very_high_out;
		 * RULE 2 : IF temperature_in IS high_in AND actual_pressure_in IS very_high_in THEN wind_speed_out IS very_high_out;
		 */
		String filename = "meteorology_rainfall_18062019.fcl";
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		// Set inputs
		fb.setVariable("relative_humidity_in", 72);
		fb.setVariable("temperature_in", 9.1);
		//fb.setVariable("sunny", 8.4);

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("rainfall_out").defuzzify();

		Variable predictedHot = fb.getVariable("rainfall_out");
		// Print ruleSet
		System.out.println(fb);
		System.out.println("very_high_out-value: " + predictedHot.getValue());
		System.out.println("low_out-membership: " + predictedHot.getMembership("low_out"));
		System.out.println("normal_out-membership: " + predictedHot.getMembership("normal_out"));
		System.out.println("high_out-membership: " + predictedHot.getMembership("high_out"));
		System.out.println("very_high_out-membership: " + predictedHot.getMembership("very_high_out"));
		
		JFuzzyChart.get().chart(fb);

	}

}
