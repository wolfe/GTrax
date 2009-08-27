package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.server.Contact;

public interface CompanyServiceAsync  {
	public void create(ClientCompany m, AsyncCallback<ClientCompany> async);
    public void getAll(AsyncCallback<ArrayList<ClientCompany>> async);
    public void delete(ClientCompany m, AsyncCallback async);
    public void save(ClientCompany m, AsyncCallback async);
    public void getAuthMembers(ClientCompany m, AsyncCallback<ArrayList<ClientAuth>> async);
    public void getContacts(AsyncCallback<ArrayList<ClientContact>> asyncCallback);
}
