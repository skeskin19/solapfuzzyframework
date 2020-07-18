package tr.edu.metu.ceng.htmldatareader;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TableEg {
   public static void main(String[] args) {
      try {
    	  File input = new File("aall.htm");
    	  Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
         Elements tableElements = doc.select("table");

         Elements tableHeaderEles = tableElements.select("thead tr th");
         System.out.println("headers");
         for (int i = 0; i < tableHeaderEles.size(); i++) {
            System.out.println(tableHeaderEles.get(i).text());
         }
         System.out.println();

         Elements tableRowElements = tableElements.select(":not(thead) tr");

         for (int i = 0; i < tableRowElements.size(); i++) {
            Element row = tableRowElements.get(i);
            System.out.println("row:"+i);
            Elements rowItems = row.select("td");
            for (int j = 0; j < rowItems.size(); j++) {
               System.out.println(rowItems.get(j).text());
               if(!rowItems.get(j).attr("title").isEmpty())
               System.out.println(rowItems.get(j).attr("title"));
            }
            System.out.println();
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}