import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class SolverNovoFudido {

    private Board initial;
    private Board twin;
    private Node endNode;

    private Boolean solvable;

    /**
     * Hamming comparator
     */
    private class HammingBoardComparator implements java.util.Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {

            int diff = o1.board.hamming() - o2.board.hamming();

            if (diff == 0) {
                return o1.moves - o2.moves;
            } else {
                return diff;

            }

        }
    }

    /**
     * Manhattan comparator
     */
    private class ManhattanBoardComparator implements java.util.Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {

            int diff = o1.board.manhattan() - o2.board.manhattan();

            if (diff == 0) {
                return o1.moves - o2.moves;
            } else {
                return diff;

            }

        }
    }

    private class Node {

        public Node prev;

        public Node next;

        public Board board;
        public int moves;

        public Node(Node prev, Board board, /*Node next,*/ int moves) {
            this.prev = prev;
            //this.next = next;
            this.board = board;
            this.moves = moves;
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param board
     */
    public SolverNovoFudido(Board board) {

        this.initial = board;
        this.twin = initial.twin();

        if (this.initial == null) throw new NullPointerException("null Board");


        Node tup1 = new Node(new Node(null, null, 0), this.initial, 0);
        MinPQ<Node> minPqInit = new MinPQ<Node>(512, new ManhattanBoardComparator());
        minPqInit.insert(tup1);

        MinPQ<Node> minPqTwin = new MinPQ<Node>(512, new ManhattanBoardComparator());
        //MinPQ<Node> minPqTwin = minPqInit;
        Node tup2 = new Node(new Node(null, null, 0), this.twin, 0);
        minPqTwin.insert(tup2);

        this.solvable = solver(minPqInit, minPqTwin);
    }


    private boolean solver_new(MinPQ<Node> minPq,MinPQ<Node> minPqTwin) {


        while (true) {

            if (!minPq.isEmpty()) {
                Node min = minPq.delMin();
                if (min.board.isGoal()) {
                    this.endNode = min;

                    Node startNode = getStartNode(this.endNode);

                    if (startNode.board.equals(this.initial)) {
                        return true;

                    } else if (startNode.board.equals(this.twin)) {
                        return false;

                    } else {
                        //wtf!!
                        assert false;
                        StdOut.println("Start node supose to be equal to initial or twin node!!");
                        return false;

                    }
                }
                fillNeigs(minPq, min);
            } else {
                this.endNode = null;
            }


        }
    }

    private Node getStartNode(Node endNode) {
        if (endNode.prev == null || endNode.prev.board == null) {
            return endNode;
        } else {
            return getStartNode(endNode.prev);
        }
    }


    private boolean solver(MinPQ<Node> minPqInit, MinPQ<Node> minPqTwin) {

        long count = 0;

        while (true) {

            if (!minPqInit.isEmpty()) {
                Node minInit = minPqInit.delMin();
                if (minInit.board.isGoal()) {
                    this.endNode = minInit;
                    return true;
                }
                fillNeigs(minPqInit, minInit);
            } else {
                this.endNode = null;
            }


            if (!minPqTwin.isEmpty()) {
                Node minTwin = minPqTwin.delMin();
                if (minTwin.board.isGoal()) {
                    this.endNode = minTwin;
                    return false;
                }
                fillNeigs(minPqTwin, minTwin);
            } else {
                this.endNode = null;
            }

            if (minPqInit.isEmpty() || minPqTwin.isEmpty()) {
                return false;
            }

        }
    }

    private void fillNeigs(MinPQ<Node> minPQ, Node node) {
        Iterable<Board> neigBoards = node.board.neighbors();

        for (Board neigBoard : neigBoards) {
            if (!wasVisited(node, neigBoard)) {
                minPQ.insert(new Node(node, neigBoard, node.moves + 1));
            }
        }
    }

    private boolean wasVisited(Node node, Board neigBoard) {

        if (node == null) {
            //StdOut.println("not visited");
            return false;
        } else {
            return neigBoard.equals(node.board) || wasVisited(node.prev, neigBoard);
        }
        //return node.prev == null || !neigBoard.equals(node.prev.board);
    }


    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {

        return this.solvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     *
     * @return
     */
    public int moves() {

        if (this.isSolvable()) {
            return this.endNode.moves;
        } else {
            return -1;
        }
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * <p/>
     * number of moves at least its priority, using either the Hamming or Manhattan priority function
     *
     * @return
     */
    public Iterable<Board> solution() {

        Stack<Board> solution = null;
        if (isSolvable()) {

            NodeIterable boards = new NodeIterable(this.endNode);
            Iterator<Board> itBoards = boards.iterator();
            Stack<Board> sBoard = new Stack<Board>();

            while (itBoards.hasNext()) {
                sBoard.push(itBoards.next());
            }

            solution = sBoard;
        }


        return solution;
    }

    /**
     * solve a slider puzzle (given below)
     *
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        SolverNovoFudido solver = new SolverNovoFudido(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


    private class NodeIterable implements Iterable<Board> {
        private Node node;

        public NodeIterable(Node node) {
            this.node = node;
        }

        @Override
        public Iterator<Board> iterator() {
            return new NodeIterator(this.node);
        }
    }


    private class NodeIterator implements Iterator<Board> {
        private Node node;

        public NodeIterator(Node node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null && node.board != null;
        }

        @Override
        public Board next() {
            Node currNode = node;

            this.node = node.prev;

            return currNode.board;
        }
    }

}