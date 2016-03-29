import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dpacif1 on 3/27/16.
 */
public class WordNet {

    //private Map<Integer, Synset> mapByNounSynset = new HashMap<Integer, Synset>();
    private Map<String, Set<Synset>> mapByNounSynset = new HashMap<String, Set<Synset>>();

    private Map<Integer, Synset> mapByIdSynset = new HashMap<Integer, Synset>();

    private Digraph digraph;

    private SAP sap;

    /**
     * Synset data structure / class
     */
    private class Synset {
        Integer id;
        String synset;
        String definition;

        public Synset(Integer id, String synset, String definition) {
            this.id = id;
            this.synset = synset;
            this.definition = definition;
        }
    }


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException("synsets and hypernyms must not be null!");
        }

        fillMapSynset(synsets);
        fillHypernyms(hypernyms);
    }

    /**
     * FIll digraph with hypernyms
     *
     * @param hypernyms
     */
    private void fillHypernyms(String hypernyms) {
        this.digraph = new Digraph(getLineCount(hypernyms));

        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            // StdOut.println(line);
            String[] cols = line.split(",");

            if (cols.length < 2) {
//                throw new IllegalArgumentException("hypernyms file doesn't fit to expected layout (id, ids...) line=" + line);
            }

            Integer idOrig = new Integer(cols[0]);

            for (int i = 1; i < cols.length; i++) {

                this.digraph.addEdge(idOrig, new Integer(cols[i]));

            }
        }

        this.sap = new SAP(this.digraph);
    }

    /**
     * Count lines from file
     *
     * @param filePath
     * @return
     */
    private Integer getLineCount(String filePath) {
        Long lineCount;
        try {
            lineCount = Files.lines(Paths.get(filePath), Charset.defaultCharset()).count();
        } catch (IOException e) {
            throw new IllegalArgumentException("File doesn't found!", e);
        }
        return lineCount.intValue() + 1;
    }

    /**
     * Fills map synset
     *
     * @param synsets
     */
    private void fillMapSynset(String synsets) {
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] cols = line.split(",");

            if (cols.length < 3) {
                throw new IllegalArgumentException("synsets file doesn't fit to expected layout (id, synset, definition)!");
            }

            Integer id = new Integer(cols[0]);
            String synonyms = cols[1];
            String[] arrSynset = synonyms.split(" ");
            String definition = cols[2];

            Synset synset = new Synset(id, synonyms, definition);

            this.mapByIdSynset.put(id, synset);
            for (String syn : arrSynset) {
                if (!this.mapByNounSynset.containsKey(syn)) {
                    Set<Synset> syns = new HashSet<Synset>();
                    this.mapByNounSynset.put(syn, syns);
                }

                this.mapByNounSynset.get(syn).add(synset);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.mapByNounSynset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.mapByNounSynset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        Set<Synset> synsetA = this.mapByNounSynset.get(nounA);
        Set<Synset> synsetB = this.mapByNounSynset.get(nounB);

        if (synsetA == null || synsetB == null) {
            return -1;
        }

        Iterable<Integer> as = getIds(synsetA);
        Iterable<Integer> bs = getIds(synsetB);


        return this.sap.length(as, bs);
    }

    /**
     * map to ids
     *
     * @param synsetA
     * @return
     */
    private Iterable<Integer> getIds(Set<Synset> synsetA) {
        Set<Integer> ids = new HashSet<Integer>(synsetA.size());
        for (Synset s : synsetA) {
            ids.add(s.id);
        }
        return ids;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        Set<Synset> synsetA = this.mapByNounSynset.get(nounA);
        Set<Synset> synsetB = this.mapByNounSynset.get(nounB);

        if (synsetA == null || synsetB == null) {
            return null;
        }


        Iterable<Integer> as = getIds(synsetA);
        Iterable<Integer> bs = getIds(synsetB);

        Integer ancestor = this.sap.ancestor(as, bs);

        String synset = this.mapByIdSynset.get(ancestor).synset;

        return synset;
    }

    // do unit testing of this class
    public static void main(String[] args) {

        String synsetFile;
        String hypernyms;

        if (args.length < 2) {

            synsetFile = "/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/synsets.txt";
            hypernyms = "/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/hypernyms.txt";

            //synsetFile = "/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/synsets15.txt";
            //hypernyms = "/Users/dpacif1/scala/PercolatoinAssignment/WordNet/src/hypernyms15Path.txt";


        } else {
            synsetFile = args[0];
            hypernyms = args[1];
        }

        WordNet wordNet = new WordNet(synsetFile, hypernyms);

        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();

            int distance = wordNet.distance(v, w);
            String ancestor = wordNet.sap(v, w);

            StdOut.printf("distance = %d, ancestor = %s\n", distance, ancestor);
        }

    }
}