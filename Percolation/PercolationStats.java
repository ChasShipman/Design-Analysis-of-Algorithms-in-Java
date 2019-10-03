/*
 * Author: Chas Shipman
 * Date due: 6/23/18
 */
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats{
    private double mean;
    private double stdev;
    private double confLow;
    private double confHigh;
    private double percThreshold[];//holds results of each trial run
   
    /*
     *Constructor: performs independent percolation trials by opening random cells
     */
    public PercolationStats(int n, int trials){
        if (n <= 0) throw new IllegalArgumentException("n must be larger than 0");
        if (trials <= 0) throw new IllegalArgumentException("trials must be larger than 0");
        
        percThreshold = new double[trials];
        
        for(int i = 0; i < trials; i++){//run montecarlo trials
            int openCount = 0;
            Percolation newTrial = new Percolation(n);
            
            while(!newTrial.percolates()){//open random cells until system percolates
                int j = StdRandom.uniform(n);
                int k = StdRandom.uniform(n);
                
                if(!newTrial.isOpen(j,k)){
                    newTrial.open(j,k);
                    openCount++;
                }
            }
           double size = n*n;
           percThreshold[i] = openCount/size;//store fraction of sites that are opened when system percolates.
        }
        
        //solving statistics
        mean = StdStats.mean(percThreshold);
        stdev = StdStats.stddev(percThreshold);
        confLow = mean - (1.96 * stdev) / Math.sqrt(trials);
        confHigh = mean + (1.96 * stdev) / Math.sqrt(trials);
    }
    
    public double mean(){
        return mean;
    }
    
    public double stddev(){
        return stdev;
    }
    
    public double confidenceLow(){
        return confLow;
    }
    
    public double confidenceHigh(){
        return confHigh;
    }
    
    public static void main(String[] args){
        Stopwatch runTime = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats unitTest = new PercolationStats(n, trials);
        double time = runTime.elapsedTime();
        StdOut.printf("mean()           = %f\n", unitTest.mean());
        StdOut.printf("stddev()         = %f\n", unitTest.stddev());
        StdOut.printf("confidenceLow()  = %f\n", unitTest.confidenceLow());
        StdOut.printf("confidenceHigh() = %f\n", unitTest.confidenceHigh());
        StdOut.printf("elapsed time     = %.3f\n", time);
    }
}


