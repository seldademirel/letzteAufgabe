package FixedSizeThreadSafeBufferManager;

public class TestFrameDbl {

	private static void TestDoubleValuesV1(int capacity) {
		System.out.println("START: TEST Version 1");
		BufferManager<Double> shBuffer1 = new BufferManagerV1<Double>(capacity);
		Thread prodThread1 = new Thread(new Producer(shBuffer1)); // Erzeugt ein Consumer
		Thread conThread1 = new Thread(new Consumer(shBuffer1)); // Erzeugt ein Producer
		prodThread1.start(); // Startet beide Threads
		conThread1.start();
	}

	private static void TestDoubleValuesV2(int capacity) {
		System.out.println("START: TEST Version 2");
		BufferManager<Double> shBuffer2 = new BufferManagerV2<Double>(capacity);
		Thread prodThread2 = new Thread(new Producer(shBuffer2));
		Thread conThread2 = new Thread(new Consumer(shBuffer2));
		prodThread2.start();
		conThread2.start();
	}

	private static void TestDoubleValuesV3(int capacity) {
		System.out.println("START: TEST Version 3");
		BufferManager<Double> shBuffer3 = new BufferManagerV2<Double>(capacity);
		Thread prodThread3 = new Thread(new Producer(shBuffer3));
		Thread conThread3 = new Thread(new Consumer(shBuffer3));
		prodThread3.start();
		conThread3.start();
	}

	public static void main(final String... unused) {
		int capacity = 15;

		System.out.println("TEST BufferManager for double-values");
		TestDoubleValuesV1(capacity);
		// TestDoubleValuesV2(capacity);
		// TestDoubleValuesV3(capacity);

	}// method()
}
