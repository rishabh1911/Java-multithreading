package threads5.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/*
 By recycling Threads in Thread pool we avoid overheads of spawning and deleting a thread.
 */
class Runner implements Runnable {

	private int id;
	
	public Runner(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Starting Process with id: "+id+ " with thread: "+ Thread.currentThread().getName());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Process with id: "+id+" Ended.");
	}
	
}

public class ThreadPoolExample {
	
	private static int THREAD_POOL_SIZE = 2;
	
	public static void main(String[] args) {
		
		// creating a thread pool of 2 threads
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		
		for(int i=1; i<= 5 ; i++) {
			executor.submit(new Runner(i));
		}
		
		//shutdown the executor service after all threads are free i.e completed their task.
		//executor 
		executor.shutdown();
		System.out.println("All Tasks Submitted and Shutdown called");
		// thus executor itself runs on different thread.
		
		//to wait till all tasks have completed
		try {
			executor.awaitTermination(3, TimeUnit.MINUTES);
			// it will forcefully close all tasks if they do not
			//terminate in 3 minutes.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All tasks done.");
		
	}

}
