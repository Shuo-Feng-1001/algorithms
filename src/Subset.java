import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        int subsetSize = Integer.parseInt(args[0]);
        String[] inputStrings = StdIn.readAllStrings();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        for (String str : inputStrings) {
            randomizedQueue.enqueue(str);
        }
        for (int i = 0; i < subsetSize; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
