package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.norex.gtrax.client.auth.ClientContact;

@RemoteServiceRelativePath("contact")
public interface ContactService extends RemoteService {
	public ArrayList<ClientContact> getCompanyContacts();
	public ClientContact create(ClientContact contact);
}
