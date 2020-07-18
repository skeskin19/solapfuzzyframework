package tr.edu.metu.ceng.sk.fuzzy;

import java.awt.*;


public class Centroid extends Point
{
	String centroidName;
    /**
     * Create a new centroid object
     * @param x the position on the x-axis
     * @param c the color
     */
    public Centroid(double x, Color c, String name) {
        super(x, c);
        setCentroidName(name);
    }
    /**
     * Create a new centroid object
     * @param x the position on the x-axis
     * @param c the color
     */
    public Centroid(int x, Color c) {
        super(x, c);
    }

    /**
     * Create a cloned center object
     * @return the cloned centroid
     */
    public Centroid clone() {
        Centroid c = null;
        c = (Centroid) super.clone();
        return c;
    }
    
    public String getCentroidName() {
		return centroidName;
	}

	public void setCentroidName(String centroidName) {
		this.centroidName = centroidName;
	}
}
