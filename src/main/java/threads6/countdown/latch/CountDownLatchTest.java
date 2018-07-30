package threads6.countdown.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 Countdown latch is a THREAD SAFE counter which counts fron n to 0.
 */
		
class Processor implements Runnable {
	
	private CountDownLatch latch;
	
	public Processor(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Started with Thread: "+ Thread.currentThread().getName()+ " with count:"+ latch.getCount());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// no need to synchronize here as it is thread safe.
		latch.countDown();
	}
	
}
public class CountDownLatchTest {
	
	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(5);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		for(int i=0; i<14; i++) {
			executor.submit(new Processor(countDownLatch));
		}
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed as latch reaches zero");
		executor.shutdown();
	}

}
