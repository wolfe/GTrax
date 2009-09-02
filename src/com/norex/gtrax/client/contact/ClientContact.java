package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.auth.ClientCompany;

public class ClientContact extends ClientModel {
	protected String id;
	protected String name;
	protected ArrayList<EmailAddress> email = new ArrayList<EmailAddress>();
	protected ArrayList<PhoneNumber> phone = new ArrayList<PhoneNumber>();
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
	public ArrayList<PhoneNumber> getPhone() {
		return this.phone;
	}
	public void setPhone(ArrayList<PhoneNumber> phones) {
		this.phone = phones;
	}
}
