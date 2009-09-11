package com.norex.gtrax.client.authentication;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.authentication.group.ClientGroup;

public interface AuthServiceAsync {
	public void create(ClientAuth auth, AsyncCallback<ClientAuth> async);
	public void delete(ClientAuth auth, AsyncCallback async);
	public void exchangeAuthSubToken(String token, AsyncCallback<ClientAuth> async);
	public void login(String url, AsyncCallback<ClientAuth> async);
	
	public void createGroup(ClientGroup group, AsyncCallback<ClientGroup> async);
	public void getGroups(AsyncCallback<ArrayList<ClientGroup>> async);
	public void addAuthToGroup(String auth, ClientGroup group, AsyncCallback<ClientAuth> async);
	public void getGroupMembers(ClientGroup group, AsyncCallback<ArrayList<ClientAuth>> async);
}
