package com.norex.gtrax.client.authentication;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.norex.gtrax.client.GenericService;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.authentication.group.ClientGroup;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	public ArrayList<ClientAuth> getAll();
	public ArrayList<ClientAuth> getAll(String search);
	public ClientAuth create(ClientAuth a);
	public ClientAuth save(ClientAuth a);
	public void delete(ClientAuth a);
	public ClientAuth exchangeAuthSubToken(String token);
	public ClientAuth login(String url) throws NotLoggedInException;
	
	public ClientGroup saveGroup(ClientGroup group);
	public ArrayList<ClientGroup> getGroups();
	public ClientAuth addAuthToGroup(ClientAuth auth, ClientGroup group);
	public ArrayList<ClientAuth> getGroupMembers(ClientGroup group);
	public void deleteGroup(ClientGroup group);
	
	public ArrayList<String> getAllPermissions();
}
