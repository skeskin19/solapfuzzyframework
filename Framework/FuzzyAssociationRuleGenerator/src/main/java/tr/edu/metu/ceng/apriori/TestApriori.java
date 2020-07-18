package tr.edu.metu.ceng.apriori;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.mrapp.apriori.Apriori;
import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.Output;
import de.mrapp.apriori.RuleSet;
import de.mrapp.apriori.Transaction;
import de.mrapp.apriori.metrics.Confidence;
import de.mrapp.apriori.metrics.Leverage;
import de.mrapp.apriori.metrics.Lift;
import de.mrapp.apriori.metrics.Support;
import tr.edu.metu.ceng.sk.DataIterator;
import tr.edu.metu.ceng.sk.NamedItem;

public class TestApriori {

	private static String DATA_FILE = "all-clustered-met-data.txt";//"data4.txt"
	public static void main(String[] args) {
		File inputFile = new File(DATA_FILE);
		/*
		double minSupport = 0.5;
		Apriori<NamedItem> apriori = new Apriori.Builder<NamedItem>(minSupport).create();
		Iterable<Transaction<NamedItem>> iterable = () -> new DataIterator(inputFile);
		Output<NamedItem> output = apriori.execute(iterable);
		FrequentItemSets<NamedItem> frequentItemSets = output.getFrequentItemSets();
		*/
		
		double minSupport = 0.1;
		double minConfidence = 1.0;
		Apriori<NamedItem> apriori = new Apriori.Builder<NamedItem>(minSupport).generateRules(minConfidence).create();
		Iterable<Transaction<NamedItem>> iterable = () -> new DataIterator(inputFile);
		Output<NamedItem> output = apriori.execute(iterable);
		RuleSet<NamedItem> ruleSet = output.getRuleSet();
		
		System.out.println("ruleSet:\n"+ruleSet);
		
		NumberFormat decimalFormat = new DecimalFormat("#0.00"); 
		        
		System.out.println("RULEBLOCK No1");
		System.out.println("\tAND : MIN;");
		System.out.println("\tACT : MIN;");
		System.out.println("\tACCU : MAX;");
		System.out.println("");
		int i=1;
		for(AssociationRule<NamedItem> rl:ruleSet) {
			//System.out.println((i++) + ".rule:"+rl);
			//System.out.println("support:"+rl.getSupport());
			//System.out.println("body:"+rl.getBody());
			//System.out.println("head:"+rl.getHead());
			//System.out.println("rule toString:"+rl.toString());
			//System.out.println("---head-------");
			StringBuilder leftSide = new StringBuilder();
			
			String ruleDef = "\tRULE " + (i++) + " : IF ";
			int k=0;
			for(NamedItem st:rl.getBody()) {
				leftSide.append(getPredicate(st.toString(), true));
				k++;
				if(k < rl.getBody().getSize())
					leftSide.append(" AND ");
				//System.out.println("item:"+st.toString());
			}
			
			k=0;
			leftSide.append(" THEN ");
			
			for(NamedItem st:rl.getHead()) {
				String rightSide = getPredicate(st.toString(), false);
				k++;
				
				System.out.println(ruleDef+leftSide.toString()+rightSide+";");
				ruleDef = "\tRULE " + (i++) + " : IF ";
			}
			//System.out.println("---body-------");
			//System.out.println();
			//System.out.println("=====================================");
	        /*
			StringBuilder stringBuilder = new StringBuilder();
	        
			stringBuilder.append(" support = ");
			stringBuilder.append(decimalFormat.format(new Support().evaluate(rl)));
			stringBuilder.append(", confidence = ");
			stringBuilder.append(decimalFormat.format(new Confidence().evaluate(rl)));
			stringBuilder.append(", lift = ");
			stringBuilder.append(decimalFormat.format(new Lift().evaluate(rl)));
			stringBuilder.append(", leverage = ");
			stringBuilder.append(decimalFormat.format(new Leverage().evaluate(rl)));

			System.out.println(stringBuilder.toString());
			*/
		}
		System.out.println();
		System.out.println("END_RULEBLOCK");
	}
	private static String getPredicate(String rule, boolean input) {
		String pred = rule.substring( 0, rule.indexOf("<"));
		String attr = rule.substring(rule.indexOf("<")+1, rule.length()-1); 
		
		if(input) {
			pred = pred + "_in";
			attr = attr + "_in";
		}
		else {
			pred = pred + "_out";
			attr = attr + "_out";
		}
		return pred+" IS "+attr;
	}

}
