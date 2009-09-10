package com.norex.gtrax.client.contact;

import java.util.ArrayList;


public interface ContactInterface {
	public abstract String getName();
	public abstract void setName(String name);
	public abstract ArrayList<EmailAddress> getEmail();
	public abstract void setEmail(ArrayList<EmailAddress> email);
	public abstract ArrayList<PhoneNumber> getPhone();
	public abstract void setPhone(ArrayList<PhoneNumber> phone);
	public abstract void setWebsite(ArrayList<Website> website);
	public abstract ArrayList<Website> getWebsite();
	public abstract void setNote(String note);
	public abstract String getNote();
}