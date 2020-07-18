package tr.edu.metu.ceng.geomondrian;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;

public class GeoMondrianTest {

    public static void main(String[] args) {
    	
    	//Locale trlocale= new Locale("us","US");

        java.io.PrintStream cout = System.out;

		//defines a JDBC connection with the Postgres-PostGIS Database
        String connectString = "jdbc:postgresql_postGIS://localhost:5444/template_postgis?user=test&password=test";
    
        //the location of the schema file that maps the database source
		String schemaFile = "MeteorologicalSchema_newData.xml";

        String mondrianConnString = "Provider=mondrian;" +
        "Jdbc=" + connectString + ";" +
        "JdbcDrivers=org.postgis.DriverWrapper;" +
        "Catalog=file:"+ schemaFile + ";";

        Connection connection = DriverManager.getConnection(
                mondrianConnString, null
        );

		//a sample SOLAP query
        /*
        final String qryString = "SELECT {[Measures].[Unit Sales]} ON columns, " +
		"Filter( "+ 
        "		{[Store].[Store City].members}, "+
        "		ST_Distance([Store].CurrentMember.Properties(\"geom\"), "+
        "			ST_GeomFromText(\"POINT (-118.383811 34.069609)\")) < 1.0 "+
        "	) ON rows " +
		"FROM SALES";
        */
        String mdxQuery = "SELECT {[Measures].[Tempature_avg], [Measures].[actual_pressure_avg], [Measures].[wind_speed2_avg]} ON COLUMNS, "
        		+ " {([DateDimension1.Date Hierarchy 0].[All Dates].[2014].[12].[21] : [DateDimension1.Date Hierarchy 0].[All Dates].[2015].[3].[21])} ON ROWS "
        		+ " FROM [MeteorologicalCube]";
        
        String mdxQuery2 = "SELECT {[Measures].[Tempature_avg], [Measures].[sunshine_hour_avg], [Measures].[relative_humidity_avg]} ON COLUMNS, "
        		+ " {[DateDimension1.Date Hierarchy 0].[All Dates]} ON ROWS "
        		+ " FROM [MeteorologicalCube] "
        		+ " WHERE [Station.StationHierarchy].[All Stations].[IC ANADOLU REGION].[Ankara]";
        
        String mdxQuery3 = "SELECT {[Measures].[Tempature_avg], [Measures].[sunshine_hour_avg], [Measures].[relative_humidity_avg]} ON COLUMNS, "
        		+ " {[DateDimension1.Date Hierarchy 0].[All Dates]} ON ROWS "
        		+ " WHERE [MeteorologicalCube] ";
        
        String mdxQuery4 = "SELECT {[Measures].[Tempature_avg], [Measures].[sunshine_hour_avg], [Measures].[relative_humidity_avg]} ON COLUMNS, "
        		+ " {([DateDimension1.Date Hierarchy 0].[All Dates].[2014].[12].[21] : [DateDimension1.Date Hierarchy 0].[All Dates].[2015].[3].[21])} ON ROWS "
        		+ " FROM [MeteorologicalCube] "
        		+ " WHERE [Station.StationHierarchy].[All Stations].[IC ANADOLU REGION].[Ankara]";
        
        String mdxQuery5 = "SELECT "
        			+ " {[Measures].[Tempature_avg], [Measures].[actual_pressure_avg], [Measures].[relative_humidity_avg]} on columns, "
        			+ " Filter( "
        				+ " {[Station].[Station City].members}, "
        				+ " ST_Within([Station].CurrentMember.Properties(\"geom\"), "
        				+ " [Station].[Station Region].[MARMARA REGION].Properties(\"geom\")) "
                    + " ) on rows "
                    + " FROM [MeteorologicalCube] "
                    + " WHERE ([DateDimension1.Date Hierarchy 0].[2016]) ";

        //System.out.println(mdxQuery5);
        Query query = connection.parseQuery(mdxQuery5);
        Result result = connection.execute(query);
        
		//return the results into the output stream (in this case was set to be STD OUT)
		result.print(new PrintWriter(cout, true));
		
		createLineChart();
    }

	private static void createLineChart() {
		LineChart_AWT chart = new LineChart_AWT("Tuples vs Years", "Number of Tuples vs years", "Years", "Number of Tuples");
		chart.setDataset(createDataset2());
		chart.createLineChart();
		
		      chart.pack( );
		      RefineryUtilities.centerFrameOnScreen( chart );
		      chart.setVisible( true );
	}
	
	private static DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(23, "Turkey", "1970");
		dataset.addValue(25, "Turkey", "1975");
		dataset.addValue(20, "Turkey", "1980");
		dataset.addValue(23, "Turkey", "1985");
		dataset.addValue(31, "Turkey", "1990");
		dataset.addValue(38, "Turkey", "2000");
		dataset.addValue(35, "Turkey", "2010");
		dataset.addValue(43, "Turkey", "2015");
		
		dataset.addValue(14, "Marmara", "1970");
		dataset.addValue(35, "Marmara", "1980");
		dataset.addValue(40, "Marmara", "1990");
		dataset.addValue(110, "Marmara", "2000");
		dataset.addValue(220, "Marmara", "2010");
		dataset.addValue(290, "Marmara", "2014");
		
		dataset.addValue(17, "Ege", "1970");
		dataset.addValue(38, "Ege", "1980");
		dataset.addValue(42, "Ege", "1990");
		dataset.addValue(115, "Ege", "2000");
		dataset.addValue(210, "Ege", "2010");
		dataset.addValue(270, "Ege", "2014");
		return dataset;
	}
	
	private static DefaultCategoryDataset createDataset2() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int i=1971;i<2018;++i) {
			int mr = getRandomVal(30, 50);
			int ege = getRandomVal(20, 40);
			int akd = getRandomVal(40, 55);
			int krd = getRandomVal(10, 30);
			int ic = getRandomVal(10, 40);
			int dogu = getRandomVal(10, 50);
			int gdogu = getRandomVal(20, 55);
			//dataset.addValue((mr+ege+akd+krd+ic+dogu+gdogu)/7, "Turkey Avg", Integer.toString(i));
			//dataset.addValue((mr+ege+akd+krd+ic+dogu+gdogu), "Turkey Total", Integer.toString(i));
			dataset.addValue(mr, "Marmara", Integer.toString(i));
			dataset.addValue(ege, "Ege", Integer.toString(i));
			dataset.addValue(akd, "Akdeniz", Integer.toString(i));
			dataset.addValue(krd, "Karadeniz", Integer.toString(i));
//			dataset.addValue(ic, "Ic Anadolu", Integer.toString(i));
//			dataset.addValue(dogu, "Dogu Anadolu", Integer.toString(i));
//			dataset.addValue(gdogu, "Guney Dogu Anadolu", Integer.toString(i));
		}
		
		return dataset;
	}
	
	private static int getRandomVal(int low, int high) {
		Random r = new Random();
		int Low = low;
		int High = high;
		int result = r.nextInt(High-Low) + Low;
		
		return result;
	}

}
