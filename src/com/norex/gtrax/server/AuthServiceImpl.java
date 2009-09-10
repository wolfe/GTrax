package com.norex.gtrax.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.util.AuthenticationException;
import com.norex.gtrax.client.auth.AuthService;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.NotLoggedInException;

public class AuthServiceImpl extends GeneralServiceImpl implements
		AuthService {

	public ClientAuth create(ClientAuth m) {
		Auth auth = null;
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Key k = KeyFactory.createKey(Auth.class.getSimpleName(), m.getEmail());
		
		
		try {
			auth = (Auth) pm.getObjectId(k);
		} catch (Exception e) {
			auth = new Auth();
			auth.setId(k);
			auth.setEmail(m.getEmail());
		} finally {
			pm.close();
		}
		
		return auth.toClient();
	}

	public static Auth getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Key k = KeyFactory.createKey(Auth.class.getSimpleName(), user.getEmail());
		Auth auth = pm.getObjectById(Auth.class, k);
		
		pm.close();
		
		return auth;
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

	@Override
	public void delete(ClientAuth a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientAuth login(String url) throws NotLoggedInException {
		UserService userService = UserServiceFactory.getUserService();

		PersistenceManager pm = PMF.getPersistenceManager();
		
		try {
			@SuppressWarnings("unused")
			User user = userService.getCurrentUser();
			
			Auth a = getCurrentUser();
			ClientAuth ca = a.toClient();
			ca.setAuthSubURL(AuthSubUtil.getRequestUrl(url, "http://www.google.com/m8/feeds/", false, true));
			return ca;
		} catch (JDOObjectNotFoundException e) {
			User u = userService.getCurrentUser();
			
			Key k = KeyFactory.createKey(Auth.class.getSimpleName(), u.getEmail());
			Auth a = new Auth();
			a.setId(k);
			a.setEmail(u.getEmail());
			pm.makePersistent(a);
			
			
			ClientAuth ca = a.toClient();
			
			ca.setAuthSubURL(AuthSubUtil.getRequestUrl(url, "http://www.google.com/m8/feeds/", false, true));
			
			return ca;
		} catch (Exception e) {
			NotLoggedInException nli = new NotLoggedInException();
			nli.setLoginURL(userService.createLoginURL(url));
			throw nli;
		} finally {
			pm.close();
		}
	}
}
