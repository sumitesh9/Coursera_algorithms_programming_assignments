/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 20/11/18
 *  Description: Optimised way of finding 4 collinear points
 **************************************************************************** */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] ls;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        // Throw an error
        if (points == null)
            throw new IllegalArgumentException();
        int count = points.length;
        for (int i = 0; i < count; i++) {
            // Throw an error
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < count; j++) {
                // Throw an error
                if (points[j] == null)
                    throw new IllegalArgumentException();
                // Throw an error
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        // Clone the points
        Point[] obj = points.clone();
        // Sort using standard java library
        Arrays.sort(obj);
        List<LineSegment> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            // Clone the points
            Point[] p = obj.clone();
            // Sort
            Arrays.sort(p, p[i].slopeOrder());
            int j = 1;
            while (j < count - 2) {
                int k = j;
                double s1 = p[0].slopeTo(p[k++]);
                double s2;
                do {
                    if (k == count) {
                        k++;
                        break;
                    }
                    s2 = p[0].slopeTo(p[k++]);
                } while (Double.compare(s1, s2) == 0);
                if (k - j < 4) {
                    j++;
                    continue;
                }
                int le = k-- - j;
                Point[] line = new Point[le];
                line[0] = p[0];

                for (int g = 1; g < le; g++) {
                    line[g] = p[j + g - 1];
                }
                Arrays.sort(line);
                // Remove duplicate
                if (line[0].equals(p[0])) {
                    list.add(new LineSegment(line[0], line[le - 1]));
                }
                j = k;
            }
        }
        // Convert to array
        ls = list.toArray(new LineSegment[list.size()]);
    }
    // the number of line segments
    public  int numberOfSegments()
    {
        return ls.length;
    }
    // the line segments
    public LineSegment[] segments()
    {
        return ls.clone();
    }
}