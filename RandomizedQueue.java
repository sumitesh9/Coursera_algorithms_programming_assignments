/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 17/11/18
 *  Description: Java class RandomizedQueue implementing basic operations
 *****************************************************************************/
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rqueue;
    // n holds number of elements
    private int n;
    // construct an empty randomized queue
    public RandomizedQueue() {
        rqueue = (Item[]) new Object[0];
        n = 0;
    }
    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }
    // return the number of items on the randomized queue
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return n;
    }
    // add the item
    public void enqueue(Item item) {
        // Throw and error
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // Holds size of randomized queue
        int sizeQ = rqueue.length;
        if (sizeQ == 0) {
            reSize(1);
        } else if (sizeQ == n) {
            reSize(2 * rqueue.length);
        }
        rqueue[n] = item;
        n++;
    }
    // remove and return a random item
    public Item dequeue() {
        // Thorow an error
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int randN = StdRandom.uniform(n);
        Item item = rqueue[randN];
        rqueue[randN] = rqueue[n - 1];
        rqueue[n - 1] = null;
        n--;
        int sizeQ = rqueue.length;
        if (n > 0 && n == sizeQ / 4) {
            reSize(sizeQ / 2);
        }
        return item;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        // Holds a random number
        int randN = StdRandom.uniform(n);
        return rqueue[randN];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }
    // ReverseArrayIterator
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = n;
        public boolean hasNext() {
            return i > 0;
        }
        public void remove() {
            // Throw an error
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                // Throw an error
                throw new NoSuchElementException();
            }
            return rqueue[--i];
        }
    }
    // To resize a randomized queue
    private void reSize(int x) {
        Item[] tempA = (Item[]) new Object[x];
        for (int i = 0; i < n; i++) {
            tempA[i] = rqueue[i];
        }
        rqueue = tempA;
    }
    public static void main(String[] args) {     // unit testing (optional)

    }
}