import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

/**
 * Created by dpacif1 on 2/23/16.
 */
public class KdTree {

    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private int size = 0;

    private static final boolean VERT_Y = false;
    private static final boolean HORI_X = true;

    private Node root;

    /**
     * Basic node representantion
     */
    private class Node {

        Point2D p;
        boolean isHoriz;
        Node left;
        Node right;

        public Node(Point2D p, boolean isHoriz) {
            this(p, isHoriz, null, null);
        }

        public Node(Point2D p, boolean isHoriz, Node left, Node right) {
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
        return this.root == null;
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

        this.root = insert(p, this.root, true);

    }

    private Node insert(Point2D p, Node node, boolean isHoriz) {

        if (node == null) {
            this.size++;
            return new Node(p, isHoriz);
        }

        if (node.p.equals(p)) {
            return node;
        }

        if ((node.isHoriz && p.x() >= node.p.x()) || (!node.isHoriz && p.y() >= node.p.y())) {
            node.right = insert(p, node.right, !node.isHoriz);
        } else /*if ((node.isHoriz && p.x() < node.p.x()) || (!node.isHoriz && p.y() < node.p.y()))*/ {
            node.left = insert(p, node.left, !node.isHoriz);
        }

        return node;
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


    private boolean contains(Node root, Point2D p) {

        if (root == null) {
            return false;
        } else if (p.equals(root.p)) {
            return true;
        } else {
            if ((root.isHoriz && p.x() >= root.p.x() || (!root.isHoriz && p.y() >= root.p.y()))) {
                return contains(root.right, p);
            } else {
                return contains(root.left, p);
            }
        }


    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(null, this.root);
    }

    private void draw(Node parent, Node node) {
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

    private void drawVerticalLine(Node parent, Node node) {

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

    private void drawHorizontalLine(Node parent, Node node) {
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

    private void drawPoint(Node node) {
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
    private Node nearest(Point2D pRef, Node node, Node champion) {
        if (node == null) return champion;
        if (node.p.equals(pRef)) return node;
        if (pRef.distanceTo(node.p) < pRef.distanceTo(champion.p)) champion = node;

        if (node.right != null && ((node.isHoriz && pRef.x() >= node.p.x()) || (!node.isHoriz && pRef.y() >= node.p.y()))) {
            if (pRef.distanceTo(node.right.p) < pRef.distanceTo(champion.p)) {
                champion = node.right;
            }

            champion = nearest(pRef, node.right, champion);

            if (node.left != null && lowerPossibleDist(pRef, node) < pRef.distanceTo(champion.p)) {
                champion = nearest(pRef, node.left, champion);
            }


        } else {
            if (node.left != null && pRef.distanceTo(node.left.p) < pRef.distanceTo(champion.p)) {
                champion = node.left;
            }

            champion = nearest(pRef, node.left, champion);

            if (node.right != null && lowerPossibleDist(pRef, node) < pRef.distanceTo(champion.p)) {
                champion = nearest(pRef, node.right, champion);
            }
        }

        return champion;
    }

    private double lowerPossibleDist(Point2D pRef, Node node) {
        double lowestPtX = pRef.x();
        double lowestPtY = pRef.y();

        if (node.isHoriz) {
            lowestPtX = node.p.x();
        } else {
            lowestPtY = node.p.y();
        }

        return new Point2D(lowestPtX, lowestPtY).distanceTo(pRef);
    }


    private boolean isWorthGoLeft(Point2D pRef, Node champion, Node node) {
        if (node.isHoriz) {
            assert pRef.x() > node.p.x(); // pRef must be in right side of node
        } else {
            assert pRef.y() > node.p.y();// pRef must be in right side of node
        }

        return false;
    }


    private boolean isWorthGoRight(Point2D pRef, Node champion, Node node) {
        if (node.isHoriz) {
            assert pRef.x() > node.p.x(); // pRef must be in right side of node
        } else {
            assert pRef.y() > node.p.y();// pRef must be in right side of node
        }

        return false;
    }


    private boolean layRightSide(Point2D pRef, Node node) {
        if (node.right == null) {
            return false;
        }

        if (node.isHoriz) {
            return pRef.x() > node.p.x();
        } else {
            return pRef.y() > node.p.y();
        }
    }

    private boolean layLeftSide(Point2D pRef, Node node) {
        if (node.left == null) {
            return false;
        }

        if (node.isHoriz) {
            return pRef.x() < node.p.x();
        } else {
            return pRef.y() < node.p.y();
        }
    }


    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {

        KdTree kdTree = new KdTree();

        Point2D p1 = new Point2D(234, 789);
        Point2D p2 = new Point2D(756, 568);
        Point2D p3 = new Point2D(156, 435);
        Point2D p4 = new Point2D(186, 123);

        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);

        StdOut.println(kdTree.contains(p1));
        StdOut.println(kdTree.contains(p2));
        StdOut.println(kdTree.contains(p3));
        StdOut.println(kdTree.contains(p4));


    }
}