package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;
import com.norex.gtrax.client.auth.ClientContact;
import com.norex.gtrax.client.auth.CompanyService;

public class CompanyServiceImpl extends GeneralServiceImpl implements
		CompanyService {

	public ClientCompany create(ClientCompany m) {
		Company c = new Company();
		c.setName(m.getName());
		c.save();

		return (ClientCompany)c.toClient();
	}

	
	public ArrayList<ClientCompany> getAll() {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		ArrayList<ClientCompany> list = new ArrayList<ClientCompany>();
		Query query = pm.newQuery(Company.class);
		
		try {
			List<Company> results = (List<Company>) query.execute();
			if (results.iterator().hasNext()) {
	            for (Company e : results) {
	            	list.add(e.toClient());
	            }
	        }
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return list;
	}
	
	
	public void delete(ClientCompany m) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Company e = pm.getObjectById(Company.class, m.getId());
			pm.deletePersistent(e);
		} finally {
			pm.close();
		}
	}

	
	public void save(ClientCompany m) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Company e = pm.getObjectById(Company.class, m.getId());
			e.setName(m.getName());
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}

	
	public ArrayList<ClientContact> getContacts() {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		ArrayList<ClientContact> list = new ArrayList<ClientContact>();
		Query query = pm.newQuery(Contact.class);
		
		try {
			List<Contact> results = (List<Contact>) query.execute();
			if (results.iterator().hasNext()) {
	            for (Contact e : results) {
	            	list.add(e.toClient());
	            }
	        }
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return list;
	}

	
	public ArrayList<ClientAuth> getAuthMembers(ClientCompany m) {
		PersistenceManager pm = PMF.getPersistenceManager();
		ArrayList<ClientAuth> auths = new ArrayList<ClientAuth>();
		try {
			Company e = pm.getObjectById(Company.class, m.getId());
			for (Auth a : e.getAuthSet()) {
				auths.add(a.toClient());
			}
		} finally {
			pm.close();
		}
		
		return auths;
	}


	@Override
	public ClientCompany login() {
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
		}
		
		User user = userService.getCurrentUser();
		
		return new ClientCompany();
	}
	
	

}
