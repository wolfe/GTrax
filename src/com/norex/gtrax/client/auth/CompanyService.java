package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.norex.gtrax.server.Contact;

@RemoteServiceRelativePath("company")
public interface CompanyService extends RemoteService {
	public ClientCompany create(ClientCompany m);
    public ArrayList<ClientCompany> getAll();
    public void delete(ClientCompany m);
    public void save(ClientCompany m);
    public ArrayList<ClientAuth> getAuthMembers(ClientCompany m);
    public ArrayList<ClientContact> getContacts();
}
