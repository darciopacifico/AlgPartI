import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by dpacif1 on 2/19/16.
 */
public class Lab {


    public static void main(String[] args) {

        args = new String[]{
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle_testeDLP.txt",
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle17.txt",
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle27.txt"/*,
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle00.txt",
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle04.txt",
            "/Users/dpacif1/scala/PercolatoinAssignment/src/puzzle07.txt",
            "/Users/dpacif1/scala/PercolatoinAssignment/src/hammingRef_16127.txt",
            "/Users/dpacif1/scala/PercolatoinAssignment/src/9x9_ref.txt"*/
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
        }
    }

}
