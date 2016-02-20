import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * BruteCollinearPoints implementation
 */
public class BruteCollinearPoints {

    private final LineSegment[] segments;
    private final Point[] localPoints;


    /**
     * Finds all line segments containing 4 points
     */
    public BruteCollinearPoints(final Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException("points==null");
        }
        this.localPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(this.localPoints);
        checkDuplicatedPoints(this.localPoints);


        this.segments = getLineSegments(this.localPoints);

    }

    /**
     * Check for duplicated points
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
        return segments().length;
    }

    /**
     * The line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.segments.length);
    }

    /**
     * Calculate collinear points using brute force
     *
     * @param points
     * @return
     */
    private LineSegment[] getLineSegments(Point[] points) {
        LineSegment[] lineSegments = new LineSegment[0];

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int l = j + 1; l < points.length - 1; l++) {
                    for (int m = l + 1; m < points.length; m++) {
                        if (points[i] != null && points[j] != null && points[l] != null && points[i] != null) {

                            double slope_ij = points[i].slopeTo(points[j]);
                            double slope_jl = points[j].slopeTo(points[l]);
                            double slope_lm = points[l].slopeTo(points[m]);
                            double slope_im = points[i].slopeTo(points[m]);

                            if (slope_ij == slope_jl && slope_jl == slope_lm && slope_lm == slope_im && slope_im == slope_ij) { // same segment

                                Point[] ps = new Point[]{points[i], points[j], points[l], points[m]};
                                Arrays.sort(ps);

                                lineSegments = Arrays.copyOf(lineSegments, lineSegments.length + 1);

                                lineSegments[lineSegments.length - 1] = new LineSegment(ps[0], ps[3]);
                            }
                        }
                    }

                }
            }
        }

        final LineSegment[] lineSegments2 = Arrays.copyOf(lineSegments, lineSegments.length);

        return lineSegments2;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}