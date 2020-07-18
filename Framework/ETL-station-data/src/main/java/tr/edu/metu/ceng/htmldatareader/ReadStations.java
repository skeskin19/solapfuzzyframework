package tr.edu.metu.ceng.htmldatareader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ReadStations {
	private static final String FILENAME = "stations.txt";
	
   public static void main(String[] args) {
	   Map<String, Station> stationList = new HashMap<String, Station>();
	   
      try {
    	  File input = new File("aall2.htm");
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
            try {
				Element row = tableRowElements.get(i);
				//System.out.println("row:"+i);
				Elements rowItems = row.select("td");
				String id = rowItems.get(1).text();
				String city_id = rowItems.get(3).text();
				String town_name = rowItems.get(4).text();
				String station_name = rowItems.get(5).text();
				String latitude = (rowItems.get(5).attr("title").split("#")[2]).split(":")[1];
				String longitude = (rowItems.get(5).attr("title").split("#")[3]).split(":")[1];
				String altitude_m = (rowItems.get(5).attr("title").split("#")[1]).split(":")[1];
				Station station = new Station(id, station_name, latitude.trim(), longitude.trim(), altitude_m.trim(), city_id, town_name);

				System.out.println("station "+i+":"+station.toString());
				stationList.put(id, station);
			} catch (Exception e) {
				e.printStackTrace();
			}
         }
         String content = "id|station_name|latitude|longitude|altitude_m|town_name|city_name\n";
         Set<String> keys= stationList.keySet();
         for(String id: keys) {
        	 Station s = stationList.get(id);
        	 if(s.getId()!=null && !"".equals(s.getId()))
        	 content+=s.getId()+"|"+s.getStation_name()+"|"+s.getLatitude()+"|"+s.getLongitude()+"|"+s.getAltitude_m()+"|"+s.getTown_name()+"|"+s.getCity_id()+"\n";
         }
         writeToFile(content);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   private static void writeToFile(String content) {
	   BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

   }
}