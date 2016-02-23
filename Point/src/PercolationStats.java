import edu.princeton.cs.algs4.*;

import java.util.Arrays;

/**
 * Created by dpacif1 on 1/25/16.
 */
public class PercolationStats {

    private double[] percThresholds;
    private int n;
    private int t;
    private double interv = 1.96d;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int t){
        if (n <= 0) throw new java.lang.IllegalArgumentException("n should be >=0) :"+n);
        if (t <= 0) throw new java.lang.IllegalArgumentException("t should be >=0) :"+t);

        this.n=n;
        this.t=t;

        int totalSites = n*n;

        percThresholds = new double[t];

        for (int countTimes=0; countTimes<t; countTimes++){

            Percolation p = new Percolation(n);
            int openSites = 0;

            while(!p.percolates()){

                int i = (int)Math.ceil(StdRandom.uniform()*n);
                int j = (int)Math.ceil(StdRandom.uniform()*n);

                if (!p.isOpen(i,j)){ // avoid to count same site again
                    p.open(i,j);
                    openSites++;
                }
            }

            percThresholds[countTimes] = (double)openSites / (double)totalSites;
        }

        Arrays.sort(percThresholds);
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percThresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        return  mean() - ((interv*stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return  mean() + ((interv*stddev()) / Math.sqrt(t));
    }

    // test client (described below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n,t);

        System.out.println("mean                    = "+ps.mean());
        System.out.println("stddev                  = "+ps.stddev());
        System.out.println("95% confidence interval = "+ps.confidenceLo()+ ", "+ps.confidenceHi());
    }
}

