package tr.edu.metu.ceng.sk.hac;

import java.applet.Applet;
import java.awt.*;


public class HACDemoApplet extends Applet
{
    private static int WIDTH = 800;
    private static int HEIGHT = 640;

    public void init() {
        super.init();

        Component panel = new HACDemoPanel(false);

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
