package threads11.deadlock;

public class DeadlockExample1 {

	public static void main(String[] args) throws InterruptedException {
		Runner1 example1 = new Runner1();
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
