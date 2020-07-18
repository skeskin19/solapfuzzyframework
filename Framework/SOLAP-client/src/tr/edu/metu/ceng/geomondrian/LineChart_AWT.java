package tr.edu.metu.ceng.geomondrian;

import org.jfree.chart.ChartPanel;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xml.CategoryDatasetHandler;

public class LineChart_AWT extends ApplicationFrame {

	String applicationTitle; 
	String chartTitle;
	String xLabel;
	String yLabel;
	DefaultCategoryDataset dataset;

	public LineChart_AWT(String applicationTitle, String chartTitle, String xLabel, String yLabel) {
		super(applicationTitle);
		this.applicationTitle = applicationTitle;
		this.chartTitle = chartTitle;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}
	
	public void createLineChart() {
		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, xLabel, yLabel, 
				getDataset(),
				PlotOrientation.VERTICAL, true, true, true);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
		
		CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
	    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
	    renderer.setShapesVisible(true);
	    
	    CategoryAxis domainAxis = plot.getDomainAxis();
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
	}

	private DefaultCategoryDataset createDataset() {
		dataset = new DefaultCategoryDataset();
		dataset.addValue(15, "schools", "1970");
		dataset.addValue(30, "schools", "1980");
		dataset.addValue(60, "schools", "1990");
		dataset.addValue(120, "schools", "2000");
		dataset.addValue(240, "schools", "2010");
		dataset.addValue(300, "schools", "2014");
		
		dataset.addValue(14, "hospitals", "1970");
		dataset.addValue(35, "hospitals", "1980");
		dataset.addValue(40, "hospitals", "1990");
		dataset.addValue(110, "hospitals", "2000");
		dataset.addValue(220, "hospitals", "2010");
		dataset.addValue(290, "hospitals", "2014");
		
		dataset.addValue(17, "cafes", "1970");
		dataset.addValue(38, "cafes", "1980");
		dataset.addValue(42, "cafes", "1990");
		dataset.addValue(115, "cafes", "2000");
		dataset.addValue(210, "cafes", "2010");
		dataset.addValue(270, "cafes", "2014");
		
		return dataset;
	}
	
	public DefaultCategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataset = dataset;
	}

	public static void main(String[] args) {
		LineChart_AWT chart = new LineChart_AWT("School Vs Years", "Numer of Schools vs years", "Years", "Number of Schools");
		chart.createDataset();
		chart.createLineChart();
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
