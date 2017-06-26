package FixedSizeThreadSafeBufferManager;

/*
 *------------------------------------------------------------------------------
 * VCS: git@BitBucket.org:schaefers/Prg_Px_K-L_FixedSizeThreadSafeBufferManager_Distr[.git]
 * For further information see ReadMe.txt
 *                                                Michael Schaefers  2017/06/06
 *------------------------------------------------------------------------------
 */
public class BufferManagerV1<T> implements BufferManager<T> {

	private final T[] elements;
	private int elementCounter = 0;
	private int position = 0;

	@SuppressWarnings("unchecked")
	public BufferManagerV1(final int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException("capacity must be positive.");

		elements = (T[]) new Object[capacity]; // Array wird erstellt
	}// constructor()
	
	

	@Override
	public synchronized void insert(final T data) throws InterruptedException { // Mit synchronized wird sichergestellt, das nicht mehrere Threads die Methode in der selben Zeit durchlaufen.
		while (elementCounter == elements.length) { // Warten wenn array voll ist...
			wait();
		}
		
		elements[position] = data; // Datei in Array hinzufügen (Position = Offset)
		position = (position + 1) % elements.length; // position wird dadurch nicht größer elements.length
		++elementCounter; // Counter wird hochgezählt
		notifyAll(); // Threads benachrichtigt
	}// method()

	@Override
	public synchronized T remove() throws InterruptedException {
		while (elementCounter == 0) { // Warten, wenn keine Datei im Array ist.
			wait();
		}
		T result = elements[(position + getRemainingCapacity()) % getCapacity()]; // Gibt eine Datei zurück (Reihenfolge wird beachtet)
		--elementCounter; // Counter wird runtergezählt
		notifyAll(); // Threads benachrichtigt
		return result;
	}// method()

	@Override
	public synchronized int getUsage() {
		return elementCounter;
	}// method()

	@Override
	public synchronized int getRemainingCapacity() {
		return getCapacity() - elementCounter;
	}// method()

	@Override
	public synchronized int getCapacity() {
		return elements.length;
	}// method()

}// class
