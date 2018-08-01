package threads14.interrupting.threads;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//For thread pool we have cancel method 
//which we need to call on future returned
class MyRunner4 implements Runnable {

	@Override
	public void run() {
		Random random = new Random();
		for(int i=0;i<1E7;i++) {
			//check for interruption before CPU heavy job
			//break to interrupt
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("Thread Interrupted. ");
				break;
			}
			//CPU intensive operation below
			Math.sin(random.nextDouble());
			//thread wont be interrupted this time as thread is always in running state
			if(i%1000000==0)
				System.out.println("i: "+i);
		}
	}
	
}
public class InterruptingThreadsExample4 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		System.out.println("Starting in new Thread.");
		Future<?> future = executor.submit(new MyRunner4());
		
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		future.cancel(true);
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			System.out.println("Join will get the Exception: "+ e);
		}
		System.out.println("Therad Interruption possible using checking inside thread");
		executor.shutdown();
		
	}
}
