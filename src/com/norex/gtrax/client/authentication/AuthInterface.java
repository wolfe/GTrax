package com.norex.gtrax.client.authentication;


public interface AuthInterface {
	public abstract void setEmail(String email);
	public abstract String getEmail();
	public abstract void setAuthSubToken(String authSubToken);
	public abstract String getAuthSubToken();

}