package tr.edu.metu.ceng.sk.kmeans;

import voronoi.VoronoiSegment;
import voronoi.Vertex;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;

public class DrawPanel extends Canvas implements MouseListener, MouseMotionListener,
        ComponentListener
{
    private ArrayList<Point> points;
    private ArrayList<Centroid> clusters;

    private ArrayList<CentroidNode> centroids;

    private ArrayList<Point> points_cloned;
    private ArrayList<Centroid> clusters_cloned;

    private BufferedImage bi;
    private Graphics2D big;
    private Dimension dim;

    private boolean dragging;
    private CentroidNode dragNode;
    private Centroid dragCenter;

    private KMeansAlgorithm algorithm;

    private static final int POINT_SIZE = 6;
    private static final int CENTROID_SIZE = 10;

    private CommandPanel command;

    public DrawPanel(CommandPanel cmd) {
        addMouseMotionListener(this);
        addMouseListener(this);
        addComponentListener(this);

        algorithm = new KMeansAlgorithm();

        points = new ArrayList<Point>();
        clusters = new ArrayList<Centroid>();

        centroids = new ArrayList<CentroidNode>();

        command = cmd;
        command.setState(1, 0);
    }

    public void initialize() {
        Random r = new Random();

        points.clear();
        clusters.clear();
        centroids.clear();

        for (int j=0; j<command.getClusters(); j++) {
            int x = (int) dim.getWidth() - 100;
            x = r.nextInt(x) + 50;
            int y = (int) dim.getHeight() - 80;
            y = r.nextInt(y) + 40;

            CentroidNode c = new CentroidNode(x, y, command.getDeviationX(),
                    command.getDeviationY(), command.getNodes());
            centroids.add(c);
        }

        algorithm.setInit(false);
        command.setState(2, 0);
        repaint();
    }

    public void reset() {
        if (points_cloned != null) {
            points = points_cloned;
            clusters = clusters_cloned;
            algorithm.setInit(false);
            command.setState(3, 0);
            repaint();
        }
    }

    public void start() {
        Random r = new Random(new Date().getTime());

        points.clear();
        clusters.clear();

        // copy points from centroidNodes to pointlist
        for (CentroidNode n : centroids) {
            for (java.awt.geom.Point2D p : n.nodes) {
                points.add(new Point(p));
            }

            int x = r.nextInt((int) dim.getWidth() - 100) + 50;
            int y = r.nextInt((int) dim.getWidth() - 80) + 40;

            int rr = r.nextInt(256);
            int gg = r.nextInt(256);
            int bb = r.nextInt(256);
            clusters.add(new Centroid(new java.awt.geom.Point2D.Double(x, y), new Color(rr, gg, bb)));
        }

        points_cloned = new ArrayList<Point>();
        for (Point p : points) {
            points_cloned.add(p.clone());
        }

        clusters_cloned = new ArrayList<Centroid>();
        for (Centroid c : clusters) {
            clusters_cloned.add(c.clone());
        }

        algorithm.init(points, clusters);

        command.setState(4, 0);
        repaint();
    }

    public void step() {
        algorithm.step();
        repaint();
    }

    public void run() {
        int iterations = algorithm.run();
        command.setState(5, iterations);
        repaint();
    }

    public void mouseDragged(MouseEvent e) {
        if(dragging){
            updateLocation(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        // check wether we want to drag a centroid
        int x = e.getX();
        int y = e.getY();

        // if algorithm is not yet initialized, we can change layout of data items
        if (!algorithm.isInitialized()) {
            for (CentroidNode n : centroids) {
                if (n.box.contains(x, y)) {
                    dragging = true;
                    dragNode = n;
                    break;
                }
            }

            if (!dragging) {
                CentroidNode c = new CentroidNode(e.getX(), e.getY(), command.getDeviationX(),
                        command.getDeviationY(), command.getNodes());
                centroids.add(c);
            }
        } else {
            for (Centroid c : clusters) {
                Shape s = new Ellipse2D.Double((int) c.getP().getX() - CENTROID_SIZE / 2,
                        (int) c.getP().getY() - CENTROID_SIZE / 2, CENTROID_SIZE, CENTROID_SIZE);
                if (s.contains(x, y)) {
                    dragging = true;
                    dragCenter = c;
                    break;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        dragging = false;
        dragNode = null;
        dragCenter = null;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    // These methods are required by MouseMotionListener.
    public void updateLocation(MouseEvent e){
        if (dragNode != null)
            dragNode.setPosition(e.getX(), e.getY());
        if (dragCenter != null)
            dragCenter.getP().setLocation(e.getX(), e.getY());
        repaint();
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        // Draws the buffered image to the screen.
        g2.drawImage(bi, 0, 0, this);
    }

    public void update(Graphics g){
        // Clears the rectangle that was previously drawn.
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.fillRect(0, 0, dim.width, dim.height);

        if (algorithm.isInitialized())
           drawAlgo();
        else
           drawPlacing();

        paint(g);
    }

    private void drawPlacing() {
        Rectangle2D.Float r = new Rectangle2D.Float();

        for(CentroidNode n: centroids) {
            big.setColor(Color.black);
            for (java.awt.geom.Point2D p : n.nodes) {
                big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                big.fillOval((int) p.getX() - POINT_SIZE / 2, (int) p.getY() - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
                big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                big.drawOval((int) p.getX() - POINT_SIZE / 2, (int) p.getY() - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
            }

            r.setRect(n.box);
            big.setColor(Color.red);
            big.fill(r);
        }
    }

    private void drawAlgo() {
        for(Point p : points) {
            drawPoint((int) p.getP().getX(), (int) p.getP().getY(), POINT_SIZE, p.getColor(), p.getColor(), 0.4f);
        }

        for (Centroid c : clusters) {
            java.awt.geom.Point2D old = null;
            if (command.getShowHistory()) {
                for (java.awt.geom.Point2D hp : c.getHistory()) {
                    if (old != null) {
                        big.setColor(Color.black);
                        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                        drawArrow(big, (int) old.getX(), (int) old.getY(), (int) hp.getX(), (int) hp.getY());
                    }

                    drawPoint((int) hp.getX(), (int) hp.getY(), CENTROID_SIZE, Color.gray, c.getColor(), 0.8f);
                    old = hp;
                }

                if (old != null) {
                    big.setColor(Color.black);
                    big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    drawArrow(big, (int) old.getX(), (int) old.getY(), (int) c.getP().getX(), (int) c.getP().getY());
                }
            }

            drawPoint((int) c.getP().getX(), (int) c.getP().getY(), CENTROID_SIZE, Color.black, c.getColor(), 0.8f);
        }

        if (algorithm.getDiagram() != null) {
            drawVoronoi2();
        }
    }

    private void drawPoint(int x, int y, int size, Color c, Color fill, float fill_val) {
        big.setColor(fill);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fill_val));
        big.fillOval(x - size / 2, y - size / 2, size, size);
        big.setColor(c);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.drawOval(x - size / 2, y - size / 2, size, size);
    }

    private void drawVoronoi() {
        Vertex[] diagram = algorithm.getDiagram();
        for(int j = 0; j < diagram.length; j++) {
            Enumeration e = diagram[j].edges.elements();
            while(e.hasMoreElements()) {
                VoronoiSegment s = (VoronoiSegment) e.nextElement();
                if(s.floating)
                    continue;

                int x1 = (int)s.v1.x;
                int y1 = (int)s.v1.y;
                int x2 = (int)s.v2.x;
                int y2 = (int)s.v2.y;

                big.setColor(Color.black);
                //System.out.println("Drawing line from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")");
                big.drawLine(x1, y1, x2, y2);
            }
        }
    }

    private void drawVoronoi2() {
        Vertex[] diagram = algorithm.getDiagram();
        for(int j = 0; j < diagram.length; j++) {
            Enumeration e = diagram[j].edges.elements();
            while(e.hasMoreElements()) {
                VoronoiSegment s = (VoronoiSegment)e.nextElement();

                if(s.floating)
                    continue;

                int x1, y1, x2, y2;
                int rx1, ry1, rx2, ry2;

                double dx = s.p2.x - s.p1.x;
                double dy = s.p2.y - s.p1.y;

                double m = dy / dx;
                double b = s.p1.y - m * s.p1.x;

                if(dx > 0) {
                    rx1 = 0;
                    rx2 = dim.width;
                }
                else {
                    rx1 = dim.width;
                    rx2 = 0;
                }

                if(dy > 0) {
                    ry1 = 0;
                    ry2 = dim.height;
                }
                else {
                    ry1 = dim.height;
                    ry2 = 0;
                }

                x1 = (int)s.p1.x;
                y1 = (int)s.p1.y;
                x2 = (int)s.p2.x;
                y2 = (int)s.p2.y;

                if(s.i1) {
                    if(dx == 0) {
                        y1 = ry1;
                    }
                    else {
                        x1 = rx1;
                        y1 = (int)(m * rx1 + b);
                    }
                }
                if(s.i2) {
                    if(dx == 0) {
                        y2 = ry2;
                    }
                    else {
                        x2 = rx2;
                        y2 = (int)(m * rx2 + b);
                    }
                }

                big.setColor(Color.black);
                big.drawLine(x1, y1, x2, y2);
            }
        }
    }

    int al = 12;		// Arrow length
    int aw = 10;		// Arrow width
    int haw = aw/2;	// Half arrow width
    int xValues[] = new int[3];
    int yValues[] = new int[3];

    public void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        // Draw line
        g.drawLine(x1,y1,x2,y2);

        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(x1, y1);
        // only draw arrow if the line is long enough
        if (p.distance(x2, y2) > al) {
            // Calculate x-y values for arrow head
            calcValues(x1,y1,x2,y2);
            g.fillPolygon(xValues,yValues,3);
        }
    }

    /* CALC VALUES: Calculate x-y values. */

    public void calcValues(int x1, int y1, int x2, int y2) {
        // North or south
        if (x1 == x2) {
            // North
            if (y2 < y1) arrowCoords(x2,y2,x2-haw,y2+al,x2+haw,y2+al);
            // South
            else arrowCoords(x2,y2,x2-haw,y2-al,x2+haw,y2-al);
            return;
        }
        // East or West
        if (y1 == y2) {
            // East
            if (x2 > x1) arrowCoords(x2,y2,x2-al,y2-haw,x2-al,y2+haw);
            // West
            else arrowCoords(x2,y2,x2+al,y2-haw,x2+al,y2+haw);
            return;
        }
        // Calculate quadrant

        calcValuesQuad(x1,y1,x2,y2);
    }

    /* CALCULATE VALUES QUADRANTS: Calculate x-y values where direction is not
    parallel to eith x or y axis. */

    public void calcValuesQuad(int x1, int y1, int x2, int y2) {
        double arrowAng = Math.toDegrees (Math.atan((double) haw/(double) al));
        double dist = Math.sqrt(al*al + aw);
        double lineAng = Math.toDegrees(Math.atan(((double) Math.abs(x1-x2))/
                ((double) Math.abs(y1-y2))));

        // Adjust line angle for quadrant
        if (x1 > x2) {
            // South East
            if (y1 > y2) lineAng = 180.0-lineAng;
        }
        else {
            // South West
            if (y1 > y2) lineAng = 180.0+lineAng;
            // North West
            else lineAng = 360.0-lineAng;
        }

        // Calculate coords

        xValues[0] = x2;
        yValues[0] = y2;
        calcCoords(1,x2,y2,dist,lineAng-arrowAng);
        calcCoords(2,x2,y2,dist,lineAng+arrowAng);
    }

    /* CALCULATE COORDINATES: Determine new x-y coords given a start x-y and
    a distance and direction */

    public void calcCoords(int index, int x, int y, double dist,
                           double dirn) {
        while(dirn < 0.0)   dirn = 360.0+dirn;
        while(dirn > 360.0) dirn = dirn-360.0;

        // North-East
        if (dirn <= 90.0) {
            xValues[index] = x + (int) (Math.sin(Math.toRadians(dirn))*dist);
            yValues[index] = y - (int) (Math.cos(Math.toRadians(dirn))*dist);
            return;
        }
        // South-East
        if (dirn <= 180.0) {
            xValues[index] = x + (int) (Math.cos(Math.toRadians(dirn-90))*dist);
            yValues[index] = y + (int) (Math.sin(Math.toRadians(dirn-90))*dist);
            return;
        }
        // South-West
        if (dirn <= 90.0) {
            xValues[index] = x - (int) (Math.sin(Math.toRadians(dirn-180))*dist);
            yValues[index] = y + (int) (Math.cos(Math.toRadians(dirn-180))*dist);
        }
        // Nort-West
        else {
            xValues[index] = x - (int) (Math.cos(Math.toRadians(dirn-270))*dist);
            yValues[index] = y - (int) (Math.sin(Math.toRadians(dirn-270))*dist);
        }
    }

    // ARROW COORDS: Load x-y value arrays */

    public void arrowCoords(int x1, int y1, int x2, int y2, int x3, int y3) {
        xValues[0] = x1;
        yValues[0] = y1;
        xValues[1] = x2;
        yValues[1] = y2;
        xValues[2] = x3;
        yValues[2] = y3;
    }

    public Dimension getPreferredSize() {
        return new Dimension(300,200);
    }

    public void componentResized(ComponentEvent e) {
        dim = getSize();
        int w = dim.width;
        int h = dim.height;

        bi = (BufferedImage)createImage(w, h);
        big = bi.createGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clears the rectangle that was previously drawn.
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.fillRect(0, 0, dim.width, dim.height);
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}
