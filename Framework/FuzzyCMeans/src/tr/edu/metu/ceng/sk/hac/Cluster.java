package tr.edu.metu.ceng.sk.hac;

import java.awt.geom.Point2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Cluster implements Comparable
{
    private ArrayList<Cluster> clusters;
    private ArrayList<Point> points;
    private Color color;

    /**
     * Create a new centroid object
     * @param c the color associated with this cluster
     */
    public Cluster(Color c) {
        color = c;
        points = new ArrayList<Point>();
        clusters = new ArrayList<Cluster>();
    }

    /**
     * Return the color associated with this cluster
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * add a point to the point list
     * @param p
     */
    public void addPoint(Point p) {
        points.add(p);
    }

    /**
     * clear point list
     */
    public void clearPoints() {
        points.clear();
    }

    /**
     * get a list containing all points associated with this cluster centroid
     * @return
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * get all points associated with this cluster, therefore also the points of
     * other clusters associated with this one
     * @return all points
     */
    public List<Point> getAllPoints() {
        List<Point> all_points = new ArrayList<Point>();

        for (Point p : points) {
            all_points.add(p);
        }

        for (Cluster c : clusters) {
            all_points.addAll(c.getAllPoints());
        }

        return all_points;
    }

    /**
     * Wether or not a point is within this cluster
     * @param p the point
     * @return true if the point is in the cluster, false otherwise
     */
    public boolean containsPoint(Point p) {
        if (points.contains(p)) return true;

        for (Cluster c : clusters) {
            if (c.containsPoint(p)) return true;
        }

        return false;
    }

    /**
     * Add another cluster to this one (aka merge 2 clusters)
     * @param c the cluster to add
     */
    public void addCluster(Cluster c) {
        clusters.add(c);
    }

    /**
     * Return all clusters
     * @return the clusters
     */
    public List<Cluster> getClusters() {
        return clusters;
    }

    /**
     * create a clone of this centroid
     * @return the clone centroid
     */
    public Cluster clone() {
        Cluster c = null;
        try {
            c = (Cluster) super.clone();
        } catch (CloneNotSupportedException e) {}
        return c;
    }

    /**
     * Compare method for sorting clusters
     * @param o the other cluster
     * @return
     */
    public int compareTo(Object o) {
        if (o instanceof Cluster) {
            Cluster c2 = (Cluster) o;

            String smallest1 = getSmallestPoint();
            String smallest2 = c2.getSmallestPoint();

            return smallest1.compareTo(smallest2);
        }
        return 0;
    }

    /**
     * Get the "smallest" name of all associated points
     * @return the smallest name
     */
    private String getSmallestPoint() {
        String s = "Z";
        String cmp;
        for (Cluster c : clusters) {
            cmp = c.getSmallestPoint();
            if (cmp.compareTo(s) < 0) {
                s = cmp;
            }
        }

        for (Point p : points) {
            cmp = p.getName();
            if (cmp.compareTo(s) < 0) {
                s = cmp;
            }
        }

        return s;
    }
}
