import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        Node(Item item) {
            this.item = item;
            next = null;
            previous = null;
        }
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        emptyCheck(item);
        this.size++;
        if (first == null) {
            first = new Node(item);
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node(item);
            first.next = oldFirst;
            oldFirst.previous = first;
        }
    }

    public void addLast(Item item) {
        emptyCheck(item);
        this.size++;
        if (last == null) {
            last = new Node(item);
            first = last;
        } else {
            Node oldLast = last;
            last = new Node(item);
            oldLast.next = last;
            last.previous = oldLast;
        }
    }

    public Item removeFirst() {
        emptyQueueCheck();
        Item item = first.item;
        this.size--;
        if (first.next != null) {
            first.next.previous = null;
            first = first.next;
        } else {
            first = null;
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        emptyQueueCheck();
        Item item = last.item;
        this.size--;
        if (last.previous != null) {
            last.previous.next = null;
            last = last.previous;
        } else {
            last = null;
            first = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public void remove() {
            throw new UnsupportedOperationException("cannot call remove()");
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more item to iterate");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void emptyCheck(Item item) {
        if (item == null) {
            throw new NullPointerException("the item is null");
        }
    }

    private void emptyQueueCheck() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item from empty queue");
        }
    }

    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<>();
        queue.addFirst(1);
        queue.addFirst(2);
        queue.addFirst(3);
        queue.addFirst(4);
        queue.addLast(5);
        queue.addLast(6);
        StdOut.println(queue.size());
        queue.removeFirst();
        queue.removeLast();
        queue.removeLast();
        queue.removeLast();
        queue.removeLast();
        StdOut.println(queue.size());
        for (int i : queue) {
            StdOut.println(i);
        }
    }
}
