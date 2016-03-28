import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Collection;
import java.util.LinkedList;

//import java.awt.*;

/**
 * KDtree implementation
 * Created by dpacif1 on 2/23/16.
 */
public class KdTree {
    private int size = 0;

    private Node root;

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

        if (node.getP().equals(p)) {
            return node;
        }

        if (isOnNotNullRight(p, node)) {
            node.setRight(insert(p, node.getRight(), !node.isHoriz()));

        } else {
            node.setLeft(insert(p, node.getLeft(), !node.isHoriz()));
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
     * @param node
     * @param p
     * @return
     */
    private boolean contains(Node node, Point2D p) {
        if (node == null) {
            return false;
        } else if (p.equals(node.getP())) {
            return true;
        } else {
            if (isOnRight(p, node)) {
                return contains(node.getRight(), p);
            } else {
                return contains(node.getLeft(), p);
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

            draw(par, node, node.getLeft());
            draw(par, node, node.getRight());
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

        if (node.isHoriz()) {
            if (par != null) {
                if (node.getP().y() > par.getP().y()) {
                    startY = par.getP().y();
                } else {
                    endY = par.getP().y();
                }
            }

            StdDraw.line(node.getP().x(), startY, node.getP().x(), endY);
        } else {
            if (par != null) {
                if (node.getP().x() > par.getP().x()) {
                    startX = par.getP().x();
                } else {
                    endX = par.getP().x();
                }
            }

            StdDraw.line(startX, node.getP().y(), endX, node.getP().y());
        }
    }


    /**
     * @param node
     */
    private void drawPoint(Node node) {
        StdDraw.setPenRadius(.03);
        StdDraw.point(node.getP().x(), node.getP().y());
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
        } else if (rect.contains(node.getP())) {
            agg.add(node.getP());
        }

        if (layAllOverRightSide(rect, node)) { // rec lay all over right side
            range(rect, node.getRight(), agg);

        } else if (layAllOverLeftSide(rect, node)) { // rec lay all over left side
            range(rect, node.getLeft(), agg);

        } else { // rec intersect split line
            range(rect, node.getRight(), agg);
            range(rect, node.getLeft(), agg);
        }
        return agg;
    }

    private boolean isOnRight(Point2D p, Node node) {
        return (node.isHoriz() && p.x() >= node.getP().x()) || (!node.isHoriz() && p.y() >= node.getP().y());
    }

    private boolean isOnNotNullRight(Point2D p, Node node) {
        return node.getRight() != null && isOnRight(p, node);
    }


    private boolean layAllOverLeftSide(RectHV rect, Node node) {
        return (node.isHoriz() && rect.xmax() < node.getP().x() && rect.xmin() < node.getP().x())
                ||
                (!node.isHoriz() && rect.ymax() < node.getP().y() && rect.ymin() < node.getP().y());
    }

    private boolean layAllOverRightSide(RectHV rect, Node node) {
        return (node.isHoriz() && rect.xmax() >= node.getP().x() && rect.xmin() >= node.getP().x())
                ||
                (!node.isHoriz() && rect.ymax() >= node.getP().y() && rect.ymin() >= node.getP().y());
    }


    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("p is null");

        if (this.isEmpty())
            return null;

        return nearest(p, this.root, this.root).getP();
    }

    /**
     * Nearest point
     *
     * @param pRef
     * @param node
     * @return
     */
    private Node nearest(Point2D pRef, Node node, Node champ) {
        if (node == null) return champ;
        if (node.getP().equals(pRef)) return node;
        if (pRef.distanceTo(node.getP()) < pRef.distanceTo(champ.getP())) champ = node;

        if (isOnNotNullRight(pRef, node)) { // point lay on not null right side

            if (pRef.distanceTo(node.getRight().getP()) < pRef.distanceTo(champ.getP())) {
                champ = node.getRight(); // update champ
            }

            champ = nearest(pRef, node.getRight(), champ);

            if (node.getLeft() != null && lowerPossibleDist(pRef, node) < pRef.distanceTo(champ.getP())) {
                champ = nearest(pRef, node.getLeft(), champ);
            }

        } else {
            if (node.getLeft() != null && pRef.distanceTo(node.getLeft().getP()) < pRef.distanceTo(champ.getP())) {
                champ = node.getLeft();
            }

            champ = nearest(pRef, node.getLeft(), champ);

            if (node.getRight() != null && lowerPossibleDist(pRef, node) < pRef.distanceTo(champ.getP())) {
                champ = nearest(pRef, node.getRight(), champ);
            }
        }

        return champ;
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

        if (node.isHoriz())
            lowestPtX = node.getP().x();
        else
            lowestPtY = node.getP().y();

        return new Point2D(lowestPtX, lowestPtY).distanceTo(pRef);
    }

    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
    }

    /**
     * Basic node representantion
     */
    private static class Node {
        private Point2D p;
        private boolean isHoriz;
        private Node left;
        private Node right;

        public Node(Point2D p, boolean isHoriz) {
            this(p, isHoriz, null, null);
        }

        public Node(Point2D p, boolean isHoriz, Node left, Node right) {
            this.setIsHoriz(isHoriz);
            this.setP(p);
            this.setLeft(left);
            this.setRight(right);
        }

        public Point2D getP() {
            return p;
        }

        public void setP(Point2D p) {
            this.p = p;
        }

        public boolean isHoriz() {
            return isHoriz;
        }

        public void setIsHoriz(boolean isHoriz) {
            this.isHoriz = isHoriz;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}