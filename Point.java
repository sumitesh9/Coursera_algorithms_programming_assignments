/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    // x-coordinate of this point
    private final int x;
    // y-coordinate of this point
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    // draws this point
    public   void draw()
    {
        StdDraw.point(x, y);
    }
    // draws the line segment from this point to that point
    public   void drawTo(Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    // string representation
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that)
    {
        if (this.y < that.y) return -1;
        if (this.y == that.y) {
            if (this.x < that.x) return -1;
            if (this.x == that.x) return 0;
        }
        return 1;
    }
    // the slope between this point and that point
    public  double slopeTo(Point that)
    {
        // x coordinates are equal
        if (this.x == that.x) {
            // Both x and y coordinates are equal
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            // Only x coordinates are equal
            else return Double.POSITIVE_INFINITY;
        }
        // y coordinates are equal
        if (this.y == that.y) return 0;
        // x and y coordinates are distinct
        return (double) (this.y - that.y) / (this.x - that.x);
    }
    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder()
    {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double d1 = slopeTo(o1);
            double d2 = slopeTo(o2);
            return Double.compare(d1, d2);
        }
    }
}