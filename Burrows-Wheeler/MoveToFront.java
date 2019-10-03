/**
 * Author: Chas Shipman
 * Date due: 8/08/18
 * 
 */


import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront{
    
    public static void encode(){
        char[] asciiArray = new char[256];
        for(char i = 0; i < 256; i++) //populate ordered ascii character array
            asciiArray[i] = i;
        
        while(!BinaryStdIn.isEmpty()){ //read input
            char charInput = BinaryStdIn.readChar();
            for (int i = 0; i < asciiArray.length; i++){
                if(asciiArray[i] == charInput){ //find index of input 
                    BinaryStdOut.write((char)i);
                    for(int j = i; j > 0; j--) //move to front
                        asciiArray[j] = asciiArray[j-1];
                    
                    asciiArray[0] = charInput;
                    break;
                }
            }
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
        return;   
    }
    
    public static void decode(){
        char[] asciiArray = new char[256];
        for(char i = 0; i < 256; i++) //populate ordered ascii character array
            asciiArray[i] = i;
        
        while(!BinaryStdIn.isEmpty()){ //read input
            char charInput = BinaryStdIn.readChar();
            BinaryStdOut.write(asciiArray[charInput]);
            
            char holdChar = asciiArray[charInput];
            for(int j = charInput; j > 0; j--) //move to front
                asciiArray[j] = asciiArray[j-1];
            
            asciiArray[0] = holdChar;
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }
    
    public static void main(String[] args){
        
        if(args[0].equals("-"))
            encode();
        
        if(args[0].equals("+"))
            decode();
        
        return;
        
    }
}