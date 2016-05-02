/**
 * Created by dpacif1 on 4/30/16.
 */
public class KMP_DFA_Lab {

    private final int lenAlfabeto;       // the radix
    private int[][] dfa;       // the KMP automoton

    private char[] pattern;    // either the character array for the pattern
    private String pat;        // or the pattern string


    public KMP_DFA_Lab(String pat) {
        this.lenAlfabeto = 256;
        this.pat = pat;

        // build DFA from pattern
        int patLen = pat.length();
        dfa = new int[lenAlfabeto][patLen];

        // SET TRANSITION TO FIRST CHAR AT FIRST POSITION

        final char primeiraLetraDoPadrao = pat.charAt(0);// C ou B ou A...
        dfa[primeiraLetraDoPadrao][0] = 1;

        //default restart
        int idxRESTART = 0;

        // percorre toodo o padrao
        for (int idxPattern = 1; idxPattern < patLen; idxPattern++) {

            // percorre todoo o alfabeto... PARA UM ELEMENTO DO PADRAO
            for (int idxAlfab = 0; idxAlfab < lenAlfabeto; idxAlfab++) {
                // Copy mismatch cases.
                // considera, inicialmente, todas as respostas como mismatch
                final int[] arrTransicoesDaLetraDoAlfabeto = dfa[idxAlfab];

                arrTransicoesDaLetraDoAlfabeto[idxPattern] = arrTransicoesDaLetraDoAlfabeto[idxRESTART];
            }

            final char caracterDoIdxPattern = pat.charAt(idxPattern);

            final int[] arrTransicoesDoCaracter = dfa[caracterDoIdxPattern];

            // ajusta o default dos chars acertados => +1 = seguir para o proximo
            arrTransicoesDoCaracter[idxPattern] = idxPattern + 1;


            Boolean igual = idxRESTART == arrTransicoesDoCaracter[idxRESTART];
            System.out.print(" idxPattern=" + idxPattern + " character=" + caracterDoIdxPattern);
            System.out.println(" idxRESTART="+idxRESTART+ "  arrTransicoesDoCaracter[idxRESTART]="+arrTransicoesDoCaracter[idxRESTART]+" igual = "+igual  );

            idxRESTART = arrTransicoesDoCaracter[idxRESTART];
            // Update restart state.
        }
    }


    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //KMP_DFA_Lab dfa = new KMP_DFA_Lab("BCBCBCBABA");
        KMP_DFA_Lab dfa = new KMP_DFA_Lab("ABCABBAFDE");

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

    public static void printArr(KMP_DFA_Lab dfa, int[][] arr, char c) {
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
