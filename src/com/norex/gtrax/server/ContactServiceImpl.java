package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.norex.gtrax.client.auth.AuthService;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;
import com.norex.gtrax.client.auth.ClientContact;
import com.norex.gtrax.client.contact.ContactService;

public class ContactServiceImpl extends GeneralServiceImpl implements
		ContactService {

	@Override
	public ArrayList<ClientContact> getCompanyContacts() {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		ArrayList<ClientContact> list = new ArrayList<ClientContact>();
		Query query = pm.newQuery(Contact.class);
		try {
			List<Contact> rs = (List<Contact>) query.execute();
			for (Contact c : rs) {
				list.add(c.toClient());
			}
		} finally {
			pm.close();
		}
		
		return list;
	}

}
