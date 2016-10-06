/**
 * Algorithms Lab1 PercolationStats
 * @version 	1.01 2015_0225
 * @author 		darkpikachu
 * Compilation 	javac -cp .;stdlib.jar;algs4.jar PercolationStats.java
 * Execution 	java  -cp .;stdlib.jar;algs4.jar PercolationStats
 * Dependencies	Percolation.java, StdIn.java, StdOut.java, WeightedQuickUnionUF.java, StdRandom.java
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
   private double[] prob;
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
	    prob = new double[T];
		if (N<=0||T<=0) throw new IllegalArgumentException("Illegal N");
		
		for(int ki=1; ki<=T; ki++) //Display random pairs and open
		{
			Percolation P = new Percolation(N);
			int a1,a2;
			int c=1;
			while(!(P.percolates()))
			{
				do 
				{
					a1=StdRandom.uniform(1,N+1);
					a2=StdRandom.uniform(1,N+1);
				} while (P.isOpen(a1,a2));
				P.open(a1,a2);
				c++;
			}
			prob[ki-1]=((double)c-1)/(N*N);
		}	
   }
   public double mean()                      // sample mean of percolation threshold
   {
	   return StdStats.mean(prob);
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
	   return StdStats.stddev(prob);
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
	   return (mean()-1.96*stddev()/Math.pow(prob.length,0.5));
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
	   return (mean()+1.96*stddev()/Math.pow(prob.length,0.5));
   }

   public static void main(String[] args)    // test client (described below)
   {
	   //StdOut.print("Enter N & T ");
	   int N=StdIn.readInt();
	   int T=StdIn.readInt();
	   StdOut.println("(N="+N+"; T="+T+")");
	   PercolationStats PS=new PercolationStats(N,T);
	   StdOut.printf("Mean                   = %.12f %n",PS.mean());
	   StdOut.printf("stddev                 = %.12f %n",PS.stddev());
	   StdOut.printf("95 confidence interval = %.12f, %.12f %n",PS.confidenceLo(),PS.confidenceHi());	   
   }
}