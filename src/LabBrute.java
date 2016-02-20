import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by dpacif1 on 2/12/16.
 */
public class LabBrute {


    public static void main(String[] args) {
        LineSegment[] ref = null;
        int qtd = 0;
        BruteCollinearPoints b = null;
        Point[] points;
        for (int k = 0; k < 30000; k++) {

            // read the N points from a file
            In in = new In(args[0]);
            int N = in.readInt();
            points = new Point[N];
            for (int i = 0; i < N; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }


            // draw the points
            if (b == null)
                b = new BruteCollinearPoints(points);

            points[StdRandom.uniform(points.length)] = null;

            LineSegment[] segArray = b.segments();

            if (ref != null) {

                for (int w = 0; w < segArray.length; w++) {

                    if (!segArray[w].toString().equals(ref[w].toString())) {
                        StdOut.println("PQP CARALHO FUDEU!!!");
                    } else {
                        //StdOut.println("igual");
                    }


                    if (qtd != segArray.length) {
                        StdOut.println("PQP CARALHO FUDEU!!!");
                    }

                }

            }
            qtd = segArray.length;
            ref = segArray;

        }

    }

}
