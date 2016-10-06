/**
 * Algorithms Lab1 Percolation
 * Solves problem using weighted quick union-find algorithm
 *
 * @version 1.01 2015_0223
 * @author Artem.tym
 * Compilation  javac -cp .;stdlib.jar;algs4.jar Percolation.java
 * Execution    java  -cp .;stdlib.jar;algs4.jar Percolation
 * Dependencies StdIn.java, StdOut.java, WeightedQuickUnionUF.java, StdRandom.java
 * @see WeightedQuickUnionUF
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Fields
    private WeightedQuickUnionUF wquUF;     // WeightedQuickUnionUF data structure
    private int givenN = 1;                 // N in Percolation constructor
    private boolean[] openList;             // Boolean array to mark if element is open or not

    // Getters
    private boolean[] getOpenList() {
        return openList;
    }

    private WeightedQuickUnionUF getWquUF() {
        return wquUF;
    }

    // Constructors
    /**
     * Create N-by-N grid, with all sites blocked
     *
     * @param n grid's side length
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Illegal N");
        givenN = n;
        openList = new boolean[n * n + 2];
        openList[0] = true;             // Open Virtual Top
        openList[n * n + 1] = true;     // Open Virtual Bottom
        wquUF = new WeightedQuickUnionUF(n * n + 2);
    }

    // Methods
    /**
     * Checks row and column indices of the site
     *
     * @param row    row index
     * @param column column index
     * @throws IndexOutOfBoundsException if one of parameters is smaller
     *                                   than 0 or greater than grid size N
     */
    private void check(int row, int column) {
        if (row <= 0 || row >= givenN + 1)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (column <= 0 || column >= givenN + 1)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    /**
     * Converts array of the site to array of indexes
     *
     * @param row    row index of the site
     * @param column column index of the site
     * @return site's index in 1D array
     */
    private int xyToId(int row, int column) {
        return ((row - 1) * givenN + column);
    }

    /**
     * Open site (row i, column j) if it is not open already
     *
     * @param i row index
     * @param j column index
     */
    public void open(int i, int j) {
        check(i, j);
        openList[xyToId(i, j)] = true;
        connectToNeighbour(i, j);
    }

    /**
     * Connects site to neighbour if they are both opened
     *
     * @param i row index of the site 9in 1D array)
     * @param j column index of the neighboring site (in 1D array)
     */
    private void connectToNeighbour(int i, int j) {
        if (openList[xyToId(i, j)]) {
            if (i == 1) wquUF.union(xyToId(i, j), 0);
            if (i == givenN) wquUF.union(xyToId(i, j), givenN * givenN + 1);

            // Connect to right neighbour
            if (j < givenN && xyToId(i, j) >= 1 && xyToId(i, j) <= (givenN * givenN - 1)) {
                if (openList[xyToId(i, j) + 1])
                    wquUF.union(xyToId(i, j), xyToId(i, j) + 1);
            }

            // Connect to left neighbour
            if (j > 1 && xyToId(i, j) >= 2 && xyToId(i, j) <= (givenN * givenN)) {
                if (openList[xyToId(i, j) - 1])
                    wquUF.union(xyToId(i, j), xyToId(i, j) - 1);
            }

            // Connect to top neighbour
            if (xyToId(i, j) >= givenN + 1 && xyToId(i, j) <= (givenN * givenN)) {
                if (openList[xyToId(i, j) - givenN])
                    wquUF.union(xyToId(i, j), xyToId(i, j) - givenN);
            }

            // Connect to bottom neighbour
            if (xyToId(i, j) >= 1 && xyToId(i, j) <= (givenN * givenN - givenN)) {
                if (openList[xyToId(i, j) + givenN])
                    wquUF.union(xyToId(i, j), xyToId(i, j) + givenN);
            }
        }
    }

    /**
     * Determines whether site (row i, column j) is opened
     *
     * @param i row index
     * @param j column index
     * @return {@code true} if site is opened
     */
    public boolean isOpen(int i, int j) {
        check(i, j);
        return openList[xyToId(i, j)];
    }

    /**
     * Determines whether site (row i, column j) is full
     *
     * @param i row index
     * @param j column index
     * @return {@code true} if site is full
     */
    public boolean isFull(int i, int j) {
        check(i, j);
        return isOpen(i, j) && wquUF.connected(xyToId(i, j), 0);
    }

    /**
     * Determines does the system percolates
     *
     * @return {@code true} if grid percolates
     */
    public boolean percolates() {
        return wquUF.connected(0, givenN * givenN + 1);
    }

    /**
     * Test client (optional)
     *
     * @param args any
     */
    public static void main(String[] args) {
        int m, k, a1, a2;
        StdOut.print("Enter Matrix dimension, N= ");
        m = StdIn.readInt();
        Percolation percolation = new Percolation(m);

        // Display open/closed initial matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++)
                StdOut.print(percolation.getOpenList()[percolation.xyToId(i, j)] + " ");
            StdOut.println();
        }
        StdOut.println();

        StdOut.print("Enter Number of Random Opens, k= ");
        k = StdIn.readInt();

        // Display random pairs and open
        for (int ki = 1; ki <= k; ki++) {
            a1 = StdRandom.uniform(1, m + 1);
            a2 = StdRandom.uniform(1, m + 1);
            StdOut.print("(" + a1 + "; " + a2 + ") ");
            percolation.open(a1, a2);
        }
        StdOut.println();

        // Display changed open/ closed matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++)
                StdOut.print(percolation.getOpenList()[percolation.xyToId(i, j)] + " ");
            StdOut.println();
        }
        StdOut.println();

        // Display changed open/closed matrix by isOpen method
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++)
                StdOut.print(percolation.isOpen(i, j) + " ");
            StdOut.println();
        }
        StdOut.println();

        StdOut.printf("%02d %n", percolation.getWquUF().find(0)); // Virtual Top Element
        // Display Dependency Matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++)
                StdOut.printf("%02d ", percolation.getWquUF().find(percolation.xyToId(i, j)));
            StdOut.println();
        }
        StdOut.printf("%02d %n", percolation.getWquUF().find(m * m + 1)); // Virtual Bottom element
        StdOut.println(percolation.percolates());
    }
}