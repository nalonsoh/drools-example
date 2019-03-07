package es.noelalonso.drools.example.account.dto;

public class AccountBalance {

	private long accountNo;
	private double balance;
	
	private AccountPeriod period;
	

	public AccountBalance(long accountNo, double balance, AccountPeriod period) {
		super();
		this.accountNo = accountNo;
		this.balance = balance;
		this.period = period;
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

	public AccountPeriod getPeriod() {
		return period;
	}

	public void setPeriod(AccountPeriod period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "AccontBalance [accountNo=" + accountNo + ", balance=" + balance + ", period=" + period + "]";
	}
	
}
