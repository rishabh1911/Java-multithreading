package threads2.volatility;

import java.util.Scanner;

/* this might sometime not work as Thread t may cache the value 
  of processor and we update value of shutdown in main thread
  the "t" thread may cache the value and this thread will run forever
 */
class Processor extends Thread {

	//private boolean shutdown = false;
	//To avoid the condition use volatile keyword as
	private volatile boolean shutdown = false;

	@Override
	public void run() {
		super.run();
		int i=0;
		while(!shutdown) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+ "  Hi "+i);
			i++;
		}
	}
	
	public void shutdown() {
		System.out.println("Shutdown called from: "+ Thread.currentThread().getName());
		shutdown = true;
	}
}

public class VolatileKeyword {

	public static void main(String[] args) {
		
		Processor t = new Processor();
		t.start();
		System.out.println("Press enter to stop");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		t.shutdown();
	}

}
