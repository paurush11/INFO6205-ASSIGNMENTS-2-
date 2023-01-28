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
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
       
    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();
        // FIXME : for each candidate, test if a[i] + a[j] + a[k] = 0.
        // END 
        
		// FIXME : for each candidate, test if a[i] + a[j] + a[k] = 0.
		int first = j - 1;
		int last = j + 1;
		while (first >= 0 && last < length) {
			int currentSum = a[first] + a[j] + a[last];
			if (currentSum == 0) {
				triples.add(new Triple(a[first], a[j], a[last]));
				first--;
				last++;
			} else if (currentSum < 0) {
				last++;
			} else {
				first--;
			}
		}
		// FIXME : for each candidate, test if a[i] + a[j] + a[k] = 0.
		// END
		
        return triples;
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
			intsSupplier = new Source((int)(100 * factor), 10).intsSupplier(10);
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
				ThreeSum target = new ThreeSumQuadratic(arr);
				Triple[] triples = target.getTriples();
				double timeEnd = watch.lap();
				times.add(timeEnd);
				times1.add(Math.log(timeEnd));
			}
		});

		plot(nValues, times);
		plot1(nValues1, times1);
	}
    
    private static void plot(List<Integer> nValues, List<Double> times) {

		XYSeries series = new XYSeries("Paurush Batish - 002755631");

		System.out.println("\nThree Sum Quadratic plot:");
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
		JFrame frame = new JFrame("Three Sum Quadratic plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	private static void plot1(List<Double> nValues1, List<Double> times1) {

		XYSeries series = new XYSeries("Paurush Batish - 002755631");

		System.out.println("\nThree Sum Quadratic Log:");
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
		JFrame frame = new JFrame("Three Sum Quadratic plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	int yy = 9;

    private final int[] a;
    private final int length;
}
