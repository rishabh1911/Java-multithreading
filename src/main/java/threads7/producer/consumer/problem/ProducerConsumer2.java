package threads7.producer.consumer.problem;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 Since it is better not to use low level blocking we will be using a thread safe queue
 no synchronize keyword here
 */
public class ProducerConsumer2 {

	//thread safe queue which we can use
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

	//reverse timings to check for overflow/underflow
	private static int producerWaitTime = 1000;
	private static int consumerWaitTime = 0;


	public static void main(String[] args) throws InterruptedException {

		Thread producerThread = new Thread(() -> producer());
		Thread consumerThread = new Thread(() -> consumer());

		producerThread.start();
		consumerThread.start();

		producerThread.join();
		consumerThread.join();
	}

	private static void producer() {
		int i = 1;
		while(true) {
			try {
				Thread.sleep(producerWaitTime);
				queue.put(i);
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

	private static void consumer(){
		while(true) {
			try {
				Thread.sleep(consumerWaitTime);
				int i = queue.take();
				System.out.println("Removed from queue: "+ i+ " queueSize: "+ queue.size());
				/*
				 * When overflow can occur,
				 * queue size may be equal to max size when after taking the element and before printing again an element is added.
				 * but overflow will not occur.
				 * 
				 * similarly, in underflow possible case,
				 * size can be more than 0
				 */
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

}
