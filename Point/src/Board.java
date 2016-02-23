import edu.princeton.cs.algs4.LinkedBag;

public class Board {

    private final int[][] blocksx;

    //private Tuple2 zeroRC;

    private int hamming = 0;
    private int manhattan = 0;

    /**
     * construct a board from an N-by-N array
     * of blocks (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] pblocks) {
        this(pblocks, null);
    }

    private Board(int[][] pblocks, Tuple2 zeroRC) {
        if (pblocks == null) throw new java.lang.NullPointerException("null block array");

        //this.zeroRC = zeroRC;

        int[][] localBlocks = deepCopy(pblocks);


        //this.blocks = blocks;
        this.blocksx = localBlocks;
    }

    /**
     * board dimension N
     */
    public int dimension() {
        return blocksx.length;
    }

    /**
     * number of blocks out of place
     * 0 or 1
     */
    public int hamming() {

        if (this.hamming == 0) {
            for (int r = 0; r < blocksx.length; r++) {
                for (int c = 0; c < blocksx[r].length; c++) {

                    int dimension = this.dimension();

                    int expected = ((r * blocksx.length) + c + 1) % (dimension * dimension);

                    if (blocksx[r][c] != 0 && blocksx[r][c] != expected) {
                        this.hamming++;
                    }
                }
            }
        }

        return this.hamming;

    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {


        if (this.manhattan == 0) {
            for (int r = 0; r < blocksx.length; r++) {
                for (int c = 0; c < blocksx[r].length; c++) {
                    int dimension = this.dimension();
                    int expected = ((r * blocksx.length) + c + 1) % (dimension * dimension);

                    // sums out of place blocks only if current cell is not the last one
                    if (blocksx[r][c] != 0 && blocksx[r][c] != expected) {

                        Tuple2 tEncontrado = calculateExpectedRC(blocksx[r][c], this.dimension());

                        int distance = calcDistance(tEncontrado, new Tuple2(r, c));

                        this.manhattan = this.manhattan + distance;
                    }
                }
            }
        }


        return this.manhattan;
    }

    private int calcDistance(Tuple2 expTup, Tuple2 currTup) {
        return Math.abs(expTup.getC() - currTup.getC()) + Math.abs(expTup.getR() - currTup.getR());

    }

    private Tuple2 calculateExpectedRC(int expected, int dimension) {
        int r = (expected - 1) / dimension;
        int c = (expected - 1) % dimension;

        return new Tuple2(r, c);
    }

    /**
     * is this board the goal board?
     * public boolean isGoal() {
     * return hamming()-this.moves == 0;
     * }
     */
    public boolean isGoal() {

        //if (this.isGoal == null) {

        //  this.isGoal = true;
        for (int r = 0; r < blocksx.length; r++) {
            for (int c = 0; c < blocksx[r].length; c++) {

                int dimension = this.dimension();

                int expected = ((r * blocksx.length) + c + 1) % (dimension * dimension);

                // sums out of place blocks only if current cell is not the last one
                if (expected != 0 && blocksx[r][c] != expected) {

                    //this.isGoal = false;
                    return false;
                }
            }
        }
        //}

        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {

        int[][] newBlocks = deepCopy(this.blocksx);

        Tuple4 t4 = nonZeroIndex(newBlocks);

        int swap = newBlocks[t4.getR1()][t4.getC1()];
        newBlocks[t4.getR1()][t4.getC1()] = newBlocks[t4.getR2()][t4.getC2()];
        newBlocks[t4.getR2()][t4.getC2()] = swap;

        return new Board(newBlocks, null);
    }


    /**
     * Randomly select 2 non zero value different cells to swap
     *
     * @param blocks
     * @return
     */
    private Tuple4 nonZeroIndex(int[][] blocks) {
        /*
        int attRow = 0;
        if (blocks[attRow][1] == 0 || blocks[attRow][0] == 0) {
            attRow = 1;
        }
        return new Tuple4(attRow, 1, attRow, 0);
         */

        if (blocks[0][0] * blocks[0][1] == 0) {
            return new Tuple4(1, 0, 1, 1);
        } else {
            return new Tuple4(0, 0, 0, 1);
        }

    }


    /**
     * tuple class
     */
    private class Tuple4 {
        private int r1;
        private int c1;
        private int r2;
        private int c2;

        public Tuple4(int r1, int c1, int r2, int c2) {
            this.setR1(r1);
            this.setC1(c1);
            this.setR2(r2);
            this.setC2(c2);
        }

        public int getR1() {
            return r1;
        }

        public void setR1(int r1) {
            this.r1 = r1;
        }

        public int getC1() {
            return c1;
        }

        public void setC1(int c1) {
            this.c1 = c1;
        }

        public int getR2() {
            return r2;
        }

        public void setR2(int r2) {
            this.r2 = r2;
        }

        public int getC2() {
            return c2;
        }

        public void setC2(int c2) {
            this.c2 = c2;
        }
    }

    private class Tuple2 {
        private int r;
        private int c;

        public Tuple2(int r, int c) {
            this.setR(r);
            this.setC(c);
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }
    }

    /**
     * does this board equal y?
     */
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (this.getClass() != that.getClass()) return false;

        Board thatBoard = (Board) that;

        if (this.blocksx.length != ((Board) that).blocksx.length) {
            return false;
        }

        for (int r = 0; r < thatBoard.blocksx.length; r++) {
            if (this.blocksx[r].length != thatBoard.blocksx[r].length) {
                return false;
            }

            for (int c = 0; c < thatBoard.blocksx[r].length; c++) {
                if (this.blocksx[r][c] != thatBoard.blocksx[r][c]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {

        Tuple2 cellZero = findZeroRC(this.blocksx);

        LinkedBag<Tuple2> neigCells = findNeigCells(this.blocksx, cellZero);
        LinkedBag<Board> neibBoards = new LinkedBag<Board>();

        assert !neigCells.isEmpty();

        for (Tuple2 neigCell : neigCells) {

            int[][] pBlocks = deepCopy(this.blocksx);

            neibBoards.add(createNeigBoard(pBlocks, cellZero, neigCell));
        }

        assert !neibBoards.isEmpty();
        return neibBoards;
    }

    private int[][] deepCopy(int[][] pblocks) {
        int[][] newBlock = new int[pblocks.length][pblocks.length];

        for (int r = 0; r < pblocks.length; r++) {
            for (int c = 0; c < pblocks[r].length; c++) {
                newBlock[r][c] = pblocks[r][c];
            }
        }

        return newBlock;
    }

    /**
     * make the expected cell movement and create a new board state
     *
     * @param pBlocks
     * @param cellZero
     * @param neigCell
     * @return
     */
    private Board createNeigBoard(int[][] pBlocks, Tuple2 cellZero, Tuple2 neigCell) {
        assert pBlocks[neigCell.getR()][neigCell.getC()] != 0;
        assert pBlocks[cellZero.getR()][cellZero.getC()] == 0;

        int zeroCell = pBlocks[cellZero.getR()][cellZero.getC()];
        pBlocks[cellZero.getR()][cellZero.getC()] = pBlocks[neigCell.getR()][neigCell.getC()];
        pBlocks[neigCell.getR()][neigCell.getC()] = zeroCell;

        Board board = new Board(pBlocks, neigCell);

        return board;
    }


    /**
     * @param blocks
     * @param rc
     * @return
     */
    private LinkedBag<Tuple2> findNeigCells(int[][] blocks, Tuple2 rc) {

        LinkedBag<Tuple2> neigs = new LinkedBag<Tuple2>();

        if (rc.getR() != 0) { // it's a first row
            neigs.add(new Tuple2(rc.getR() - 1, rc.getC()));
        }

        if (rc.getR() != this.dimension() - 1) { // it's a first row
            neigs.add(new Tuple2(rc.getR() + 1, rc.getC()));
        }

        if (rc.getC() != 0) { // it's a first row
            neigs.add(new Tuple2(rc.getR(), rc.getC() - 1));
        }

        if (rc.getC() != this.dimension() - 1) { // it's a first row
            neigs.add(new Tuple2(rc.getR(), rc.getC() + 1));
        }

        return neigs;
    }

    /**
     * search 0 rc
     *
     * @param blocks
     * @return
     */
    private Tuple2 findZeroRC(int[][] blocks) {

        //if (this.zeroRC != null)
        //  return this.zeroRC;

        for (int r = 0; r < blocks.length; r++) {
            for (int c = 0; c < blocks[r].length; c++) {
                if (blocks[r][c] == 0) {
                    return new Tuple2(r, c);
                }
            }
        }

        throw new NullPointerException("0 value cell not found!");
    }


    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(this.dimension() + "\n");
        for (int[] row : this.blocksx) {
            for (int col : row) {
                buffer.append(String.format("%2d ", col));
            }
            buffer.append("\n");
        }

        return buffer.toString();
    }

    /**
     * unit tests (not graded)
     */
    public static void main(String[] args) {

    }
}
