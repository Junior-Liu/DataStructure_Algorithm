import java.util.Iterator;
import java.util.NoSuchElementException;

// compile with: javac -cp ".;algs4.jar" 编程Java\Java_lang_learning——algs_n_datastruct\src\Deque.java
// run with: java -cp ".;algs4.jar;编程Java\Java_lang_learning——algs_n_datastruct\src" Deque

public class Deque<Item> implements Iterable<Item> {

    // use linked-list to implement it?
    private Node front;
    private Node rear;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

	// construct na empty deque
    public Deque() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

	// is the deque empty?
    public boolean isEmpty() {
        return this.front == null;
    }

	// return the number of items on the deque
    public int size() {
        return this.size;
    }

	// add the item to the front
    public void addFirst(Item item) {
		if (item == null) { throw new IllegalArgumentException("Cannot add null item."); }
		Node oldfront = this.front;
		this.front = new Node();
		this.front.item = item;
		this.front.prev = null;
		this.front.next = oldfront;
		this.size++;
		if (oldfront == null) { this.rear = this.front; }
		else { oldfront.prev = this.front; }
    }

	// add the item to the back
    public void addLast(Item item) {
		if (item == null) { throw new IllegalArgumentException("Cannot add null item."); }
		Node oldrear = this.rear;
		this.rear = new Node();
		this.rear.item = item;
		this.rear.next = null;
		this.rear.prev = oldrear;
		this.size++;
		if (oldrear == null) { this.front = this.rear; }
		else { oldrear.next = this.rear; }
    }

	// remove the item at the front
    public Item removeFirst() {
		if (this.isEmpty()) { throw new NoSuchElementException("Deque is empty."); }
		Item item = this.front.item;
		this.front = this.front.next;
		if (this.front == null) { this.rear = null; }
		else { this.front.prev = null; }
		this.size--;
		return item;
    }

	// remove the item at the rear
    public Item removeLast() {
		if (this.isEmpty()) { throw new NoSuchElementException("Deque is empty."); }
		Item item = this.rear.item;
		this.rear = this.rear.prev;
		if (this.rear == null) { this.front = null; }
		else { this.rear.next = null; }
		this.size--;
		return item;
    }

    // the iterator
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // the Deque Iterator class
    private class DequeIterator implements Iterator<Item> {
        private Node current = front;

		@Override
		public boolean hasNext()
		{
			return current != null;
		}

		@Override
		public Item next()
		{
			if (!hasNext()) { throw new NoSuchElementException("No more item to return."); }
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException("Remove operation not supported.");
		}
    }

    public static void main(String[] args) {
        // test the addition of elements
        Deque<String> deque = new Deque<>();
        System.out.println("Is deque empty? " + deque.isEmpty());
        deque.addFirst("front1");
        deque.addLast("rear1");
        deque.addFirst("front2");
        deque.addLast("rear2");
        System.out.println("Size: " + deque.size());
        
        // test the iterator
        System.out.println("Items in deque:");
        for (String item : deque) {
            System.out.println(" · " + item + " ");
        }
        System.out.println();

        System.out.println("Remove first: " + deque.removeFirst());
        System.out.println("Remove last: " + deque.removeLast());
        System.out.println("Size after removals: " + deque.size());

        // test if the empty queue throws exception
        Deque<Integer> emptyDeque = new Deque<>();
        try {
            emptyDeque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("Caught excepted Exception: " + e.getMessage());
        }

        // test if a null element throws exception
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught excepted exception: "+ e.getMessage());
        }
    }
}