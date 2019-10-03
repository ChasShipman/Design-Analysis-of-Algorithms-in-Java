/*
 * Author: Chas Shipman
 * Project 2: Autocomplete me
 * Due: July 9th 
 */

import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Autocomplete {
    private Term[] terms;
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms){
        if(terms == null) 
            throw new IllegalArgumentException("Term[] can not be null");
        for(int i = 0; i < terms.length; i++){
            if(terms[i] == null) throw new IllegalArgumentException("No index entry can be null");
        }
        
            this.terms = terms;
        
        MergeX.sort(terms); //worse case nlogn time.
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix){
         if(prefix == null) 
            throw new IllegalArgumentException("prefix can not be null");
         
         int firstIndex = BinarySearchDeluxe.firstIndexof(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())); //find first instance. worse case n time
         int lastIndex = BinarySearchDeluxe.lastIndexof(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())); //find last instance. worse case n time 
         
         if(firstIndex == -1 || lastIndex == -1) return new Term[0]; //if no such key exists return empty array.
         
         Term[] matchedTerms = new Term[numberOfMatches(prefix)]; //populate Term array from first found instance to last found instance. worse case n time because calling numberOfMatches.      
         for (int i = 0; i < matchedTerms.length; i++){
             matchedTerms[i] = terms[firstIndex++];
         }                                    
           
         MergeX.sort(matchedTerms, Term.byReverseWeightOrder()); //sort by reverse weight. worse case nlogn time
         return matchedTerms;
         //n +n + n + nlogn = 3n + nlogn = nlogn time
         
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix){
         if(prefix == null) 
            throw new IllegalArgumentException("prefix can not be null");
         
         int firstIndex = BinarySearchDeluxe.firstIndexof(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())); //find first index. worse case n time
         int lastIndex = BinarySearchDeluxe.lastIndexof(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));   //find last index. worse case n time
         if(firstIndex == -1 || lastIndex == -1) return 0;
         return lastIndex - firstIndex + 1; //need to add 1 for inclusivity.
         //n + n = 2n = n time
    }

    // unit testing (required)
    public static void main(String[] args){ //THIS WAS TAKEN FROM THE SPECS
    // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

    // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
            StdOut.println(results[i]);
        }
    }
}