package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.authentication.NotLoggedInException;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.contact.ClientContact;
import com.norex.gtrax.client.contact.ContactService;
import com.norex.gtrax.client.project.ClientProject;

import javax.jdo.Query;


public class ContactServiceImpl extends GeneralServiceImpl implements
		ContactService {

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

	public ArrayList<ClientProject> getContactProjects(ClientContact contact) {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Query query = pm.newQuery(Project.class);
		query.setFilter("contactKey == ckey");
		query.declareParameters("com.google.appengine.api.datastore.Key ckey");

		ArrayList<ClientProject> projects = new ArrayList<ClientProject>();
		
		try {
			List<Project> rs = (List<Project>) query.execute(KeyFactory.stringToKey(contact.getId()));
			for (Project p : rs) {
				projects.add(p.toClient());
			}
		} finally {
			query.closeAll();
			pm.close();
		}
		
		
		return projects;
	}

}
