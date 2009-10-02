package com.norex.gtrax.client.contact;

import java.io.Serializable;

public class EmailAddress implements Serializable {

	public enum EmailAddressType { home, work, other }
	
	private String address;
	private EmailAddressType type;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public EmailAddressType getType() {
		return type;
	}
	public void setType(EmailAddressType type) {
		this.type = type;
	}
	
}
