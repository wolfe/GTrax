package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.norex.gtrax.client.auth.ClientContact;

public interface ContactServiceAsync {
	public void getCompanyContacts(AsyncCallback<ArrayList<ClientContact>> async);
	public void save(ClientContact contact, AsyncCallback<ClientContact> async);
}
