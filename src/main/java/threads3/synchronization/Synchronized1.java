package threads3.synchronization;
/*
 Incrementing count is not an atomic operation.
 count++ is equivalent to
 
 int i = count + 1;
 count = i;
 
 so 2 threads are reading and writing an object counter simultaneously, 
 leading to concurrency issues.
 
 When one is has read the value other writes before one has written the value this causes error,
 There are such many examples for this.
 
 so instead of counter being equal to FINAL_VAL it is something else 
 
 */
class Incrementer implements Runnable {

	@Override
	public void run() {
		for(int i=0;i<Synchronized1.FINAL_VAL/2;i++) {
			Synchronized1.increment();;
		}
	}
}

public class Synchronized1 {

	public static int counter = 0;
	public static int  FINAL_VAL = 20000;
	public static void main(String[] args) {
		Runnable runnable = new Incrementer();
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
	
	public static void increment() {
		counter++;
	}
}
