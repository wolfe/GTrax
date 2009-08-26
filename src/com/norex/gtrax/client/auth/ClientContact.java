package com.norex.gtrax.client.auth;

import com.norex.gtrax.client.ClientModel;

public class ClientContact extends ClientModel {
	protected String id;
	protected String email;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
