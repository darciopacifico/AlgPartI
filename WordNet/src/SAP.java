import edu.princeton.cs.algs4.*;

import java.util.*;

/**
 * sap
 * Created by dpacif1 on 3/27/16.
 */
public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        this.digraph = digraph;
    }


    /**
     * Helper class for tuple processing
     *
     * @param <T>
     */
    private class Tuple2<T> {
        public T _1, _2;

        public Tuple2(T _1, T _2) {
            this._1 = _1;
            this._2 = _2;
        }
    }


    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        Tuple2<List<Integer>> twoPathsResult = getLCAPaths(v, w);

        int length = getLength(twoPathsResult);

        return length;

    }

    private Integer getLength(Tuple2<List<Integer>> twoPathsResult) {
        if (twoPathsResult != null) {

            int size1 = twoPathsResult._1.size();
            int size2 = twoPathsResult._2.size();


            int desconto = 0;

            if (size1 == 1 && size2 == 1) {
                // mesmo ponto.. impossivel, mas...
                return 0;

            } else if (size1 == 1 || size2 == 1) {
                // um é filho do outro.. entao um deve ser desprezado. pegar maior
                return Math.max(size1, size2) - 1;
            } else {
                //dois branches diferentes
                return size1 + size2 - 2;
            }


        }
        return -1;
    }

    /**
     * Calculate LCA with lowest distance
     *
     * @param v
     * @param w
     * @return
     */
    private Tuple2<List<Integer>> getLCAPaths(int v, int w) {
        Set<List<Integer>> setPathsToV = pathsToRoot(v,w);
        Set<List<Integer>> setPathsToW = pathsToRoot(w,v);

        //Set<Integer> commonParents = getCommonParents(pathsToV, pathsToW);
        Integer champLen = Integer.MAX_VALUE; // as default, no common parent
        Tuple2<List<Integer>> champPat = null;

        for (List<Integer> pathV : setPathsToV) {
            for (List<Integer> pathW : setPathsToW) {
                if (isSameRoot(pathV, pathW)) {

                    List<Integer> copyPathV = copyList(pathV);
                    List<Integer> copyPathW = copyList(pathW);

                    Tuple2<List<Integer>> twoPaths = removeCommonStart(copyPathV, copyPathW);

                    Integer dist = getLength(twoPaths);

                    if (dist < champLen) {
                        champLen = dist; // new champion
                        champPat = twoPaths;
                    }
                }
            }
        }

        return champPat;
    }

    /*
        if (champPat != null) {
            assert (champPat._1 != null && champPat._2 != null && champPat._1.size() > 0 && champPat._2.size() > 0);
        }
*/


    private boolean isSameRoot(List<Integer> pathToV, List<Integer> pathToW) {
        final Integer rootV = pathToV.get(0);
        final Integer rootW = pathToW.get(0);

        return rootV.intValue() == rootW.intValue();
    }
/*


    private Tuple2<List<Integer>> getLCAPaths(int v, int w) {
        Set<List<Integer>> pathsToV = pathsToHighest(v);
        Set<List<Integer>> pathsToW = pathsToHighest(w);

        //Set<Integer> commonParents = getCommonParents(pathsToV, pathsToW);

        Integer champLen = Integer.MAX_VALUE; // as default, no common parent
        Tuple2<List<Integer>> champPat = null;




        for (Integer parent : commonParents) {

            List<Integer> pathToV = pathsToV.get(parent);
            List<Integer> pathToW = pathsToW.get(parent);

            Tuple2<List<Integer>> twoPaths = removeCommonStart(pathToV, pathToW);

            Integer dist = twoPaths._1.size() + twoPaths._2.size() - 2;

            if (dist < champLen) {
                champLen = dist; // new champion
                champPat = twoPaths;
            }

        }

        if (champPat != null) {
            assert (champPat._1 != null && champPat._2 != null && champPat._1.size() > 0 && champPat._2.size() > 0);
        }

        return champPat;
    }
*/

    /**
     * Recursively remove the largest parent in common, letting only the LCA
     *
     * @param pathsToV
     * @param pathsToW
     * @return
     */
    private Tuple2<List<Integer>> removeCommonStart(List<Integer> pathsToV, List<Integer> pathsToW) {
        assert (pathsToV.size() > 0 && pathsToW.size() > 0);

        //are the first and second element equals? if yes, remove the first one
        if (pathsToV.size() > 1 && pathsToW.size() > 1 &&
                pathsToV.get(0).equals(pathsToW.get(0)) &&
                pathsToV.get(1).equals(pathsToW.get(1))) {

            pathsToV.remove(0);
            pathsToW.remove(0);

            removeCommonStart(pathsToV, pathsToW);
        }

        return new Tuple2<List<Integer>>(pathsToV, pathsToW);
    }

    /**
     * From all paths from vertices to border, consider only those with same largest parent
     *
     * @param pathsToV
     * @param pathsToW
     * @return
     */
    private Set<Integer> getCommonParents(Set<List<Integer>> pathsToV, Set<List<Integer>> pathsToW) {


        HashSet<Integer> set = new HashSet<Integer>();

        set.add(pathsToV.iterator().next().get(0));

        return set;
    }


    /**
     * @param v
     * @return
     */
    private Set<List<Integer>> pathsToRoot(int v, int w) {
        Set<List<Integer>> initialSet = new HashSet<List<Integer>>();
        return this.pathsToRoot(v, w,new HashSet<Integer>(10), new LinkedList<Integer>(), initialSet);
    }


    /**
     * @param v
     * @param visiteds
     * @param pathToHigh
     * @param pathsToEnd
     * @return
     */
    private Set<List<Integer>> pathsToRoot(int v, int w, Set<Integer> visiteds, List<Integer> pathToHigh, Set<List<Integer>> pathsToEnd) {
        pathToHigh.add(v);
        visiteds.add(v);


        Iterable<Integer> adjList = digraph.adj(v);

        if (digraph.outdegree(v) > 0) {
            for (Integer adjV : adjList) {

                if (!visiteds.contains(adjV)) {

                    List<Integer> newPathToHigh = copyList(pathToHigh);
                    Set<Integer> copyVisiteds = copyVisiteds(visiteds);

                    if (!(adjV == w)) {
                        //pathToHigh = null; // TODO: think about memory optimization
                        pathsToRoot(adjV, w, copyVisiteds, newPathToHigh, pathsToEnd);
                    }

                }
            }
        }
        //no more from here
        Collections.reverse(pathToHigh);

        pathsToEnd.add(pathToHigh);


        return pathsToEnd;
    }


    private HashSet<Integer> copyVisiteds(Set<Integer> visiteds) {
        return new HashSet<Integer>(visiteds);
    }

    /**
     * Simple copy of a list
     *
     * @param pathToHigh
     * @return
     */
    private List<Integer> copyList(List<Integer> pathToHigh) {
        return new ArrayList<Integer>(pathToHigh);
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        Tuple2<List<Integer>> twoPathsResult = getLCAPaths(v, w);

        if (twoPathsResult != null) {
            return twoPathsResult._1.get(0);
        } else {
            return -1;

        }

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph digraph = new Digraph(in);

        int v = 1;
        int w = 0;

        BreadthFirstDirectedPaths bfds = new BreadthFirstDirectedPaths(digraph, v);
        Iterable<Integer> path = bfds.pathTo(w);

        for (Integer p:path){
            System.out.println(p);
        }

        /*
        SAP sap = new SAP(digraph);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        */
        //sap.pathToHighest(10);

    }

}
