import edu.princeton.cs.algs4.*;

import java.util.*;

/**
 * sap
 * Created by dpacif1 on 3/27/16.
 */
public class SAP {
    private Digraph digraph;
    private Map<Integer, Set<Integer>> cacheAncestors = new HashMap<Integer, Set<Integer>>();

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {

        if (digraph == null)
            throw new java.lang.NullPointerException("digraph is null");

        this.digraph = copyDigraph(digraph);
    }

    /**
     * Make a defensive copy of digraph
     *
     * @param digraph
     * @return
     */
    private Digraph copyDigraph(Digraph digraph) {
        int vertices = digraph.V();
        Digraph digraphCopy = new Digraph(vertices);

        for (int v = 0; v < vertices; v++) {
            for (int w : digraph.adj(v)) {
                digraphCopy.addEdge(v, w);
            }
        }

        return digraphCopy;
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
     * Get tuple LCA + Length
     *
     * @param vs
     * @param ws
     * @return
     */
    private Tuple2<Long> getLCA_Legth(Iterable<Integer> vs, Iterable<Integer> ws) {

        if (ws == null || vs == null)
            throw new NullPointerException("ws or vs = null");

        for (Integer v : vs)
            this.digraph.indegree(v);

        for (Integer v : vs)
            this.digraph.indegree(v);


        Tuple2<Long> tup = new Tuple2<Long>(-1l, -1l); // by defaul, none was found


        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(digraph, vs);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(digraph, ws);

        Set<Integer> commonAncestors = allCommonAncestors(vs, ws);
        if (!commonAncestors.isEmpty()) {

            for (Integer p : commonAncestors) {

                Long dv = new Long(bv.distTo(p));
                Long dw = new Long(bw.distTo(p));

                long distTotal = dv + dw;

                if (tup._2 == -1 || distTotal < tup._2) {
                    tup._1 = p.longValue();
                    tup._2 = distTotal;
                }
            }
        }

        return tup;
    }

    /**
     * join all common ancestors
     *
     * @param vs
     * @param ws
     * @return
     */
    private Set<Integer> allCommonAncestors(Iterable<Integer> vs, Iterable<Integer> ws) {
        Set<Integer> reachsV = allAncestors(vs);
        Set<Integer> reachsW = allAncestors(ws);

        reachsV.retainAll(reachsW);
        return reachsV;
    }

    /**
     * join all common ancestors
     *
     * @param v
     * @param w
     * @return
     */
    private Set<Integer> allCommonAncestors(Integer v, Integer w) {
        Set<Integer> reachsV = allAncestors(Collections.singleton(v));
        Set<Integer> reachsW = allAncestors(Collections.singleton(w));

        reachsV.retainAll(reachsW);
        return reachsV;
    }


    /**
     * Take all ancestors
     *
     * @param v
     * @param visiteds
     * @return
     */
    private Set<Integer> allAncestors(Integer v, Set<Integer> visiteds) {

        visiteds.add(v);
        Iterable<Integer> adj = digraph.adj(v);

        for (Integer vAdj : adj) {
            if (!visiteds.contains(vAdj)) {
                allAncestors(vAdj, visiteds);
            }
        }

        return visiteds;

    }


    /**
     * take all ancestors
     *
     * @param v
     * @return
     */
    private Set<Integer> allAncestors(Integer v) {

        if (this.cacheAncestors.containsKey(v)) {
            return this.cacheAncestors.get(v);

        } else {
            final Set<Integer> ancestors = allAncestors(v, new HashSet<Integer>());
            this.cacheAncestors.put(v, ancestors);

            return ancestors;
        }

    }

    /**
     * take all ancestors
     *
     * @param vs
     * @return
     */
    private Set<Integer> allAncestors(Iterable<Integer> vs) {
        Set<Integer> visiteds = new HashSet<Integer>();
        Set<Integer> reachables = new HashSet<Integer>();

        for (Integer v : vs) {
            reachables.addAll(allAncestors(v, visiteds));
        }

        return reachables;
    }


    /**
     * Length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        return length(Collections.singletonList(v), Collections.singletonList(w));
    }

    /**
     * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v), Collections.singletonList(w));
    }

    /**
     * Length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        return getLCA_Legth(vs, ws)._2.intValue();
    }

    /**
     * A common ancestor that participates in shortest ancestral path; -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return getLCA_Legth(v, w)._1.intValue();
    }


    /**
     * do unit testing of this class
     *
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph digraph = new Digraph(in);
        SAP sap = new SAP(digraph);


        List<Integer> vs = new ArrayList<Integer>();
        List<Integer> ws = new ArrayList<Integer>();

        vs.add(77401);
        //vs.add();

        ws.add(33587);
        ws.add(40679);

        Integer l = sap.length(vs, ws);

        System.out.print("  vs:" + vs + " ws:" + ws + " length = " + l);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);

            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }

}
