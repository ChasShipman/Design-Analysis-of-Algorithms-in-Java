/*
 * Author: Chas Shipman
 * Project 2: Autocomplete me
 * Due: July 9th 
 */

import java.util.Comparator;

public class BinarySearchDeluxe{
    /*
     * Returns the index of the first key in a[] that equals the seach key, 
     * or -1 if no such key.
     */
    public static <Key> int firstIndexof(Key[] a, Key key, Comparator<Key> comparator){
        if(a==null || key == null || comparator == null) 
            throw new IllegalArgumentException("One or more args of firstIndexOf is null");
        
        if(a.length == 0) return -1; //if a is empty then the key cant exist anyway;
        if(comparator.compare(a[0], key) == 0) return 0; //saves time by not having to do the binary search just to reach the front of the array. 
        
        int foundKey = -1;
        int low = 0;
        int high = a.length -1;
        
        while(low <= high){//controlled adaptation of binary search. 
            int mid = low + (high - low) / 2;
            if(comparator.compare(key, a[mid]) == 0) {//if key and mid are same;
                foundKey = mid;
                high = mid - 1; 
            }
            else if(comparator.compare(key, a[mid]) < 0)//if key is less than mid;
                   high = mid - 1;
            else low = mid + 1; //if key is greater than mid;
        }
        
        return foundKey;
    }
   
    /*
     * Returns the index of the last key in a[] that equals the seach key, 
     * or -1 if no such key.
     */

    public static <Key> int lastIndexof(Key[] a, Key key, Comparator<Key> comparator){
         if(a==null || key == null || comparator == null) 
            throw new IllegalArgumentException("One or more args of firstIndexOf is null");
         
         if(a.length == 0) return -1; //if a is empty then the key cant exist anyway;
         if(comparator.compare(a[0], key) == 0) return 0; //saves time by not having to do the binary search just to reach the end of the array 
    
         int foundKey = -1;
        int low = 0;
        int high = a.length -1;
        
        while(low <= high){//controlled adaptation of binary search. 
            int mid = low + (high - low) / 2;
            if(comparator.compare(key, a[mid]) == 0) {//if key and mid are same;
                foundKey = mid;
                low = mid + 1; 
            }
            else if(comparator.compare(key, a[mid]) > 0)//if key is greater than mid;
                   low = mid + 1;
            else high = mid - 1; //if key is less than than mid;
        }
        
        return foundKey;
    }
    
    /*
     * unit testing (required)
     */
     public static void main(String[] args){
         
     }
    
}