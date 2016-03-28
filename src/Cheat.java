import edu.princeton.cs.algs4.StdOut;

/**
 * Created by dpacif1 on 2/29/16.
 */
public class Cheat {

    public static void main(String[] args) {

        for (int N = 1; N < 257; N = N * 2) {

            int sum = 0;
            for (int i = 1; i <= N * N; i++)
                for (int j = 1; j <= i; j++)
                    sum++;

            StdOut.println(N+", "+sum);
        }
    }

}
