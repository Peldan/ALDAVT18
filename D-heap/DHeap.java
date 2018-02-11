// Arvid Peldán - arpe3186
package alda.heap;

import java.util.Arrays;

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 * @author Arvid Peldán
 */

// modified Mark Allen Weiss' Binary Heap to a D-Heap

	private static final int DEFAULT_DIMENSION = 2;
	private static final int DEFAULT_CAPACITY = 10;
	private int currentSize; 
	private int d;
	private AnyType[] array; 

	public DHeap() {
		this(DEFAULT_DIMENSION);
	}

	public DHeap(int d) {
		this.d = d;
		if (d < 2) {
			throw new IllegalArgumentException();
		}
		currentSize = 0;
		array = (AnyType[]) new Comparable[d * DEFAULT_CAPACITY + 1];
	}

	public int parentIndex(int index) {
		if (index < 2) {
			throw new IllegalArgumentException();
		}

		return d == DEFAULT_DIMENSION ? index / 2 : (index - 2) / d + 1;
	}

	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * d + 1);
		int hole = ++currentSize;
		if (hole > 1) {
			for (array[0] = x; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)) {
				array[hole] = array[parentIndex(hole)];
			}
		}
		array[hole] = x;
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);
		return minItem;
	}

	private void percolateDown(int hole) {
		int child;
		AnyType tmp = array[hole];
		for (; firstChildIndex(hole) <= currentSize; hole = child) {
			child = firstChildIndex(hole);
			int min = child;
			for (; child <= firstChildIndex(hole) + d - 1; child++) {
				if (child <= currentSize && array[child].compareTo(array[min]) < 0) {
					min = child;
				}
			}
			if (array[min].compareTo(tmp) < 0) {
				array[hole] = array[min];
				child = min;
			} else {
				break;
			}
		}
		array[hole] = tmp;
	}

	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	public int size() {
		return currentSize;
	}

	AnyType get(int index) {
		return array[index];
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void makeEmpty() {
		currentSize = 0;
	}

	public static void main(String[] args) {
		int numItems = 10000;
		DHeap<Integer> h = new DHeap<>();
		int i = 37;

		for (i = 37; i != 0; i = (i + 37) % numItems)
			h.insert(i);
		for (i = 1; i < numItems; i++)
			if (h.deleteMin() != i)
				System.out.println("Oops! " + i);
	}

	public int firstChildIndex(int i) {
		if (i < 1)
			throw new IllegalArgumentException();
		return ((i - 1) * d) + 2;
	}
	
	public String toString(){
		return Arrays.toString(array);
	}
}
