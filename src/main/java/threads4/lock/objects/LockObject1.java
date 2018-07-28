package threads4.lock.objects;

/*
In worker 1 we have both methods having a synchronized keyword.
So the lock is placed on Worker object and when one thread is inside one method 
the other thread can access any of the method.

Since stageOne and stageTwo work on different objects
we can allow two different threads to access them concurrently
only we need to focus that when one list is access no other thread accesses that list

This is done by an object lock as we see in worker2.

Thus we see time taken is reduced to half
 */


public class LockObject1 {

	public static void main(String[] args) {
		System.out.println("Synchronized Methods: ");
		Worker1 worker1 = new Worker1();
		worker1.main();
		System.out.println("Synchronized Objects: ");
		Worker2 worker2 = new Worker2();
		worker2.main();

	}
}
