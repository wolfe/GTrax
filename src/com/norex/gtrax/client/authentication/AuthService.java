package com.norex.gtrax.client.authentication;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.authentication.group.ClientGroup;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	public ClientAuth create(ClientAuth a);
	public void delete(ClientAuth a);
	public ClientAuth exchangeAuthSubToken(String token);
	public ClientAuth login(String url) throws NotLoggedInException;
	
	public ClientGroup createGroup(ClientGroup group);
	public ArrayList<ClientGroup> getGroups();
	public ClientAuth addAuthToGroup(String auth, ClientGroup group);
	public ArrayList<ClientAuth> getGroupMembers(ClientGroup group);
	public void deleteGroup(ClientGroup group);
}
