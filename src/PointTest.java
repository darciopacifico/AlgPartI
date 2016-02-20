import edu.princeton.cs.algs4.StdOut;

/**
 * Created by dpacif1 on 2/11/16.
 */
public class PointTest {


    public static void main (String[] args){
        /**
         *
         p                        = (4, 6)
         q                        = (2, 9)
         student   p.slopeTo(q) = -1.0
         reference p.slopeTo(q) = -1.5
         Point p = new Point(4, 6);
         Point q = new Point(2, 9);



         p                        = (2113, 5384)
         q                        = (2113, 16792)
         student   p.slopeTo(q) = 0.0
         reference p.slopeTo(q) = Infinity

         */


        Point p = new Point(4, 6);
        Point q = new Point(2, 9);

        StdOut.println(p.slopeTo(q));

    }

}
