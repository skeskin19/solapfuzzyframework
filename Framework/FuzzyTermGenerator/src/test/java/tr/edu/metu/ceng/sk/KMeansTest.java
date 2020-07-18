
package tr.edu.metu.ceng.sk;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.xerial.util.FileResource;
import org.xerial.util.log.Logger;

import tr.edu.metu.ceng.sk.EuclidDistanceMetric2D;
import tr.edu.metu.ceng.sk.KMeans;
import tr.edu.metu.ceng.sk.KMeans.ClusterInfo;

public class KMeansTest
{

    private static Logger _logger = Logger.getLogger(KMeansTest.class);

    @Test
    public  void faithful() throws Exception {
        List<Point2D> input = new ArrayList<Point2D>();

        // read the faithful data set
//        BufferedReader faithful = FileResource.open(KMeansTest.class, "faithful.txt");
        try {
//            for (String line; (line = faithful.readLine()) != null;) {
//                String[] c = line.split("\\s+");
//                input.add(new Point2D.Double(Double.parseDouble(c[0]), Double.parseDouble(c[1])));
//            }
        	
            for (int i=0;i<100;i++) {
                input.add(new Point2D.Double(Double.parseDouble(i+""), Double.parseDouble("0")));
            }

            KMeans<Point2D> kmeans = new KMeans<Point2D>(new EuclidDistanceMetric2D());
            ClusterInfo<Point2D> result = kmeans.execute(20, input);

            for (int i = 0; i < input.size(); ++i) {
                Point2D p = input.get(i);
                int clusterID = result.clusterAssignment[i];
                System.out.println(String.format("%f\t%f\t%d", p.getX(), p.getY(), clusterID));
            }
        }
        finally {
//            if (faithful != null)
//                faithful.close();
        }

    }
}
