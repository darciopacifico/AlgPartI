import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * KDtree implementation
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
    private static class Node {
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

    /**
     * @param p
     * @param node
     * @param isHoriz
     * @return
     */
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

    private RectHV takeSide(Point2D p, int right) {
        return null;
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

    /**
     * @param root
     * @param p
     * @return
     */
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
        Node fakePar = new Node(new Point2D(1, 1), false);
        draw(fakePar, fakePar, this.root);
    }


    /**
     * @param par
     * @param node
     */
    private void draw(Node parPar, Node par, Node node) {
        if (node != null) {
            drawPoint(node);

            drawSplit(parPar, par, node);
            drawSplit(parPar, par, node);

            draw(par, node, node.left);
            draw(par, node, node.right);

        }
    }


    private void drawSplit(Node parPar, Node par, Node node) {
        StdDraw.setPenRadius();

        if (node == null) {
            return;
        }

        double startY = 0d;
        double endY = 1d;

        double startX = 0d;
        double endX = 1d;

        if (node.isHoriz) {
            if (par != null) {
                if (node.p.y() > par.p.y()) {
                    startY = par.p.y();

                    //endY = parPar.p.y();
                } else {
                    //startY = parPar.p.y();
                    endY = par.p.y();
                }
            }

            StdDraw.setPenColor(Color.RED);
            StdDraw.line(node.p.x(), startY, node.p.x(), endY);
        } else {
            if (par != null) {
                if (node.p.x() > par.p.x()) {
                    startX = par.p.x();
                    //endX = parPar.p.x();
                } else {
                    endX = par.p.x();
                    //startX = parPar.p.x();
                }
            }

            StdDraw.setPenColor(Color.GREEN);
            StdDraw.line(startX, node.p.y(), endX, node.p.y());
        }
    }


    /**
     * @param node
     */
    private void drawPoint(Node node) {
        StdDraw.setPenRadius(.03);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.point(node.p.x(), node.p.y());
    }

    /**
     * All points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException("rect is null");
        }

        return range(rect, this.root, new LinkedList<Point2D>());
    }

    /**
     * Nearest point
     *
     * @param rect
     * @param node
     * @return
     */
    private Collection<Point2D> range(RectHV rect, Node node, Collection<Point2D> agg) {

        if (node == null) {
            return agg;
        } else if (rect.contains(node.p)) {
            agg.add(node.p);
        }

        if (layAllOverRightSide(rect, node)) { // rec lay all over right side
            range(rect, node.right, agg);

        } else if (layAllOverLeftSide(rect, node)) { // rec lay all over left side
            range(rect, node.left, agg);

        } else { // rec intersect split line

            range(rect, node.right, agg);
            range(rect, node.left, agg);
        }

        return agg;
    }

    private boolean layAllOverLeftSide(RectHV rect, Node node) {
        return (node.isHoriz && rect.xmax() < node.p.x() && rect.xmin() < node.p.x()) || (!node.isHoriz && rect.ymax() < node.p.y() && rect.ymin() < node.p.y());
    }

    private boolean layAllOverRightSide(RectHV rect, Node node) {
        return (node.isHoriz && rect.xmax() >= node.p.x() && rect.xmin() >= node.p.x()) || (!node.isHoriz && rect.ymax() >= node.p.y() && rect.ymin() >= node.p.y());
    }


    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException("p is null");
        }
        if (this.isEmpty()) {
            return null;
        }

        return nearest(p, this.root, this.root).p;
    }

    /**
     * Nearest point
     *
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

    /**
     * Return the lowest possible distante to some node division
     *
     * @param pRef
     * @param node
     * @return
     */
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

    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
    }
}