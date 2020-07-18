package tr.edu.metu.ceng.sk.hac;

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
     * The name of this point, for debugging reasons
     */
    protected String m_name;

    private static int m_counter = 0;

    private static String[] m_chars = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                                        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                                        "W", "X", "Y", "Z" };
    /**
     * Create a new point object
     * @param p the position on the x-y plane
     */
    public Point(Point2D p) {
        this(p, Color.gray, m_chars[m_counter % 26] + String.valueOf(m_counter / 26 + 1));
        m_counter++;
    }

    /**
     * Create a new point object
     * @param p the position on the x-y plane
     * @param c the color
     */
    public Point(Point2D p, Color c) {
        this(p, c, m_chars[m_counter % 26] + String.valueOf(m_counter / 26 + 1));
        m_counter++;
    }

    /**
     * Create a new point object
     * @param p the position on the x-y plane
     * @param c the color
     * @param name the name of the point
     */
    public Point(Point2D p, Color c, String name) {
        m_p = p;
        m_c = c;
        m_name = name;
    }

    /**
     * Reset static name counter
     */
    public static void resetCounter() {
        m_counter = 0;
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
     * Return the name
     * @return the name
     */
    public String getName() {
        return m_name;
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
