package tr.edu.metu.ceng.apriori;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TrainTestSplitFuzzy {
	
	public static String allDataFile = "met-data-all-02052020-for-association-fuzzy.txt";
	public static String testIdFile = "test-split-data-id.txt";
	
	public static String trainDataFile = "met-data-train-02052020-for-association-fuzzy-1.txt";
	public static String testDataFile = "met-data-test-02052020-for-association-fuzzy-1.txt";

	public static void main(String[] args) {
		Map<String, String> allDataIdDataMap = readFile(allDataFile);
		
		Map<String, String> testIdMap = readFile(testIdFile);
		
		try {
			PrintWriter trainWriter = new PrintWriter(trainDataFile, "UTF-8");
			PrintWriter testWriter = new PrintWriter(testDataFile, "UTF-8");
	        System.out.println("=========================print data "+trainDataFile+" start==============================================");
	        System.out.println("=========================print data "+testDataFile+" start==============================================");
	        Set<String> dataKeys = allDataIdDataMap.keySet();
	        for(String id:dataKeys) {
	        	if(testIdMap.get(id)!=null)
	        		testWriter.println(allDataIdDataMap.get(id).trim());
	        	else
	        		trainWriter.println(allDataIdDataMap.get(id).trim());
	        }
	        
	        trainWriter.close();
	        testWriter.close();
		} catch(Exception ex) {
			
		}
		System.out.println("=========================print data "+trainDataFile+" end==============================================");
	    System.out.println("=========================print data "+testDataFile+" end==============================================");
	    System.out.println("End of execution");

	}
	
	private static Map<String, String> readFile(String fileName) {
		Map<String, String> resultList = new HashMap<String, String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains("#")) {
					String[] prt = sCurrentLine.split("#");
					resultList.put(prt[0]+"#", sCurrentLine);
					System.out.println(prt[0] +" "+ prt[1]);
				} else
					resultList.put(sCurrentLine+"#", "1");
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

}
