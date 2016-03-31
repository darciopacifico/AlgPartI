import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpacif1 on 3/27/16.
 */
public class Outcast {

    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null)
            throw new NullPointerException("null wordnet ref");
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {

        if (nouns == null)
            throw new NullPointerException("null nouns array");

        if (nouns.length == 0)
            throw new IllegalArgumentException("nouns is an empty array");


        Map<String, Long> mapDistSum = new HashMap<String, Long>();

        String outcast = null;
        Long outcastDist = 0l;

        for (String nounA : nouns) {
            for (String nounB : nouns) {
                if (!mapDistSum.containsKey(nounA)) {
                    mapDistSum.put(nounA, 0l);
                }

                long totalA = mapDistSum.get(nounA) + this.wordNet.distance(nounA, nounB);
                mapDistSum.put(nounA, totalA);

                if (totalA > outcastDist) {
                    outcast = nounA;
                    outcastDist = totalA;

                }
            }
        }


        return outcast;
    }


    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }


}
