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
import com.norex.gtrax.client.authentication.group.ClientGroup;
import com.norex.gtrax.client.authentication.group.GroupInterface;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Group extends Model implements GroupInterface<Key>, ModelInterface {
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
		this.setDescription(cg.getDescription());
		
		this.getAuthSet().clear();
		for (String s : cg.getAuthSet()) {
			this.getAuthSet().add(KeyFactory.stringToKey(s));
		}
	}
	
	public ClientGroup toClient() {
		ClientGroup cg = new ClientGroup();
		cg.setId(KeyFactory.keyToString(getId()));
		cg.setName(getName());
		cg.setDescription(getDescription());
		
		if (getAuthSet() != null && getAuthSet().iterator().hasNext()) {
			for (Key k : getAuthSet()) {
				cg.getAuthSet().add(KeyFactory.keyToString(k));
			}
		} else {
			cg.setAuthSet(new HashSet<String>());
		}
		
		return cg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}
}
