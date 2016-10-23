import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    public RandomizedQueue() {
        int defaultSize = 1;
        this.queue = (Item[]) new Object[defaultSize];
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(Item item) {
        emptyCheck(item);
        if (size == queue.length) {
            resize(2 * queue.length);
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        emptyQueueCheck();
        int rand = getRandomOccupiedIndex();
        Item item = queue[rand];
        this.size--;
        queue[rand] = queue[size];
        queue[size] = null;
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        emptyQueueCheck();
        int rand = getRandomOccupiedIndex();
        return queue[rand];
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Item[] iteratorQueue;
        private int iteratorIndex;
        QueueIterator() {
            iteratorQueue = (Item[]) new Object[size];
            iteratorIndex = 0;
            //copy queue
            for (int i = 0; i < iteratorQueue.length; i++) {
                iteratorQueue[i] = queue[i];
            }
            //shuffle
            StdRandom.shuffle(iteratorQueue);
        }

        @Override
        public boolean hasNext() {
            return iteratorIndex < iteratorQueue.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("the queue is reached to the end");
            }
            Item item = iteratorQueue[iteratorIndex++];
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
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

    private int getRandomOccupiedIndex() {
        while (true) {
            int rand = StdRandom.uniform(size);
            if (queue[rand] != null) {
                return rand;
            }
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        randQueue.enqueue(1);
        randQueue.enqueue(2);
        randQueue.enqueue(3);
        randQueue.enqueue(4);
        randQueue.enqueue(5);
        StdOut.println("number of elements: " + randQueue.size());
        StdOut.println("is Empty: " + randQueue.isEmpty());
        StdOut.println("random element: " + randQueue.sample());
        StdOut.println("=============random iterator 1 start: ============");
        for (int elem : randQueue) {
            StdOut.println(elem);
        }
        StdOut.println("=============random iterator 2 start: ============");
        for (int elem : randQueue) {
            StdOut.println(elem);
        }
    }
}
