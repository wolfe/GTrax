package com.norex.gtrax.client.contact;

import java.util.ArrayList;
import java.util.Date;

public interface ContactInterface {
	public String getName();
	public void setName(String name);
	public ArrayList<EmailAddress> getEmail();
	public void setEmail(ArrayList<EmailAddress> email);
	public ArrayList<PhoneNumber> getPhone();
	public void setPhone(ArrayList<PhoneNumber> phone);
	public void setWebsite(ArrayList<Website> website);
	public ArrayList<Website> getWebsite();
	public void setNote(String note);
	public String getNote();
	public void setBirthday(Date day);
	public Date getBirthday();
}