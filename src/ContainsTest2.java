/******************************************************************************
 * Compilation:  javac NearestNeighborVisualizer.java
 * Execution:    java NearestNeighborVisualizer input.txt
 * Dependencies: PointSET.java KdTree.java
 * <p/>
 * Read points from a file (specified as a command-line argument) and
 * draw to standard draw. Highlight the closest point to the mouse.
 * <p/>
 * The nearest neighbor according to the brute-force algorithm is drawn
 * in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContainsTest2 {

    public static void main(String[] args) {


        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        StdOut.println(kdtree.isEmpty());
        StdOut.println(kdtree.size());

        int points = 1000*1000*10;

        List<Point2D> allPoints = new ArrayList<Point2D>(points);

        for (int i = 0; i < points; i++) {

            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);


            /*x = StdRandom.uniform();
            y = StdRandom.uniform();
            p = new Point2D(x, y);
            */
            allPoints.add(p);
        }

        Iterator<Point2D> itP = allPoints.iterator();

        while (itP.hasNext()) {

            Point2D p = itP.next();
            if (!kdtree.contains(p)) {
                throw new RuntimeException("fudeu");
            } else {
                StdOut.println("POint " + p);
            }

        }


        StdOut.println(kdtree.contains(new Point2D(0.278871, 0.773084)));

        StdOut.println(kdtree.isEmpty());
        StdOut.println(kdtree.size());
    }
}
