package tr.edu.metu.ceng.clustering.kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import tr.edu.metu.ceng.clustering.kmeans.db.DatabaseConstants;
import tr.edu.metu.ceng.clustering.kmeans.db.DatabaseOperations;

public class KMeans {

	  private static final int REPLICATION_FACTOR = 200;

	  public static class Point2D {
	      
	      private float x;
	      private float y;
	      
	      public Point2D(float x, float y) {
	          this.x = x;
	          this.y = y;
	      }
	      
	      private double getDistance(Point2D other) {
	          return Math.sqrt(Math.pow(this.x - other.x, 2)
	                  + Math.pow(this.y - other.y, 2));
	      }
	      
	      public int getNearestPointIndex(List<Point2D> points) {
	          int index = -1;
	          double minDist = Double.MAX_VALUE;
	          for (int i = 0; i < points.size(); i++) {
	              double dist = this.getDistance(points.get(i));
	              if (dist < minDist) {
	                  minDist = dist;
	                  index = i;
	              }
	          }
	          return index;
	      }
	      
	      public static Point2D getMean(List<Point2D> points) {
	          float accumX = 0;
	          float accumY = 0;
	          if (points.size() == 0) return new Point2D(accumX, accumY);
	          for (Point2D point : points) {
	              accumX += point.x;
	              accumY += point.y;
	          }
	          return new Point2D(accumX / points.size(), accumY / points.size());
	      }
	      
	      @Override
	      public String toString() {
	          return "[" + this.x + "," + this.y + "]";
	      }
	      
	      @Override
	      public boolean equals(Object obj) {
	          if (obj == null || !(obj.getClass() != Point2D.class)) {
	              return false;
	          }
	          Point2D other = (Point2D) obj;
	          return this.x == other.x && this.y == other.y;
	      }
	      
	  }

	  public static List<Point2D> getDataset(String inputFile) throws Exception {
	      List<Point2D> dataset = new ArrayList<Point2D>();
	      BufferedReader br = new BufferedReader(new FileReader(inputFile));
	      String line;
	      while ((line = br.readLine()) != null) {
	          String[] tokens = line.split(",");
	          float x = Float.valueOf(tokens[0]);
	          float y = Float.valueOf(tokens[1]);
	          Point2D point = new Point2D(x, y);
	          for (int i = 0; i < REPLICATION_FACTOR; i++)
	              dataset.add(point);
	      }
	      br.close();
	      return dataset;
	  }
	  
	  public static List<Point2D> getTestDataset() throws Exception {
	      List<Point2D> dataset = new ArrayList<Point2D>();
	      
	      for(int i = 0; i< 100; ++i) {
	    	  Point2D point = new Point2D(i+1, 0);
	    	  dataset.add(point);
	      }
	      return dataset;
	  }
	  
	  public static List<Point2D> getMeasurementDataset() throws Exception {
		  
		  double[] measurements = DatabaseOperations.getMeasurementValues(DatabaseConstants.getDatabaseTableName(DatabaseConstants.DATA_TYPE__TEMPERATURE), DatabaseConstants.getDatabaseTableColoumn(DatabaseConstants.DATA_TYPE__TEMPERATURE), 2012);
		  System.out.println("measurement size:"+measurements.length);
	      List<Point2D> dataset = new ArrayList<Point2D>();
	      
	      for(int i = 0; i< measurements.length; ++i) {
	    	  Point2D point = new Point2D((float)measurements[i], 0);
	    	  dataset.add(point);
	      }
	      return dataset;
	  }
	  
	  
	  
	  public static List<Point2D> initializeRandomCenters(int n, int lowerBound, int upperBound) {
	      List<Point2D> centers = new ArrayList<Point2D>(n);
	      for (int i = 0; i < n; i++) {
	          float x = (float)(Math.random() * (upperBound - lowerBound) + lowerBound);
	          float y = 0;//(float)(Math.random() * (upperBound - lowerBound) + lowerBound);
	          Point2D point = new Point2D(x, y);
	          centers.add(point);
	      }
	      return centers;
	  }

	  public static List<Point2D> getNewCenters(List<Point2D> dataset, List<Point2D> centers) {
	      List<List<Point2D>> clusters = new ArrayList<>(centers.size());
	      for (int i = 0; i < centers.size(); i++) {
	          clusters.add(new ArrayList<Point2D>());
	      }
	      for (Point2D data : dataset) {
	          int index = data.getNearestPointIndex(centers);
	          clusters.get(index).add(data);
	      }
	      List<Point2D> newCenters = new ArrayList<>(centers.size());
	      for (List<Point2D> cluster : clusters) {
	          newCenters.add(Point2D.getMean(cluster));
	      }
	      return newCenters;
	  }
	  
	  public static double getDistance(List<Point2D> oldCenters, List<Point2D> newCenters) {
	      double accumDist = 0;
	      for (int i = 0; i < oldCenters.size(); i++) {
	          double dist = oldCenters.get(i).getDistance(newCenters.get(i));
	          accumDist += dist;
	      }
	      return accumDist;
	  }
	  
	  public static List<Point2D> kmeans(List<Point2D> centers, List<Point2D> dataset, int k) {
	      boolean converged;
	      do {
	          List<Point2D> newCenters = getNewCenters(dataset, centers);
	          double dist = getDistance(centers, newCenters);
	          centers = newCenters;
	          converged = dist == 0;
//	          System.out.println("initialized Random Centers:");
//		      for(Point2D p:newCenters) {
//		    	  System.out.println("newCenter.cluster: x:"+p.x+" y:"+p.y);
//		      }
	      } while (!converged);
	      return centers;
	  }

	  public static void main(String[] args) {
		  /*
	      if (args.length != 2) {
	          System.err.println("Usage: KMeans <INPUT_FILE> <K>");
	          System.exit(-1);
	      }
	      String inputFile = args[0];
	      int k = Integer.valueOf(args[1]);
	      List<Point2D> dataset = null;
	      try {
	          dataset = getDataset(inputFile);
	      } catch (Exception e) {
	          System.err.println("ERROR: Could not read file " + inputFile);
	          System.exit(-1);
	      }
	      List<Point2D> centers = initializeRandomCenters(k, 0, 1000000);
	      long start = System.currentTimeMillis();
	      kmeans(centers, dataset, k);
	      System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
	      System.exit(0);
	      */
		  int k = 3;
		  List<Point2D> dataset = null;
	      try {
	          //dataset = getTestDataset();
	          dataset = getMeasurementDataset();
	      } catch (Exception e) {
	          System.exit(-1);
	      }
	      List<Point2D> centers = initializeRandomCenters(k, 0, 100);
	      int i=1;
	      System.out.println("initialized Random Centers:");
	      for(Point2D p:centers) {
	    	  System.out.println(i+".cluster: x:"+p.x+" y:"+p.y);
	    	  i++;
	      }
	      long start = System.currentTimeMillis();
	      centers = kmeans(centers, dataset, k);
	      System.out.println("clustered centers:");
	      i=1;
	      for(Point2D p:centers) {
	    	  System.out.println(i+".cluster: x:"+p.x+" y:"+p.y);
	    	  i++;
	      }
	      System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) + "ms");
	      System.exit(0);
		  
	  }

	}