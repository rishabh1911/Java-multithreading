package threads1.starting.threads;

/*
 With Java 8 we can implement runnable using a lambda also
 */
public class Thread3 {

	public static void main(String[] args) {
		//Runnable is also now an Functional interface
		Thread t1 = new Thread(()->
		{
			for(int i=1;i<=10; i++) {
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Inside: "+ Thread.currentThread().getName()+ " i: "+ i);
			}
		});
		t1.start();


		
		System.out.println("Name of main Thread: "+ Thread.currentThread().getName());
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Join command waits for other thread to end.");
	}
	
}
