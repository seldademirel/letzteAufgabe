package FixedSizeThreadSafeBufferManager;

import java.util.concurrent.Semaphore;

public class BufferManagerV3<T> implements BufferManager<T> {

	private final T[] elements;

	private Semaphore mutex;
	private Semaphore empty;
	private Semaphore full;

	private int elementCounter = 0;
	private int position = 0;

	@SuppressWarnings("unchecked")
	public BufferManagerV3(final int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException("capacity must be positive.");
		mutex = new Semaphore(1); // Anstellbare anzahl
		empty = new Semaphore(capacity); // Anstellbare Anzahl, wenn leer
		full = new Semaphore(0); // Anstellbare Anzahl, wenn voll
		elements = (T[]) new Object[capacity];
	}// constructor()

	private T peek() throws InterruptedException {
		return elements[(position + getRemainingCapacity()) % getCapacity()];
	}

	@Override
	public void insert(T data) throws InterruptedException {
		try {
			empty.acquire(); // Empty wird geblockt
			mutex.acquire(); // Blockt das noch einer rein(raus)gehen kann (wenn er nicht blocken kann, wartet er)

			elements[position] = data;
			position = (position + 1) % elements.length;
			++elementCounter;
		} finally {
			mutex.release(); // Entsperrt, das einer wieder rein(raus)gehen kann
			full.release();
		}
	}

	@Override
	public T remove() throws InterruptedException {
		try {
			full.acquire();
			mutex.acquire();
			
			T result = peek();
			--elementCounter;
			return result;
		} finally {
			mutex.release();
			empty.release();
		}
	}

	@Override
	public int getUsage() {
		return elementCounter;
	}

	@Override
	public int getRemainingCapacity() {
		return getCapacity() - elementCounter;
	}

	@Override
	public int getCapacity() {
		return elements.length;
	}

}
