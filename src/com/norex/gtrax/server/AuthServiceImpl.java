package com.norex.gtrax.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.util.AuthenticationException;
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
				pm.close();
				return a;
			}
		}
		
		NotLoggedInException e = new NotLoggedInException();
		throw e;
	}
	
	public static Company getCurrentCompany() throws NotLoggedInException {
		return (Company)getCurrentUser().getCompany(); 
	}

	public ClientAuth exchangeAuthSubToken(String token) {
		try {
			Auth a = getCurrentUser();
			String sessionToken = AuthSubUtil.exchangeForSessionToken(token, null);
			
			PersistenceManager pm = PMF.getPersistenceManager();
			Auth auth = pm.getObjectById(Auth.class, a.getId());
			auth.setAuthSubToken(sessionToken);
			pm.close();
			return auth.toClient();
		} catch (NotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
