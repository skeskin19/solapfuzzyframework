package tr.edu.metu.ceng.sk.hac;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ChangeListener;

import tr.edu.metu.ceng.sk.SwingUtils;
import tr.edu.metu.ceng.sk.hac.actions.HACAbstractAction;

import javax.swing.event.ChangeEvent;
import java.util.Map;
import java.util.Hashtable;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;


public class CommandPanel extends JPanel implements ChangeListener
{
    private String[] linkageNames = { "Single", "Complete", "Average" };
    private JComboBox linkageCombo;

    private JSlider nodeSlider, zoomSlider;
    private JButton initButton, startButton, stepButton, runButton, resetButton;

    private Font font = new Font("serif", Font.PLAIN, 12);

    private HACAbstractAction zoomAction;

    /**
     * Create a new command panel
     * @param actions available action map
     */
    public CommandPanel(Map<String, HACAbstractAction> actions)
    {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        JLabel l = new JLabel("Linkage Algorithm");
        l.setFont(font);
        SwingUtils.addToGridBag(this, l, 0, 0, 2, 1, 0.0, 0.0);

        linkageCombo = new JComboBox();
        linkageCombo.setPreferredSize(new Dimension(120, 18));
        linkageCombo.setLightWeightPopupEnabled(true);
        linkageCombo.setFont(font);
        for (int i = 0; i < linkageNames.length; i++) {
            linkageCombo.addItem(linkageNames[i]);
        }
        //distributionCombo.addItemListener(this);
        SwingUtils.addToGridBag(this, linkageCombo, 0, 1, 2, 1, 0.0, 0.0);

        nodeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        TitledBorder tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Number of nodes");
        nodeSlider.setBorder(tb);
        nodeSlider.setMinimumSize(new Dimension(80,56));
        nodeSlider.setMajorTickSpacing(25);
        nodeSlider.setMinorTickSpacing(5);
        nodeSlider.setPaintTicks(true);
        nodeSlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, nodeSlider, 0, 2, 2, 1, 1.0, 1.0);

        zoomSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 10);
        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Dendrogram zoom");
        zoomSlider.setBorder(tb);
        zoomSlider.setMinimumSize(new Dimension(80,56));

        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 1 ), new JLabel("0.1") );
        labelTable.put( new Integer( 10 ), new JLabel("1.0") );
        labelTable.put( new Integer( 20 ), new JLabel("2.0") );
        zoomSlider.setLabelTable( labelTable );

        zoomSlider.setPaintLabels(true);
        zoomSlider.addChangeListener(this);
        SwingUtils.addToGridBag(this, zoomSlider, 0, 3, 2, 1, 1.0, 1.0);
        zoomAction = actions.get("Zoom Dendrogram");

        initButton = new JButton(actions.get("Init"));
        initButton.setFont(font);
        SwingUtils.addToGridBag(this, initButton, 0, 4, 1, 1, 1.0, 1.0);

        resetButton = new JButton(actions.get("Reset"));
        resetButton.setFont(font);
        SwingUtils.addToGridBag(this, resetButton, 1, 4, 1, 1, 1.0, 1.0);

        startButton = new JButton(actions.get("Start"));
        startButton.setFont(font);
        SwingUtils.addToGridBag(this, startButton, 0, 5, 2, 1, 1.0, 1.0);

        stepButton = new JButton(actions.get("Step"));
        stepButton.setFont(font);
        SwingUtils.addToGridBag(this, stepButton, 0, 6, 1, 1, 1.0, 1.0);

        runButton = new JButton(actions.get("Run"));
        runButton.setFont(font);
        SwingUtils.addToGridBag(this, runButton, 1, 6, 1, 1, 1.0, 1.0);
    }

    public String getLinkage() {
        return (String) linkageCombo.getSelectedItem();
    }

    public int getNodes() {
        return nodeSlider.getValue();
    }

    public int getZoomLevel() {
        return zoomSlider.getValue();
    }

    public Dimension getPreferredSize() {
        return new Dimension(155,400);
    }

    public void setState(int state, int val) {
        switch (state) {
            case 1:
                runButton.setEnabled(false);
                stepButton.setEnabled(false);
                resetButton.setEnabled(false);
                startButton.setEnabled(false);
                break;

            case 2:
                runButton.setText("Run");
                runButton.setEnabled(false);
                stepButton.setEnabled(false);
                resetButton.setEnabled(false);
                startButton.setEnabled(true);
                break;
            case 3:
                runButton.setEnabled(false);
                stepButton.setEnabled(false);
                runButton.setText("Run");
                break;
            case 4:
                resetButton.setEnabled(true);
                runButton.setEnabled(true);
                stepButton.setEnabled(true);
                break;
            case 5:
                runButton.setText("Run: " + val + "x");
                break;

        }
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            zoomAction.actionPerformed(new ActionEvent(this, 0, zoomAction.getActionCommand()));
        }
    }
}
