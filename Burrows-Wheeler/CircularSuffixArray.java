/**
 * Author: Chas Shipman
 * Date due: 8/08/18
 * 
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
    
public class CircularSuffixArray {
    private int length;
    private int[] index;
    
    
    private class CircularSuffix implements Comparable<CircularSuffix>{ //private class to implement compareTo method
        private int charShift;
        private String suffixString;
        
        public CircularSuffix(int charShift, String suffixString){
            this.charShift = charShift;
            this.suffixString = suffixString;
        }
       
        public int compareTo(CircularSuffix compareString){ //new compareTo method
            if (this == compareString) //if same suffix, return 0;
                return 0;
            
            for(int i = 0; i < length; i++){ //find/sort by character in suffix array at ith index
                if(suffixString.charAt((this.charShift + i) % suffixString.length()) 
                       > 
                   compareString.getString().charAt((compareString.charShift + i) % suffixString.length()))
                    return 1;              
               
                if(suffixString.charAt((this.charShift + i) % suffixString.length()) 
                       < 
                   compareString.getString().charAt((compareString.charShift + i) % suffixString.length()))
                    return -1;
            }
            return 0;
        }
        
        private String getString(){ //return String. used to compare suffix string indeces.
            return suffixString;
        }
    }
      
    public CircularSuffixArray(String s){    // circular suffix array of s
        if(s == null) throw new java.lang.IllegalArgumentException("s can not be null");
        
        this.length = s.length();
        this.index = new int[length];
        
        CircularSuffix[] CSArray = new CircularSuffix[length];
        
        for(int i = 0; i < length; i++){
            CSArray[i] = new CircularSuffix(i, s); //populate suffix array 
        }
       
        Arrays.sort(CSArray); //sort suffix array
        for( int i = 0; i < length; i++){
            index[i] = CSArray[i].charShift 
;
        }
    } 
    
    public int length(){                     // length of s
        return length;
    }
    
    public int index(int i){                 // returns index of ith sorted suffix
        if(i < 0 || i > length - 1) throw new java.lang.IllegalArgumentException("i out of bounds");
        return index[i];
    }
    
    public static void main(String[] args){  // unit testing (required)
        /*
         *suffix array           sorted suffix array      index array
         *
         * s[0] = WEEKEND        s[0] = DWEEKEN           index[0] = 6
         * s[1] = EEKENDW        s[1] = EEKENDW           index[1] = 2
         * s[2] = EKENDWE        s[2] = EKENDWE           index[2] = 4
         * s[3] = KENDWEE        s[3] = ENDWEEK           index[3] = 5
         * s[4] = ENDWEEK        s[4] = KENDWEE           index[4] = 3
         * s[5] = NDWEEKE        s[5] = NDWEEKE           index[5] = 0
         * s[6] = DWEEKEN        s[6] = WEEKEND           index[6] = 1
         * 
         */
          
        CircularSuffixArray csa = new CircularSuffixArray("NWEKEED");
        for(int i = 0; i < csa.length; i++)
            StdOut.println(csa.index(i)); //should print index array values
        StdOut.println("");
        StdOut.println("Length of csa is: " + csa.length()); //should print 7
    }
    
}
