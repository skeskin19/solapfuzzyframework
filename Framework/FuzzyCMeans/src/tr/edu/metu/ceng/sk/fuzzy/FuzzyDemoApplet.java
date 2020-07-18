package tr.edu.metu.ceng.sk.fuzzy;

import java.applet.Applet;
import java.awt.*;


public class FuzzyDemoApplet extends Applet
{
    private static int WIDTH = 730;
    private static int HEIGHT = 570;

    public void init() {
        super.init();

        Component panel = new FuzzyDemoPanel(false);

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
