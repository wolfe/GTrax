package com.norex.gtrax.client.auth;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	public ClientAuth create(ClientCompany c, ClientAuth m);
	public ClientAuth exchangeAuthSubToken(String token);
}
