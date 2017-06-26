package FixedSizeThreadSafeBufferManager;

import java.util.concurrent.*;

public class BufferManagerV2<T> implements BufferManager<T> {

	private final BlockingQueue<T> elements;
	private final int max;

	public BufferManagerV2(final int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity must be positive.");
		}
		max = capacity;
		elements = new ArrayBlockingQueue<T>(capacity); // Die schlange nimmt keinen mehr an, wenn sie voll ist
	}// constructor()

	@Override
	public void insert(final T data) throws InterruptedException {
		elements.put(data);
	}

	@Override
	public T remove() throws InterruptedException {
		T result = elements.take();
		return result;
	}

	@Override
	public int getUsage() {
		return elements.size();
	}

	@Override
	public int getRemainingCapacity() {
		return elements.remainingCapacity();
	}

	@Override
	public int getCapacity() {
		return max;
	}
}
