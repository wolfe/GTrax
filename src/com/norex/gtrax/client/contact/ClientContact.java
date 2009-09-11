package com.norex.gtrax.client.contact;

import java.util.ArrayList;
import java.util.Date;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;

public class ClientContact extends ClientModel implements ContactInterface, ClientModelInterface {
	protected String id;
	protected String name;
	protected ArrayList<EmailAddress> email = new ArrayList<EmailAddress>();
	protected ArrayList<PhoneNumber> phone = new ArrayList<PhoneNumber>();
	private ArrayList<Website> website = new ArrayList<Website>();
	protected String pictureBlobKey;
	private String note;
	private Date birthday;
	
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
	public String getPictureBlobKey() {
		return pictureBlobKey;
	}
	public void setPictureBlobKey(String pictureBlobKey) {
		this.pictureBlobKey = pictureBlobKey;
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
		return birthday;
	}
	public void setBirthday(Date day) {
		this.birthday = day;
	}
}
