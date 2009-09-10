package com.norex.gtrax.client.auth;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;

public class ClientGroup extends ClientModel implements GroupInterface, ClientModelInterface {

	private String id;
	private String name;
	private String desc;
	
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

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public void setDescription(String desc) {
		this.desc = desc;
	}

}
