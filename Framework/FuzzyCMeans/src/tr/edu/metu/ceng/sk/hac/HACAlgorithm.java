package tr.edu.metu.ceng.sk.hac;

import javax.swing.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Line2D;


public class HACAlgorithm
{
    public enum Linkage { Single, Complete, Average };

    /**
     * Linkage method
     */
    private Linkage m_method;

    /**
     * array containing the centroids
     */
    private Set<Cluster> m_clusters;

    /**
     * History of updates
     */
    private ArrayList<Pair<Point, Point>> m_history;

    /**
     * wether the algorithm is initialized or not
     */
    private boolean m_initialized;

    /**
     * create a new algorithm instance
     */
    public HACAlgorithm() {
        m_initialized = false;
    }

    /**
     * Set linkage method
     * @param method the method
     */
    public void setLinkage(Linkage method) {
        m_method = method;
    }

    /**
     * Initialize the algorithm with points and clusters
     * @param clusters
     */
    void init(Set<Cluster> clusters) {
        m_clusters = clusters;
        m_initialized = true;
        m_history = new ArrayList<Pair<Point, Point>>();
    }

    /**
     * Perform one step of the algorithm
     * @return the number of changes in cluster association occured in this step
     */
    public int step() {
        double distance = Double.MAX_VALUE;
        double d;

        Cluster m1 = null, m2 = null;

        Pair<Point, Point> upd = null;
        for (Cluster c1 : m_clusters) {
            for (Cluster c2 : m_clusters) {
                if (c1 == c2) continue;
                d = getDistance(c1, c2);
                if (d < distance) {
                    distance = d;
                    m1 = c1;
                    m2 = c2;
                    upd = update_pair;
                }
            }
        }

        if (m1 != null && m2 != null) {
            Cluster merge = new Cluster(m1.getColor());
            merge.addCluster(m1);
            merge.addCluster(m2);

            m_clusters.remove(m1);
            m_clusters.remove(m2);
            m_clusters.add(merge);
            m_history.add(upd);
            return 1;
        } else {
            return 0;
        }
    }

    public double getDistance(Cluster c1, Cluster c2) {
        double d = 0.0;
        switch (m_method) {
            case Single:
                d = getSingleLinkDistance(c1, c2);
                break;
            case Complete:
                d = getCompleteLinkDistance(c1, c2);
                break;
            case Average:
                d = getAverageLinkDistance(c1, c2);
                break;
        }

        return d;
    }

    public Pair<Point, Point> getUpdatePair() {
        return update_pair;
    }

    /**
     * Perform a complete run of the algorithm.
     * For demo purposes a maximum iteration count of 20 is assumed.
     * @return the number of steps until the algorithm converged
     */
    public int run() {
        int i, maxIterations, diff;
        i = 0;
        maxIterations = 2000;
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
    }

    private Pair<Point, Point> update_pair;
    private double getSingleLinkDistance(Cluster c1, Cluster c2) {
        double distance = Double.MAX_VALUE;
        double d;

        List<Point> points1 = c1.getAllPoints();
        List<Point> points2 = c2.getAllPoints();
        update_pair = null;
        for (Point p1 : points1) {
            for (Point p2 : points2) {
                d = getDistance(p1, p2);
                if (d < distance) {
                    distance = d;
                    update_pair = new Pair<Point, Point>(p1, p2);
                }
            }
        }

        return distance;
    }

    private double getCompleteLinkDistance(Cluster c1, Cluster c2) {
        double distance = Double.MIN_VALUE;
        double d;

        List<Point> points1 = c1.getAllPoints();
        List<Point> points2 = c2.getAllPoints();
        update_pair = null;
        for (Point p1 : points1) {
            for (Point p2 : points2) {
                d = getDistance(p1, p2);
                if (d > distance) {
                    distance = d;
                    update_pair = new Pair<Point, Point>(p1, p2);
                }
            }
        }
        return distance;
    }

    private double getAverageLinkDistance(Cluster c1, Cluster c2) {
        double distance = Double.MIN_VALUE;
        double d = 0.0;

        List<Point> points1 = c1.getAllPoints();
        List<Point> points2 = c2.getAllPoints();
        update_pair = null;
        for (Point p1 : points1) {
            for (Point p2 : points2) {
                if (update_pair == null)
                    update_pair = new Pair<Point, Point>(p1, p2);
                d += getDistance(p1, p2);
            }
        }
        distance = d / (double) (points1.size() * points2.size());
        return distance;
    }

    public double getDistance(Point p1, Point p2) {
        return p1.getP().distance(p2.getP());
    }

    /**
     * Get a list of update points
     * @return
     */
    public List<Pair<Point, Point>> getHistory() {
        return m_history;
    }
}
