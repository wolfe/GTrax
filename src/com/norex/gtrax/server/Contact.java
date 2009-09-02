package com.norex.gtrax.server;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.contact.ClientContact;
import com.norex.gtrax.client.contact.EmailAddress;
import com.norex.gtrax.client.contact.PhoneNumber;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Contact extends Model {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Key id;
	
	@Persistent
	protected String name;
	
	@Persistent(serialized = "true")
	protected ArrayList<EmailAddress> email = new ArrayList<EmailAddress>();
	
	@Persistent(serialized = "true")
	protected ArrayList<PhoneNumber> phone = new ArrayList<PhoneNumber>();
	
	@Persistent
	protected Company company;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ArrayList<EmailAddress> getEmail() {
		return email;
	}

	public void setEmail(ArrayList<EmailAddress> email) {
		this.email = email;
	}
	
	public ArrayList<PhoneNumber> getPhone() {
		return phone;
	}

	public void setPhone(ArrayList<PhoneNumber> phone) {
		this.phone = phone;
	}

	public void update(ClientContact contact) {
		this.setEmail(contact.getEmail());
		this.setPhone(contact.getPhone());
		this.setName(contact.getName());
	}
	
	public ClientContact toClient() {
		ClientContact tmp = new ClientContact();
		tmp.setName(this.getName());
		tmp.setEmail(this.getEmail());
		tmp.setPhone(this.getPhone());
		tmp.setId(KeyFactory.keyToString(this.getId()));
		
		return tmp;
	}
}
