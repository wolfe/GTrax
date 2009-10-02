package com.norex.gtrax.client.authentication.group;

import java.util.HashSet;
import java.util.Set;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;

public class ClientGroup extends ClientModel implements GroupInterface<String>, ClientModelInterface {

	private String id;
	private String name;
	private String desc;
	private Set<String> authSet = new HashSet<String>();
	private Set<String> permSet = new HashSet<String>();
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return desc;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}

	public Set<String> getAuthSet() {
		return this.authSet;
	}

	public void setAuthSet(Set<String> set) {
		this.authSet = set;
	}

	public void setPermSet(Set<String> permSet) {
		this.permSet = permSet;
	}

	public Set<String> getPermSet() {
		return permSet;
	}

}
