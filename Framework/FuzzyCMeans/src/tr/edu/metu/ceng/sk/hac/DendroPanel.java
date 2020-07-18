package tr.edu.metu.ceng.sk.hac;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.ArrayList;


public class DendroPanel extends Canvas implements ComponentListener
{
    private BufferedImage bi;
    private Graphics2D big;
    private Dimension dim;

    private DrawPanel draw;
    private CommandPanel controls;

    private static final int POINT_SIZE = 6;

    private Font font = new Font("serif", Font.PLAIN, 12);

    public DendroPanel(DrawPanel draw, CommandPanel controls) {
        this.setBackground(Color.white);
        this.draw = draw;
        this.controls = controls;
        addComponentListener(this);
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        // draw the offscreen image to the screen
        g2.drawImage(bi, 0, 0, this);
    }

    public void update(Graphics g){
        // if the offscreen image has not yet been initialized, do it now
        if (bi == null) {
            dim = getSize();
            int w = dim.width;
            int h = dim.height;
            setSize(w, h);
        }

        double zoom = (double) controls.getZoomLevel() / 10.0;

        Dimension d = drawDendrogram();
        Dimension parentdim = ((ScrollPane) getParent()).getViewportSize();
        setSize((int) Math.max(d.width * zoom, parentdim.width),
                (int) Math.max(d.height * zoom, parentdim.height));

        dim = getSize();

        // Clears the rectangle that was previously drawn.
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.fillRect(0, 0, dim.width, dim.height);

        AffineTransform t = new AffineTransform();
        t.scale(zoom, zoom);
        big.setTransform(t);

        drawDendrogram();

        getParent().doLayout();

        paint(g);
    }

    private int cluster_count;
    private Dimension max_dimension;

    private Dimension drawDendrogram() {
        big.setColor(Color.black);
        big.setFont(font);

        Set<Cluster> clusters = draw.getClusters();
        max_dimension = new Dimension(0, 0);

        cluster_count = 0;
        for (Cluster c : clusters) {
            drawCluster(c);
        }

        max_dimension.setSize(max_dimension.getWidth() + 15, max_dimension.getHeight() + 15);
        return max_dimension;
    }

    private Point2D drawCluster(Cluster c) {
        java.util.List<Cluster> clusters = c.getClusters();
        int start_y = 0;
        int end_y = 0;
        int end_x = Integer.MIN_VALUE;
        ArrayList<Point2D> pps = new ArrayList<Point2D>();
        for (Cluster cc : clusters) {
            Point2D pp = drawCluster(cc);
            pps.add(pp);
            if (start_y == 0) start_y = (int) pp.getY();
            else end_y = (int) pp.getY();

            end_x = Math.max(end_x, (int) pp.getX());
        }

        if (start_y != 0) {
            end_x += 25;

            big.setColor(Color.black);
            for (Point2D pp : pps) {
                big.drawLine((int) pp.getX(), (int) pp.getY(), end_x, (int) pp.getY());
            }

            big.drawLine(end_x, start_y, end_x, end_y);
            max_dimension.setSize(Math.max(max_dimension.width, end_x), Math.max(max_dimension.height, end_y));
            return new Point2D.Double(end_x, (start_y + end_y) / 2);
        }

        Color col = c.getColor();
        java.util.List<Point> points = c.getPoints();
        Point2D point = new Point2D.Double();
        for (Point p : points) {
            drawLabel(5, cluster_count * 20 + 15, p.getName());

            int y = cluster_count * 20 + 22 - font.getSize();
            int x = 30 + POINT_SIZE / 2;

            drawPoint(30, y, POINT_SIZE, col, col, 0.8f);
            point.setLocation(x, y);
            max_dimension.setSize(Math.max(max_dimension.width, x), Math.max(max_dimension.height, y));
            cluster_count++;
        }

        return point;
    }

    private void drawPoint(int x, int y, int size, Color c, Color fill, float fill_val) {
        big.setColor(fill);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fill_val));
        Shape s = getPointShape(x, y, size);
        big.fill(s);
        big.setColor(c);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.draw(s);
    }

    private void drawLabel(int x, int y, String name) {
        big.setColor(Color.black);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.drawString(name, x, y);
    }

    private Shape getPointShape(double x, double y, double size) {
        Shape s = new Ellipse2D.Double(x - size / 2, y - size / 2, size, size);
        return s;
    }

    public void componentResized(ComponentEvent e) {
        dim = getSize();
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);

        bi = (BufferedImage)createImage(width, height);
        big = bi.createGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clears the rectangle
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.fillRect(0, 0, width, height);
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}

    public Dimension getMinimumSize() {
        return new Dimension(300, 400);
    }
}
