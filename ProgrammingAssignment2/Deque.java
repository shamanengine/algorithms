/*****************
 * Author:      Artem.tym
 * Written:     01/03/2015
 * Updated:     01/03/2015
 * <p>
 * Compilation:     javac -cp .;stdlib.jar;algs4.jar Deque.java
 * Execution:       java  -cp .;stdlib.jar;algs4.jar Deque
 * Dependencies:    StdIn.java, StdOut.java, StdRandom.java
 * <p>
 * Implements generic data type Deque for Lab 2.1 Algorithms Coursera
 *****************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    // Fields
    private int N;              // size of the stack
    private Node<Item> first;   // top of Deque
    private Node<Item> last;    // bottom of Deque

    /**
     * Helper linked list class
     *
     * @param <Item> item for the node
     */
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // Constructors

    /**
     * Constructs an empty Deque
     */
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    /**
     * Checks whether Deque is empty
     *
     * @return {@code true} if this Deque is empty; {@code false} otherwise
     */
    public boolean isEmpty() {    // is the Deque empty?
        return (size() == 0);
    }

    /**
     * Gets the size of the Deque
     *
     * @return number of items on the Deque
     */
    public int size() {
        return N;
    }

    /**
     * Adds the item to the front
     *
     * @param item item to add
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> oldfirst = first;
        first = new Node<Item>();

        first.prev = null;
        first.item = item;
        first.next = oldfirst;

        if (last == null) last = first;
        else oldfirst.prev = first;
        N++;
    }

    /**
     * Adds the item to the end
     *
     * @param item item to add
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> oldlast = last;
        last = new Node<Item>();

        last.prev = oldlast;
        last.item = item;
        last.next = null;

        if (first == null) first = last;
        else oldlast.next = last;
        N++;
    }

    /**
     * Removes and returns the item from the front
     *
     * @return item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        if (first != null) first.prev = null;
        N--;
        if (first == null) last = null;
        return item;
    }

    /**
     * Removes and returns the item from the end
     *
     * @return item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;        // save item to return
        last = last.prev;
        if (last != null) last.next = null;
        N--;
        if (last == null) first = null;
        return item;                   // return the saved item
    }

    /**
     * Returns an iterator over items in order from front to end
     *
     * @return iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }


    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    /**
     * Test client (optional)
     *
     * @param args any
     */
    public static void main(String[] args) { // unit testing
        StdOut.println("===Input your stuff===");
        StdOut.printf("> - input text on the right/ end of Deque %n< - input text on the left/ beginning of Deque %n");
        StdOut.printf("] - output text on the right/ end of Deque %n[ - output text on the right/ end of Deque %n");
        StdOut.printf("0 - add null (first) %n e - Exit%n");
        Deque<String> q = new Deque<String>();
        StdOut.println(q);
        char tok;

        do {
            StdOut.printf("Make your choice: ");
            tok = StdIn.readChar();

            switch (tok) {
                case '>': {
                    StdOut.printf("Enter string -> ");
                    String sitem = StdIn.readString();
                    q.addLast(sitem);
                }
                break;
                case '<': {
                    StdOut.printf("%nEnter string <- ");
                    String sitem = StdIn.readString();
                    q.addFirst(sitem);
                }
                break;
                case '[':
                    StdOut.println(q.removeFirst());
                    break;
                case ']':
                    StdOut.println(q.removeLast());
                    break;
                case '0':
                    q.addFirst(null);
                    break;
            }
        } while (tok != 'e');

        StdOut.println("The End.");
    }
}