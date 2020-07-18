package tr.edu.metu.ceng.sk.fuzzy;

import java.util.ArrayList;


import java.awt.*;


public class FuzzyAlgorithm
{
    /**
     * array containing all points used by the algorithm
     */
    private ArrayList<Point> m_points;
    /**
     * array containing all clusters handled by the algorithm
     */
    private ArrayList<Centroid> m_clusters;

    /**
     * internally used fields by the algorithm
     */
    public double U[][];
    private double Uk[][];

    /**
     * the current fuzzyness factor
     */
    private double m_fuzzyness;

    /**
     * Indicates wether the algorithm is initialized
     */
    private boolean m_initialized;

    private double eps = 1.0e-10;

    /**
     * Create a new algorithm instance
     */
    public FuzzyAlgorithm() {
        m_initialized = false;
    }

    /**
     * Initialize the algorithm with points and initial clusters
     * @param points
     * @param clusters
     * @param fuzzy the fuzzyness factor to be used
     */
    public void init(ArrayList<Point> points, ArrayList<Centroid> clusters, float fuzzy) {
        m_points = points;
        m_clusters = clusters;

        U = new double[m_points.size()][m_clusters.size()];
        Uk = new double[m_points.size()][m_clusters.size()];

        m_fuzzyness = fuzzy;

        int i = 0;
        int diff;
        // iterate through all points to create initial U matrix
        for (Point p : m_points) {
            double sum = 0.0;
            int j = 0;
            for (Centroid c : m_clusters) {
                diff = Math.abs((int)p.getX() - (int)c.getX());
                if (diff == 0) U[i][j] = eps;
                else U[i][j] = diff;
                sum += U[i][j];
                j++;
            }

            j = 0;
            double sum2 = 0.0;
            for (Centroid c : m_clusters) {
                U[i][j] = 1.0 / Math.pow(U[i][j] / sum, 2.0 / (m_fuzzyness - 1.0));
                sum2 += U[i][j];
                j++;
            }

            j = 0;
            double maxMF = 0.0;
            Color newc = p.getColor();
            for (Centroid c : m_clusters) {
                U[i][j] = U[i][j] / sum2;

                // assign the color of the associated cluster to the point
                if (U[i][j] > maxMF) {
                    maxMF = U[i][j];
                    newc = c.getColor();
                }
                j++;
            }
            p.setColor(newc);
            i++;
        }
        m_initialized = true;
    }

    /**
     * Set the fuzzyness factor
     * @param fuzzy
     */
    public void setFuzzyness(float fuzzy) {
        m_fuzzyness = fuzzy;
    }

    /**
     * Perform one step of the algorithm
     */
    public void step() {
        int j = 0;
        int i = 0;
        for (Centroid c : m_clusters) {
            i = 0;
            double u = 0.0;
            double l = 0.0;
            for (Point p : m_points) {
                double uu = Math.pow(U[i][j], m_fuzzyness);
                u += uu * p.getX();
                l += uu;
                i++;
            }
            c.setX((int) (u / l));
            j++;
        }

        for (i = 0; i<m_points.size(); i++) {
            Color newc = m_points.get(i).getColor();
            double maxMF = 0.0;

            for (j = 0; j<m_clusters.size(); j++) {
                int k = 0;
                double sum = 0.0;
                double xx = Math.abs(m_points.get(i).getX() - m_clusters.get(j).getX());
                if (xx < 1.0) xx = eps;

                for (Centroid c : m_clusters) {
                    double xx2 = Math.abs(m_points.get(i).getX() - m_clusters.get(k).getX());
                    if (xx2 < 1.0) xx2 = eps;
                    sum += Math.pow(xx/xx2, 2.0 / (m_fuzzyness - 1.0));
                    k++;
                }
                U[i][j] = 1.0 / sum;

                // assign the color of the associated cluster to the point
                if (U[i][j] > maxMF) {
                    maxMF = U[i][j];
                    newc = m_clusters.get(j).getColor();
                }
            }
            m_points.get(i).setColor(newc);
        }
    }

    /**
     * Calculate the objective function
     *
     * @return the objective function as double value
     */
    private double calculateObjectiveFunction() {
        double Jk = 0.0;
        int i = 0;
        for (Point p : m_points) {
            int j = 0;
            for (Centroid c : m_clusters) {
                Jk += Math.pow(U[i][j], m_fuzzyness) *
                        Math.pow(m_points.get(i).getX() - m_clusters.get(j).getX(), 2.0);
                j++;
            }
            i++;
        }
        return Jk;
    }

