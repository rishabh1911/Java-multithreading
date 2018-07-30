package threads9.producer.consumer.using.wait.notify.imp;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

	//thread unsafe queue which we will use thread safely by using lock
	private static Queue<Integer> queue = new LinkedList<>() ;
	private static int maxQueueSize = 5;
	private static Object lock = new Object(); 

	//reverse timings to check for overflow/underflow
	private static int producerWaitTime = 500;
	private static int consumerWaitTime = 0;

	public static void main(String[] args) throws InterruptedException {
		Thread produce = new Thread( () -> producer());
		Thread consume = new Thread( () -> consumer());
		
		produce.start();
		consume.start();
		
		produce.join();
		consume.join();
	}

	private static void producer() {
		int i=0;
		try {
			while(true) {
				Thread.sleep(producerWaitTime);
				synchronized (lock) {
					if(queue.size() == maxQueueSize)
						lock.wait();
					queue.add(i);
					i++;
					lock.notify();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void consumer() {
		try {
			while(true) {
				Thread.sleep(consumerWaitTime);
				synchronized (lock) {
					if(queue.size()==0)
						lock.wait();
					System.out.print("Size of list is: "+ queue.size());
					int i = queue.peek();
					queue.remove();
					System.out.println("Element is: "+i);
					lock.notify();
				}
			}	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
