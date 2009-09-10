package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.norex.gtrax.client.ClientModel;

public interface AuthServiceAsync {
	public void create(ClientAuth auth, AsyncCallback<ClientAuth> async);
	public void delete(ClientAuth auth, AsyncCallback async);
	public void exchangeAuthSubToken(String token, AsyncCallback<ClientAuth> async);
	public void login(String url, AsyncCallback<ClientAuth> async);
}
