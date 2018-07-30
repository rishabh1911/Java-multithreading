package threads11.deadlock;

// A simple account class with methods to deposit, withdraw and transfer balance bwtween two accounts.
public class Account {
	
	 private int balance = 10000;

	    public void deposit(int amount) {
	        balance += amount;
	    }

	    public void withdraw(int amount) {
	        balance -= amount;
	    }

	    public int getBalance() {
	        return balance;
	    }

	    public static void transfer(Account acc1, Account acc2, int amount) {
	        acc1.withdraw(amount);
	        acc2.deposit(amount);
	    }
}
