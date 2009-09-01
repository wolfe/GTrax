package com.norex.gtrax.client.auth;

import java.io.Serializable;

public class NotLoggedInException extends Exception implements Serializable {
	private String loginURL;

	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}
}
