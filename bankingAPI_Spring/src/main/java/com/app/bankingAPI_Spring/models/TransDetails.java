package com.app.bankingAPI_Spring.models;

public class TransDetails {

	private int targetID;
	private int sourceID;
	private double amount;
	
	public TransDetails() {
		// TODO Auto-generated constructor stub
	}

	public int getTargetID() {
		return targetID;
	}

	public void setTargetID(int targetID) {
		this.targetID = targetID;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TransDetails [targetID=" + targetID + ", sourceID=" + sourceID + ", amount=" + amount + "]";
	}

	
	
}
