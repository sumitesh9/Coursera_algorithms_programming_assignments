/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 17/11/18
 *  Description: Java class Deque for performing basic queue based operations.
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {
    // Node based Deque
    private Node<Item> first;
    private Node<Item> last;
    private int num;
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        // For holding no. of nodes
        num = 0;
    }
    // Class to intialise Node<Item>
    private static class Node<Item> {
        Item item = null;
        Node<Item> next = null;
        Node<Item> previous = null;
    }
    // is the deque empty?
    public boolean isEmpty() {
        // Check if number of nodes equals to 0
        return num == 0;
    }
    // return the number of items on the deque
    public int size() {
        // Return number of nodes in Deque
        return num;
    }
    // add the item to the front
    public void addFirst(Item item) {
        // Throw an error
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // If it is empty intially create a temporary node with first=last=temp and return
        if (num == 0) {
            Node<Item> temp = new Node<>();
            temp.item = item;
            first = temp;
            last = temp;
            num++;
            return;
        }
        // prev holds previous first
        Node<Item> prev = first;
        first = new Node<>();
        first.item = item;
        first.next = prev;
        prev.previous = first;
        num++;

    }
    // add the item to the end
    public void addLast(Item item) {
        // Throw an error
        if (item == null) {
            throw new IllegalArgumentException();
        }
        // If it is empty intially
        if (num == 0) {
            Node<Item> temp = new Node<>();
            temp.item = item;
            first = temp;
            last = temp;
            num++;
            return;
        }
        // prev holds previous last
        Node<Item> prevlast = last;
        last = new Node<>();
        last.item = item;
        prevlast.next = last;
        last.previous = prevlast;
        num++;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        // Throw an error
        if (num == 0) {
            throw new NoSuchElementException();
        } else {
            Item val = first.item;
            // If number of nodes is 1 then intialise first = last = null
            if (num == 1) {
                first = null;
                last = null;
                num--;
                return val;
            } else {
                first = first.next;
                first.previous = null;
                num--;
                return val;
            }
        }
    }
    // remove and return the item from the end
    public Item removeLast() {
        // Throw and error
        if (num == 0) {
            throw new NoSuchElementException();
        } else {
            Item val = last.item;
            // If number of nodes is 1 then intialise first = last = null
            if (num == 1) {
                first = null;
                last = null;
                num--;
                return val;
            }
            else {
                last = last.previous;
                last.next = null;
                num--;
                return val;
            }
        }
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Deque.Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {        // unit testing (optional)
    }
}