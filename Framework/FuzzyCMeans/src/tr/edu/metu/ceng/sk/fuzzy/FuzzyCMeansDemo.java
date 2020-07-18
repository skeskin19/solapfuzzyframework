package tr.edu.metu.ceng.sk.fuzzy;


import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import tr.edu.metu.ceng.sk.fuzzy.actions.FuzzyAbstractAction;


public class FuzzyCMeansDemo {
    private Map<String, FuzzyAbstractAction> actions;
    private CommandPanel controls;
    private FuzzyCMeansInitParameters fuzzyCMeans;

    private FuzzyAbstractAction exitAction, initAction, resetAction, startAction, stepAction, runAction;

    /**
     * Create the demo panel
     */
    public FuzzyCMeansDemo(boolean showMenu)
    {
        
        createActions();

       
        controls = new CommandPanel(actions);

        fuzzyCMeans = new FuzzyCMeansInitParameters(controls);
        
        
        
        //applyFuzzyCMeansAlgorithm();
    }
    
    public void addPoint(double pt) {
    	fuzzyCMeans.addPoint(pt);
    }

    private void createActions() {
        actions = new HashMap<String, FuzzyAbstractAction>();

        
    }

    private void registerAction(FuzzyAbstractAction action)  {
        
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
    
    public void applyFuzzyCMeansAlgorithm() {
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
    
    public HashMap<Double,double[]> getCalculatedValues() {
    	HashMap<Double,double[]> resultMap = new HashMap<Double,double[]>();
    	
    	for(Point p:fuzzyCMeans.getPoints()) {
    		double[] a= fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(p.getX());
    		resultMap.put(Double.valueOf(p.getX()), a);
    	}
    	return resultMap;
    }
    
    public double[] getFuzzyMembershipOfCentroids(double value) {
    	return fuzzyCMeans.calculateFuzzyMemberShipFromCentroidRange(value);
    }

	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Compare the action command to the known actions.
        if (command.equals(exitAction.getActionCommand()))  {
            System.exit(0);
        } else if (command.equals(initAction.getActionCommand()))  {
            fuzzyCMeans.initialize(3);
        } else if (command.equals(resetAction.getActionCommand()))  {
            fuzzyCMeans.reset();
        } else if (command.equals(startAction.getActionCommand()))  {
            fuzzyCMeans.start();
        } else if (command.equals(stepAction.getActionCommand()))  {
            fuzzyCMeans.step();
        } else if (command.equals(runAction.getActionCommand()))  {
            fuzzyCMeans.run();
        }
    }
    
    public static void main(String[] args) {
    	new FuzzyCMeansDemo(false);
    }
}
