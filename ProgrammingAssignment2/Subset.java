/*****************
 * Author:  Artem.tym
 * Written: 07/03/2015
 * Updated: 07/03/2015
 * <p>
 * Compilation:   javac -cp .;stdlib.jar;algs4.jar Subset.java
 * Execution:     java  -cp .;stdlib.jar;algs4.jar Subset
 * Dependencies:  StdIn.java, StdOut.java, StdRandom.java
 * <p>
 * Implements generic data type Deque for Lab 2.3 Algorithms Coursera
 *****************/

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    // Fields
    private static RandomizedQueue<String> queue;
    private static int k;

    // Methods

    /**
     * Test client (optional)
     *
     * @param args array where args[0] represents k (number of items to print)
     */
    public static void main(String[] args) {
        queue = new RandomizedQueue<String>();
        k = Integer.parseInt(args[0]);

        String x = null;

        try {
            while ((x = StdIn.readString()) != null) {
                queue.enqueue(x);
            }
        } catch (NoSuchElementException e) {
            for (int i = 0; i < k; i++) {
                StdOut.println(queue.dequeue());
            }
        }
    }
}