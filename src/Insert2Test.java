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
import java.util.List;

public class Insert2Test {

    public static void main(String[] args) {


        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        StdOut.println(kdtree.isEmpty());
        StdOut.println(kdtree.size());

        kdtree.insert(new Point2D(0.629938, 0.468227));
        kdtree.insert(new Point2D(0.953186, 0.921608));
        kdtree.insert(new Point2D(0.731948, 0.469667));
        kdtree.insert(new Point2D(0.950223, 0.474933));
        kdtree.insert(new Point2D(0.001535, 0.806464));
        kdtree.insert(new Point2D(0.278871, 0.773083));
        kdtree.insert(new Point2D(0.278871, 0.773666));

        kdtree.insert(new Point2D(0.278871, 0.773083));
        kdtree.insert(new Point2D(0.278871, 0.773083));
        kdtree.insert(new Point2D(0.278871, 0.773083));
        kdtree.insert(new Point2D(0.278871, 0.773083));
        kdtree.insert(new Point2D(0.278871, 0.773083));

        StdOut.println(kdtree.contains(new Point2D(0.629938, 0.468227)));
        StdOut.println(kdtree.contains(new Point2D(0.953186, 0.921608)));
        StdOut.println(kdtree.contains(new Point2D(0.731948, 0.469667)));
        StdOut.println(kdtree.contains(new Point2D(0.950223, 0.474933)));
        StdOut.println(kdtree.contains(new Point2D(0.001535, 0.806464)));
        StdOut.println(kdtree.contains(new Point2D(0.278871, 0.773083)));

        StdOut.println(kdtree.isEmpty());
        StdOut.println(kdtree.size());
    }
}
