package com.norex.gtrax.client.project;

import com.norex.gtrax.client.ClientModel;

public class ClientProject extends ClientModel implements ProjectInterface<String> {
	
	protected String id;
	protected String name;
	protected String contactKey;
	protected String description;
	protected boolean status;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String key) {
		this.id = key;
	}

	public String getContactKey() {
		return this.contactKey;
	}

	public void setContactKey(String key) {
		this.contactKey = key;
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
