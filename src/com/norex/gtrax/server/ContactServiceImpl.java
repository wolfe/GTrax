package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.norex.gtrax.client.auth.AuthService;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;
import com.norex.gtrax.client.auth.ClientContact;
import com.norex.gtrax.client.auth.NotLoggedInException;
import com.norex.gtrax.client.contact.ContactService;

public class ContactServiceImpl extends GeneralServiceImpl implements
		ContactService {

	@Override
	public ArrayList<ClientContact> getCompanyContacts() {
		try {
			Auth u = AuthServiceImpl.getCurrentUser();
			
			ArrayList<ClientContact> list = new ArrayList<ClientContact>();
			
			for (Contact c : u.getCompany().getContactSet()) {
				list.add(c.toClient());
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ClientContact save(ClientContact contact) {
		Company company = null;
		Contact c = null;
		
		try {
			company = AuthServiceImpl.getCurrentCompany();
		} catch (NotLoggedInException e) {
			return null;
		}
		
		if (contact.getId() != null) {
			PersistenceManager pm = PMF.getPersistenceManager();
			c = pm.getObjectById(Contact.class, contact.getId());
			c.update(contact);
			pm.close();
			
			return c.toClient();
		} else {
			c = new Contact();
			c.update(contact);
			
			company.getContactSet().add(c);
			
			return c.toClient();
		}
	}

}
