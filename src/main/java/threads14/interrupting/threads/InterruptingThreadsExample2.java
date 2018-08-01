package threads14.interrupting.threads;

import java.util.Random;

//Thread is not interrupted directly when it is in running state
class MyRunner2 implements Runnable {

	@Override
	public void run() {
		Random random = new Random();
		for(int i=0;i<1E7;i++) {
			//CPU intensive operation below
			Math.sin(random.nextDouble());
			//thread wont be interrupted this time as thread is always in running state
			if(i%1000000==0)
				System.out.println("i: "+i);
		}
	}
	
}
public class InterruptingThreadsExample2 {

	public static void main(String[] args) {
		Thread t1 = new Thread(new MyRunner2());
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
		System.out.println("Therad Interruption not possible as it is always running");
		
		
	}
}
