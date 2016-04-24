import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dpacif1 on 4/23/16.
 */
public class BaseballElimination {


    private int[] ws, ls, rm;
    private int[][] matches;
    private int maxWins = Integer.MIN_VALUE;
    private Map<String, Integer> teamToId;
    private String leaderTeam;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        int teams = in.readInt();
        ws = new int[teams];
        ls = new int[teams];
        rm = new int[teams];
        matches = new int[teams][teams];

        teamToId = new HashMap<String, Integer>();
        for (int id = 0; id < teams; id++) {
            String team = in.readString();
            teamToId.put(team, id);
            ws[id] = in.readInt();
            ls[id] = in.readInt();
            rm[id] = in.readInt();

            for (int j = 0; j < teams; j++) {
                matches[id][j] = in.readInt();
            }
            if (ws[id] > maxWins) {
                maxWins = ws[id];
                leaderTeam = team;
            }
        }
    }


    public int numberOfTeams() {
        return teamToId.size();
    }

    public Iterable<String> teams() {
        return teamToId.keySet();
    }

    public int wins(String team) {
        if (!teamToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return ws[teamToId.get(team)];
    }

    public int losses(String team) {
        if (!teamToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return ls[teamToId.get(team)];
    }

    /**
     * @param team
     * @return Number of rm matches for given team
     */
    public int remaining(String team) {
        if (!teamToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return rm[teamToId.get(team)];
    }

    /**
     * @param team1
     * @param team2
     * @return Number of rm matches between team1 and team2
     */
    public int against(String team1, String team2) {
        if (!teamToId.containsKey(team1) || !teamToId.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        return matches[teamToId.get(team1)][teamToId.get(team2)];
    }

    public boolean isEliminated(String team) {
        if (!teamToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        int id = teamToId.get(team);
        if (triviallyEliminated(id)) {
            //System.out.print("trivialelimination ");
            return true;
        }
        Graph graph = buildGraphFor(id);
        for (FlowEdge edge : graph.network.adj(graph.source)) {
            if (edge.flow() < edge.capacity()) {
                return true;
            }
        }
        return false;
    }

    private boolean triviallyEliminated(int id) {
        for (int i = 0; i < ws.length; i++) {
            if (i != id) {
                if (ws[id] + rm[id] < ws[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if (!teamToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        Set<String> set = new HashSet<String>();
        if (triviallyEliminated(teamToId.get(team))) {
            set.add(leaderTeam);
            return set;
        }
        Graph g = buildGraphFor(teamToId.get(team));
        for (FlowEdge edge : g.network.adj(g.source)) {
            if (edge.flow() < edge.capacity()) {
                for (String t : teams()) {
                    int id = teamToId.get(t);
                    if (g.ff.inCut(id)) {
                        set.add(t);
                    }
                }
            }
        }
        g = null;
        if (set.isEmpty()) {
            return null;
        }
        return set;
    }

    private Graph buildGraphFor(int id) {
        int n = numberOfTeams();
        int source = n;
        int sink = n + 1;
        int gameNode = n + 2;
        int currentMaxWins = ws[id] + rm[id];
        Set<FlowEdge> edges = new HashSet<FlowEdge>();
        for (int i = 0; i < n; i++) {
            if (i == id || ws[i] + rm[i] < maxWins) {
                continue;
            }

            for (int j = 0; j < i; j++) {
                if (j == id || matches[i][j] == 0 || ws[j] + rm[j] < maxWins) {
                    continue;
                }

                edges.add(new FlowEdge(source, gameNode, matches[i][j]));
                edges.add(new FlowEdge(gameNode, i, Double.POSITIVE_INFINITY));
                edges.add(new FlowEdge(gameNode, j, Double.POSITIVE_INFINITY));
                gameNode++;
            }
            edges.add(new FlowEdge(i, sink, currentMaxWins - ws[i]));
        }

        FlowNetwork network = new FlowNetwork(gameNode);
        for (FlowEdge edge : edges) {
            network.addEdge(edge);
        }
        FordFulkerson ff = new FordFulkerson(network, source, sink);
        return new Graph(ff, network, source, sink);
    }

    private class Graph {
        FordFulkerson ff;
        FlowNetwork network;
        int source;
        int sink;

        public Graph(FordFulkerson ff, FlowNetwork network, int source, int sink) {
            super();
            this.ff = ff;
            this.network = network;
            this.source = source;
            this.sink = sink;
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }


}
