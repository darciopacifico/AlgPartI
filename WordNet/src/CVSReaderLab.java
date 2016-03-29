import edu.princeton.cs.algs4.In;

/**
 * Created by dpacif1 on 3/29/16.
 */
public class CVSReaderLab {

    public static void main(String[] args) {

        In in = new In(args[0]);




        while (in.hasNextLine()) {
            String line = in.readLine();

            String[] cols = line.split(",");



            assert (cols.length >= 3);

            System.out.println(cols.length);

        }

    }
}
