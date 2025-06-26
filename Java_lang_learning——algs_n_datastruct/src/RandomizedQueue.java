import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    // private Random random;

    // construct an empty randomized queue
    // @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        this.items = (Item[]) new Object[2];
        this.size = 0;
        // this.random = new Random();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (this.size == this.items.length) resize(2 * this.items.length);
        this.items[this.size++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if (this.isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        int randomIndex = (int) (Math.random() * this.size);
        Item item = this.items[randomIndex];
        this.items[randomIndex] = this.items[--this.size];// replace with the last element for avoiding the vacuum
        this.items[this.size] = null; // avoid loitering
        if (this.size > 0 && this.size == this.items.length / 4) {
            resize(this.items.length / 2);
        }
        return item;
    }

    // resize the underlying array
    // @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            newItems[i] = this.items[i];
        }
        this.items = newItems;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        int randomIndex = (int) (Math.random() * this.size);
        return this.items[randomIndex];
    }

    // the iterator
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // the Randomized Queue Iterator class
    private class RandomizedQueueIterator implements Iterator<Item>{
        private int current;          // current index of iteration
        private Item[] shuffledItems; // save the elements in random order

        // @SuppressWarnings("unchecked")
        public RandomizedQueueIterator() {
            this.current = 0;
            this.shuffledItems = (Item[]) new Object[RandomizedQueue.this.size];
            for (int i = 0; i < RandomizedQueue.this.size; i++) {
                this.shuffledItems[i] = RandomizedQueue.this.items[i];
            }
            for (int i = 0; i < RandomizedQueue.this.size; i++) {
                int j= Math.abs((int) (Math.random() * RandomizedQueue.this.size()));
                // RandomizedQueue.this.random.nextInt(RandomizedQueue.this.size);
                Item temp = this.shuffledItems[i];
                this.shuffledItems[i] = this.shuffledItems[j];
                this.shuffledItems[j] = temp;
            }
        }

		@Override
		public boolean hasNext() {
			return this.current < RandomizedQueue.this.size;
		}

		@Override
		public Item next() {
			if (!hasNext()) { throw new NoSuchElementException("No more item to return."); }
			return this.shuffledItems[this.current++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove operation not supported.");
		}
    }

    // test client (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        // test isEmpty
        System.out.println("Is empty: " + queue.isEmpty());

        // test enqueue
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);

        // test size and sample
        System.out.println("Size: " + queue.size());
        System.out.println("Sample: " + queue.sample());

        // test dequeue and iterator
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Size after dequeue: " + queue.size());

        for (Integer item : queue) {
            System.out.println("Item: " + item);
        }
        System.out.println();

        // test aberrant empty queue
        RandomizedQueue<Integer> emptyQueue = new RandomizedQueue<>();
        try {
            emptyQueue.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // test null element error
        RandomizedQueue<Integer> queueWithNull = new RandomizedQueue<>();
        try {
            queueWithNull.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}