package tr.edu.metu.ceng.sk.kmeans;

import java.applet.Applet;
import java.awt.*;


public class KMeansDemoApplet extends Applet
{
    private static int WIDTH = 630;
    private static int HEIGHT = 540;

    public void init() {
        super.init();

        Component panel = new KMeansDemoPanel(false);

        setSize(WIDTH, HEIGHT);

        removeAll();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public Dimension getMinimumSize() {
        Dimension d = new Dimension(WIDTH, HEIGHT);
        return d;
    }
}
