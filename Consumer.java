package FixedSizeThreadSafeBufferManager;

class Consumer implements Runnable {

	final BufferManager<Double> bm;

	public Consumer(BufferManager<Double> b) {
		this.bm = b;
	}

	public void run() {
		while (true) {
			System.out.println("Consumer");

			double result;
			try {
				result = (Double) bm.remove(); // Nimmt ständig aus dem Array was raus

				System.out.println("Consumer consumed \"" + result + "\"");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}