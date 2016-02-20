import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by dpacif1 on 1/25/16.
 */
public class Lab {

    public static void main (String []args){

        WeightedQuickUnionUF w = new WeightedQuickUnionUF(4*4);

        System.out.println(w.connected(5,5));

        w.union(5, 5);
        System.out.println(w.connected(5,5));


    }
}
