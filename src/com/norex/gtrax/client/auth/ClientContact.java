package com.norex.gtrax.client.auth;

import com.norex.gtrax.client.ClientModel;

public class ClientContact extends ClientModel {
	protected String id;
	protected String name;
	protected String email;
	protected ClientCompany company;
	public ClientCompany getCompany() {
		return company;
	}
	public void setCompany(ClientCompany company) {
		this.company = company;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
