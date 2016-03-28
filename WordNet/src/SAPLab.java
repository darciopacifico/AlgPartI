import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.List;
import java.util.Set;

/**
 * Created by dpacif1 on 3/27/16.
 */
public class SAPLab {


    public static void main(String[] args) {

        //um fim apenas
        System.out.println("um final apenas");
        printPaths("/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/digraph1.txt", 12);

        //System.out.println("um final apenas");
        //printPaths("/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/digraph5.txt", 12);

        //dois finais possiveis
        System.out.println("mais de um fim");
        printPaths("/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/digraph1_duploFim.txt", 12);


    }

    private static void printPaths(String digraphFile, int v) {
        In in = new In(digraphFile);
        Digraph digraph = new Digraph(in);
        SAP sap = new SAP(digraph);

        /*Set<List<Integer>> paths = sap.pathsToHighest(v);

        for (List<Integer> path : paths) {
            System.out.println(path);
        }*/
    }
}
