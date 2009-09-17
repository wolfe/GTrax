package com.norex.gtrax.client.authentication.auth;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;
import com.norex.gtrax.client.authentication.AuthInterface;

public class ClientAuth extends ClientModel implements AuthInterface, ClientModelInterface {
	protected String id;
	
	protected String email;
	protected String firstname;
	protected String lastname;
	
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
	public String getFirstName() {
		return this.firstname;
	}
	public void setFirstName(String name) {
		this.firstname = name;
	}
	public String getLastName() {
		return this.lastname;
	}
	public void setLastName(String name) {
		this.lastname = name;
	}
	
	public String toString() {
		if (getFirstName() != null && getLastName() != null) {
			return getFirstName() + " " + getLastName() + " <" + getEmail() + ">";
		} else {
			return getEmail();
		}
	}
}
