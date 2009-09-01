package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.contact.EmailAddress;

public class ClientContact extends ClientModel {
	protected String id;
	protected String name;
	protected ArrayList<EmailAddress> email = new ArrayList<EmailAddress>();
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
	public ArrayList<EmailAddress> getEmail() {
		return email;
	}
	public void setEmail(ArrayList<EmailAddress> email) {
		this.email = email;
	}
}
