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

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

public class ContainsTest {

    public static void main(String[] args) {

        args = new String[]{"/Users/dpacif1/scala/PercolatoinAssignment/src/input400K.txt"};

        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        StdOut.println(kdtree.isEmpty());
        StdOut.println(kdtree.size());


        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        StdOut.println(kdtree.contains(new Point2D(0.629938, 0.468227)));
        StdOut.println(kdtree.contains(new Point2D(0.953186, 0.921608)));
        StdOut.println(kdtree.contains(new Point2D(0.731948, 0.469667)));
        StdOut.println(kdtree.contains(new Point2D(0.950223, 0.474933)));
        StdOut.println(kdtree.contains(new Point2D(0.001535, 0.806464)));
        StdOut.println(kdtree.contains(new Point2D(0.278871, 0.773083)));

        StdOut.println(kdtree.contains(new Point2D(0.278871, 0.773084)));

        StdOut.println(kdtree.isEmpty());
        StdOut.println(kdtree.size());
    }
}
