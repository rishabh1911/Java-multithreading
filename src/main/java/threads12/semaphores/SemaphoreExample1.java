package threads12.semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
Semaphore is a integer variable that can be accessed by ATOMIC statements only.

Java has a counting Semaphore.

Difference between Semaphore and Lock in Java.
Lock needs to locked and unlocked on same thread while that is not the case for semaphore.

Semaphore are used to control access to a pool of resource of fixed size.

Semaphore also has a fairness parameter allowing threads which asked to require permission first will get access first.
 */

class Connection1 {

	private static Connection1 instance = new Connection1();
	private int connections = 0;
	/*
    limit connections to 10
    true means whichever thread gets first in the waiting pool (queue)
    waiting to acquire a resource, is first to obtain the permit.
    Note that I called it a pool!
    The reason is when you say "Queue", you're saying that things are
    scheduled to be FIFO (First In First Out) .. which is not always the case
    here!
    When you initialize the semaphore with Fairness, by setting its second
    argument to true, it will treat the waiting threads like FIFO.
    But,
    it doesn't have to be that way if you don't set on the fairness. the JVM
    may schedule the waiting threads in some other manner that it sees best
    (See the Java specifications for that).
	 */
	private Semaphore semaphore = new Semaphore(10, true);

	private Connection1() {

	}

	public static Connection1 getInstance() {
		return instance;
	}

	public void connect() {
		try {
			semaphore.acquire();
			doConnect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	private void doConnect() throws InterruptedException {
		
		synchronized (this) {
			connections++;
            System.out.println("Current connections (max 10 allowed): " + connections);
		}
		
		Thread.sleep(2000);
		// consider some tasks happening
		
		synchronized (this) {
			connections--;
			System.out.println("I'm done " + Thread.currentThread().getName() +
					" Connection is released , connection count: " + connections);
		}
	}

}
public class SemaphoreExample1 {
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService executors = Executors.newCachedThreadPool();
		for(int i=0;i<100;i++) {
			executors.submit(()->{
				Connection1.getInstance().connect();
			});
		}
		executors.shutdown();
		executors.awaitTermination(1, TimeUnit.HOURS);
	}

}
