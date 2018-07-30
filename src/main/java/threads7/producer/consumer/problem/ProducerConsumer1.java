package threads7.producer.consumer.problem;

import java.util.LinkedList;
import java.util.Queue;

/*
 A lock object to lock the whenever we add or delete from queue
 */
public class ProducerConsumer1 {

	//thread unsafe queue which we will use thread safely by using lock
	private static Queue<Integer> queue = new LinkedList<>() ;
	private static int maxQueueSize = 5;
	private static Object lock = new Object(); 
	
	//reverse timings to check for overflow/underflow
	private static int producerWaitTime = 0;
	private static int consumerWaitTime = 1000;

	
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lock) {
				if(queue.size()<maxQueueSize) {
					queue.add(i);
					i++;
				}
			}
		}
	}

	private static void consumer(){
		while(true) {
			try {
				Thread.sleep(consumerWaitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lock) {
				if(queue.size()>0) {
					int i = queue.peek();
					queue.remove();
					System.out.println("Removed from queue: "+ i+ " queueSize: "+ queue.size());
				}
			}
		}
	}
}
