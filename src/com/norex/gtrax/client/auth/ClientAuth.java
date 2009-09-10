package com.norex.gtrax.client.auth;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;

public class ClientAuth extends ClientModel implements AuthInterface, ClientModelInterface {
	protected String id;
	
	protected String email;
	
	protected String authSubToken;
	protected String authSubURL;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAuthSubToken(String authSubToken) {
		this.authSubToken = authSubToken;
	}
	public String getAuthSubToken() {
		return authSubToken;
	}
	public void setAuthSubURL(String authSubURL) {
		this.authSubURL = authSubURL;
	}
	public String getAuthSubURL() {
		return authSubURL;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
