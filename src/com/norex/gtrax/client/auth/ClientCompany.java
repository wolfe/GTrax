package com.norex.gtrax.client.auth;

import java.util.ArrayList;
import java.util.List;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.server.Auth;

public class ClientCompany extends ClientModel {
	
	private String id;
	private String name = new String();
	private List<ClientAuth> authSet = new ArrayList<ClientAuth>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ClientAuth> getAuthSet() {
		return authSet;
	}
	public void setAuthSet(List<ClientAuth> list) {
		this.authSet = list;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
}
