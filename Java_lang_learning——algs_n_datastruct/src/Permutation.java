// import RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Permutation <number of items to dequeue>");
            return;
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // Read strings from standard input and enqueue them
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        // Dequeue k items and print them
        for (int i = 0; i < k; i++) {
            if (!queue.isEmpty()) {
                System.out.println(queue.dequeue());
            } else {
                break; // If there are fewer than k items, stop
            }
        }
    }
}