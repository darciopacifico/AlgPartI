import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

/**
 * Created by dpacif1 on 2/23/16.
 */
public class KdTree {

    private int size = 0;

    private static final boolean VERT_Y = false;
    private static final boolean HORI_X = true;

    private Nodex root;

    /**
     * Basic node representantion
     */
    private class Nodex {

        Point2D p;
        boolean isHoriz;
        Nodex left;
        Nodex right;

        public Nodex(Point2D p, boolean isHoriz) {
            this(p, isHoriz, null, null);
        }

        public Nodex(Point2D p, boolean isHoriz, Nodex left, Nodex right) {
            this.isHoriz = isHoriz;
            this.p = p;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * construct an empty set of points
     */
    public KdTree() {
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return this.size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }

        if (this.root == null) {
            this.root = new Nodex(p, true);
        } else {
            insert(this.root, p);
        }

    }

    private void insert(Nodex parNode, Point2D p) {

        if (parNode.isHoriz) {

            if (p.x() > parNode.p.x()) {

                if (parNode.right == null) {
                    parNode.right = new Nodex(p, false);
                } else {
                    insert(parNode.right, p);
                }

            } else if (p.x() < parNode.p.x()) {
                if (parNode.left == null) {
                    parNode.left = new Nodex(p, false);
                } else {
                    insert(parNode.left, p);
                }

            } else {
                assert false;
            }

        } else {

            if (p.y() > parNode.p.y()) {

                if (parNode.right == null) {
                    parNode.right = new Nodex(p, true);
                } else {
                    insert(parNode.right, p);
                }

            } else if (p.y() > parNode.p.y()) {
                if (parNode.left == null) {
                    parNode.left = new Nodex(p, true);
                } else {
                    insert(parNode.left, p);
                }

            } else {
                assert false;
            }


        }


    }


    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }


        return contains(this.root, p);
    }


    private boolean contains(Nodex root, Point2D p) {

        if (root == null) {
            return false;
        } else if (p.equals(root.p)) {
            return true;
        } else {
            if (root.isHoriz) {

                if (p.x() > root.p.x()) {
                    contains(root.right, p);
                } else if (p.x() < root.p.x()) {
                    contains(root.left, p);
                } else {
                    assert false;
                    return false;
                }

            } else {

                if (p.y() > root.p.y()) {
                    contains(root.right, p);
                } else if (p.y() < root.p.y()) {
                    contains(root.left, p);
                } else {
                    assert false;
                    return false;
                }

            }
        }

        assert false;
        return false;

    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(null, this.root);
    }

    private void draw(Nodex parent, Nodex node) {
        if (node != null) {
            drawPoint(node);
            if (node.isHoriz) {
                drawVerticalLine(parent, node);
            } else {
                drawHorizontalLine(parent, node);
            }
            draw(node, node.left);
            draw(node, node.right);
        }
    }

    private void drawVerticalLine(Nodex parent, Nodex node) {

        double startY = 0d;
        double endY = 1d;

        if (parent != null) {
            if (node.p.y() > parent.p.y()) {
                startY = parent.p.y();
            } else {
                endY = parent.p.y();
            }
        }

        StdDraw.setPenRadius();
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(node.p.x(), startY, node.p.x(), endY);
    }

    private void drawHorizontalLine(Nodex parent, Nodex node) {
        double startX = 0d;
        double endX = 1d;

        if (parent != null) {
            if (node.p.x() > parent.p.x()) {
                startX = parent.p.x();
            } else {
                endX = parent.p.x();
            }
        }

        StdDraw.setPenRadius();
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.line(startX, node.p.y(), endX, node.p.y());
    }

    private void drawPoint(Nodex node) {
        StdDraw.setPenRadius(.03);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.point(node.p.x(), node.p.y());
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

        return nearest(p, this.root, this.root).p;
    }


    /**
     * @param pRef
     * @param node
     * @return
     */
    private Nodex nearest(Point2D pRef, Nodex node, Nodex nearest) {

        if (node == null) {
            return nearest;
        }

        if (pRef.distanceTo(node.p) < pRef.distanceTo(nearest.p)) {
            nearest = node;
        }


        nearest = nearest(pRef,node.right,nearest);

        nearest = nearest(pRef,node.left,nearest);


        return nearest;
    }


    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
    }
}