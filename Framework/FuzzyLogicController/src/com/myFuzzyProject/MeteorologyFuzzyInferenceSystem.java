package com.myFuzzyProject;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MeteorologyFuzzyInferenceSystem {
	public static void main(String[] args) throws Exception {
		String filename = "meteorology_3.fcl";
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		// Set inputs
		fb.setVariable("sunshine_hour_in", 9.8);
		//fb.setVariable("sunny", 8.4);

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("actual_pressure_out").defuzzify();

		Variable predictedHot = fb.getVariable("actual_pressure_out");
		// Print ruleSet
		System.out.println(fb);
		System.out.println("low_out-value: " + predictedHot.getValue());
		System.out.println("low_out-membership: " + predictedHot.getMembership("low_out"));
		System.out.println("normal_out-membership: " + predictedHot.getMembership("normal_out"));
		System.out.println("high_out-membership: " + predictedHot.getMembership("high_out"));
		System.out.println("very_high_out-membership: " + predictedHot.getMembership("very_high_out"));

	}

}
