package com.norex.gtrax.server;

import java.util.HashSet;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.norex.gtrax.client.auth.AuthService;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;
import com.norex.gtrax.client.auth.NotLoggedInException;

public class AuthServiceImpl extends GeneralServiceImpl implements
		AuthService {

	public ClientAuth create(ClientCompany company, ClientAuth m) {
		Auth auth = null;
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Company c = pm.getObjectById(Company.class, company.getId());
		

		Query q = pm.newQuery(Auth.class);
		q.setFilter("email == e");
		q.declareParameters("String e");
		try {
			List<Auth> rs = (List<Auth>) q.execute(m.getEmail());
			
			if (rs.iterator().hasNext()) {
				return null;
			} else {
				// User does not yet exist
				auth = new Auth();
				auth.setEmail(m.getEmail());
				c.getAuthSet().add(auth);
			}
		} finally {
			q.closeAll();
		}
		
		pm.close();
		
		return auth.toClient();
	}

	public static Auth getCurrentUser() throws NotLoggedInException {
		Auth auth = null;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		PersistenceManager pm = PMF.getPersistenceManager();
		Query query = pm.newQuery(Auth.class);
		query.setFilter("email == e");
		query.declareParameters("String e");
		
		List<Auth> rs = (List<Auth>) query.execute(user.getEmail());
		if (rs.iterator().hasNext()) {
			for (Auth a : rs) {
				return a;
			}
		}
		
		NotLoggedInException e = new NotLoggedInException();
		throw e;
	}
	
	public static Company getCurrentCompany() throws NotLoggedInException {
		return (Company)getCurrentUser().getCompany(); 
	}
}
