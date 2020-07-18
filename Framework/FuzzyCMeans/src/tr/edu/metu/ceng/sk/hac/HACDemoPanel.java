package tr.edu.metu.ceng.sk.hac;

import javax.swing.*;
import javax.swing.border.*;

import tr.edu.metu.ceng.sk.SwingUtils;
import tr.edu.metu.ceng.sk.hac.actions.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;


public class HACDemoPanel extends JPanel implements ActionListener
{
    private Map<String, HACAbstractAction> actions;
    private CommandPanel controls;
    private DrawPanel drawing;
    private DendroPanel dendro;

    private HACAbstractAction exitAction, initAction, resetAction, startAction, stepAction, runAction, zoomDendrogram;

    /**
     * Construct the demo.
     */
    public HACDemoPanel(boolean showMenu) {

        setLayout(new GridBagLayout());
        setBorder(new EtchedBorder());

        createActions();

        if (showMenu) {
            JMenuBar menu = createMenuBar();
            menu.setMinimumSize(new Dimension(100, 22));
            SwingUtils.addToGridBag(this, menu, 0, 0, 3, 1, 0.0, 0.0);
        }

        controls = new CommandPanel(actions);

        //JPanel p = new JPanel(new GridBagLayout());
        JPanel p = new JPanel(new BorderLayout());
        EmptyBorder eb = new EmptyBorder(5,0,5,5);
        BevelBorder bb = new BevelBorder(BevelBorder.LOWERED);
        p.setBorder(new CompoundBorder(eb,bb));
        //addToGridBag(p,controls,0,0,1,1,0,0);
        p.add(controls, BorderLayout.NORTH);
        SwingUtils.addToGridBag(this, p, 2, 1, 1, 1, 0.0, 1.0);

        drawing = new DrawPanel(controls);

        JPanel q = new JPanel(new BorderLayout());
        q.setBorder(new CompoundBorder(eb,bb));
        q.add(drawing, BorderLayout.CENTER);
        SwingUtils.addToGridBag(this, q, 0, 1, 1, 1, 1.0, 1.0);

        dendro = new DendroPanel(drawing, controls);

        q = new JPanel(new BorderLayout());
        q.setBorder(new CompoundBorder(eb,bb));

        ScrollPane scrollp = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scrollp.add(dendro);

        q.add(scrollp, BorderLayout.CENTER);
        q.setPreferredSize(new Dimension(300, 300));

        SwingUtils.addToGridBag(this, q, 1, 1, 1, 1, 0.0, 1.0);
    }

    private void createActions() {
        actions = new HashMap<String, HACAbstractAction>();

        exitAction = new ExitAction();
        registerAction(exitAction);

        initAction = new InitAction();
        registerAction(initAction);

        resetAction = new ResetAction();
        registerAction(resetAction);

        startAction = new StartAction();
        registerAction(startAction);

        stepAction = new StepAction();
        registerAction(stepAction);

        runAction = new RunAction();
        registerAction(runAction);

        zoomDendrogram = new ZoomDendrogramAction();
        registerAction(zoomDendrogram);
    }

    private void registerAction(HACAbstractAction action)  {
        action.addActionListener(this);
        actions.put(action.getName(), action);
    }

    private JMenuBar createMenuBar() {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JMenuBar menuBar = new JMenuBar();

        JMenu file = (JMenu) menuBar.add(new JMenu("File"));
        file.add(exitAction);

        return menuBar;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Compare the action command to the known actions.
        if (command.equals(exitAction.getActionCommand()))  {
            System.exit(0);
        } else if (command.equals(initAction.getActionCommand()))  {
            drawing.initialize();
            dendro.repaint();
        } else if (command.equals(resetAction.getActionCommand()))  {
            drawing.reset();
            dendro.repaint();
        } else if (command.equals(startAction.getActionCommand()))  {
            drawing.start();
            dendro.repaint();
        } else if (command.equals(stepAction.getActionCommand()))  {
            drawing.step();
            dendro.repaint();
        } else if (command.equals(runAction.getActionCommand()))  {
            drawing.run();
            dendro.repaint();
        } else if (command.equals(zoomDendrogram.getActionCommand())) {
            dendro.repaint();
        }
    }
}
