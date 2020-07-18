package tr.edu.metu.ceng.sk.fuzzy;

import sk.util.FCMUtil;
import tr.edu.metu.ceng.sk.fuzzy.actions.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FuzzyDemoPanel extends JPanel implements ActionListener
{
    private Map<String, FuzzyAbstractAction> actions;
    private CommandPanel controls;
    private DrawPanel drawing;
    
    private FuzzyCMeansInitParameters fuzzyCMeans;

    private FuzzyAbstractAction exitAction, initAction, resetAction, startAction, stepAction, runAction;

    /**
     * Create the demo panel
     */
    public FuzzyDemoPanel(boolean showMenu)
    {
        setLayout(new BorderLayout());
        setBorder(new EtchedBorder());

        createActions();

        if (showMenu)
            add(createMenuBar(), BorderLayout.NORTH);

        controls = new CommandPanel(actions);

        JPanel p = new JPanel(new BorderLayout());
        EmptyBorder eb = new EmptyBorder(5,0,5,5);
        BevelBorder bb = new BevelBorder(BevelBorder.LOWERED);
        p.setBorder(new CompoundBorder(eb,bb));
        p.add(controls, BorderLayout.NORTH);
        add(p, BorderLayout.EAST);

        drawing = new DrawPanel(controls);

        JPanel q = new JPanel(new BorderLayout());
        q.setBorder(new CompoundBorder(eb,bb));
        q.add(drawing, BorderLayout.CENTER);
        add(q, BorderLayout.CENTER);
        
        fuzzyCMeans = new FuzzyCMeansInitParameters(controls);
    }

    private void createActions() {
        actions = new HashMap<String, FuzzyAbstractAction>();

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
    }

    private void registerAction(FuzzyAbstractAction action)  {
        action.addActionListener(this);
        actions.put(action.getName(), action);
    }

    /**
     * Create the menu bar
     * @return the menu bar
     */
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
        }
    }
    
    public void applyFuzzyCMeansAlgorithm(int numberOfCluster) {
    	fuzzyCMeans.initialize(numberOfCluster);
    	fuzzyCMeans.start();
    	
    	double difference = 0.0;
		do {
			fuzzyCMeans.run();
			difference = fuzzyCMeans.getAlgorithm().calculateMaxMembershipDistance();
		} while(difference>0.0);
    	
    }
    
    public void applyFuzzyCMeansAlgorithm2() {
    	fuzzyCMeans.initialize(3);
    	fuzzyCMeans.start();
    	fuzzyCMeans.run();
    	//fuzzyCMeans.printResult(); // TODO - deneme yaparken acilacak
    	double testVal = -15.5;
    	double[] a= fuzzyCMeans.getMemberShip(testVal);
    	System.out.println(" before "+testVal+" =====> "+a[0] + ", "+ a[1] + ", " + a[2]);
    	System.out.println(" before-1 "+testVal+" =====> "+(1-a[0]) + ", "+ (1-a[1]) + ", " + (1-a[2]));
    	double[] b = fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(testVal);
    	System.out.println(" after "+testVal+" =====> "+b[0] + ", "+ b[1] + ", " + b[2]); 
    	System.out.println(" after-1 "+testVal+" =====> "+(1-b[0]) + ", "+ (1-b[1]) + ", " + (1-b[2])); 
    	
    	double testVal2 = -18.9;
    	a= fuzzyCMeans.getMemberShip(testVal2);
    	//System.out.println(" before "+testVal2+" =====> "+a[0] + ", "+ a[1] + ", " + a[2]);
    	//System.out.println(" before-1 "+testVal2+" =====> "+(1-a[0]) + ", "+ (1-a[1]) + ", " + (1-a[2]));
    	b = fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(testVal2);
    	//System.out.println(" after "+testVal2+" =====> "+b[0] + ", "+ b[1] + ", " + b[2]); 
    	//System.out.println(" after-1 "+testVal2+" =====> "+(1-b[0]) + ", "+ (1-b[1]) + ", " + (1-b[2])); 
    	
    	/*
    	a= fuzzyCMeans.getMemberShip(-16.5);
    	System.out.println("-16.5 =====> "+a[0] + ", "+ a[1] + ", " + a[2]);
    	b = fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(-16.5);
    	System.out.println("-16.5 sonra =====> "+b[0] + ", "+ b[1] + ", " + b[2]); 
    	
    	a= fuzzyCMeans.getMemberShip(17.3);
    	System.out.println("17.3 =====> "+a[0] + ", "+ a[1] + ", " + a[2]);
    	b = fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(17.3);
    	System.out.println("17.3 sonra =====> "+b[0] + ", "+ b[1] + ", " + b[2]); 
    	
    	a= fuzzyCMeans.getMemberShip(0.4);
    	System.out.println("0.4 =====> "+a[0] + ", "+ a[1] + ", " + a[2]);    	
    	b = fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(0.4);
    	System.out.println("0.4 sonra =====> "+b[0] + ", "+ b[1] + ", " + b[2]); 
    	*/
    }
    
    public HashMap<Double,double[]> getCalculatedValues(String[] clusterLabels) {
    	HashMap<Double,double[]> resultMap = new HashMap<Double,double[]>();
    	
		for (Point p : fuzzyCMeans.getPoints()) {
			if (resultMap.get(Double.valueOf(p.getX())) == null) {
				double[] fuzzyVal = fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(p.getX());
				resultMap.put(Double.valueOf(p.getX()), fuzzyVal);
				
				System.out.print(p.getX() + " => max [ " + getMaxClusterLabel(fuzzyVal, clusterLabels) + " ] ");
				for (int i = 0; i < fuzzyVal.length; ++i)
					System.out.print(fuzzyVal[i] + "; ");
				System.out.print("\n");
			}
		}
    	return resultMap;
    }
    
    public double[] getFuzzyMembershipOfCentroids(double value) {
    	return fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(value);
    }
    
    public void addPoint(double pt) {
    	fuzzyCMeans.addPoint(pt);
    }
    
    public ArrayList<Centroid> getClusters() {
		return fuzzyCMeans.getClusters();
	}
    
	public void printGeneratedFuzzyValues(String[] clusterLabels) {
		for (Point p : fuzzyCMeans.getPoints()) {
			double[] fuzzyVal= fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(p.getX());
			
			System.out.print(p.getX() + " => max [ " + getMaxClusterLabel(fuzzyVal, clusterLabels) + " ] ");
			for (int i = 0; i < fuzzyVal.length; ++i)
				System.out.print(fuzzyVal[i] + "; ");
			System.out.print("\n");
		}
	}
	
	private String getMaxClusterLabel(double[] array, String[] clusterLabels) {
		double max = array[0];
		int ind=0;
	      for (int j = 1; j < array.length; j++) {
	          if (array[j] > max) {
	              max = array[j];
	              ind=j;
	          }
	      }
	  
	      return clusterLabels[ind]+"("+max+")";
	}
}
