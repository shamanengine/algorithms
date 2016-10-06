/**
 * Algorithms Lab1 PercolationStats
 *
 * @version 1.01 2015_0225
 * @author Artem.tym
 * Compilation  javac -cp .;stdlib.jar;algs4.jar PercolationStats.java
 * Execution    java  -cp .;stdlib.jar;algs4.jar PercolationStats
 * Dependencies Percolation.java, StdIn.java, StdOut.java, WeightedQuickUnionUF.java, StdRandom.java
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] prob;

    // Perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        prob = new double[t];
        if (n <= 0 || t <= 0) throw new IllegalArgumentException("Illegal N");

        // Display random pairs and open
        for (int ki = 1; ki <= t; ki++) {
            Percolation percolation = new Percolation(n);
            int a1, a2;
            int c = 1;
            while (!(percolation.percolates())) {
                do {
                    a1 = StdRandom.uniform(1, n + 1);
                    a2 = StdRandom.uniform(1, n + 1);
                } while (percolation.isOpen(a1, a2));
                percolation.open(a1, a2);
                c++;
            }
            prob[ki - 1] = ((double) c - 1) / (n * n);
        }
    }

    // Sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(prob);
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(prob);
    }

    // Low  endpoint of 95% confidence interval
    public double confidenceLo() {
        // 'Math.pow(x, 0.5)' is slow. Use 'Math.sqrt(x)' instead.
        return (mean() - 1.96 * stddev() / Math.sqrt(prob.length));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(prob.length));
    }

    // Test client
    public static void main(String[] args) {
        // StdOut.print("Enter N & T ");
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        StdOut.println("(N=" + n + "; T=" + t + ")");
        PercolationStats PS = new PercolationStats(n, t);
        StdOut.printf("Mean                   = %.12f %n", PS.mean());
        StdOut.printf("stddev                 = %.12f %n", PS.stddev());
        StdOut.printf("95 confidence interval = %.12f, %.12f %n", PS.confidenceLo(), PS.confidenceHi());
    }
}
