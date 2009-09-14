package com.norex.gtrax.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.project.ClientProject;
import com.norex.gtrax.client.project.ProjectInterface;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Project extends Model implements ProjectInterface<Key> {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	protected Key id;
	
	@Persistent
	protected String name;
	
	@Persistent
	private Key contactKey;
	
	@Persistent
	private String description;
	
	@Persistent 
	private boolean status = true;

	public ClientProject toClient() {
		ClientProject p = new ClientProject();
		p.setId(KeyFactory.keyToString(getId()));
		p.setName(getName());
		p.setContactKey(KeyFactory.keyToString(getContactKey()));
		p.setDescription(getDescription());
		p.setStatus(getStatus());
		
		return p;
	}
	
	public void update(ClientProject p) {
		setName(p.getName());
		setContactKey(KeyFactory.stringToKey(p.getContactKey()));
		setDescription(p.getDescription());
		setStatus(p.getStatus());
	}

	public Key getId() {
		return this.id;
	}

	public void setId(Key key) {
		this.id = key;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContactKey(Key contactKey) {
		this.contactKey = contactKey;
	}

	public Key getContactKey() {
		return contactKey;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean flag) {
		this.status = flag;
	}

}
