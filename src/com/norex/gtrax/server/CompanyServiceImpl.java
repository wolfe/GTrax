package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.core.client.GWT;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;
import com.norex.gtrax.client.auth.ClientContact;
import com.norex.gtrax.client.auth.CompanyService;
import com.norex.gtrax.client.auth.NotLoggedInException;

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

	
	public ArrayList<ClientAuth> getAuthMembers(ClientCompany m) {
		PersistenceManager pm = PMF.getPersistenceManager();
		ArrayList<ClientAuth> auths = new ArrayList<ClientAuth>();
		try {
			Company e = pm.getObjectById(Company.class, m.getId());
			
			if (e.getAuthSet().iterator().hasNext()) {
				for (Auth a : e.getAuthSet()) {
					auths.add(a.toClient());
				}
			}
		} finally {
			pm.close();
		}
		
		return auths;
	}


	@Override
	public ClientAuth login(String url) throws NotLoggedInException {
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			NotLoggedInException e = new NotLoggedInException();
			e.setLoginURL(userService.createLoginURL(url));
			throw e;
		}
		
		User user = userService.getCurrentUser();
		
		try {
			return AuthServiceImpl.getCurrentUser().toClient();
		} catch (Exception exception){
			NotLoggedInException e = new NotLoggedInException();
			e.setLoginURL(userService.createLoginURL(url));
			throw e;
		}
	}
	
	

}
