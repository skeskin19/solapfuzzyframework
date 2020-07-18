package tr.edu.metu.ceng.sk.kmeans;

import java.awt.geom.Point2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Centroid extends Point
{
    private ArrayList<Point> points;
    private ArrayList<Point2D> history;

    /**
     * Create a new centroid object
     * @param p the point on the x-y plane
     * @param c the color associated with this cluster
     */
    public Centroid(Point2D p, Color c) {
        super(p, c);

        points = new ArrayList<Point>();
        history = new ArrayList<Point2D>();
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
     * get an iterator containing all points associated with this cluster centroid
     * @return
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * add a 2d point as track for history information
     * @param p the point
     */
    public void addTrack(Point2D p) {
        history.add(p);
    }

    /**
     * Get a list of all history points
     * @return
     */
    public List<Point2D> getHistory() {
        return history;
    }

    /**
     * Clear the history information
     */
    public void clearHistory() {
        history.clear();
    }

    /**
     * create a clone of this centroid
     * @return the clone centroid
     */
    public Centroid clone() {
        Centroid c = null;
        c = (Centroid) super.clone();
        return c;
    }
}
