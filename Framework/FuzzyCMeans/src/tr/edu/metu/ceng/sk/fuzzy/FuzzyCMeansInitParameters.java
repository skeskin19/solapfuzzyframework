package tr.edu.metu.ceng.sk.fuzzy;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class FuzzyCMeansInitParameters {
   
    private Dimension dim;

    private ArrayList<Point> points;
    private ArrayList<Centroid> clusters;

    private ArrayList<Point> points_cloned;
    private ArrayList<Centroid> clusters_cloned;

 
    private final int RASTER = 1;
    //private static final int NUMBER_OF_CLUSTER = 8;

    private FuzzyAlgorithm algorithm;

    private CommandPanel command;
    

    public FuzzyCMeansInitParameters(CommandPanel cmd) {
    	 command = cmd;

    	points = new ArrayList<Point>(command.getPoints());
        clusters = new ArrayList<Centroid>(command.getClusters());

        algorithm = new FuzzyAlgorithm();

        command.setState(1, 0);
    }
    
    public void addPoint(double p) {
    	points.add(new Point(p));
    }

    public void initialize(int numberOfCluster) {

    	dim = new Dimension(100, 100);
        int w = dim.width;
        int h = dim.height;
        
    	Random r = new Random();
    	//int num_cells = (dim.width - 20) / RASTER - 1;

        Collections.sort(points);    
        
        
        clusters.clear();
        for (int j=0; j<numberOfCluster; j++) {
        	int index = (points.size()/(numberOfCluster+1))*(j+1);
            Point x = points.get(index);
            int rr = r.nextInt(256);
            int gg = r.nextInt(256);
            int bb = r.nextInt(256);
            System.out.println("j:"+j+ " index:"+index+" cluster x="+x.getX());
            clusters.add(new Centroid(x.getX(), new Color(rr, gg, bb), Integer.toString(j)));
        }
        
        algorithm.setInit(false);
        command.setState(2, 0);
    
    }

    public void reset() {
        if (points_cloned != null) {
            points = points_cloned;
            clusters = clusters_cloned;
            algorithm.setInit(false);
            algorithm.setFuzzyness(command.getFuzzyness());
            command.setState(3, 0);
           
        }
    }

    public void start() {
        Collections.sort(points);

        points_cloned = new ArrayList<Point>(command.getPoints());
        for (Point p : points) {
            points_cloned.add(p.clone());
        }

        clusters_cloned = new ArrayList<Centroid>(command.getClusters());
        for (Centroid c : clusters) {
            clusters_cloned.add(c.clone());
        }

        algorithm.init(points, clusters, command.getFuzzyness());
        command.setState(4, 0);
        
    }

    public void step() {
        algorithm.step();
        
    }

    public void run() {
        int iterations = algorithm.run(command.getAccuracy());
        System.out.println("run iterations="+iterations);
        command.setState(5, iterations);
       
    }
    
    public void printResult() {
    	algorithm.printResult();
    }
    
    public double[] getMemberShip(double num) {
    	return algorithm.getMemberShip(num);
    }
    
    public double[] calculateFuzzyMemberShipFromCentroidRange(double number) {
    	return algorithm.calculateFuzzyMemberShipFromCentroidRange(number);
    }

	public ArrayList<Point> getPoints() {
		return points;
	}

	public FuzzyAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(FuzzyAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public ArrayList<Centroid> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<Centroid> clusters) {
		this.clusters = clusters;
	}

}
