package tr.edu.metu.ceng.sk.fuzzy;

import java.awt.*;


public class Point implements Comparable, Cloneable
{
    /**
     * The position of the point on the x-axis
     */
    protected double m_x;
    /**
     * Additional color information
     */
    protected Color m_c;

    /**
     * Create a new point object
     * @param x the position on the x-axis
     */
    public Point(double x) {
        m_x = x;
        // init all points to a gray color
        m_c = Color.gray;
    }

    /**
     * Create a new point object
     * @param x the position on the x-axis
     * @param c the color
     */
    public Point(double x, Color c) {
        m_x = x;
        // init all points to a gray color
        m_c = c;
    }

    /**
     * Get the value for x
     * @return x
     */
    public double getX() {
        return m_x;
    }

    /**
     * Set a new value for x
     * @param x new x value
     */
    public void setX(double x) {
        m_x = x;
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
     * Compare a point to another instance of type kc.test.fuzzy.kc.test.kmeans.Point
     * @param o the point to compare to
     * @return @see Comparable.compareTo
     */
    public int compareTo(Object o) {
        if (o == null) throw new NullPointerException();
        if (!(o instanceof Point)) throw new IllegalArgumentException("instance of type kc.test.fuzzy.kc.test.kmeans.Point required");
        double cm = (m_x - ((Point) o).m_x);
        if (cm>0)
        	return 1;
        else if (cm<0)
        	return -1;
        
        return 0;//(int) (m_x - ((Point) o).m_x);
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
