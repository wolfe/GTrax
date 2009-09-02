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
import com.norex.gtrax.client.group.ClientGroup;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Group extends Model {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	protected Key id;
	
	@Persistent
	protected String name;
	
	@Persistent
	protected Company company;
	
	@Persistent
	protected Set<Key> authSet = new HashSet<Key>();

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Key> getAuthSet() {
		return authSet;
	}

	public void setAuthSet(Set<Key> authSet) {
		this.authSet = authSet;
	}
	
	public void update(ClientGroup group) {
		this.setName(group.getName());
	}
	
	public ClientGroup toClient() {
		ClientGroup tmp = new ClientGroup();
		tmp.setId(KeyFactory.keyToString(this.getId()));
		tmp.setName(this.getName());
		
		return tmp;
	}
}
