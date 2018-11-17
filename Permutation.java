/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 17/11/18
 *  Description: Java class Permutation that takes an integer k as a command-line argument;
 *  reads in a sequence of strings from standard input using StdIn.readString();
 *  and prints exactly k
 *****************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
public class Permutation {
    public static void main(String[] args) {
        int k;
        String s;
        Iterator<String> it;
        RandomizedQueue<String> sq = new RandomizedQueue<String>();
        // Input limit i.e k
        k = Integer.parseInt(args[0]);
        // Enqueue s to sq
        while (!StdIn.isEmpty())
        {
            s = StdIn.readString();
            sq.enqueue(s);
        }
        // For each loop for printing k strings
        for (String sn : sq) {
            if (k == 0) break;
            StdOut.println(sn);
            k--;
        }
    }
}