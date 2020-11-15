package com.app.bankingAPI_Spring.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class TransDetails {

	@JsonInclude(Include.NON_NULL)
	private Integer targetID;
	@JsonInclude(Include.NON_NULL)
	private Integer sourceID;
	private Double amount;
	
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
