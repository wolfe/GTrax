package com.norex.gtrax.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.Birthday;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.Website.Rel;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.model.gd.Namespaces;
import com.google.gdata.util.ServiceException;
import com.norex.gtrax.client.contact.ClientContact;
import com.norex.gtrax.client.contact.ContactInterface;
import com.norex.gtrax.client.contact.EmailAddress;
import com.norex.gtrax.client.contact.PhoneNumber;
import com.norex.gtrax.client.contact.Website;
import com.norex.gtrax.client.contact.EmailAddress.EmailAddressType;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Contact extends Model implements ContactInterface {
	
	ContactsService svc = new ContactsService("norex-gtrax-1");
	
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
	
	private String gdataURL;
	
	public Contact() {
		super();
		svc.setAuthSubToken(AuthServiceImpl.getCurrentUser().getAuthSubToken(), null);
	}

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
	
	public void setGdataURL(String gdataURL) {
		this.gdataURL = gdataURL;
	}

	public String getGdataURL() {
		return gdataURL;
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
		
		updateGDataContact();
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
	
	private ContactEntry getGDataContactFromFeedURL() throws MalformedURLException, IOException, ServiceException {
		if (getGdataURL() == null) return new ContactEntry();
		
		return svc.getEntry(new URL(getGdataURL().replace("/base/", "/full/")), ContactEntry.class);
	}
	
	public ContactEntry updateGDataContact() {
		UserService userService = UserServiceFactory.getUserService();
		
		ContactEntry entry;
		try {
			entry = getGDataContactFromFeedURL();
		} catch (Exception e) {
			return null;
		}
		
		Name name = new Name();
		FullName fullname = new FullName();
		fullname.setValue(getName());
		name.setFullName(fullname);
		entry.setName(name);

		entry.getEmailAddresses().clear();
		if (getEmail().iterator().hasNext()) {
			for (EmailAddress e : getEmail()) {
				Email email = new Email();
				email.setAddress(e.getAddress());
				String type = Namespaces.gPrefix + e.getType().name();
				
				email.setRel(type);
				
				entry.addEmailAddress(email);
			}
		}
		
		entry.getPhoneNumbers().clear();
		if (getPhone().iterator().hasNext()) {
			for (PhoneNumber p : getPhone()) {
				com.google.gdata.data.extensions.PhoneNumber phone = new com.google.gdata.data.extensions.PhoneNumber();
				phone.setPhoneNumber(p.getNumber());
				phone.setRel(Namespaces.gPrefix + p.getType().name());
				
				entry.addPhoneNumber(phone);
			}
		}
		
		entry.getWebsites().clear();
		if (getWebsite().iterator().hasNext()) {
			for (Website w : getWebsite()) {
				com.google.gdata.data.contacts.Website website = new com.google.gdata.data.contacts.Website();
				website.setHref(w.getAddress());
				
				com.google.gdata.data.contacts.Website.Rel rel = null;
				if (w.getType().name() == "home") {
					rel = com.google.gdata.data.contacts.Website.Rel.HOME;
				} else if (w.getType().name() == "work") {
					rel = com.google.gdata.data.contacts.Website.Rel.WORK;
				} else if (w.getType().name() == "blog") {
					rel = com.google.gdata.data.contacts.Website.Rel.BLOG;
				} else if (w.getType().name() == "profile") {
					rel = com.google.gdata.data.contacts.Website.Rel.PROFILE;
				}  else if (w.getType().name() == "other") {
					rel = com.google.gdata.data.contacts.Website.Rel.OTHER;
				}
				website.setRel(rel);
				
				entry.addWebsite(website);
			}
		}
		
		if (getBirthday() != null) {
			Birthday birthday = new Birthday();
			birthday.setWhen(getBirthday().toString());
			entry.setBirthday(birthday);
		}

		try {
			User user = userService.getCurrentUser();
			URL url = new URL("http://www.google.com/m8/feeds/contacts/" + user.getAuthDomain() + "/full");
			
			if (getGdataURL() == null) {
				entry = svc.insert(url, entry);
			} else {
				entry.update();
			}
		} catch (Exception e) {
			return null;
		}
		
		setGdataURL(entry.getId());
		
		return entry;
	}
}
