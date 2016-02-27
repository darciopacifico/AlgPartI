import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * PointSET implementation
 * Created by dpacif1 on 2/23/16.
 */
public class PointSET {
    private TreeSet<Point2D> pointSet = new TreeSet<Point2D>();

    /**
     * construct an empty set of points
     */
    public PointSET() {
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return this.pointSet.isEmpty();
    }

    /**
     * number of points in the set
     */
    public int size() {
        return this.pointSet.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }
        this.pointSet.add(p);
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }
        return this.pointSet.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : this.pointSet) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("rect is null");
        }

        List<Point2D> q = new LinkedList<Point2D>();

        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                q.add(p);
            }
        }

        return q;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D pExtRef) {
        if (pExtRef == null) {
            throw new java.lang.NullPointerException("p is null");
        }

        Point2D nearest = null;
        double distance = Double.POSITIVE_INFINITY;

        for (Point2D pFromSet : this.pointSet) {
            double dist = pExtRef.distanceTo(pFromSet);
            if (nearest == null || dist < distance) {
                distance = dist;
                nearest = pFromSet;
            }
        }

        return nearest;
    }

    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
    }
}