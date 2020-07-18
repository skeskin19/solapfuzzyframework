package tr.edu.metu.ceng.sk.kmeans;

import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.Point2D;

import voronoi.Vertex;
import voronoi.Coord;
import voronoi.Voronoi;


public class KMeansAlgorithm
{
    /**
     * array containing all points
     */
    private ArrayList<Point> m_points;
    /**
     * array containing the centroids
     */
    private ArrayList<Centroid> m_clusters;

    /**
     * wether the algorithm is initialized or not
     */
    private boolean m_initialized;

    /**
     * contains the voronoi diagram
     */
    private Vertex[] m_diagram;

    /**
     * create a new algorithm instance
     */
    public KMeansAlgorithm() {
        m_initialized = false;
    }

    /**
     * Initialize the algorithm with points and clusters
     * @param points
     * @param clusters
     */
    void init(ArrayList<Point> points, ArrayList<Centroid> clusters) {
        m_points = points;
        m_clusters = clusters;

        m_initialized = true;
    }

    /**
     * Perform one step of the algorithm
     * @return the number of changes in cluster association occured in this step
     */
    public int step() {
        // clear point list in centers
        for (Centroid c : m_clusters) {
            c.clearPoints();
        }

        int changes = 0;

        // calculate for each point the smallest distance to a cluster centroid
        for (Point p : m_points) {
            double distance = Double.MAX_VALUE;
            Centroid min_center = null;

            for (Centroid c : m_clusters) {
                double d = p.getP().distance(c.getP());
                if (d < distance) {
                    distance = d;
                    min_center = c;
                }
            }

            // this is a hack at the moment, changes in cluster association is at the moment
            // tracked by comparing the color information
            if (!p.getColor().equals(min_center.getColor())) changes++;

            p.setColor(min_center.getColor());
            min_center.addPoint(p);
        }

        // calculate mean center for each cluster
        for (Centroid c : m_clusters) {
            c.addTrack((Point2D) c.getP().clone());
            int num_points = c.getPoints().size();
            if (num_points > 0) {
                double mean_x = 0.0;
                double mean_y = 0.0;
                for (Point p : c.getPoints()) {
                    mean_x += p.getP().getX();
                    mean_y += p.getP().getY();
                }

                mean_x = mean_x / num_points;
                mean_y = mean_y / num_points;
                c.getP().setLocation(mean_x, mean_y);
            } else {
                // if a centroid has no points attached, place it near another centroid with
                // many points
                Centroid max = c;
                int cnt = 0;
                for (Centroid other_center : m_clusters) {
                    int num = other_center.getPoints().size();
                    if (num > cnt) {
                        cnt = num;
                        max = other_center;
                    }
                }

                Random r = new Random();
                int devX = r.nextInt(20) - 10;
                int devY = r.nextInt(20) - 10;
                c.getP().setLocation(max.getP().getX() + devX, max.getP().getY() + devY);
            }
        }

        calcVoronoiDiagram();
        return changes;
    }

    /**
     * Perform a complete run of the algorithm.
     * For demo purposes a maximum iteration count of 20 is assumed.
     * @return the number of steps until the algorithm converged
     */
    public int run() {
        int i, maxIterations, diff;
        i = 0;
        maxIterations = 20;
        do {
            i++;
            diff = step();
            System.out.println("Iteration #" + i + ", changes = " + diff);
        } while (diff > 0 && i < maxIterations);
        return i;
    }

    /**
     * Get the init state of the algorithm
     * @return the init state
     */
    public boolean isInitialized() {
        return m_initialized;
    }

    /**
     * Set the init state
     * @param val the new state
     */
    public void setInit(boolean val) {
        m_initialized = val;
        m_diagram = null;
    }

    /**
     * Calculate the voronoi diagram for the current state of the algorithm
     */
    private void calcVoronoiDiagram() {
        Coord[] t = new Coord[m_clusters.size()];
        int i = 0;
        for (Centroid c : m_clusters) {
            Coord coord = new Coord();
            coord.x = c.getP().getX();
            coord.y = c.getP().getY();
            t[i++] = coord;
        }

        m_diagram = Voronoi.generate(t);
    }

    public Vertex[] getDiagram() {
        return m_diagram;
    }
}
