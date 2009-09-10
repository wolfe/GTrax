package com.norex.gtrax.client.auth;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	public ClientAuth create(ClientAuth a);
	public void delete(ClientAuth a);
	public ClientAuth exchangeAuthSubToken(String token);
	public ClientAuth login(String url) throws NotLoggedInException;
}
