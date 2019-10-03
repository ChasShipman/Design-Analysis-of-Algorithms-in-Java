/**
 * Author: Chas Shipman
 * Date due: 6/23/18
 */
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation{
    
    private int n;
    private int top; //imaginary top of grid
    private int bot; //imaginary bottom of grid
    private WeightedQuickUnionUF percComponents; //for connected component
    private WeightedQuickUnionUF fullComponents; //for full components
    //private QuickFindUF percComponents; //for connected component
    //private QuickFindUF fullComponents; //for full components
    private boolean[][] grid; //will be used for "openess." false= closed; true= open;
    private int openCounter;
    
    /*
     * Constuctor: create grid, initalizing to closed
     * minimizes need for 2D array by converting 2D grid to 1D array for openess
     */
    
    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException("n <= 0");
       
        this.n = n;
        int size = n * n + 2; //account for room for top and bottom
        percComponents = new WeightedQuickUnionUF(size);
        fullComponents = new WeightedQuickUnionUF(size - 1); //controls back wash. only need room for top
       // percComponents = new QuickFindUF(size);
        //fullComponents = new QuickFindUF(size - 1); //controls back wash. only need room for top
        grid = new boolean[n][n];
        
        top = size - 2;
        bot = size - 1;
      
        openCounter = 0;
    }
    
    /*
     * open: opens indicated cell at point (row, col)
     */
    
    public void open(int row, int col){
        exceptionThrow(row,col);
        
        int cell = to1D(row, col);          
        if(isOpen(row,col) == false){ //if cell is blocked, open it
            grid[row][col] = true;
            openCounter++;
            
            //declare cells adjacent to current cell
            int aboveCell = to1D(row-1, col);
            int belowCell = to1D(row+1, col);
            int leftCell = to1D(row, col-1);
            int rightCell = to1D(row, col+1);
            
            if (row == 0){//at top of grid, then bind to virtual top
                percComponents.union(cell,top);
                fullComponents.union(cell, top);
            }
            
            if (row == n-1){//at bottom of grid, then bind to virtual bottom
                percComponents.union(cell,bot);
            }
            
            if(row > 0 && isOpen(row -1, col)) {//if cell above is open, union
                percComponents.union(cell,aboveCell);
                fullComponents.union(cell,aboveCell);
            }
            
            if(row < n-1 && isOpen(row +1, col)){//if cell below is open, union
                percComponents.union(cell,belowCell);
                fullComponents.union(cell,belowCell);
            }
            
            if(col > 0 && isOpen(row,col-1)){//if cell to left is open, union
                percComponents.union(cell,leftCell);
                fullComponents.union(cell,leftCell);
            }
            
            if(col < n-1 && isOpen(row,col+1)){//if cell to right is open, union
                percComponents.union(cell,rightCell);
                fullComponents.union(cell,rightCell);
            }
        }
            else return;
        
    }
        
    public boolean isOpen(int row, int col){//returns true if open, false if blocked
        exceptionThrow(row, col);
        return grid[row][col];
    }
    
    public boolean isFull(int row, int col){//returns true if full, false if not
        exceptionThrow(row, col);
        return isOpen(row,col) && fullComponents.connected(top, to1D(row,col));
    }
    
    public int numberOfOpenSites(){//returns value of openCounter. is iterated each time open() is called and opens a cell
        return openCounter;
    }
    
    public boolean percolates(){//if top and bottom connected, returns true
        return percComponents.connected(top,bot);
    }
    
    public static void main(String[] args){//tests program
        Percolation unitTest = new Percolation(10);
        unitTest.open(0,5);
        unitTest.open(1,5);
        unitTest.open(2,5);
        unitTest.open(3,5);
        unitTest.open(4,5);
        unitTest.open(5,5);
        unitTest.open(6,5);
        unitTest.open(7,5);
        unitTest.open(8,5);
        unitTest.open(9,5);
  
        boolean five = unitTest.isFull(5,5);
        boolean perced = unitTest.percolates();
        boolean closed = unitTest.isOpen(5,4);
        int opened = unitTest.numberOfOpenSites();

        StdOut.println("(5,5) is full: true or false? " + five);
        StdOut.println("(5,4) is open: true or false? " + closed);
        StdOut.println("unitTest percolates: true or false? " + perced);
        StdOut.println("Open sites = 10?: " + opened);
    }
    
    private int to1D(int r, int c){//converts 2d to 1d
        return r * n + c;
    }
    
    private void exceptionThrow(int i, int j){//saves me from typing this multiple times
        if(i < 0 || i > n ||j < 0 || j > n )
           throw new IndexOutOfBoundsException(" out of bounds");
    }
    
}