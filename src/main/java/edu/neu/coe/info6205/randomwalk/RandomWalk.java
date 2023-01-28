/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.ChartFactory;

public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // FIXME do move by replacing the following code
    	x += dx;
		y += dy;
//         throw new RuntimeException("Not implemented");
        // END 
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // FIXME
    	for (int i = 0; i < m; i++) {
			randomMove();
		}
        // END 
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // FIXME by replacing the following code
//         return 0.0;
    	return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
        // END 
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }
    
    public static double randomWalkPlotGraphPerExp(int m, int n) {

		double totalDistance = 0;

		List<Double> total = new ArrayList<Double>();
		for (int i = 0; i < n; i++) { // run 10 experiments
			RandomWalk walk = new RandomWalk();
			walk.randomWalk(m);
			totalDistance = totalDistance + walk.distance(); // 1 experiment for 10 steps
			total.add(totalDistance);
			totalDistance = 0;

		}

		return total.stream().mapToDouble(Double::longValue).sum() / n; // 10 steps 10 experiments gives you the mean
																		// expected distance.
	}
    public static void makegraph(int x, int y) {
    	List<Integer> noOfSteps = new ArrayList<Integer>();
    	for (int u = 0; u < x; u++) { 
    		noOfSteps.add(u);
		}
    	int noOfExp = y;
    	double meanDistance = 0;
    	List<Integer> steps = new ArrayList<Integer>();
		List<Double> meanDist = new ArrayList<Double>();
		for (int a : noOfSteps) {
			steps.add(a);
			meanDistance = randomWalkPlotGraphPerExp(a, noOfExp);
			meanDist.add(meanDistance);
			System.out.println(a + " steps: " + meanDistance + " over " + noOfExp + " experiments");
			meanDistance = 0;
		}
		
		List<Integer> xaxis = new ArrayList<Integer>();  
		List<Double> yaxis = new ArrayList<Double>();
		
		for (int a : noOfSteps) {
			xaxis.add(a);
			yaxis.add(0.90 * Math.sqrt(a));
		}

		
		RandomWalk.plot(steps, meanDist);
		
		RandomWalk.plot(xaxis, yaxis); 
    }
    public static void main(String[] args) {
        if (args.length == 0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
        int m = Integer.parseInt(args[0]);
        int n = 0;
        if (args.length > 1) n = Integer.parseInt(args[1]);
        makegraph(m,n);
        double meanDistance = randomWalkMulti(m, n);
        System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
    }
    
    private static void plot(List<Integer> x, List<Double> y) {

    	XYSeries series = new XYSeries("Graph Depicting the relationship");

		for (int i = 0; i < x.size(); i++) {
			int xval = x.get(i);
			double yval = y.get(i);
			series.add(xval, yval);
		}

		XYDataset dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Graph Depicting the relationship", "steps", "DistanceTravelled", dataset,
			PlotOrientation.VERTICAL, false, false, false);
		ChartPanel panel = new ChartPanel(chart);
		JFrame frame = new JFrame("Paurush Batish");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);

	}

}
