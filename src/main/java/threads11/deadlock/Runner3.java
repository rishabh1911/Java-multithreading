package threads11.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 To solve the synchronization issues in Runner1 
 we saw a deadlock in Runner2

 Since two locks are needed we have a deadlock. Which can be avoided by:
 	1. Using locks in same order. (Usually can't be ensured in applications)
 	2. Having a timeout approach to get lock to tackle deadlock. (Hold and wait method)
 	
 	using Hold and Wait here.
 	
 */
public class Runner3 {

	Account acc1 = new Account();
	Account acc2 = new Account();
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();
	
	private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
		while(true) {
			boolean isFirstLockTaken = false;
			boolean isSecondLockTaken = false;
			try{
				isFirstLockTaken = firstLock.tryLock();
				isSecondLockTaken = secondLock.tryLock();
				//Trying to hold lock
			} finally {
				if(isFirstLockTaken && isSecondLockTaken) //continue if able to take all locks.
					return;
				else if(isFirstLockTaken) 
					firstLock.unlock();
				else if(isSecondLockTaken)
					secondLock.unlock();
			}
			Thread.sleep(1);
			//wait to retry if needed.
			
		}
	}

	// transfer some Random Amount from 1st account to 2nd
	public void transferFromAccount1To2() throws InterruptedException {
		Random random = new Random();
		for(int i=0;i <100000; i++) {
			try {
				acquireLocks(lock1,lock2);
				Account.transfer(acc1, acc2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	// transfer some Random Amount from 2nd account to 1st
	public void transferFromAccount2To1() throws InterruptedException {
		Random random = new Random();
		for(int i=0;i<100000; i++) {
			try{
				acquireLocks(lock2,lock1);
				Account.transfer(acc2, acc1, random.nextInt(100));
			} finally {
				lock2.unlock();
				lock1.unlock();
			}
		}

	}

	//When both threads finish execution, finished runs.
	//Total balance should equal 2000
	public void finished() {
		System.out.println("Account 1 balance: " + acc1.getBalance());
		System.out.println("Account 2 balance: " + acc2.getBalance());
		System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
	}
}
