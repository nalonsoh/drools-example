package es.noelalonso.drools.example.account.dto;

public class Account {
	private long accountNo;
	private double balance;
	
	public Account() {
		super();
	}

	public Account(long accountNo, double balance) {
		super();
		this.accountNo = accountNo;
		this.balance = balance;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", balance=" + balance + "]";
	}
	
}