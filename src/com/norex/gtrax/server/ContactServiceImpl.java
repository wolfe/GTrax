package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.norex.gtrax.client.authentication.NotLoggedInException;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.contact.ClientContact;
import com.norex.gtrax.client.contact.ContactService;
import javax.jdo.Query;


public class ContactServiceImpl extends GeneralServiceImpl implements
		ContactService {

	@Override
	public ArrayList<ClientContact> getContacts() {
		PersistenceManager pm = PMF.getPersistenceManager();
		Query query = pm.newQuery(Contact.class);
		
		ArrayList<ClientContact> list = new ArrayList<ClientContact>();
		
		try {
			List<Contact> results = (List<Contact>) query.execute();
			if (!results.iterator().hasNext()) return null;
			
			for (Contact a : results) {
				list.add(a.toClient());
			}
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return list;
	}

	@Override
	public ClientContact save(ClientContact contact) {
		Contact c = null;

		PersistenceManager pm = PMF.getPersistenceManager();
		
		if (contact.getId() != null) {
			c = pm.getObjectById(Contact.class, contact.getId());
		} else {
			c = new Contact();
			pm.makePersistent(c);
		}
		
		c.update(contact);
		
		pm.close();
		return c.toClient();
	}

}
