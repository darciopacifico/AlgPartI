import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by dpacif1 on 2/19/16.
 */
public class LabMut {


    public static void main(String[] args) {

        args = new String[]{
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle17.txt"
        };

        // for each command-line argument
        for (String filename : args) {


            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);

            StdOut.println("");
            StdOut.println(filename + " manhattan: " + initial.manhattan());
            StdOut.println(filename + " hamming  : " + initial.hamming());

            /**
             3
             5  1  8
             2  7  3
             4  0  6

             */

            int s = tiles[0][0];
            tiles[0][0]=tiles[0][1];
            tiles[0][1]=s;

            StdOut.println("");
            StdOut.println(filename + " manhattan: " + initial.manhattan());
            StdOut.println(filename + " hamming  : " + initial.hamming());

            StdOut.println("");
            StdOut.println(filename + " manhattan: " + initial.manhattan());
            StdOut.println(filename + " hamming  : " + initial.hamming());


        }
    }

}
