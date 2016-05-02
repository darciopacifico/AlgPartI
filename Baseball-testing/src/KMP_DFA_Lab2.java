/**
 * Created by dpacif1 on 4/30/16.
 */
public class KMP_DFA_Lab2 {

    private final int R;       // the radix
    private int[][] dfa;       // the KMP automoton

    private char[] pattern;    // either the character array for the pattern
    private String pat;        // or the pattern string

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public KMP_DFA_Lab2(String pat) {
        this.R = 256;
        this.pat = pat;

        // build DFA from pattern
        int M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];     // Copy mismatch cases.
            dfa[pat.charAt(j)][j] = j+1;   // Set match case.
            X = dfa[pat.charAt(j)][X];     // Update restart state.
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //KMP_DFA_Lab dfa = new KMP_DFA_Lab("BCBCBCBABA");
        KMP_DFA_Lab2 dfa = new KMP_DFA_Lab2("ABABACAC");

        System.out.print("  ");
        for (int i = 0; i < dfa.pat.length(); i++) {
            System.out.print((i + "   ").substring(0,4));
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------");

        System.out.print("  ");
        for (int i = 0; i < dfa.pat.length(); i++) {
            System.out.print(dfa.pat.charAt(i) + "   ");
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------");

        printArr(dfa, dfa.dfa, 'A');
        printArr(dfa, dfa.dfa, 'B');
        printArr(dfa, dfa.dfa, 'C');
        printArr(dfa, dfa.dfa, 'D');
        printArr(dfa, dfa.dfa, 'E');
        printArr(dfa, dfa.dfa, 'F');
        printArr(dfa, dfa.dfa, 'G');

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }

    public static void printArr(KMP_DFA_Lab2 dfa, int[][] arr, char c) {
        System.out.print(c + " ");
        printArr(arr[c]);
    }


    public static void printArr(int[] arr) {

        for (int i = 0; i < arr.length; i++){
            if(arr[i]>0)
                System.out.print((arr[i] + "    ").substring(0,4));
            else
                System.out.print("    ");
        }

        System.out.println("");
    }
}
