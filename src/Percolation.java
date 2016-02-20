import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation implementation, using Weighted Quick Union UF class
 *
 * Created by dpacif1 on 1/24/16.
 */
public class Percolation {
    /**
     * Size of one grid dimension (rows and columns)
     */
    private int n;

    /**
     * implementation of Weighted Quick Union UF
     */
    private WeightedQuickUnionUF wqf;

    /**
     * site states n*n (open/closed)
     */
    private boolean[][] siteState;

    /**
     * virtual site A
     */
    private int vSiteA;

    /**
     * virtual site B
     */
    private int vSiteB;

    /**
     * Create N-by-N grid, with all sites blocked
     *
     */
    public Percolation(int n){
        if(n<=0){
            throw new IllegalArgumentException ("n should be greater than 0");
        }

        this.n = n;

        int sites = n*n;
        wqf = new WeightedQuickUnionUF(sites +2); // two more virtual sites

        this.vSiteA = sites;
        this.vSiteB = sites+1;

        //connect virtual sites to first and last rows
        for (int i=0; i<n; i++){
            int stColLastRow = n*(n-1);
            wqf.union(vSiteA, i);                  // unite first row to virtual site A
            wqf.union(vSiteB, stColLastRow + i);   // unite last row to virtual site B
        }
        siteState = new boolean[n][n];
    }

    /**
     * Open site (row i, column j) if it is not open already
     * @param i
     * @param j
     * @return
     */
    public void open(int i, int j){
        if(i<0 || i>n){throw new java.lang.IndexOutOfBoundsException("i should be between 0 and n");}
        if(j<0 || j>n){throw new java.lang.IndexOutOfBoundsException("j should be between 0 and n");}

        int row = i-1;
        int col = j-1;

        //mark site as open
        siteState[row][col] = true;

        //convert row/col to a single array index
        int openSite = toIndex(row, col);

        //check for adjacent sites in cross pattern
        if (row>0     && siteState[row-1][col]) wqf.union(openSite, toIndex(row-1,col));
        if (row<(n-1) && siteState[row+1][col]) wqf.union(openSite, toIndex(row+1,col));
        if (col>0     && siteState[row][col-1]) wqf.union(openSite, toIndex(row,col-1));
        if (col<(n-1) && siteState[row][col+1]) wqf.union(openSite, toIndex(row,col+1));
        //int index = convertToArrIndexModel(i,j);
    }

    /**
     * Convert a row/col coordinate to array index, compatible with WeightedQuickUnionUF class
     * @param row
     * @param col
     * @return array index
     */
    private int toIndex(int row, int col) {
        return (row * n) + col;
    }

    /**
     * Is site (row i, column j) open?
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j) {
        if(i<0 || i>n){throw new java.lang.IndexOutOfBoundsException("i should be between 0 and n");}
        if(j<0 || j>n){throw new java.lang.IndexOutOfBoundsException("j should be between 0 and n");}

        return siteState[i-1][j-1];
    }

    /**
     * Is site (row i, column j) full?
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j){
        if(i<0 || i>n){throw new java.lang.IndexOutOfBoundsException("i should be between 0 and n");}
        if(j<0 || j>n){throw new java.lang.IndexOutOfBoundsException("j should be between 0 and n");}

        int row = i-1;
        int col = j-1;

        return isOpen(i,j) && wqf.connected(vSiteA, toIndex(row,col));
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates(){
        return wqf.connected(vSiteA,vSiteB);
    }

    /**
     * test client (optional)
     * @return
     */
    public static void main(String[] args){

    }
}