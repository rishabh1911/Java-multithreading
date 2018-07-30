package threads11.deadlock;

//This is an examle of deadlock.
//
public class DeadLockExample2 {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("If no output after 2 sec. Deadlock has occured. Close the program.");
		Runner2 example1 = new Runner2();
		Thread t1 = new Thread(() -> {
			try {
				example1.transferFromAccount1To2();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Thread t2 = new Thread(() -> {
			try {
				example1.transferFromAccount2To1();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		example1.finished();

	}

}
