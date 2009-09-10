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
import com.norex.gtrax.client.auth.ClientGroup;
import com.norex.gtrax.client.auth.GroupInterface;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Group extends Model implements GroupInterface, ModelInterface {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Key id;
	
	@Persistent
	protected String name;
	
	@Persistent
	protected String description;
	
	@Persistent
	private Set<Key> authSet = new HashSet<Key>();

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

	public void setAuthSet(Set<Key> authSet) {
		this.authSet = authSet;
	}

	public Set<Key> getAuthSet() {
		return authSet;
	}
	
	public void update(ClientGroup cg) {
		this.setName(cg.getName());
	}
	
	public ClientGroup toClient() {
		ClientGroup cg = new ClientGroup();
		cg.setId(KeyFactory.keyToString(getId()));
		cg.setName(getName());
		
		return cg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}
}
