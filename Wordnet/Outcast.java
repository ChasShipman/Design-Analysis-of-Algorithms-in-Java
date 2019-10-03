import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    
    private WordNet wordNet;
    
    public Outcast(WordNet wordnet){         // constructor takes a WordNet object
        this.wordNet = wordnet;
    }
    
    public String outcast(String[] nouns){   // given an array of WordNet nouns, return an outcast
        String outcast = null;
        int maxdist = 0;
        
        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;
            for (int j = 0; j < nouns.length; j++) {//trying to find furthest synset distance
                    dist += wordNet.distance(nouns[i], nouns[j]); //update distance
            }
            
            if (dist > maxdist) {//if you find a larger distance
                maxdist = dist;//update
                outcast = nouns[i];//set outcast at i at that distance
            }
        }
        return outcast;
    }
        
    
    public static void main(String[] args){  // see test client below
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    
        }
    }
}