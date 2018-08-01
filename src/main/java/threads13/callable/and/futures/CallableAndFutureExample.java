package threads13.callable.and.futures;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
To get return object from a thread we use callable and future.
It also gives thread the ability to throw Exceptions.

This example shows exception throwing case
 */

class CallableExample implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		Random random = new Random();
		int waitDuration = random.nextInt(3000);
		System.out.println("Started new Thread");
		Thread.sleep(waitDuration);
		System.out.println("Finished new Thread");
		return waitDuration;
	}

}

public class CallableAndFutureExample {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		System.out.println("Calling new Thread");
		Future<Integer> future = executor.submit(new CallableExample());
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//Main and new thread running parallelly
		System.out.println("Main Method Not Waiting");
		int result;
		try {
			result = future.get();
			//here thread.join occurs
			System.out.println("Result is: "+result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("Main Thread Waits after we call future.get()");
	}

}
