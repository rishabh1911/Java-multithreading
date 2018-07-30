package threads8.wait.and.notify;
/*
Every object has an intrinsic wait method. 
It can be called only under synchronized code blocks.
It gives up and waits until notify is called for it or timeout occurs.

Notify can also be called within a synchronized block.
It will notify one of the threads to resume operation 
after taking back the lock when the current threads leaves the lock.

 */
import java.util.Scanner;

class Processor1 {
	
	void thread1method() {
		synchronized (this) {
			System.out.println("Thread 1 running.");
			try {
				System.out.println("Wait called on Thread 1.");
				wait(10000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Thread 1 resumed.");
		}
	}
	
	void thread2method() throws InterruptedException {
		Thread.sleep(1000);
		synchronized (this) {
			System.out.println("Thread 2 running.");
			Scanner scanner = new Scanner(System.in);
			System.out.println("Press continue to resume.");
			scanner.nextLine();
			notify();
			System.out.println("Notify called and lock is still with thread 1 as we are waiting for 2 sec.");
			Thread.sleep(2000);
			//wait of 2 seconds shows thread 1 will not resume until thread 2 gives the lock
			
		}
	}
}

public class WaitNotifyExample {

	public static void main(String[] args) throws InterruptedException {
		final Processor1 processor = new  Processor1();
		Thread t1 = new Thread(() -> processor.thread1method());
		Thread t2 = new Thread(() -> {
			try {
				processor.thread2method();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
	
	
}
