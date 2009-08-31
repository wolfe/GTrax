package com.norex.gtrax.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.auth.ClientContact;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Contact extends Model {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Key id;
	
	@Persistent
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Persistent
	protected String email;
	
	@Persistent
	protected Company company;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public ClientContact toClient() {
		ClientContact tmp = new ClientContact();
		tmp.setName(this.getName());
		tmp.setEmail(this.getEmail());
		//tmp.setCompany(this.getCompany().toClient());
		tmp.setId(KeyFactory.keyToString(this.getId()));
		
		return tmp;
	}
}
