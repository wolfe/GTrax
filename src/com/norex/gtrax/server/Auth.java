package com.norex.gtrax.server;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.auth.ClientAuth;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Auth extends Model {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Key id;
	
	@Persistent
	protected String email;
	
	@Persistent
	protected Company company;
	
	@Persistent
	private String authSubToken;

	public Company getCompany() {
		return company;
	}

	public void setCompanySet(Company company) {
		this.company = company;
	}

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
	
	public void setAuthSubToken(String authSubToken) {
		this.authSubToken = authSubToken;
	}

	public String getAuthSubToken() {
		return authSubToken;
	}

	public ClientAuth toClient() {
		ClientAuth tmp = new ClientAuth();
		
		tmp.setId(KeyFactory.keyToString(this.getId()));
		tmp.setEmail(this.getEmail());
		tmp.setAuthSubToken(this.getAuthSubToken());
		
		return tmp;
		
	}
	
	
}
