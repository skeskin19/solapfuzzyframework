package tr.edu.metu.ceng.sk.fuzzy;

import javax.swing.*;

import sk.bean.MeteorologicData;
import sk.database.JDBCStatementSelect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;


public class DrawPanel extends Canvas implements MouseListener, MouseMotionListener,
        ComponentListener
{
    private BufferedImage bi;
    private Graphics2D big;
    private Dimension dim;

    private ArrayList<Point> points;
    private ArrayList<Centroid> clusters;

    private ArrayList<Point> points_cloned;
    private ArrayList<Centroid> clusters_cloned;

    private boolean dragging;
    private Centroid dragCentroid = null;
    private Point dragPoint = null;

    private final int RASTER = 1;

    private FuzzyAlgorithm algorithm;

    private CommandPanel command;

    public DrawPanel(CommandPanel cmd) {
        addMouseMotionListener(this);
        addMouseListener(this);
        addComponentListener(this);

        command = cmd;

        points = new ArrayList<Point>(command.getPoints());
        clusters = new ArrayList<Centroid>(command.getClusters());

        algorithm = new FuzzyAlgorithm();

        command.setState(1, 0);
    }

    public void initialize() {
        Random r = new Random();

        int num_cells = (dim.width - 20) / RASTER - 1;

        points.clear();
        
        for (int i=0; i<command.getPoints(); i++) {
            points.add(new Point(r.nextInt(num_cells)));
        }
        /*
        List<Temperature> list = new ArrayList<Temperature>();
        try {
			list = JDBCStatementSelect.selectRecordsFromDbTemperatureTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        points.clear();
        for (int i=0; i<list.size(); i++) {
            points.add(new Point( Double.parseDouble(((Temperature)list.get(i)).getTemperature()) ) );
        }*/
        /*
        points.add(new Point(5));
        points.add(new Point(20));
        points.add(new Point(4));
        points.add(new Point(7));
        points.add(new Point(3));
        points.add(new Point(12));
        points.add(new Point(5));
        points.add(new Point(15));
        points.add(new Point(8));
        points.add(new Point(25));*/
  
        Collections.sort(points);

        clusters.clear();
        for (int j=0; j<command.getClusters(); j++) {
            int x = r.nextInt(num_cells);
            int rr = r.nextInt(256);
            int gg = r.nextInt(256);
            int bb = r.nextInt(256);
            clusters.add(new Centroid(x, new Color(rr, gg, bb)));
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
            algorithm.setFuzzyness(command.getFuzzyness());
            command.setState(3, 0);
            repaint();
        }
    }

    public void start() {
        Collections.sort(points);

        points_cloned = new ArrayList<Point>(command.getPoints());
        for (Point p : points) {
            points_cloned.add(p.clone());
        }

        clusters_cloned = new ArrayList<Centroid>(command.getClusters());
        for (Centroid c : clusters) {
            clusters_cloned.add(c.clone());
        }

        algorithm.init(points, clusters, command.getFuzzyness());
        command.setState(4, 0);
        repaint();
    }

    public void step() {
        algorithm.step();
        repaint();
    }

    public void run() {
        int iterations = algorithm.run(command.getAccuracy());
        command.setState(5, iterations);
        repaint();
    }

    public void mouseDragged(MouseEvent e) {
        if(dragging){
            if (dragCentroid != null) {
                dragCentroid.setX(e.getX() - 10);
                updateLocation(e);
            } else if (dragPoint != null) {
                dragPoint.setX(e.getX() - 10);
                updateLocation(e);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        // check wether we want to drag a centroid
        int x = e.getX();
        int y = e.getY();

        // only allow dragging if the algorithm is not yet initialized
        if (algorithm.isInitialized()) return;

        for (Centroid c : clusters) {
            Rectangle2D.Float rect = new Rectangle2D.Float((int)c.getX() * RASTER + 10 - 3, 10, 6, dim.height - 90);
            if (rect.contains(x, y)) {
                dragging = true;
                dragCentroid = c;
                break;
            }
        }

        for (Point p : points) {
            Ellipse2D.Float circle = new Ellipse2D.Float((int)p.getX() * RASTER + 10 - 5, dim.height - 65, 10, 10);
            if (circle.contains(x,y)) {
                dragging = true;
                dragPoint = p;
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        dragging = false;
        dragCentroid = null;
        dragPoint = null;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    // These methods are required by MouseMotionListener.
    public void updateLocation(MouseEvent e){
        repaint();
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        // Draws the buffered image to the screen.
        g2.drawImage(bi, 0, 0, this);
    }

    public void update(Graphics g){
        // Clears the screen
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));        
        big.fillRect(0, 0, dim.width, dim.height);

        big.setColor(Color.black);
        big.drawLine(10,dim.height - 80, dim.width - 10, dim.height - 80);
        for (Point p : points) {
            big.setColor(p.getColor());
            big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            big.fillOval((int) (p.getX() * RASTER + 10 - 5), dim.height - 65, 10, 10);
            big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            big.drawOval((int) (p.getX() * RASTER + 10 - 5), dim.height - 65, 10, 10);
        }

        for (Centroid c : clusters) {
            big.setColor(c.getColor());
            big.fillRect((int) (c.getX() * RASTER + 10 - 3), 10, 6, dim.height - 90);
        }

        // if fuzzy algorithm is initialized we can display membership functions
        if (algorithm.isInitialized()) {
            int j = 0;
            int height = dim.height - 90;
            for (Centroid c : clusters) {
                GeneralPath path = new GeneralPath();

                int[] xx = new int[points.size() + 2];
                int[] yy = new int[points.size() + 2];
                int i = 0;

                for (Point p : points) {
                    xx[i] = (int) (p.getX() * RASTER + 10);
                    yy[i] = dim.height - 80 - (int) (algorithm.U[i][j] * (float) height);
                    //path.lineTo(xx[i], yy[i]);

                    if (i == 0) {
                        path.moveTo(xx[i], yy[i]);
                    }

                    if (i % 2 == 1) {
                        int x1 = xx[i-1];
                        int y1 = yy[i-1];
                        path.quadTo(x1, y1, xx[i], yy[i]);
                    }
                    i++;
                }

                if (i % 2 == 1)
                    path.lineTo(xx[i-1], yy[i-1]);

                xx[i] = xx[i-1];
                yy[i++] = dim.height - 80;

                path.lineTo(xx[i-1], yy[i-1]);
                path.lineTo(xx[0], dim.height - 80);

                xx[i] = xx[0];
                yy[i] = yy[i-1];

                path.closePath();

                big.setColor(c.getColor());
                big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                big.fill(path);
                big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                big.draw(path);
                System.out.println("path:"+path.getBounds());
                j++;
            }
        }

        paint(g);
    }

    public Dimension getPreferredSize() {
        return new Dimension(400,300);
    }

    public void componentResized(ComponentEvent e) {
        dim = getSize();
        int w = dim.width;
        int h = dim.height;

        bi = (BufferedImage)createImage(w, h);
        big = bi.createGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clears the screen
        big.setColor(Color.white);
        big.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        big.fillRect(0, 0, dim.width, dim.height);
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}
