import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Created by dpacif1 on 2/23/16.
 */
public class KdTree {
    /**
     * construct an empty set of points
     */
    public KdTree() {
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return 0;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }
        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("rect is null");
        }

        return null;

    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }

        return null;
    }

    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
    }
}