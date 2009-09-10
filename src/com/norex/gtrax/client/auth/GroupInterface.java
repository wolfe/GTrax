package com.norex.gtrax.client.auth;

import com.norex.gtrax.client.ClientModelInterface;

public interface GroupInterface extends ClientModelInterface {
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String desc);
}