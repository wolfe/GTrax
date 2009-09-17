package com.norex.gtrax.client.authentication;


public interface AuthInterface {
	public void setEmail(String email);
	public String getEmail();
	public void setAuthSubToken(String authSubToken);
	public String getAuthSubToken();
	public void setFirstName(String name);
	public String getFirstName();
	public void setLastName(String name);
	public String getLastName();
}