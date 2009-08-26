package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.norex.gtrax.client.ClientModel;

@RemoteServiceRelativePath("auth")
public interface AuthService extends RemoteService {
	public ClientAuth create(ClientCompany c, ClientAuth m);
}
