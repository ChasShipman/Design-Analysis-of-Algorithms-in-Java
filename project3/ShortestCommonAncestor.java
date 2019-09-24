import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {

    private Digraph digraph;
    
   // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G){
        if (G == null) throw new NullPointerException("G is null");
        
        digraph = new Digraph(G);
    }

   // length of shortest ancestral path between v and w
    public int length(int v, int w){
        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V()) throw new IndexOutOfBoundsException("v or w is null");
        BreadthFirstDirectedPaths ssdPathV = new BreadthFirstDirectedPaths(digraph, v);//find all paths to other vertices from v
        BreadthFirstDirectedPaths ssdPathW = new BreadthFirstDirectedPaths(digraph, w);//find all paths to other vertices from w
        
        return lengthAncestorHelper("length", ssdPathV, ssdPathW); //helper function takes command as 1st arg to control output

    }

   // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w){ 
        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V()) throw new IndexOutOfBoundsException("v or w is null");
       
        BreadthFirstDirectedPaths ssdPathV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths ssdPathW = new BreadthFirstDirectedPaths(digraph, w);
      
        return lengthAncestorHelper("ancestor", ssdPathV, ssdPathW);
       
    }

   // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        for(int i : subsetA){
             if (i < 0 || i >= digraph.V()) throw new IndexOutOfBoundsException("part of subsetA is out of boundsl");
        }
        for(int i : subsetB){
             if (i < 0 || i >= digraph.V()) throw new IndexOutOfBoundsException("part of subsetA is out of boundsl");
        }
        BreadthFirstDirectedPaths subsetPathA = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths subsetPathB = new BreadthFirstDirectedPaths(digraph, subsetB);
        
        return lengthAncestorHelper("length", subsetPathA, subsetPathB);
    
    }

   // a shortest common ancestor of vertex subsets A and B
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
         for(int i : subsetA){
             if (i < 0 || i >= digraph.V()) throw new IndexOutOfBoundsException("part of subsetA is out of boundsl");
        }
        for(int i : subsetB){
             if (i < 0 || i >= digraph.V()) throw new IndexOutOfBoundsException("part of subsetA is out of boundsl");
        }
        
        BreadthFirstDirectedPaths subsetPathA = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths subsetPathB = new BreadthFirstDirectedPaths(digraph, subsetB);
        
        return lengthAncestorHelper("ancestor", subsetPathA, subsetPathB);
       
    }
    
    private int lengthAncestorHelper(String command, BreadthFirstDirectedPaths pathA, BreadthFirstDirectedPaths pathB){
        
        if(command == "length"){//this conditional handles finds shortest ancestral path length
            int pathLength = Integer.MAX_VALUE;
            for(int i=0; i< digraph.V(); i++){
                if(pathA.hasPathTo(i) && pathB.hasPathTo(i)){//means they have an ancestor 
                    int lengthA = pathA.distTo(i); int lengthB = pathB.distTo(i);
                    int lengthAB = lengthA + lengthB;
                    if(lengthAB < pathLength){//check if combined distances are less than previous found combined distances (i.e. a shorter path)
                    pathLength = lengthAB ;
                    }
                }
            }
        
        if(pathLength == Integer.MAX_VALUE) return -1;
        return pathLength;
            
        }
       
        else{//command="ancestor"//this conditional handles find ancestor
            int minimum = Integer.MAX_VALUE;
            int ancestor = -1;
        
            for(int i = 0; i < digraph.V(); i++){
                if(pathA.hasPathTo(i) && pathB.hasPathTo(i)){//means they have an ancestor 
                    int lengthAB = pathA.distTo(i) + pathB.distTo(i);
                    if(lengthAB < minimum){//check if combined distances are less than previous found combined distances (i.e. a shorter path)
                        minimum = lengthAB;
                        ancestor = i; //update ancestor to found ancestor
                    }
                }
            }
            return ancestor;  
        }
}
    
   // do unit testing of this class
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
       
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}