package threads1.starting.threads;


/*we can also run a separate thread by extending the thread class and
 then implementing the run method i.e.
 Indirectly extending runnable class.
 but this should be avoided until wee add some features to the thread.
 */
class ThreadExample extends Thread {

	@Override
	public void run() {
		super.run();
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


public class Thread2 {

	public static void main(String[] args) {

		Thread t = new ThreadExample();
		t.start();
		Thread t2 = new ThreadExample();
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
