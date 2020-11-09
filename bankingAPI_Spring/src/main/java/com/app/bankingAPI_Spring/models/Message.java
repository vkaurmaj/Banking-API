package com.app.bankingAPI_Spring.models;

// model class for message only json body

public class Message {

	private String message;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}
	
}
