import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private final int trials;

    //对n-by-n网格进行trials次独立试验
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }
        this.trials = trials;
        this.thresholds = new double[trials];
        for(int t = 0; t < trials; t++){
            Percolation perc = new Percolation(n);
            while (!perc.percolates()){
                int row, col;
                do{
                    row = StdRandom.uniformInt(1,n+1);
                    col = StdRandom.uniformInt(1,n+1);
                }while (perc.isOpen(row, col));
                perc.open(row, col);
            }
            thresholds[t] = (double)perc.numberOfOpenSites() / (n * n);
        }
    }

    //样本均值：渗流临界值
    public double mean(){
        return StdStats.mean(thresholds);
    }

    //样本标准差：渗流临界值
    public double stddev(){
        return StdStats.stddev(thresholds);
    }

    //95%置信度区间下界
    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }
    
    //95%置信度区间上界
    public double confidenceHi(){
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    //test client
    public static void main(String[] args){
        if (args.length < 2){
            throw new IllegalArgumentException("Please provide n and trials as arguments");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}