package tr.edu.metu.ceng.sk.kmeans;

import java.awt.*;
import javax.swing.*;
import java.util.Map;
import javax.swing.border.TitledBorder;

import tr.edu.metu.ceng.sk.SwingUtils;
import tr.edu.metu.ceng.sk.kmeans.actions.KMeansAbstractAction;

import javax.swing.border.EtchedBorder;


public class CommandPanel extends JPanel
{
    private String[] distributionNames = { "Normal" };
    private JComboBox distributionCombo;

    private JSlider devSliderX, devSliderY, nodeSlider, clusterSlider;
    private JButton initButton, startButton, stepButton, runButton, resetButton;
    private JCheckBox showHistory;

    private Font font = new Font("serif", Font.PLAIN, 12);

    /**
     * Create a new command panel
     * @param actions available action map
     */
    public CommandPanel(Map<String, KMeansAbstractAction> actions)
    {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        JLabel l = new JLabel("Distribution Function");
        l.setFont(font);
        SwingUtils.addToGridBag(this, l, 0, 0, 2, 1, 0.0, 0.0);

        distributionCombo = new JComboBox();
        distributionCombo.setPreferredSize(new Dimension(120, 18));
        distributionCombo.setLightWeightPopupEnabled(true);
        distributionCombo.setFont(font);
        for (int i = 0; i < distributionNames.length; i++) {
            distributionCombo.addItem(distributionNames[i]);
        }
        //distributionCombo.addItemListener(this);
        SwingUtils.addToGridBag(this, distributionCombo, 0, 1, 2, 1, 0.0, 0.0);


        devSliderX = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);
        TitledBorder tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Standard Deviation X");
        devSliderX.setBorder(tb);
        devSliderX.setMinimumSize(new Dimension(80,56));
        devSliderX.setMajorTickSpacing(10);
        devSliderX.setMinorTickSpacing(2);
        devSliderX.setPaintTicks(true);
        devSliderX.setPaintLabels(true);
        SwingUtils.addToGridBag(this, devSliderX, 0, 2, 2, 1, 1.0, 1.0);

        devSliderY = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);
        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Standard Deviation Y");
        devSliderY.setBorder(tb);
        devSliderY.setMinimumSize(new Dimension(80,56));
        devSliderY.setMajorTickSpacing(10);
        devSliderY.setMinorTickSpacing(2);
        devSliderY.setPaintTicks(true);
        devSliderY.setPaintLabels(true);
        SwingUtils.addToGridBag(this, devSliderY, 0, 3, 2, 1, 1.0, 1.0);

        nodeSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 30);
        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Number of nodes");
        nodeSlider.setBorder(tb);
        nodeSlider.setMinimumSize(new Dimension(80,56));
        nodeSlider.setMajorTickSpacing(50);
        nodeSlider.setMinorTickSpacing(10);
        nodeSlider.setPaintTicks(true);
        nodeSlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, nodeSlider, 0, 4, 2, 1, 1.0, 1.0);

        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Number of clusters");

        clusterSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 3);
        clusterSlider.setBorder(tb);
        clusterSlider.setMinimumSize(new Dimension(80,56));
        clusterSlider.setMajorTickSpacing(9);
        clusterSlider.setMinorTickSpacing(1);
        clusterSlider.setPaintTicks(true);
        clusterSlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, clusterSlider, 0, 5, 2, 1, 1.0, 1.0);

        showHistory = new JCheckBox(actions.get("Show history"));
        SwingUtils.addToGridBag(this, showHistory, 0, 6, 2, 1, 0.0, 0.0);

        initButton = new JButton(actions.get("Init"));
        initButton.setFont(font);
        SwingUtils.addToGridBag(this, initButton, 0, 7, 1, 1, 1.0, 1.0);

        resetButton = new JButton(actions.get("Reset"));
        resetButton.setFont(font);
        SwingUtils.addToGridBag(this, resetButton, 1, 7, 1, 1, 1.0, 1.0);

        startButton = new JButton(actions.get("Start"));
        startButton.setFont(font);
        SwingUtils.addToGridBag(this, startButton, 0, 8, 2, 1, 1.0, 1.0);

        stepButton = new JButton(actions.get("Step"));
        stepButton.setFont(font);
        SwingUtils.addToGridBag(this, stepButton, 0, 9, 1, 1, 1.0, 1.0);

        runButton = new JButton(actions.get("Run"));
        runButton.setFont(font);
        SwingUtils.addToGridBag(this, runButton, 1, 9, 1, 1, 1.0, 1.0);
    }

    public int getNodes() {
        return nodeSlider.getValue();
    }

    public int getClusters() {
        return clusterSlider.getValue();
    }

    public boolean getShowHistory() {
        return (showHistory.getSelectedObjects() != null);
    }

    public int getDeviationX() {
        return devSliderX.getValue();
    }

    public int getDeviationY() {
        return devSliderY.getValue();
    }

    public Dimension getPreferredSize() {
        return new Dimension(155,450);
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
}
