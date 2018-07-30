package threads11.deadlock;

import java.util.Random;

/*
 We wont use any locks and this class will have synchronization issues.
 */
public class Runner1 {

	Account acc1 = new Account();
	Account acc2 = new Account();

	// transfer some Random Amount from 1st account to 2nd
	public void transferFromAccount1To2() throws InterruptedException {
		Random random = new Random();
		for(int i=0;i <100000; i++) {
			Account.transfer(acc1, acc2, random.nextInt(100));
		}
	}

	// transfer some Random Amount from 2nd account to 1st
	public void transferFromAccount2To1() throws InterruptedException {
		Random random = new Random();
		for(int i=0;i<100000; i++) {
			Account.transfer(acc2, acc1, random.nextInt(100));
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
