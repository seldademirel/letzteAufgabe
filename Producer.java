package FixedSizeThreadSafeBufferManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Producer implements Runnable {

	final BufferManager<Double> bm;

	private double GetDblRandom(double max, double min) {
		double r = Math.random();
		if (r < 0.5) {
			return ((1 - Math.random()) * (max - min) + min);
		}
		return (Math.random() * (max - min) + min);
	}

	public Producer(final BufferManager<Double> bm) {
		this.bm = bm;
	}

	public void run() {
		while (true) {
			System.out.println("Producer");

			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				double result = GetDblRandom(0, 100);
				if (result > 10 && result < 15) {
					System.out.println("Producer: break");
					break;
				}
				System.out.println("Producer produced \"" + result + "\"");
				bm.insert(result); // Fügt Random Zahlen in den Array hinzu
				
				System.out.println("Producer capacity: " + bm.getCapacity());
				System.out.println("Producer remaining capacity: " + bm.getRemainingCapacity());
				System.out.println("Producer usage: " + bm.getUsage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}