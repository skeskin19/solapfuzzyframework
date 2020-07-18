package tr.edu.metu.ceng.sk;

import javax.swing.*;
import java.awt.*;


public class SwingUtils
{
    /**
     * Add a component to a grid bag layout using several constraints
     * @param panel the panel the component should be added to
     * @param comp the component to be added
     * @param x the x position
     * @param y the y position
     * @param w the width of the component
     * @param h the height of the component
     * @param weightx the weight on the x-axis
     * @param weighty the weight on the y-axis
     */
    public static void addToGridBag(JPanel panel, Component comp,
            int x, int y, int w, int h, double weightx, double weighty) {

        GridBagLayout gbl = (GridBagLayout) panel.getLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = w;
        c.gridheight = h;
        c.weightx = weightx;
        c.weighty = weighty;
        panel.add(comp);
        gbl.setConstraints(comp, c);
    }
}
