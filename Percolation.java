/**
 * Algorithms Lab1 Percolation
 *
 * @version 1.01 2015_0223
 * @author Artem.tym
 * Compilation  javac -cp .;stdlib.jar;algs4.jar Percolation.java
 * Execution    java  -cp .;stdlib.jar;algs4.jar Percolation
 * Dependencies StdIn.java, StdOut.java, WeightedQuickUnionUF.java, StdRandom.java
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wquUF;     // WeightedQuickUnionUF data structure
    private int givenN = 1;                 // N in Percolation constructor
    private boolean[] openList;             // Boolean array to mark if element is open or not

    // Create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Illegal N");
        givenN = n;
        openList = new boolean[n * n + 2];
        openList[0] = true;             // Open Virtual Top
        openList[n * n + 1] = true;     // Open Virtual Bottom
        wquUF = new WeightedQuickUnionUF(n * n + 2);
    }

    // Throws a IndexOutOfBoundsException
    // if i or j is smaller than 0 or greater than grid size N
    private void check(int row, int column) {
        if (row <= 0 || row >= givenN + 1)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (column <= 0 || column >= givenN + 1)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    private int xyToId(int row, int column) {
        return ((row - 1) * givenN + column);
    }

    private boolean[] openListDisplay() {
        return openList;
    }

    private WeightedQuickUnionUF wqDisp() {
        return wquUF;
    }

    // Open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        check(i, j);
        openList[xyToId(i, j)] = true;
        connectToNeighbour(i, j);
    }

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

    // Determines whether site (row i, column j) is open
    public boolean isOpen(int i, int j) {
        check(i, j);
        return openList[xyToId(i, j)];
    }

    // Determines whether site (row i, column j) is full
    public boolean isFull(int i, int j) {
        check(i, j);
        return isOpen(i, j) && wquUF.connected(xyToId(i, j), 0);
    }

    // Determines does the system percolates
    public boolean percolates() {
        return wquUF.connected(0, givenN * givenN + 1);
    }

    // Test client (optional)
    public static void main(String[] args) {
        int m, k, a1, a2;
        StdOut.print("Enter Matrix dimension, N= ");
        m = StdIn.readInt();
        Percolation percolation = new Percolation(m);

        // Display open/closed initial matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++)
                StdOut.print(percolation.openListDisplay()[percolation.xyToId(i, j)] + " ");
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
                StdOut.print(percolation.openListDisplay()[percolation.xyToId(i, j)] + " ");
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

        StdOut.printf("%02d %n", percolation.wqDisp().find(0)); // Virtual Top Element
	    
        // Display Dependency Matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= m; j++)
                StdOut.printf("%02d ", percolation.wqDisp().find(percolation.xyToId(i, j)));
            StdOut.println();
        }
        StdOut.printf("%02d %n", percolation.wqDisp().find(m * m + 1)); // Virtual Bottom element
        StdOut.println(percolation.percolates());
    }
}
