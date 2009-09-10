package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.norex.gtrax.client.ClientModel;

public interface AuthServiceAsync {
	public void create(ClientCompany c, ClientAuth m, AsyncCallback<ClientAuth> async);
	public void exchangeAuthSubToken(String token, AsyncCallback<ClientAuth> async);
}
