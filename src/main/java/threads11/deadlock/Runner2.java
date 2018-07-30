package threads11.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 To solve the synchronization issues in Runner1 
 we will use locks here.

 Since two locks are needed we have a deadlock. Which can be avoided by:
 	1. Using locks in same order. (Usually can't be ensured in applications)
 	2. Having a timeout approach to get lock to tackle deadlock. (Hold and wait method)
 */
public class Runner2 {

	Account acc1 = new Account();
	Account acc2 = new Account();
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();

	// transfer some Random Amount from 1st account to 2nd
	public void transferFromAccount1To2() throws InterruptedException {
		Random random = new Random();
		for(int i=0;i <100000; i++) {
			try {
				lock1.lock();
				lock2.lock();
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
				lock2.lock();
				lock1.lock();
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
