package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.neu.coe.info6205.util.Stopwatch;

/**
 * Implementation of ThreeSum which follows the simple optimization of
 * requiring a sorted array, then using binary search to find an element x where
 * -x the sum of a pair of elements.
 * <p>
 * The array provided in the constructor MUST be ordered.
 * <p>
 * This algorithm runs in O(N^2 log N) time.
 */
class ThreeSumQuadrithmic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadrithmic on a.
     * @param a a sorted array.
     */
    public ThreeSumQuadrithmic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++)
            for (int j = i + 1; j < length; j++) {
                Triple triple = getTriple(i, j);
                if (triple != null) triples.add(triple);
            }
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    public Triple getTriple(int i, int j) {
        int index = Arrays.binarySearch(a, -a[i] - a[j]);
        if (index >= 0 && index > j) return new Triple(a[i], a[j], a[index]);
        else return null;
    }
    
    public static void main(String[] args) {
		Supplier<int[]> intsSupplier = null;
		int[] ints = null; 
		List<int[]> listOfArrays = new ArrayList<>();
		List<Integer> nValues = new ArrayList<>();
		List<Double> nValues1 = new ArrayList<>();
		double factor = 1.5;
		
		for (int i = 1; i <= 8; i++) {
			factor *= 2; 
			intsSupplier = new Source((int)(100 * factor), 2).intsSupplier(10);
			ints = intsSupplier.get();
			listOfArrays.add(ints);
			nValues.add((int)(factor * 100));
			nValues1.add(Math.log(factor * 100));
		}
		
		List<Double> times = new ArrayList<>();
		List<Double> times1 = new ArrayList<>();

		listOfArrays.forEach(arr -> {
			try (Stopwatch watch = new Stopwatch()) {
				Arrays.sort(arr);
				ThreeSum target = new ThreeSumQuadrithmic(arr);
				Triple[] triples = target.getTriples();
				double timeEnd =  watch.lap();
				times.add(timeEnd);
				times1.add(Math.log(timeEnd));
			}
		});
		
		plot(nValues, times);
		plot1(nValues1, times1);
		
	}
    private static void plot(List<Integer> nValues, List<Double> times) {

		XYSeries series = new XYSeries("Paurush Batish - 002755631");

		System.out.println("\nThree Sum Quadrithmic:");
		for (int i = 0; i < nValues.size(); i++) {
			int xval = nValues.get(i);
			double yval = times.get(i);
			System.out.println(" x : " + xval + " y : " + yval);

			series.add(xval, yval);
		}

		XYDataset dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Paurush Batish - 002755631", "ArrayLength", "Time", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		ChartPanel panel = new ChartPanel(chart);
		JFrame frame = new JFrame("Three Sum Quadrithmics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	private static void plot1(List<Double> nValues1, List<Double> times1) {

		XYSeries series = new XYSeries("Paurush Batish - 002755631");
		System.out.println("\nThree Sum Quadrithmic log plots:");
		
		for (int i = 0; i < nValues1.size(); i++) {
				double xval = nValues1.get(i);
				double yval = times1.get(i);
				if (!Double.isInfinite(yval)) {
					System.out.println(" x : " + xval + " y : " + yval);
					series.add(xval, yval);
				}
		}

		XYDataset dataset = new XYSeriesCollection(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Paurush Batish - 002755631", "ArrayLength", "Time", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		ChartPanel panel = new ChartPanel(chart);
		JFrame frame = new JFrame("Three Sum Quadrithmic log plots:");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	

    private final int[] a;
    private final int length;
}
