package tr.edu.metu.ceng.sk.hac;

import voronoi.VoronoiSegment;
import voronoi.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.text.NumberFormat;


public class DrawPanel extends Canvas implements MouseListener, MouseMotionListener,
        ComponentListener
{
    private ArrayList<Point> points;
    private Set<Cluster> clusters;

    private ArrayList<Point> points_cloned;

    private BufferedImage bi;
    private Graphics2D big;
    private Dimension dim;

    private boolean dragging;
    private Point dragPoint;

    private Point calcPoint;
    private Point calcPoint2;

    private HACAlgorithm algorithm;

    private static final int POINT_SIZE = 6;

    private CommandPanel command;

    private Font font = new Font("serif", Font.PLAIN, 12);

    private Font point_font = new Font("serif", Font.PLAIN, 10);

    public DrawPanel(CommandPanel cmd) {
        addMouseMotionListener(this);
        addMouseListener(this);
        addComponentListener(this);

        algorithm = new HACAlgorithm();

        points = new ArrayList<Point>();
        clusters = new TreeSet<Cluster>();

        command = cmd;
        command.setState(1, 0);
    }

    public Set<Cluster> getClusters() {
        return clusters;
    }

    public void initialize() {
        Random r = new Random();

        points.clear();
        clusters.clear();

        Point.resetCounter();

        for (int j=0; j<command.getNodes(); j++) {
            int x = (int) dim.getWidth() - 100;
            x = r.nextInt(x) + 50;
            int y = (int) dim.getHeight() - 80;
            y = r.nextInt(y) + 40;

            Point p = new Point(new Point2D.Double(x, y));
            points.add(p);
        }

        algorithm.setInit(false);
        command.setState(2, 0);
        repaint();
    }

    public void reset() {
        if (points_cloned != null) {
            points = points_cloned;
            clusters.clear();
            algorithm.setInit(false);
            setLinkage();
            command.setState(3, 0);
            repaint();
        }
    }

    private void setLinkage() {
        String linkage = command.getLinkage();
        HACAlgorithm.Linkage method = HACAlgorithm.Linkage.Single;
        if (linkage.equals("Complete")) method = HACAlgorithm.Linkage.Complete;
        else if (linkage.equals("Average")) method = HACAlgorithm.Linkage.Average;
        algorithm.setLinkage(method);
    }

    public void start() {
        Random r = new Random(new Date().getTime());

        clusters.clear();

        points_cloned = new ArrayList<Point>();
        for (Point p : points) {
            points_cloned.add(p.clone());

            // create a cluster for each point
            int rr = r.nextInt(256);
            int gg = r.nextInt(256);
            int bb = r.nextInt(256);

            Cluster c = new Cluster(new Color(rr, gg, bb));
            c.addPoint(p);
            clusters.add(c);
        }

        setLinkage();
        algorithm.init(clusters);

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
            for (Point p : points) {
                Shape s = getPointShape(p.getP().getX(), p.getP().getY(), POINT_SIZE);
                if (s.contains(x, y)) {
                    dragging = true;
                    dragPoint = p;
                    break;
                }
            }

            if (!dragging) {
                Point p = new Point(new Point2D.Double(x, y));
                points.add(p);
            }
        } else {
            for (Point p : points) {
                Shape s = getPointShape(p.getP().getX(), p.getP().getY(), POINT_SIZE);
                if (s.contains(x, y)) {
                    calcPoint = p;
                    return;
                }
            }
            calcPoint = null;
        }
    }

    public void mouseReleased(MouseEvent e) {
        dragging = false;
        dragPoint = null;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        // check wether we want to drag a centroid
        int x = e.getX();
        int y = e.getY();

        // if algorithm is not yet initialized, we can change layout of data items
        if (algorithm.isInitialized()) {
            for (Point p : points) {
                Shape s = getPointShape(p.getP().getX(), p.getP().getY(), POINT_SIZE);
                if (s.contains(x, y)) {
                    calcPoint2 = p;
                    repaint();
                    return;
                }
            }

            if (calcPoint2 != null) {
                calcPoint2 = null;
                repaint();
            }
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    // These methods are required by MouseMotionListener.
    public void updateLocation(MouseEvent e){
        if (dragPoint != null)
            dragPoint.getP().setLocation(e.getX(), e.getY());
        repaint();
    }

    public void paint(Graphics g){
        update(g);
    }

    public void update(Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        // Clears the rectangle that was previously drawn.
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.fillRect(0, 0, dim.width, dim.height);

        drawAlgo();

        // Draws the buffered image to the screen.
        g2.drawImage(bi, 0, 0, this);
    }

    private void drawAlgo() {
        if (!algorithm.isInitialized()) {
            for(Point p : points) {
                drawPoint((int) p.getP().getX(), (int) p.getP().getY(), POINT_SIZE, p.getColor(), p.getColor(), 0.4f);
                drawLabel((int) p.getP().getX(), (int) p.getP().getY(), POINT_SIZE, p.getName());
            }
        } else {
            for (Cluster c : clusters) {
                drawCluster(c, c.getColor());
            }

            drawUpdates();
            drawCalculations();
        }
    }

    private void drawCalculations() {
        if (calcPoint != null && calcPoint2 != null && calcPoint != calcPoint2) {
            big.setColor(Color.RED);
            Point2D p1 = calcPoint.getP();
            Point2D p2 = calcPoint2.getP();
            big.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
            big.setColor(Color.BLACK);

            double distance = p1.distance(p2);
            int x = (int) p2.getX();
            int y = (int) p2.getY() + 15;
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            big.setFont(font);
            big.drawString(nf.format(distance), x, y);
        } else if (calcPoint2 != null) {
            Cluster calcCluster = getClusterOfPoint(calcPoint2);
            if (calcCluster == null) return;

            for (Cluster c : clusters) {
                if (c == calcCluster) continue;
                
                double distance = algorithm.getDistance(calcCluster, c);
                Pair<Point, Point> pair = algorithm.getUpdatePair();

                big.setColor(Color.RED);
                Point2D p1 = pair.getP().getP();
                Point2D p2 = pair.getQ().getP();
                big.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
                big.setColor(Color.BLACK);

                int x = (int) p2.getX();
                int y = (int) p2.getY() + 15;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
                big.setFont(font);
                big.drawString(nf.format(distance), x, y);
            }
        }
    }

    private Cluster getClusterOfPoint(Point p) {
        for (Cluster c : clusters) {
            if (c.containsPoint(p)) return c;
        }
        return null;
    }

    private void drawUpdates() {
        List<Pair<Point, Point>> updates = algorithm.getHistory();
        Point2D p1, p2;
        for (Pair<Point, Point> p : updates) {
            p1 = p.getP().getP();
            p2 = p.getQ().getP();

            big.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }

    private void drawCluster(Cluster c, Color col) {
        List<Point> points = c.getPoints();
        for (Point p : points) {
            if (p == calcPoint)
                drawPoint((int) p.getP().getX(), (int) p.getP().getY(), POINT_SIZE, Color.RED, col, 0.4f);
            else
                drawPoint((int) p.getP().getX(), (int) p.getP().getY(), POINT_SIZE, col, col, 0.4f);

            drawLabel((int) p.getP().getX(), (int) p.getP().getY(), POINT_SIZE, p.getName());
        }

        List<Cluster> clusters = c.getClusters();
        for (Cluster cc : clusters) {
            drawCluster(cc, col);
        }
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

    private void drawLabel(int x, int y, int offset, String name) {
        big.setColor(Color.gray);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.setFont(point_font);
        big.drawString(name, x - offset / 2, y + offset + point_font.getSize());
        big.setFont(font);
    }

    private Shape getPointShape(double x, double y, double size) {
        Shape s = new Ellipse2D.Double(x - size / 2, y - size / 2, size, size);
        return s;
    }

    public Dimension getPreferredSize() {
        return new Dimension(300,400);
    }

    public void componentResized(ComponentEvent e) {
        dim = getSize();
        int w = dim.width;
        int h = dim.height;

        bi = (BufferedImage)createImage(w, h);
        big = bi.createGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}
