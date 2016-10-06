/*****************
 * Author:      Artem.tym
 * Written:     01/03/2015
 * Updated:     01/03/2015
 * <p>
 * Compilation:     javac -cp .;stdlib.jar;algs4.jar Deque.java
 * Execution:       java  -cp .;stdlib.jar;algs4.jar Deque
 * Dependencies:    StdIn.java, StdOut.java, StdRandom.java
 * <p>
 * Implements generic data type RandomizedQueue for Lab 2.2 Algorithms Coursera
 *****************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // Fields
    private Item[] a;       // array of items
    private int N = 0;      // number of elements on stack

    // Constructors

    /**
     * Constructs an empty randomized queue
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        a = (Item[]) new Object[2];     // need to be this way
    }

    // Methods

    /**
     * Checks whether the que is empty
     *
     * @return {@code true} if queue is empty
     */
    public boolean isEmpty() {
        return (size() == 0);
    }

    /**
     * Gets the number of items on the queue
     *
     * @return number of items on the queue
     */
    public int size() {
        return N;
    }

    /**
     * Gets capacity of queue
     *
     * @return capacity of queue
     */
    private int getCapacity() {
        return a.length;
    }

    /**
     * Increases or decreases the size of the array with items by given capacity.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        if (N - 1 >= a.length) {
            Item[] tmp = (Item[]) new Object[2 * a.length];
            // Not allowed to use System.arraycopy();
            for (int i = 0; i < a.length; i++) {
                tmp[i] = a[i];
            }
            a = tmp;
        } else if (N >= 1 && N <= a.length / 4) {
            Item[] tmp = (Item[]) new Object[a.length / 2];
            for (int i = 0; i <= N - 1; i++) {
                tmp[i] = a[i];
            }
            a = tmp;
        }
    }

    /**
     * Adds new item
     *
     * @param item item to add
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Null pointer");
        N++;
        resize();
        a[N - 1] = item;
    }

    /**
     * Removes and returns a random item
     *
     * @return removed item
     */
    public Item dequeue() {
        if (N == 0)
            throw new NoSuchElementException("No Such Element");

        int rand = StdRandom.uniform(N);
        Item b = a[rand];
        for (int i = rand; i < N - 1; i++) {
            a[i] = a[i + 1];
        }
        a[N - 1] = null;
        N--;
        resize();
        return b;
    }

    /**
     * Gets, but does not remove a random item
     *
     * @return item to check
     */
    public Item sample() {
        if (N == 0) throw new NoSuchElementException("YOBA No Such Element");
        return a[StdRandom.uniform(N)];
    }

    /**
     * Returns iterator over items in random order
     *
     * @return iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new Riterator();
    }

    /**
     * Independent iterator
     */
    private class Riterator implements Iterator<Item> {
        private int j;

        public Riterator() {
            j = N;
        }

        public boolean hasNext() {
            return j > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported Operation");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No Such Element");
            return a[--j];
        }
    }

    /**
     * Test client (optional)
     *
     * @param args any
     */
    public static void main(String[] args) {
        StdOut.printf("+ add element                  - output element at random %n");
        StdOut.printf("c check element at random      s output number of items %n");
        StdOut.printf("* output all structure         0 add null element %n");
        StdOut.printf("e Exit%n");
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String tok;

        do {
            StdOut.printf(queue.getCapacity() + " Make your choice: ");
            tok = StdIn.readString();

            switch (tok) {
                case "+": {
                    StdOut.printf("Enter string -> ");
                    String sitem = StdIn.readString();
                    queue.enqueue(sitem);
                }
                break;
                case "-":
                    StdOut.println(queue.dequeue());
                    break;
                case "c":
                    StdOut.println(queue.sample());
                    break;
                case "s":
                    StdOut.println(queue.size());
                    break;
                case "*":
                    StdOut.println("***");
                    break;
                case "0":
                    queue.enqueue(null);
                    break;
            }
        } while (!tok.equals("e"));
    }
}