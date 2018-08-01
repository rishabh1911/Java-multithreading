package threads14.interrupting.threads;
/*
 thread.interrupt() does not interrupt the thread.
 It sets the interrupt flag of thread to true;
 This flag is checked when it moves from sleep or wait state to running state
 or it can be checked explicitily
 
 If thread is running it will not stop. It will be interrupted only if it is non running state
 
 We need to quiz that in thread after every intensive task in thread
 */

class MyRunner implements Runnable {

	@Override
	public void run() {
		for(int i=0;i<5000;i++) {
			try {
				//As thread goes to sleep i.e not running state
				//it can be interrupted
				Thread.sleep(1);
				if(i%1000==0) 
					System.out.println("i: "+i);
					//Thread continues after interruption"
			} catch (InterruptedException e) {
				System.out.println("Throws Interrupted Exception from Here");
				//Break needs to written here explicitly to make it interrupt
				//If break removed thread will not be interrupted
				break;
			}
		}
	}
	
}
public class InterruptingThreadsExample1 {

	public static void main(String[] args) {
		Thread t1 = new Thread(new MyRunner());
		System.out.println("Starting in new Thread.");
		t1.start();
		
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		t1.interrupt();
		try {
			t1.join();
		} catch (InterruptedException e) {
			System.out.println("Join will get the Exception: "+ e);
		}
		System.out.println("Therad Interruption possible in between as it was going to sleep state");
		
		
	}
}
