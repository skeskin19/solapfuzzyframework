package tr.edu.metu.ceng.sk.kmeans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class KMeansDemo
{
    private static int WIDTH = 630;
    private static int HEIGHT = 540;

    public static void main(String args[])
    {
        JFrame frame = new JFrame("KMeansDemo");

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        JOptionPane.setRootFrame(frame);

        JPanel panel = new KMeansDemoPanel(true);

        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setLocation(d.width/2 - WIDTH/2, d.height/2 - HEIGHT/2);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        frame.setVisible(true);
    }
}
