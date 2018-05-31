package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] fractionArray;
    private int num;

    public PercolationStats(int N, int T, PercolationFactory pf){
        num = T;
        fractionArray = new double[N];
        for (int i = 0; i < T; i++){
            Percolation perco = pf.make(N);
            while (!perco.percolates()){
                perco.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            fractionArray[i] = (double) perco.numberOfOpenSites() / (N * N);
        }
    }

    public double mean(){
        return StdStats.mean(fractionArray);
    }

    public double stddev(){
        return StdStats.stddev(fractionArray);
    }

    public double confidenceLow(){
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(num);
    }

    public double confidenceHigh(){
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(num);
    }
}
