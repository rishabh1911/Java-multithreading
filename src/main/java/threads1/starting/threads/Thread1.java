package threads1.starting.threads;

/*to start a thread 
 1.we implement the run() function of Runnable interface.
 2.create a thread by passing this runnable implementation to its constructor.
 3. call thred.start() function
 */
class MyRunnble implements Runnable{
	
	public void run() {
		for(int i=0; i<10; i++) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" "+ i);	
		}
	}
}


public class Thread1 {

	public static void main(String[] args) {
		Runnable runnable = new MyRunnble();
		Thread t = new Thread(runnable);
		t.start();
		Thread t2 = new Thread(runnable);
		t2.start();
		
		//
		System.out.println("Name of main Thread: "+ Thread.currentThread().getName());
		try {
			t.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Join command waits for other thread to end.");
	}
}
