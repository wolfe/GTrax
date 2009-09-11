package com.norex.gtrax.client.authentication;

import com.norex.gtrax.client.ClientModelInterface;
import com.norex.gtrax.server.ModelInterface;

public interface AuthInterface extends ClientModelInterface {
	public abstract void setEmail(String email);
	public abstract String getEmail();
	public abstract void setAuthSubToken(String authSubToken);
	public abstract String getAuthSubToken();

}