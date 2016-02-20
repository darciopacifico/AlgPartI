import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {

    private Board initial;
    private Board twin;
    private Node endNode;

    private boolean solvable;

    /**
     * Hamming comparator
     */
    private class HammingBoardComparator implements java.util.Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {

            int diff = (o1.getBoard().hamming() + o1.getMoves()) - (o2.getBoard().hamming() + o2.getMoves());

            return diff;
        }
    }

    /**
     * Manhattan comparator
     */
    private class ManhattanBoardComparator implements java.util.Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            int diff = (o1.getBoard().manhattan() + o1.getMoves()) - (o2.getBoard().manhattan() + o2.getMoves());
            return diff;
        }
    }

    private class Node {
        private Node prev;
        private Board board;
        private int moves;

        public Node(Node prev, Board board, int moves) {
            this.setPrev(prev);
            this.setBoard(board);
            this.setMoves(moves);
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Board getBoard() {
            return board;
        }

        public void setBoard(Board board) {
            this.board = board;
        }

        public int getMoves() {
            return moves;
        }

        public void setMoves(int moves) {
            this.moves = moves;
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param board
     */
    public Solver(Board board) {

        if (board == null) throw new java.lang.NullPointerException("null Board");

        this.initial = board;
        this.twin = initial.twin();


        Node tup1 = new Node(new Node(null, null, 0), this.initial, 0);
        MinPQ<Node> minPqInit = new MinPQ<Node>(512, new ManhattanBoardComparator());
        minPqInit.insert(tup1);

        MinPQ<Node> minPqTwin = new MinPQ<Node>(512, new ManhattanBoardComparator());
        //MinPQ<Node> minPqTwin = minPqInit;
        Node tup2 = new Node(new Node(null, null, 0), this.twin, 0);
        minPqTwin.insert(tup2);

        this.solvable = solver(minPqInit, minPqTwin);
    }


    private boolean solver(MinPQ<Node> minPqInit, MinPQ<Node> minPqTwin) {

        long count = 0;

        while (true) {

            if (!minPqInit.isEmpty()) {
                Node minInit = minPqInit.delMin();
                if (minInit.getBoard().isGoal()) {
                    this.endNode = minInit;
                    return true;
                }
                fillNeigs(minPqInit, minInit);
            } else {
                this.endNode = null;
            }


            if (!minPqTwin.isEmpty()) {
                Node minTwin = minPqTwin.delMin();
                if (minTwin.getBoard().isGoal()) {
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
        Iterable<Board> neigBoards = node.getBoard().neighbors();

        for (Board neigBoard : neigBoards) {
            if (!wasVisited(node, neigBoard)) {
                minPQ.insert(new Node(node, neigBoard, node.getMoves() + 1));
            }
        }
    }

    private boolean wasVisited(Node node, Board neigBoard) {
        if (node == null) {
            return false;
        } else {
            return neigBoard.equals(node.getBoard()) || wasVisited(node.getPrev(), neigBoard);
        }
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
            return this.endNode.getMoves();
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
        Solver solver = new Solver(initial);

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
            return node != null && node.getBoard() != null;
        }

        @Override
        public Board next() {
            Node currNode = node;
            this.node = node.getPrev();
            return currNode.getBoard();
        }
    }

}