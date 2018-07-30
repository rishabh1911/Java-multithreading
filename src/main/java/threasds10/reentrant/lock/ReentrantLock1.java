package threasds10.reentrant.lock;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 Re-entrant Locks can be acquired again by the same thread and the thread keeps a count of that.
 Then that thread has to unlock it same number of times.

 normally usually it is only once.

 unlock should be called in finally and code should be in try block.

 This also has a wait in form of Condition.await(). (which obviouly should be called inside a locked/synchronized block)

 This is basically helpful to avoid deadlocks which we will see in next program

 */
public class ReentrantLock1 {

	private static Lock lock = new ReentrantLock();
	private static Condition condition = lock.newCondition();

	private static Queue<Integer> queue = new LinkedList<>() ;
	private static int maxQueueSize = 10;

	private static int consumer_wait_time = 00;
	private static int producer_wait_time = 300;

	public static void main(String[] args) {
		Thread t1 = new Thread(() -> producer());
		Thread t2 = new Thread(() -> consumer());

		t1.start();
		t2.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void producer() {
		int i = 1 ;
		while(true) {
			try {
				Thread.sleep(producer_wait_time);
				lock.lock();
				if(queue.size() == maxQueueSize)
					condition.await();
				queue.add(i);
				i++;
				condition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				lock.unlock();
			}
		}

	}

	private static void consumer() {
		while(true) {
			try {
				Thread.sleep(consumer_wait_time);
				lock.lock();
				System.out.print("queue size is: "+ queue.size());
				if(queue.size()==0)
					condition.await();
				int i = queue.peek();
				queue.remove();
				System.out.println("Element on top is: "+i);
				condition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				//unlock should be called in finally and code should be in try block.
				lock.unlock();
			}
		}
	}
}
