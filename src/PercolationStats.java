import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
    private final int trials;
    private final int n;
    private double[] thresholds;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.n = n;
        this.thresholds = new double[trials];
        monteCarloStimulation();
    }

    public double mean() {
        double sum = 0.0;
        for (double threshold : thresholds) {
            sum += threshold;
        }
        return sum / trials;
    }

    public double stddev() {
        double sumOfSquare = 0.0;
        double mean = mean();
        for (double threshold : thresholds) {
            sumOfSquare += Math.pow(threshold - mean, 2);
        }
        return Math.sqrt(sumOfSquare / (trials-1));
    }

    public double confidenceLo() {
        double standDev = stddev();
        double mean = mean();
        return mean - (1.96 * standDev / Math.sqrt(trials));
    }

    public double confidenceHi() {
        double standDev = stddev();
        double mean = mean();
        return mean + (1.96 * standDev / Math.sqrt(trials));
    }

    private void monteCarloStimulation() {
        final double totalNumberOfSite = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int numberOfOpenSite = 0;
            while (!percolation.percolates()) {
                int row = randInt();
                int col = randInt();
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    numberOfOpenSite++;
                }
            }
            thresholds[i] = numberOfOpenSite / totalNumberOfSite;
        }
    }

    private int randInt() {
        return StdRandom.uniform(n) + 1;
    }

    public static void main(String[] args) {
        PercolationStats statsTest1 = new PercolationStats(2, 10000);
        StdOut.println(String.format("mean: %f", statsTest1.mean()));
        StdOut.println(String.format("stddev: %f", statsTest1.stddev()));
        StdOut.println(String.format("confidence interval: %f, %f", statsTest1.confidenceLo(), statsTest1.confidenceHi()));
    }
}