    /**
     * Calculate the maximum membership distance from one step to another
     *
     * @return the max membership distance
     */
    public double calculateMaxMembershipDistance() {
        double maxMF = 0.0f;

        for (int i=0; i<m_points.size(); i++) {
            for (int j=0; j<m_clusters.size(); j++) {
                double v = Math.abs(U[i][j] - Uk[i][j]);
                maxMF = Math.max(v, maxMF);
            }
        }
        return maxMF;
    }

    /**
     * Copy the complete membership matrix
     */
    private void copyMembershipMatrix() {
        for (int i=0; i<m_points.size(); i++) {
            for (int j=0; j<m_clusters.size(); j++) {
                Uk[i][j] = U[i][j];
            }
        }
    }

    /**
     * Perform a complete run of the algorithm until the desired accuracy is achieved.
     * For demonstration issues, the maximum Iteration counter is set to 20.
     *
     * @return the number of steps the algorithm needed to complete
     */
    public int run(double accuracy) {
        double difference = 0.0;
        int i, maxIterations;
        i = 0;
        maxIterations = 20;
        do {
            i++;
            copyMembershipMatrix();
            step();
            //tmpJ = calculateObjectiveFunction();
            //difference = Math.abs(tmpJ - J);
            //J = tmpJ;
            //System.out.println("Jk = " + J);
            difference = calculateMaxMembershipDistance();
            System.out.println("Iteration #" + i + ", diff = " + difference + ", accuracy = " + accuracy);
            printClusters();
        } while (difference > accuracy && i < maxIterations);
        return i;
    }

    /**
     * Indicates wether the algorithm is initalized or not
     * @return true if the algorithm is initialized, false otherwise
     */
    public boolean isInitialized() {
        return m_initialized;
    }

    /**
     * Set the init flag of the algorithm
     * @param val the new init state
     */
    public void setInit(boolean val) {
        m_initialized = val;
    }
    
    public void printResult() {
    	System.out.println("-----------------printResult-----------------------");
    	for(int i =0; i<U.length; ++i) {
    		System.out.println(i + ". element of U [" + U[i][0] +", "+U[i][1]+", "+U[i][2]);
    	}
    	System.out.println("-----------------2 printResult 2-----------------------");
    	for(int i =0; i<U.length; ++i) {
    		System.out.println(i + ". element of Uk [" + Uk[i][0] +", "+Uk[i][1]+", "+Uk[i][2]);
    	}
    	
    	System.out.println("-----------------3 printResult 3-----------------------");
    	for(Centroid c: m_clusters) {
    		System.out.println("c.m_x="+c.m_x);
    	}
    	
    	System.out.println("-----------------4 printResult 4-----------------------");
    	for(Point p: m_points) {
    		System.out.println("p.m_x="+p.m_x);
    	}
    }
    
    public void printClusters() {
    	
    	System.out.println("-----------------* printClusters *-----------------------");
    	for(Centroid c: m_clusters) {
    		System.out.println("c.m_x="+c.m_x);
    	}
    	
    }
    
    /**
     * Gives distance of the point to each centroid 
     * @param num
     * @return
     */
    public double[] getMemberShip(double num) {
    	int i = 0;
    	for(Point p: m_points) {
    		if(p.m_x==num)
    			return U[i];
    		++i;
    	}
    	return null;
    	
    	
    }
    
    /**
     * Calculates the fuzzy membership values of the point by using distance values from centroid 
     * 
     * @param number
     * @return
     */
    public double[] calculateFuzzyMemberShipFromCentroidRange(double number) {
    	double[] range = getMemberShip(number);
    	/*
    	double[] result = new double[3];
		double a = range[0], b = range[1], c = range[2];
		double x = (a*b*c) / ((a*b)+(b*c)+(a*c)); // (x/a)+(x/b)+(x/c) = 1
		
		result[0] = x/a;
		result[1] = x/b;
		result[2] = x/c;
		
		for(int i=0;i<3;++i) {
			if(result[i]>=1.0)
				result[i]=0.99999;
		}
		
		return result;
		*/
    	return range;
	}
}
