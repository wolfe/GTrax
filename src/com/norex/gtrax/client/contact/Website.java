package com.norex.gtrax.client.contact;

import java.io.Serializable;

public class Website implements Serializable {
	
	public enum WebsiteType { home, work, blog, profile, other }

	private String address;
	private WebsiteType type;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public WebsiteType getType() {
		return type;
	}
	public void setType(WebsiteType type) {
		this.type = type;
	}
	
}
