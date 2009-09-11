package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.contact.ClientContact;
import com.norex.gtrax.client.contact.ContactInterface;
import com.norex.gtrax.client.contact.EmailAddress;
import com.norex.gtrax.client.contact.PhoneNumber;
import com.norex.gtrax.client.contact.Website;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Contact extends Model implements ContactInterface, ModelInterface {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Key id;
	
	@Persistent
	protected String name;
	
	@Persistent(serialized = "true")
	protected ArrayList<EmailAddress> email = new ArrayList<EmailAddress>();
	
	@Persistent(serialized = "true")
	protected ArrayList<PhoneNumber> phone = new ArrayList<PhoneNumber>();
	
	@Persistent(serialized = "true")
	protected ArrayList<Website> website = new ArrayList<Website>();
	
	@Persistent
	private String note;
	
	@Persistent
	protected Key picture;
	
	@Persistent
	protected Date birthday;

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
	
	public Key getPicture() {
		return this.picture;
	}
	
	public void setPicture(Key pic) {
		this.picture = pic;
	}

	public void setWebsite(ArrayList<Website> website) {
		this.website = website;
	}

	public ArrayList<Website> getWebsite() {
		return website;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date day) {
		this.birthday = day;
	}
	
	public void update(ClientContact contact) {
		this.setEmail(contact.getEmail());
		this.setPhone(contact.getPhone());
		this.setName(contact.getName());
		this.setWebsite(contact.getWebsite());
		this.setNote(contact.getNote());
		this.setBirthday(contact.getBirthday());
		
		if (contact.getPictureBlobKey() != null) {
			this.setPicture(KeyFactory.stringToKey(contact.getPictureBlobKey()));
		}
	}
	
	public ClientContact toClient() {
		ClientContact tmp = new ClientContact();
		tmp.setName(this.getName());
		tmp.setEmail(this.getEmail());
		tmp.setPhone(this.getPhone());
		tmp.setWebsite(this.getWebsite());
		tmp.setId(KeyFactory.keyToString(this.getId()));
		tmp.setNote(this.getNote());
		tmp.setBirthday(this.getBirthday());
		
		if (this.getPicture() != null) {
			tmp.setPictureBlobKey(KeyFactory.keyToString(this.getPicture()));
		}
		
		return tmp;
	}
}
