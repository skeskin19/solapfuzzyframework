package tr.edu.metu.ceng.apriori;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class AprioriFuzzyAssociationRuleGenerator {

	private static String DATA_FILE = "met-data-train-for-association-fuzzy.txt";
	static Map<String, Integer> leftSideRules = new HashMap<String, Integer>();
	static Map<String, Integer> rightSideRules = new HashMap<String, Integer>();
	static Map<String, AssociationRule<NamedItem>> ruleMap = new HashMap<String, AssociationRule<NamedItem>>();
	static Map<String, Double> importantVariables = new HashMap<String, Double>();
	
	public static void main(String[] args) {
		File inputFile = new File(DATA_FILE);
		/*
		double minSupport = 0.5;
		Apriori<NamedItem> apriori = new Apriori.Builder<NamedItem>(minSupport).create();
		Iterable<Transaction<NamedItem>> iterable = () -> new DataIterator(inputFile);
		Output<NamedItem> output = apriori.execute(iterable);
		FrequentItemSets<NamedItem> frequentItemSets = output.getFrequentItemSets();
		*/
		
		
		double minSupport = 0.03;
		double minConfidence = 0.01;
		Apriori<NamedItem> apriori = new Apriori.Builder<NamedItem>(minSupport).generateRules(minConfidence).create();
		Iterable<Transaction<NamedItem>> iterable = () -> new DataIterator(inputFile);
		Output<NamedItem> output = apriori.execute(iterable);
		RuleSet<NamedItem> ruleSet = output.getRuleSet();
		
		
		try{
			long startTime = System.currentTimeMillis();
			
			PrintWriter writer = new PrintWriter("Fuzzy-Rule-Set-25022020.txt", "UTF-8");
			
			writer.println("ruleSet:\n"+ruleSet);
		
			NumberFormat decimalFormat = new DecimalFormat("#0.00"); 
			        
			
			System.out.println("finish");
			writer.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void printImportantRules(PrintWriter writer) {
		writer.println("======================Important Rules==========================");
		Set<String> keys = ruleMap.keySet();
		int i=1;
		for(String ky:keys) {
			AssociationRule<NamedItem> rl = ruleMap.get(ky);
			writer.println("RULEBLOCK No"+i);
			writer.println("\tAND : MIN;");
			writer.println("\tACT : MIN;");
			writer.println("\tACCU : MAX;");
			writer.println("");
			
			StringBuilder leftSide = new StringBuilder();
			
			String ruleDef = "\tRULE " + (i++) + " : IF ";
			int k=0;
			List<String> leftPreds = new ArrayList<String>();
			for(NamedItem st:rl.getBody()) {
				String rulePart = getPredicate(st.toString(), true);
				addLeftSideRule(rulePart);
				leftPreds.add(getPredType(st.toString()));
				leftSide.append(rulePart);
				k++;
				if(k < rl.getBody().getSize())
					leftSide.append(" AND ");
				//writer.println("item:"+st.toString());
			}
			
			k=0;
			leftSide.append(" THEN ");
			
			for(NamedItem st:rl.getHead()) {
				String rightSide = getPredicate(st.toString(), false);
				String predicateType = getPredType(st.toString());
				if("actual_pressure".equals(predicateType)) {
					addToImportantVariables(leftPreds, rl);
				}
				addRightSideRule(rightSide);
				
				writer.println(ruleDef+leftSide.toString()+rightSide+";");
				ruleDef = "\tRULE " + (i+k) + " : IF ";
				k++;
				i=(i+k);
			}
			
			writer.println("END_RULEBLOCK");
			writer.println();
		}
		
	}

	private static boolean checkRuleImportance(AssociationRule<NamedItem> prmRule, String predicateType) {
		AssociationRule<NamedItem> exstRule = ruleMap.get(predicateType);
		if(exstRule==null) {
			ruleMap.put(predicateType, prmRule);
			return true;
		}
		double exstRuleSupp = new Support().evaluate(exstRule);
		double exstRuleConf = new Confidence().evaluate(exstRule);
		double exstRuleLift = new Lift().evaluate(exstRule);
		
		double prmRuleSupp = new Support().evaluate(prmRule);
		double prmRuleConf = new Confidence().evaluate(prmRule);
		double prmRuleLift = new Lift().evaluate(prmRule);
		
		if(exstRuleSupp>=prmRuleSupp)
			return false;
		if(exstRuleConf>=prmRuleConf)
			return false;
		if(exstRuleLift>=prmRuleLift)
			return false;
		
		ruleMap.put(predicateType, prmRule);
		return true;
	}

	private static void printImportantVariables(String pred) {
		System.out.println("\n=================Important Variables=For="+pred+"============");
		Set<String> keys = importantVariables.keySet();
		double totalVal=0.0;
		for(String ky:keys) {
			System.out.println(ky+" --> "+importantVariables.get(ky));
			totalVal=totalVal+importantVariables.get(ky);
		}
		System.out.println("--------------------------------");
		for(String ky:keys) {
			System.out.println(ky+" --> "+(100*importantVariables.get(ky)/totalVal)+" %");
		}
	}
	
	private static void addToImportantVariables(List<String> leftPreds, AssociationRule<NamedItem> rl) {
		double support = (new Support()).evaluate(rl);
		double confidence = (new Confidence()).evaluate(rl);
		double lift = (new Lift()).evaluate(rl);
		double leverage = (new Leverage()).evaluate(rl);
		
		/*
		System.out.println("__________________");
		System.out.println("rule: "+rl);
		System.out.println(support +" "+ confidence +" "+ lift +" "+ leverage);
		*/
		double weight = (support * confidence * lift ) / (leverage * leftPreds.size());
		
		//System.out.println("weight:"+weight);
		
		for(String prd:leftPreds) {
			Double val = importantVariables.get(prd);
			if(val!=null)
				importantVariables.put(prd, val+weight);
			else
				importantVariables.put(prd, weight);
		}
		
	}
	
	private static void printRuleParts() {
		System.out.println("\n========================================================\n");
		Set<String> keys2 = leftSideRules.keySet();
		System.out.println("left side rules:");
		for(String key:keys2) {
			System.out.println(key+" --> "+leftSideRules.get(key));
		}
		System.out.println("\n========================================================\n");
		Set<String> keys = rightSideRules.keySet();
		System.out.println("right side rules:");
		for(String key:keys) {
			System.out.println(key+" --> "+rightSideRules.get(key));
		}
	}
	
	private static void printRulePartsTotals() {
		Map<String, Integer> leftSideRulesPredicates = new HashMap<String, Integer>();
		Map<String, Integer> rightSideRulesPredicates = new HashMap<String, Integer>();
		
		System.out.println("\n========================================================\n");
		Set<String> keys2 = leftSideRules.keySet();
		System.out.println("left side rules:");
		for(String key:keys2) {
			String[] predicates = key.split(" ");
			Integer cnt = leftSideRulesPredicates.get(predicates[0]);
			if(cnt!=null)
				leftSideRulesPredicates.put(predicates[0], cnt+leftSideRules.get(key));
			else
				leftSideRulesPredicates.put(predicates[0], leftSideRules.get(key));
		}
		Set<String> predKeys = leftSideRulesPredicates.keySet();
		for(String ky:predKeys) {
			System.out.println(ky+" --> "+leftSideRulesPredicates.get(ky));
		}
		System.out.println("\n========================================================\n");
		Set<String> keys = rightSideRules.keySet();
		System.out.println("right side rules:");
		for(String key:keys) {
			String[] predicates = key.split(" ");
			Integer cnt = rightSideRulesPredicates.get(predicates[0]);
			if(cnt!=null)
				rightSideRulesPredicates.put(predicates[0], cnt+rightSideRules.get(key));
			else
				rightSideRulesPredicates.put(predicates[0], rightSideRules.get(key));
		}
		
		Set<String> predKeys2 = rightSideRulesPredicates.keySet();
		for(String ky:predKeys2) {
			System.out.println(ky+" --> "+rightSideRulesPredicates.get(ky));
		}
		
	}
	private static void addLeftSideRule(String rulePart) {
		Integer count = leftSideRules.get(rulePart);
		if(count!=null)
			leftSideRules.put(rulePart, ++count);
		else
			leftSideRules.put(rulePart, 1);
		
	}
	private static void addRightSideRule(String rulePart) {
		Integer count = rightSideRules.get(rulePart);
		if(count!=null)
			rightSideRules.put(rulePart, ++count);
		else
			rightSideRules.put(rulePart, 1);
		
	}
	private static String getPredicate(String rule, boolean input) {
		if(!rule.contains("<"))
			return rule;
		
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

	private static String getPredType(String rule) {
		if(!rule.contains("<"))
			return rule;
		
		String pred = rule.substring( 0, rule.indexOf("<"));
		
		return pred;
	}

}
