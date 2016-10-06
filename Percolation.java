/**
 * Algorithms Lab1 Percolation
 * @version 	1.01 2015_0223
 * @author 		darkpikachu
 * Compilation 	javac -cp .;stdlib.jar;algs4.jar Percolation.java
 * Execution 	java  -cp .;stdlib.jar;algs4.jar Percolation
 * Dependencies	StdIn.java, StdOut.java, WeightedQuickUnionUF.java, StdRandom.java
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private WeightedQuickUnionUF wquUF; 	// WeightedQuickUnionUF data structure
   private int givenN = 1;  			// N in Percolation constructor
   private boolean[] OpenList; 			// Boolean array to mark if element is open or not
   
   public Percolation(int N) 	// create N-by-N grid, with all sites blocked
   {
	   if (N<=0) throw new IllegalArgumentException("Illegal N");
	   givenN=N;
	   OpenList = new boolean[N*N+2];
	   OpenList[0] = true;		//Open Virtual Top
	   OpenList[N*N+1] = true; 	//Open Virtual Bottom
	   wquUF = new WeightedQuickUnionUF(N*N+2); 
	   /*StdOut.println("Count -> " + wquUF.count());
	   StdOut.print("Data  -> ");
	   for (int ii = 0; ii <= N*N+1; ii++)
		   StdOut.print(wquUF.find(ii)+" ");
	   StdOut.println("____");*/
    }
	
	private void Check(int row, int column) // Throws a IndexOutOfBoundsException 
	{										//if i or j is smaller than 0 or greater than grid size N
	    if (row <= 0 || row >= givenN+1)
			throw new IndexOutOfBoundsException("row index i out of bounds");
        if (column <= 0 || column >= givenN+1) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
	
	private int xyToId (int row, int column)
	{ return ((row-1)*givenN+column); }
	private boolean[] OpenListDisplay()
	{ return OpenList; }
	private WeightedQuickUnionUF wqDisp()
	{ return wquUF; }
	
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
	   Check(i, j);
	   OpenList[xyToId(i,j)]=true;
	   ConnectToNeighbour(i, j); 
   }
   
   private void ConnectToNeighbour(int i, int j)
   {
	   if(OpenList[xyToId(i,j)])
	   {
		   if (i==1) wquUF.union(xyToId(i,j),0);
		   if (i==givenN) wquUF.union(xyToId(i,j),givenN*givenN+1);
		   if(j<givenN && xyToId(i,j)>=1 && xyToId(i,j)<=(givenN*givenN-1)) //connect to right neighbour
		   {
			   if(OpenList[xyToId(i,j)+1])
			   wquUF.union(xyToId(i,j),xyToId(i,j)+1);
			}
		   if(j>1 && xyToId(i,j)>=2 && xyToId(i,j)<=(givenN*givenN)) 	//connect to left neighbour
		   {
			   if(OpenList[xyToId(i,j)-1])
				   wquUF.union(xyToId(i,j),xyToId(i,j)-1);
		   }
		   if(xyToId(i,j)>=givenN+1 && xyToId(i,j)<=(givenN*givenN)) //connect to top neighbour
		   {
			   if(OpenList[xyToId(i,j)-givenN])
				   wquUF.union(xyToId(i,j),xyToId(i,j)-givenN); 
		   }
		   if(xyToId(i,j)>=1 && xyToId(i,j)<=(givenN*givenN-givenN)) //connect to bottom neighbour
		   {
			   if(OpenList[xyToId(i,j)+givenN])
				   wquUF.union(xyToId(i,j),xyToId(i,j)+givenN);
		   }
		}
	}
   
      public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
	   Check(i, j);
	   if (OpenList[xyToId(i,j)])
		   return true;
	   else
		   return false;
   }
   
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
	   Check(i, j);
	   if(isOpen(i,j)&&wquUF.connected(xyToId(i,j), 0))
		   return true;
	   else
		   return false;
	   //if (i <= 0 || i >99) throw new IndexOutOfBoundsException("row index i out of bounds");
   }
   
   
   public boolean percolates()             // does the system percolate?
   {
	   if(wquUF.connected(0, givenN*givenN+1))
		   return true;
	   else
		   return false;
   }
   
   public static void main(String[] args)   // test client (optional)
   {
	   int m,k,a1,a2=1;
	   StdOut.print("Enter Matrix dimension, N= ");
	   m=StdIn.readInt();
	   Percolation Perc=new Percolation(m);
	   
	   for(int i=1; i<=m; i++)	//Display open/closed initial matrix
	   {
		   for(int j=1; j<=m; j++)
		   StdOut.print(Perc.OpenListDisplay()[Perc.xyToId(i,j)]+" ");
	    StdOut.println();
		}
		StdOut.println();
		
		StdOut.print("Enter Number of Random Opens, k= ");
	    k=StdIn.readInt();
		for(int ki=1; ki<=k; ki++) //Display random pairs and open
		{
		a1=StdRandom.uniform(1,m+1);
		a2=StdRandom.uniform(1,m+1);
		StdOut.print("("+a1+"; "+a2+") ");
		Perc.open(a1,a2);
		}
		StdOut.println();

		for(int i=1; i<=m; i++) //Display changed open/closed matrix
	   {
		   for(int j=1; j<=m; j++)
		   StdOut.print(Perc.OpenListDisplay()[Perc.xyToId(i,j)]+" ");
	    StdOut.println();
	   }
	   StdOut.println();
	   
	   for(int i=1; i<=m; i++) //Display changed open/closed matrix by isOpen method
	   {
		   for(int j=1; j<=m; j++)
		   StdOut.print(Perc.isOpen(i,j)+" ");
	    StdOut.println();
	   }
	   StdOut.println();
	   
	   StdOut.printf("%02d %n", Perc.wqDisp().find(0)); //Virtual Top Element
	   for(int i=1; i<=m; i++) // Display Dependency Matrix
	   {
		   for(int j=1; j<=m; j++)
		   StdOut.printf("%02d ", Perc.wqDisp().find(Perc.xyToId(i,j)));
		StdOut.println();
	    }
		StdOut.printf("%02d %n", Perc.wqDisp().find(m*m+1));//Virtual Bottom element
		StdOut.println(Perc.percolates());
	   /*StdOut.println();
	   for(int i=1; i<=m; i++)
	   {
		   for(int j=1; j<=m; j++)
		   StdOut.print(Perc.find(Perc.xyToId(i,j))+" ");
	    StdOut.println();
	   }  */
   }
}