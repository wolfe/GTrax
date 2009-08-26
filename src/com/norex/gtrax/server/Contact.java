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
	protected String email;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public ClientContact toClient() {
		ClientContact tmp = new ClientContact();
		tmp.setEmail(this.getEmail());
		tmp.setId(KeyFactory.keyToString(this.getId()));
		
		return tmp;
	}
}
