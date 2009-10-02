package com.norex.gtrax.client.contact;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
	public enum PhoneNumberTypes { home, work, mobile, fax, pager, other }
	
	private String number;
	private PhoneNumberTypes type;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public PhoneNumberTypes getType() {
		return type;
	}
	public void setType(PhoneNumberTypes type) {
		this.type = type;
	}
}
