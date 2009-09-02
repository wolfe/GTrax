package com.norex.gtrax.client.group;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.auth.ClientCompany;

public class ClientGroup extends ClientModel {
	protected String id;
	protected String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
