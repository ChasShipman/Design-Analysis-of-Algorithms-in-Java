import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.In;

public class WordNet {
    
    SeparateChainingHashST<String, Bag<Integer>> wordIntHash;//hash symbol table to hash words into integer keys 
    SeparateChainingHashST<Integer, String> intWordHash; //hash symbol table to decode hash keys into words
    Digraph digraph;
    ShortestCommonAncestor sac;
    
   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null)
            throw new NullPointerException("no nulls");
        
        wordIntHash = new SeparateChainingHashST<>();
        intWordHash = new SeparateChainingHashST<>();
        
        int sizeV = readSyns(synsets); //readSyns reads in synsets while also returning a size for the digraph
        digraph = new Digraph(sizeV);
        
        readHyps(hypernyms);//reads in hypernyms
        sac = new ShortestCommonAncestor(digraph);
    }

   // all WordNet nouns
    public Iterable<String> nouns(){
       return wordIntHash.keys();
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null) throw new NullPointerException("no nulls");
        return wordIntHash.contains(word);
    }

   // a synset (second field of synsets.txt) that is a shortest common ancestor
   // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2){
        if(noun1 == null || noun2 == null) throw new NullPointerException("no nulls");
        if(wordIntHash.get(noun1) == null || wordIntHash.get(noun2) == null) throw new IllegalArgumentException("nouns not in wordnet");
      Iterable<Integer> Noun1 = wordIntHash.get(noun1);
      Iterable<Integer> Noun2 = wordIntHash.get(noun2);
      
      return intWordHash.get(sac.ancestor(Noun1, Noun2));
    }

   // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2){
         if(noun1 == null || noun2 == null) throw new NullPointerException("no nulls");
         if(wordIntHash.get(noun1) == null || wordIntHash.get(noun2) == null) throw new IllegalArgumentException("nouns not in wordnet");
      Iterable<Integer> Noun1 = wordIntHash.get(noun1);
      Iterable<Integer> Noun2 = wordIntHash.get(noun2);
      
      return sac.length(Noun1, Noun2);
    }
    
    /*readSyns reads in synsets to symbol table
     * 
     */
    private int readSyns(String synsets){
        In syns = new In(synsets);
        int synsetID = 0;
        
        while(syns.hasNextLine()){
            String nextSyn = syns.readLine();//reads line of file
            String[] token = nextSyn.split(","); //splits line into array 
            synsetID = Integer.parseInt(token[0]);//stores id/key
            String[] words = token[1].split(" ");//stores synset words
            intWordHash.put(synsetID, token[1]); //populate decoding symbol table
            
            for (String string : words){ //populate hash table
                if(wordIntHash.get(string) == null){//if word is not in table
                    wordIntHash.put(string, new Bag<Integer>());
                }
                wordIntHash.get(string).add(synsetID);
            }
        }
        int size = synsetID + 1;
        return size;
    }

    
    private void readHyps(String hypernyms){
        In hyps = new In(hypernyms);
        while(hyps.hasNextLine()){
            String nextHyp = hyps.readLine();
            String[] token = nextHyp.split(",");
            int hypernymID = Integer.parseInt(token[0]);
            for(int i = 1; i < token.length; i++)
                digraph.addEdge(hypernymID, Integer.parseInt(token[i]));
        }

    }
        
    

   // do unit testing of this class
   public static void main(String[] args) {
   
    }
}

