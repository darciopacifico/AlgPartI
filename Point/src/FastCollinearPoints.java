import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Fast Collinear implementation
 */
public class FastCollinearPoints {

    private static final int MIN_POINTS = 4;
    private final Point[] localPoints;
    private final LineSegment[] segments;

    /**
     * Finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(final Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException("points==null");
        }

        this.localPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(this.localPoints);
        checkDuplicatedPoints(this.localPoints);

        this.segments = getLineSegments(this.localPoints);
    }

    /**
     * Check duplicated points
     */
    private void checkDuplicatedPoints(Point[] pointsToCheckDups) {
        //check for repeated points

        for (int i = 0; i < pointsToCheckDups.length - 1; i++) {
            if (pointsToCheckDups[i].compareTo(pointsToCheckDups[i + 1]) == 0) {
                throw new IllegalArgumentException("Repeated points! Points " + i + " and " + (i + 1) + " are the same!");
            }
        }
    }

    /**
     * The number of line segments
     */
    public int numberOfSegments() {
        return this.segments.length;
    }

    /**
     * The line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.segments.length);
    }

    /**
     * Calculate line segments from given points
     * @param points
     * @return
     */
    private LineSegment[] getLineSegments(Point[] points) {
        Point[] pointCopy = Arrays.copyOf(points, points.length);

        SET<SegControl> segments = new SET<SegControl>();

        for (int i = 0; i < points.length - MIN_POINTS + 1; i++) {

            Arrays.sort(pointCopy, points[i].slopeOrder());

            SegControl segmentControl = new SegControl(points[i]);
            segmentControl.add(pointCopy[1]);

            for (int j = 2; j < pointCopy.length; j++) { // the first one will be point[i] itself, dont mind

                Point pA = pointCopy[j];
                Point pB = pointCopy[j - 1];


                double slopeToA = points[i].slopeTo(pA);
                double slopeToB = points[i].slopeTo(pB);

                if (slopeToA != slopeToB) {
                    //not same segment anymores

                    if (segmentControl.size >= MIN_POINTS)
                        segments.add(segmentControl);

                    segmentControl = new SegControl(points[i]);
                }
                segmentControl.add(pA);

            }
            if (segmentControl.size >= MIN_POINTS)
                segments.add(segmentControl);

        }

        LineSegment[] result = new LineSegment[segments.size()];
        int i = 0;
        for (SegControl ls : segments) {
            result[i++] = new LineSegment(ls.min, ls.max);
        }

        return result;

    }

    /**
     * Maintain only the two most distant points in a line segment.
     */
    private class SegControl implements Comparable<SegControl> {
        Point min, max;
        public int size = 0;

        public SegControl(Point initial) {
            this.min = initial;
            this.max = initial;
            this.size = 1;
        }

        public void add(Point p) {
            if (p.compareTo(min) < 0) this.min = p;
            if (p.compareTo(max) > 0) this.max = p;
            size++;
        }

        public int compareTo(SegControl that) {
            int min = this.min.compareTo(that.min);
            if (min == 0) {
                return this.max.compareTo(that.max);
            } else {
                return min;
            }
        }
    }

    /**
     * Java main method. Calls collinear implementations
     *
     * @param args
     */
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }


}