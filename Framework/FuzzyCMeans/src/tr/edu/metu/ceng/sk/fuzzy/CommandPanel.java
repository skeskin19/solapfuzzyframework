package tr.edu.metu.ceng.sk.fuzzy;

import java.awt.*;
import javax.swing.*;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.border.TitledBorder;

import tr.edu.metu.ceng.sk.SwingUtils;
import tr.edu.metu.ceng.sk.fuzzy.actions.FuzzyAbstractAction;

import javax.swing.border.EtchedBorder;


public class CommandPanel extends JPanel
{
    private static final int DEFAULT_NUMBER_OF_ITERATION = 10;
	private static final int NUMBER_OF_CLUSTER = 8;
	public JSlider pointSlider, fuzzySlider, accuracySlider, clusterSlider;
    public JButton initButton, startButton, stepButton, runButton, resetButton, printButton;

    private Font font = new Font("serif", Font.PLAIN, 12);

    public CommandPanel(Map<String, FuzzyAbstractAction> actions)
    {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        TitledBorder tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Number of points");

        pointSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);
        pointSlider.setBorder(tb);
        pointSlider.setMinimumSize(new Dimension(80,46));
        pointSlider.setMajorTickSpacing(10);
        pointSlider.setMinorTickSpacing(2);
        pointSlider.setPaintTicks(true);
        pointSlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, pointSlider, 0, 0, 2, 1, 1.0, 1.0);

        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Number of clusters");

        clusterSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, NUMBER_OF_CLUSTER);
        clusterSlider.setBorder(tb);
        clusterSlider.setMinimumSize(new Dimension(80,46));
        clusterSlider.setMajorTickSpacing(9);
        clusterSlider.setMinorTickSpacing(1);
        clusterSlider.setPaintTicks(true);
        clusterSlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, clusterSlider, 0, 1, 2, 1, 1.0, 1.0);

        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Fuzzyness");

        fuzzySlider = new JSlider(JSlider.HORIZONTAL, 11, 50, 20);

        // Create the label table
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 11 ), new JLabel("1.1") );
        labelTable.put( new Integer( 20 ), new JLabel("2.0") );
        labelTable.put( new Integer( 50 ), new JLabel("5.0") );
        fuzzySlider.setLabelTable( labelTable );

        fuzzySlider.setBorder(tb);
        fuzzySlider.setMinimumSize(new Dimension(80,46));
        fuzzySlider.setMajorTickSpacing(20);
        fuzzySlider.setMinorTickSpacing(2);
        fuzzySlider.setPaintTicks(true);
        fuzzySlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, fuzzySlider, 0, 2, 2, 1, 1.0, 1.0);

        tb = new TitledBorder(new EtchedBorder());
        tb.setTitleFont(font);
        tb.setTitle("Accuracy");

        accuracySlider = new JSlider(JSlider.HORIZONTAL, 1, 10, DEFAULT_NUMBER_OF_ITERATION);

        // Create the label table
        labelTable = new Hashtable();
        labelTable.put( new Integer( 1 ), new JLabel("0.1") );
        labelTable.put( new Integer( 5 ), new JLabel("0.5") );
        labelTable.put( new Integer( 10 ), new JLabel("1.0") );
        accuracySlider.setLabelTable( labelTable );

        accuracySlider.setBorder(tb);
        accuracySlider.setMinimumSize(new Dimension(80,46));
        accuracySlider.setMajorTickSpacing(5);
        accuracySlider.setMinorTickSpacing(1);
        accuracySlider.setPaintTicks(true);
        accuracySlider.setPaintLabels(true);
        SwingUtils.addToGridBag(this, accuracySlider, 0, 3, 2, 1, 1.0, 1.0);

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

    public float getFuzzyness() {
        return fuzzySlider.getValue() / 10.0f;
    }

    public float getAccuracy() {
        return accuracySlider.getValue() / 10.0f;
    }

    public int getPoints() {
        return pointSlider.getValue();
    }

    public int getClusters() {
        return clusterSlider.getValue();
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
