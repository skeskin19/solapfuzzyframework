package tr.edu.metu.ceng.sk.kmeans;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import tr.edu.metu.ceng.sk.kmeans.actions.*;

import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;


public class KMeansDemoPanel extends JPanel implements ActionListener
{
    private Map<String, KMeansAbstractAction> actions;
    private CommandPanel controls;
    private DrawPanel drawing;

    private KMeansAbstractAction exitAction, initAction, resetAction, startAction, stepAction, runAction, showHistory;

    /**
     * Construct the demo.
     */
    public KMeansDemoPanel(boolean showMenu) {

        setLayout(new BorderLayout());
        setBorder(new EtchedBorder());

        createActions();

        if (showMenu)
            add(createMenuBar(), BorderLayout.NORTH);

        controls = new CommandPanel(actions);

        //JPanel p = new JPanel(new GridBagLayout());
        JPanel p = new JPanel(new BorderLayout());
        EmptyBorder eb = new EmptyBorder(5,0,5,5);
        BevelBorder bb = new BevelBorder(BevelBorder.LOWERED);
        p.setBorder(new CompoundBorder(eb,bb));
        //addToGridBag(p,controls,0,0,1,1,0,0);
        p.add(controls, BorderLayout.NORTH);
        add(p, BorderLayout.EAST);

        drawing = new DrawPanel(controls);

        JPanel q = new JPanel(new BorderLayout());
        q.setBorder(new CompoundBorder(eb,bb));
        q.add(drawing, BorderLayout.CENTER);
        add(q, BorderLayout.CENTER);
    }

    private void createActions() {
        actions = new HashMap<String, KMeansAbstractAction>();

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

        showHistory = new ShowHistoryAction();
        registerAction(showHistory);
    }

    private void registerAction(KMeansAbstractAction action)  {
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
        } else if (command.equals(resetAction.getActionCommand()))  {
            drawing.reset();
        } else if (command.equals(startAction.getActionCommand()))  {
            drawing.start();
        } else if (command.equals(stepAction.getActionCommand()))  {
            drawing.step();
        } else if (command.equals(runAction.getActionCommand()))  {
            drawing.run();
        } else if (command.equals(showHistory.getActionCommand())) {
            drawing.repaint();
        }
    }
}
