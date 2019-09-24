/*
 * Author: Chas Shipman
 * Project 2: Autocomplete me
 * Due: July 9th 
 */

import java.util.Comparator;
import edu.princeton.cs.algs4.MergeX;

public class Term implements Comparable<Term> {
    private String query;
    private long weight;
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight){
        if(query == null || weight < 0)
            throw new IllegalArgumentException("query is null or weight < 0");
        
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder(){
        return new reversedComparatorLogic();
    }

    // Compares the two terms in lexicographic order but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r){
        if(r < 0) 
            throw new IllegalArgumentException("r must be non-negative int");
        return new substringPrefixesComparator(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that){
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString(){
        return this.weight + "\t" + this.query;
    }
    
    private static class reversedComparatorLogic implements Comparator<Term>{
        //overide the default comparator class
        //needs to reverse the logic.
        @Override
        public int compare(Term a, Term b){
            if(a.weight == b.weight) return 0;
            else if(a.weight > b.weight) return -1; //reverse logic
            else return 1;
        }
    }
    
    private static class substringPrefixesComparator implements Comparator<Term> {
        //need to overide default comparator
        //needs to be able to compare strings using compareTo method.
        private int r; 
        private substringPrefixesComparator(int r){
            this.r = r;
        }
        @Override
        public int compare(Term a, Term b){
            String asubstring;
            String bsubstring;
            
            //populate substrings
            //if Term a's length is less than r
            //just set the substring to a, else from 0 to r
            if(a.query.length() < r)                                     
                asubstring = a.query;
            else asubstring = a.query.substring(0,r);
            
            //if Term b's length is less than r
            //just set the substring to b, else from 0 to r
             if(b.query.length() < r) 
                bsubstring = b.query;
            else bsubstring = b.query.substring(0,r);
            
            return asubstring.compareTo(bsubstring);
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Term[] terms = {new Term("Goku", 10000), new Term("Vegeta", 8000), new Term("Trunks", 8500), new Term("Gohan", 9001), new Term("Goten", 8000), new Term("GogetaUI", 999)};
        for (Term term : terms) System.out.println(term);
        System.out.println("Finished printing base term array" + "\n");
  
        MergeX.sort(terms, Term.byReverseWeightOrder());
        for (Term term : terms) System.out.println(term);
        System.out.println("Finished printing term array sorted by reverse weight" + "\n");
  
        MergeX.sort(terms, Term.byPrefixOrder(2));
        for (Term term : terms) System.out.println(term);
        System.out.println("Finished printing term array by first 2 Characters" + "\n");
  
        MergeX.sort(terms);
        for (Term term : terms) System.out.println(term);
        System.out.println("Finished printing term array after mergesort");
    }
}