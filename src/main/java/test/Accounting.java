package test;

public class Accounting {
	public static void main (String [] args)
	{
		Account account = new Account();
		Transfer tt2 = new Transfer(account, "Withdrawal");
		Transfer tt1 = new Transfer(account, "Deposit");
		tt1.start();
		tt2.start();
	}
}

class Account {
	public static int amount = 0;
}

class Transfer extends Thread {

	private Account account;

	Transfer(Account account, String name) {
		super (name);
		this.account = account;
	}

	public void run () {
		while (true) {
			String transferType = this.getName();
			if (transferType.equals ("Deposit")) {
				this.addToAmount(100);
				try {
					Thread.sleep ((int) (Math.random () * 1000));
				} catch (InterruptedException e) { }
			} else {
//				this.addToAmount(-500);
				this.addToAmountSync(-500);
				try {
					Thread.sleep ((int) (Math.random () * 1000));
				} catch (InterruptedException e) { }
			}

			System.out.println (transferType + " " + this.account.amount);
		}
	}

	void addToAmount(int value) {
		account.amount += value;
	}

	void addToAmountSync(int value) {
		synchronized (this) {
			account.amount += value;
		}
	}
}
