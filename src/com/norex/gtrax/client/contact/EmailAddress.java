package com.norex.gtrax.client.contact;

import java.io.Serializable;

enum EmailAddressType { home, work, other }

public class EmailAddress implements Serializable {

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
