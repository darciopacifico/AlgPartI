import edu.princeton.cs.algs4.StdOut;
import sun.jvm.hotspot.tools.SysPropsDumper;

/**
 * Created by dpacif1 on 2/10/16.
 */
public class LabFatorial {

    public static void main(String[] args) {


        Point p1 = new Point(1,1);
        Point p2 = new Point(2,2);
        Point p3 = new Point(3,1);

        StdOut.println(p1.slopeTo(p2));
        StdOut.println(p2.slopeTo(p1));


        StdOut.println(p2.slopeTo(p3));
        StdOut.println(p3.slopeTo(p2));



    }


}
