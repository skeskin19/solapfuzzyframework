
package tr.edu.metu.ceng.sk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.xerial.util.log.Logger;

public class TermGeneratorMonthDay {
	
	
    private static Logger _logger = Logger.getLogger(TermGeneratorMonthDay.class);
   
    @Test
    public void faithful() throws Exception {

    	replaceRegexText("days.txt", "day<#>");
    	replaceRegexText("months.txt", "month<#>");
    }

	public void replaceRegexText(String fileName, String textRegex) throws Exception {
        List<String> input = new ArrayList<String>();

        try {

        	List<String> dataList = readFile(fileName);
        	
			for (String dt : dataList) {
				if (!"".equals(dt))
					input.add(dt);
			}
        	
            PrintWriter writer = new PrintWriter(fileName+"-out.txt", "UTF-8");
            System.out.println("=========================print data "+fileName+" start==============================================");
            for (int i = 0; i < input.size(); ++i) {
            	String p = input.get(i);
                //System.out.println(String.format("%f\t%d\t%s", p.getX(), clusterID, metDataType+"<"+clusterLabels[clusterID]+">"));
                writer.println(textRegex.replaceAll("#", p));
            }
            writer.close();
           
            System.out.println("finish");
            
            //---------triangular form------------
        }
        finally {
//            if (faithful != null)
//                faithful.close();
        }
    }

	private List<String> readFile(String fileName) {
		List<String> resultList = new ArrayList<String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				resultList.add(sCurrentLine);
				//System.out.println(sCurrentLine);
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
