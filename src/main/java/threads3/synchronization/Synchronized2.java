package threads3.synchronization;
/*
 The solution of this problem is to make counter accessible to single thread only at a time
 We can make incrementation of this counter as synchronized
 
 Here it will make an object lock.
 Lock will be on the object of Synchronized2 which will be created by JVM.
 */
class Incrementer2 implements Runnable {

	@Override
	public void run() {
		for(int i=0;i<Synchronized2.FINAL_VAL/2;i++) {
			Synchronized2.increment();
		}
	}
}

public class Synchronized2 {

	public static int counter = 0;
	public static int  FINAL_VAL = 20000;
	public static void main(String[] args) {
		Runnable runnable = new Incrementer2();
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Counter= "+ counter);
	}
	
	public static synchronized void increment() {
		counter++;
	}
}
