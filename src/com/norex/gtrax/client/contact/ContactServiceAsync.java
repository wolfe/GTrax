package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.norex.gtrax.client.project.ClientProject;

public interface ContactServiceAsync {
	public void getContacts(AsyncCallback<ArrayList<ClientContact>> async);
	public void save(ClientContact contact, AsyncCallback<ClientContact> async);
	public void getContactProjects(ClientContact contact, AsyncCallback<ArrayList<ClientProject>> async);
}
