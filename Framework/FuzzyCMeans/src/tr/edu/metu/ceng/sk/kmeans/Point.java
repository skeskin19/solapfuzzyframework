package tr.edu.metu.ceng.sk.kmeans;

import java.awt.*;
import java.awt.geom.Point2D;


public class Point implements Cloneable
{
    /**
     * The position of the point on the x-y plane
     */
    protected Point2D m_p;
    /**
     * Additional color information
     */
    protected Color m_c;

    /**
     * Create a new point object
     * @param p the position on the x-y plane
     */
    public Point(Point2D p) {
        m_p = p;
        // init all points to a gray color
        m_c = Color.gray;
    }

    /**
     * Create a new point object
     * @param p the position on the x-y plane
     * @param c the color
     */
    public Point(Point2D p, Color c) {
        m_p = p;
        // init all points to a gray color
        m_c = c;
    }

    /**
     * Get the value for p
     * @return p
     */
    public Point2D getP() {
        return m_p;
    }

    /**
     * Set a new value for x
     * @param p new p value
     */
    public void setP(Point2D p) {
        m_p = p;
    }

    /**
     * Get the actual color of this point
     * @return the color
     */
    public Color getColor() {
        return m_c;
    }

    /**
     * Set a new color
     * @param c new color
     */
    public void setColor(Color c) {
        m_c = c;
    }

    /**
     * Override clone of Object to support cloning of array containing points
     * @return the cloned point
     */
    public Point clone() {
        Point p = null;
        try {
            p = (Point) super.clone();
        } catch (CloneNotSupportedException e) {}
        return p;
    }
}
