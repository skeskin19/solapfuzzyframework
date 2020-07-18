package tr.edu.metu.data.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.myFuzzyProject.bean.MetDataBean;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class AydenizKuraklikAnalizi {
	private static final String KURAKLIK_FILE_NAME = "met-data-aydeniz-all.csv";
	private static final String COMMA_DELIMITER = ",";
	private static List<String> stationList;
	private static List<KuraklikData> kuraklikDataList;

	public static void main(String[] args) throws Exception {
		stationList = loadStationList();
		kuraklikDataList = loadMeteorologicalKuraklikData();
		
		for(KuraklikData dt:kuraklikDataList) {
			Double aylikKuraklikOrani = calculateAydenizAylikKuraklikOrani(dt);
			dt.setAylikKuraklikOrani(aylikKuraklikOrani);
		}
		System.out.println("\n\n\n =============YILLIK=============");
		for(String stationId:stationList) {
			for(int y=1970;y<2016;++y) {
				Double Kks = calculateAydenizYillikKuraklikOrani(stationId, Integer.toString(y));
				
				System.out.println("stationId:"+stationId+" year:"+y+" Kks:"+Kks);
			}
		}
		
	}

	
	/**
	 * 	Nks = YN12/SGs+15
	 * 	Kks = 1/Nks
	 * 
	 * 	Nks: Nemlilik katsay�s�
		Y: Ayl�k toplam ya��� (cm)
		S: Ayl�k ortalama s�cakl�k (�C)
		Gs: G�ne�lenme s�resi (%)
		N: Ayl�k ortalama nem (%)
		15: Sabite
		Kks: Kurakl�k katsay�s�
		
	 * @param dt
	 * @return
	 */
	private static Double calculateAydenizAylikKuraklikOrani(KuraklikData dt) {
		Double Nks = (Double.parseDouble(dt.getRainfall())*Double.parseDouble(dt.getRelative_humidity()))/(Double.parseDouble(dt.getTemperature())*(Double.parseDouble(dt.getSunshine_hour())/12)+15);
		
		System.out.println(dt.getStation_id()+" "+dt.getYear()+" "+dt.getMonth()+" Nks:"+Nks+" Kks:"+(1/Nks));
		if(Nks<0.4 && Nks!=0)
			System.out.println("-> check this---------");
		return Nks;
	}


	/**
	 * 	Nks = (YN/SGs+15)Np
	 * 	Kks = 1/Nks
	 * 
	 * 	Nks: Nemlilik katsay�s�
		Y: Y�ll�k toplam ya��� (cm)
		S: Y�ll�k ortalama s�cakl�k (�C)
		Gs: Y�ll�k g�ne�lenme s�resi y�zdesi
		N: Y�ll�k ortalama nem (%)
		15: Sabite
		Np: Nemli periyot y�zdesi
		Kks: Kurakl�k katsay�s�
		Np: 12 aya ait Nks de�erleri bulunur ve 0.40�dan az olanlar�n say�s� 12 den ��kar�l�r. Kalan ay say�s� 12 ye b�l�n�p Np de�eri elde edilir.
	 * 
	 * @param stationId
	 * @param year
	 * @return
	 */
	private static Double calculateAydenizYillikKuraklikOrani(String stationId, String year) {
		List<KuraklikData> partialData = queryDataByStationAndYear(stationId, year);
		Double totalYagis = 0.0;
		Double totalSicaklik = 0.0;
		Double totalGunes = 0.0;
		Double totalNem = 0.0;
		Double Np = 0.0;
		int ind = 0;
		for(KuraklikData dt:partialData) {
			totalYagis+=Double.parseDouble(dt.getRainfall());
			totalSicaklik+=Double.parseDouble(dt.getTemperature());
			totalGunes+=Double.parseDouble(dt.getSunshine_hour());
			totalNem+=Double.parseDouble(dt.getRelative_humidity());
			
			if(dt.getAylikKuraklikOrani()<0.4) {
				ind++;
				System.out.println("krk<0.4=>"+dt.getAylikKuraklikOrani()+" ind:"+ind);
			}
		}
		System.out.println("stationId:"+stationId+" year:"+year+" totalYagis:"+totalYagis+" totalSicaklik:"+totalSicaklik+" totalGunes:"+totalGunes+" totalNem:"+totalNem+" numberOfKurak:"+ind);
		
		Np =  ((12.0-(new Double(ind)))/12.0);
		
		Double Nks = (totalYagis*(totalNem/12)*Np) / ((totalSicaklik/12)*(totalGunes/12*12));
		
		System.out.println("Nks:"+Nks+" Kks:"+(1/Nks));
		
		return (1/Nks);
	}
	
	private static List<KuraklikData> queryDataByStationAndYear(String stationId, String year) {
		List<KuraklikData> dataList = new ArrayList<KuraklikData>();
		for(KuraklikData dt:kuraklikDataList) {
			if(dt.getStation_id().equals(stationId) && dt.year.equals(year))
				dataList.add(dt);
		}
		return dataList;
	}


	private static List<KuraklikData> loadMeteorologicalKuraklikData() {
		List<KuraklikData> records = new ArrayList<KuraklikData>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(KURAKLIK_FILE_NAME))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        records.add(new KuraklikData(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9]));
		    }
		} catch (Exception e) {
			System.out.println("Error on reading file:"+KURAKLIK_FILE_NAME);
			
		}
		return records;
	}
	
	private static List<String> loadStationList() {
		List<String> lst = new ArrayList<String>();
		
		lst.add("8541");
		lst.add("17037");
		lst.add("17038");
		lst.add("17040");
		lst.add("17050");
		lst.add("17064");
		lst.add("17070");
		lst.add("17084");
		lst.add("17095");
		lst.add("17096");
		lst.add("17112");
		lst.add("17130");
		lst.add("17172");
		lst.add("17190");
		lst.add("17196");
		lst.add("17199");
		lst.add("17220");
		lst.add("17244");
		lst.add("17245");
		lst.add("17261");
		lst.add("17270");
		lst.add("17280");
		lst.add("17281");
		lst.add("17298");
		lst.add("17300");
		lst.add("17302");
		lst.add("17351");
		lst.add("18275");

		return lst;
	}

}
