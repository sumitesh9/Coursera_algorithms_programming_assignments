/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 20/11/18
 *  Description: Find 4 collinear point via brute force (O(n^4) complexity)
 **************************************************************************** */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] ls;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        // Throw an error
        if (points == null)
            throw new IllegalArgumentException();
        int n = points.length;
        for (int i = 0; i < n; i++) {
            // Throw an error
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < n; j++) {
                // Throw an error
                if (points[j] == null)
                    throw new IllegalArgumentException();
                // Throw an error
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        // Clone the points for processing
        Point[] obj = points.clone();
        // Sort using standard java library
        Arrays.sort(obj);
        List<LineSegment> list = new ArrayList<>();
        // Brute force method
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int g = k + 1; g < n; g++) {
                        Point[] p = new Point[4];
                        // Throw an error
                        if (obj[i] == null || obj[j] == null || obj[k] == null || obj[g] == null)
                            throw new IllegalArgumentException();
                        p[0] = obj[i];
                        p[1] = obj[j];
                        p[2] = obj[k];
                        p[3] = obj[g];
                        double s1 = p[0].slopeTo(p[1]);
                        double s2 = p[0].slopeTo(p[2]);
                        if (Double.compare(s1, s2) != 0) continue;
                        double s3 = p[0].slopeTo(p[3]);
                        if (Double.compare(s1, s3) == 0) {
                            Arrays.sort(p);
                            list.add(new LineSegment(p[0], p[3]));
                        }
                    }
                }
            }
        }
        // Convert to Array
        ls = list.toArray(new LineSegment[list.size()]);
    }
    // the number of line segments
    public int numberOfSegments()
    {
        return ls.length;
    }
    // the line segments
    public LineSegment[] segments()
    {
        return ls.clone();
    }
}