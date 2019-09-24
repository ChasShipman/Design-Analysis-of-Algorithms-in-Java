/**
 * Author: Chas Shipman
 * Date due: 8/08/18
 * 
 */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform(){
        String readInput = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(readInput);
        
        for (int i = 0; i <csa.length(); i++){
            if(csa.index(i) == 0){ //find/write original suffix string first index.
                BinaryStdOut.write(i);
                break;
            }
        }    
        
        for(int i = 0; i < csa.length(); i++){
            BinaryStdOut.write(readInput.charAt((csa.index(i) + csa.length() - 1) % csa.length()));
        }
        
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform(){
        
        int first = BinaryStdIn.readInt();//need first
        String endSuffix = BinaryStdIn.readString();//and transformed suffix
        char[] transformArray = new char[endSuffix.length()];//actual character array of endSuffix
        int[] next = new int[endSuffix.length()];//next array
        char[] firstChar = new char[endSuffix.length()];//charater array of first indeces in sorted suffix
        int[] charCount = new int[257]; //count array for each character.
        
        for(int i = 0; i < endSuffix.length(); i++){//populate transformArray with endSuffix characters.
            transformArray[i] = endSuffix.charAt(i);
          //  next[i] = charCount[transformArray[i]];
            charCount[transformArray[i] + 1]++; //populate count array
        }
        
        for (int i = 0; i < 256; i++){
            charCount[i+1] += charCount[i]; 
        }     
        
        //decoding process original suffix processes
        for (int i = 0; i < endSuffix.length(); i++){ //populate next array and first character array
            next[charCount[transformArray[i]]] = i;
            firstChar[charCount[transformArray[i]]++] = transformArray[i];
        }
        
        for(int i = 0; i < endSuffix.length(); i++){//print
            BinaryStdOut.write(firstChar[first]);
            first = next[first];
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args){
        if(args[0].equals("-"))
            transform();
        if(args[0].equals("+"))
            inverseTransform();
    }
}
