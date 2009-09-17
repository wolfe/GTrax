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
import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.authentication.AuthInterface;
import com.norex.gtrax.client.authentication.auth.ClientAuth;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Auth extends Model implements AuthInterface {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Key id;
	
	@Persistent
	private String email;
	
	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private String authSubToken;
	
	@Persistent 
	private Set<Key> groupSet = new HashSet<Key>();

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}
	
	public void setGroupSet(Set<Key> groupSet) {
		this.groupSet = groupSet;
	}

	public Set<Key> getGroupSet() {
		return groupSet;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
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
		tmp.setEmail(getEmail());
		tmp.setFirstName(getFirstName());
		tmp.setLastName(getLastName());
		tmp.setAuthSubToken(this.getAuthSubToken());
		
		return tmp;
		
	}
	
	public void update(ClientAuth a) {
		setEmail(a.getEmail());
		setAuthSubToken(a.getAuthSubToken());
		setFirstName(a.getFirstName());
		setLastName(a.getLastName());
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

}
