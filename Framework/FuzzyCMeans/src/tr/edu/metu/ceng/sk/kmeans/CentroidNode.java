package tr.edu.metu.ceng.sk.kmeans;

import java.util.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class CentroidNode
{
    public Point2D coord;
    public Rectangle2D box;
    public LinkedList<Point2D> nodes;

    private final int boxwidth = 8;
    private final int boxheight = 8;

    public CentroidNode() {
        coord = new Point2D.Double(0, 0);
        box = new Rectangle2D.Double(0, 0, 0, 0);
        nodes = new LinkedList<Point2D>();
    }

    public CentroidNode(int x, int y, int devX, int devY, int num_nodes) {
        coord = new Point2D.Float(x, y);
        box = new Rectangle2D.Float(x - boxwidth/2, y - boxheight/2, boxwidth, boxheight);
        nodes = new LinkedList<Point2D>();

        Point2D p;
        double xx, yy;
        Random r = new Random();

        double r_val;
        for (int i=0; i<num_nodes; i++) {
            r_val = r.nextGaussian();
            xx = r_val * devX + x;
            r_val = r.nextGaussian();
            yy = r_val * devY + y;

            if (x > 0.0 && y > 0.0) {
                p = new Point2D.Double(xx, yy);
                nodes.add(p);
            }
        }
    }

    void setPosition(int new_x, int new_y) {
        int old_x = (int) coord.getX();
        int old_y = (int) coord.getY();

        coord.setLocation(new_x, new_y);
        box.setRect(new_x - boxwidth/2, new_y - boxheight/2, boxwidth, boxheight);

        int diff_x = old_x - new_x;
        int diff_y = old_y - new_y;
        for (Point2D p : nodes) {
            p.setLocation(p.getX() - diff_x, p.getY() - diff_y);
        }
    }
}
