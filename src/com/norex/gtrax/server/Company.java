package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.auth.ClientCompany;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Company extends Model {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	protected Key id;

	@Persistent
	protected String name = new String();

	@Persistent(mappedBy="company")
	protected ArrayList<Auth> authSet = new ArrayList<Auth>();
	
	@Persistent(mappedBy="company")
	protected ArrayList<Contact> contactSet = new ArrayList<Contact>();
	
	@Persistent(mappedBy="company")
	protected ArrayList<Group> groupSet = new ArrayList<Group>();

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Auth> getAuthSet() {
		return authSet;
	}

	public void setAuthSet(ArrayList<Auth> authSet) {
		this.authSet = authSet;
	}
	
	public ArrayList<Contact> getContactSet() {
		return contactSet;
	}

	public void setContactSet(ArrayList<Contact> contactSet) {
		this.contactSet = contactSet;
	}

	public synchronized ArrayList<Group> getGroupSet() {
		return groupSet;
	}

	public synchronized void setGroupSet(ArrayList<Group> groupSet) {
		this.groupSet = groupSet;
	}

	public ClientCompany toClient() {
		ClientCompany tmp = new ClientCompany();
    	
    	tmp.setName(this.getName());
    	tmp.setId(KeyFactory.keyToString(this.getId()));
    	
    	return tmp;
	}

}
