package es.noelalonso.drools.example.account.dto;

import java.util.Date;

public class CashFlow {

	private Date date;
	private double amount;
	private int type;
	private long accountNo;
	
	public CashFlow() {
		super();
	}

	public CashFlow(Date date, double amount, int type, long accountNo) {
		super();
		this.date = date;
		this.amount = amount;
		this.type = type;
		this.accountNo = accountNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

}
