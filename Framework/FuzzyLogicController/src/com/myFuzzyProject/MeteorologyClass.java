package com.myFuzzyProject;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class MeteorologyClass {
	public static void main(String[] args) throws Exception {
		String filename = "meteorology.fcl";
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);

		// Set inputs
		fb.setVariable("rainy", 5.1);
		fb.setVariable("sunny", 8.4);

		// Evaluate
		fb.evaluate();

		// Show output variable's chart
		fb.getVariable("hot").defuzzify();

		Variable predictedHot = fb.getVariable("hot");
		// Print ruleSet
		System.out.println(fb);
		System.out.println("Hot-value: " + predictedHot.getValue());
		System.out.println("Hot-much-membership: " + predictedHot.getMembership("much"));

	}

}
