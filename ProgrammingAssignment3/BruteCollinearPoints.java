/**
 * @author Artem.tym
 * @version 1.0, 08.04.2015.
 */

import java.util.Arrays;

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    // Fields
    private LineSegment[] segments;

    // Constructors

    /**
     * Finds all line segments containing 4 points
     *
     * @param points points to check
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("argument to the constructor is null");

        int n = points.length;
        Point[] pointsCopy = new Point[n];
        SET<Point> set = new SET<>();
        Queue<LineSegment> queue = new Queue<>();

        for (int i = 0; i < n; i++) {
            Point p = points[i];
            if (p == null) {
                throw new NullPointerException("the point can not be null");
            }
            if (set.contains(p)) {
                throw new IllegalArgumentException("repeated points");
            }
            set.add(p);
            pointsCopy[i] = p;
        }

        Arrays.sort(pointsCopy);

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    Point p = pointsCopy[i];
                    Point q = pointsCopy[j];
                    Point r = pointsCopy[k];
                    if (checkCollinear(p, q, r)) {
                        for (int l = k + 1; l < n; l++) {
                            Point s = pointsCopy[l];
                            if (checkCollinear(p, q, r, s)) {
                                LineSegment segment = new LineSegment(p, s);
                                queue.enqueue(segment);
                            }

                        }
                    }
                }
            }
        }

        segments = new LineSegment[queue.size()];
        for (int q = 0; q < segments.length; q++) {
            segments[q] = queue.dequeue();
        }

    }

    // Methods

    /**
     * Gets the number of line segments
     *
     * @return number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Gets line segments
     *
     * @return line segments
     */
    public LineSegment[] segments() {
        return segments.clone();
    }

    /**
     * Checks whether 3 points are collinear
     *
     * @param p1 point1
     * @param p2 point2
     * @param p3 point3
     * @return {@code true} if 3 points are collinear
     */
    private static boolean checkCollinear(Point p1, Point p2, Point p3) {
        return p1.slopeTo(p2) == p1.slopeTo(p3);
        // return Math.abs(p1.slopeTo(p2) - p2.slopeTo(p3)) < PRECISION;
    }

    /**
     * Checks whether 4 points are collinear
     *
     * @param p1 point1
     * @param p2 point2
     * @param p3 point3
     * @param p4 point4
     * @return {@code true} if 3 points are collinear
     */
    private static boolean checkCollinear(Point p1, Point p2, Point p3, Point p4) {
        return p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p2) == p1.slopeTo(p4);
    }


    /**
     * Test client
     * Takes the name of an input file as a command-line argument;
     * Reads the input file (in the format specified below);
     * Prints to standard output the line segments that your program discovers, one per line;
     * Draws to standard draw the line segments.
     *
     * @param args name of an input file
     */
    public static void main(String[] args) {

        // Read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Draw the points
        StdDraw.show();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // Print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

    }

}