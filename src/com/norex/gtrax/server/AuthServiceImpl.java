package com.norex.gtrax.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;


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
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.NotLoggedInException;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.authentication.group.ClientGroup;

public class AuthServiceImpl extends GeneralServiceImpl implements
		AuthService {

	public ClientAuth create(ClientAuth m) {
		return save(m);
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

	public void delete(ClientAuth a) {
		// TODO Auto-generated method stub
		
	}

	public ClientAuth login(String url) throws NotLoggedInException {
		Permission.setUpPermissions();
		
		UserService userService = UserServiceFactory.getUserService();

		try {
			@SuppressWarnings("unused")
			User user = userService.getCurrentUser();
			
			Auth a = getCurrentUser();
			ClientAuth ca = a.toClient();
			ca.setAuthSubURL(AuthSubUtil.getRequestUrl(url, "http://www.google.com/m8/feeds/", false, true));
			
			XMPP.sendMessage("chris@norex.ca", a.getEmail() + " just logged in from " + url);
			
			return ca;
		} catch (JDOObjectNotFoundException e) {
			PersistenceManager pm = PMF.getPersistenceManager();
			
			User u = userService.getCurrentUser();
			
			Key k = KeyFactory.createKey(Auth.class.getSimpleName(), u.getEmail());
			Auth a = new Auth();
			a.setId(k);
			a.setEmail(u.getEmail());
			pm.makePersistent(a);
			
			pm.close();
			
			ClientAuth ca = a.toClient();
			
			ca.setAuthSubURL(AuthSubUtil.getRequestUrl(url, "http://www.google.com/m8/feeds/", false, true));
			
			XMPP.sendMessage("chris@norex.ca", a.getEmail() + " just created a user account.");
			
			return ca;
		} catch (Exception e) {
			NotLoggedInException nli = new NotLoggedInException();
			nli.setLoginURL(userService.createLoginURL(url));
			throw nli;
		}
	}

	public ClientGroup saveGroup(ClientGroup group) {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Group g;
		
		if (group.getId() == null) {
			g = new Group();
			pm.makePersistent(g);
		} else {
			g = pm.getObjectById(Group.class, group.getId());
		}
		g.update(group);
		
		pm.close();
		
		return g.toClient();
	}

	public ArrayList<ClientGroup> getGroups() {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Query query = pm.newQuery(Group.class);
		
		ArrayList<ClientGroup> list = new ArrayList<ClientGroup>();
		
		try {
			List<Group> results = (List<Group>) query.execute();
			if (!results.iterator().hasNext()) return null;
			
			for (Group g : results) {
				list.add(g.toClient());
			}
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return list;
	}

	public ClientAuth addAuthToGroup(ClientAuth auth, ClientGroup group) {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Auth a = pm.getObjectById(Auth.class, auth.getId());
		Group g = pm.getObjectById(Group.class, group.getId());
		
		if (g.getAuthSet() == null) {
			g.setAuthSet(new HashSet<Key>());
		}
		
		if (a.getGroupSet() == null) a.setGroupSet(new HashSet<Key>());
		
		g.getAuthSet().add(a.getId());
		a.getGroupSet().add(g.getId());
		
		pm.close();
		
		return a.toClient();
	}

	public ArrayList<ClientAuth> getGroupMembers(ClientGroup group) {
		PersistenceManager pm = PMF.getPersistenceManager();
		Group g = pm.getObjectById(Group.class, group.getId());
		
		if (g.getAuthSet() == null) {
			g.setAuthSet(new HashSet<Key>());
		}
		
		ArrayList<ClientAuth> list = new ArrayList<ClientAuth>();
		
		for (Key k : g.getAuthSet()) {
			Auth a = pm.getObjectById(Auth.class, k);
			list.add(a.toClient());
		}
		
		pm.close();
		
		return list;
	}

	public void deleteGroup(ClientGroup group) {
		PersistenceManager pm = PMF.getPersistenceManager();
		Group g = pm.getObjectById(Group.class, group.getId());
		
		pm.deletePersistent(g);
		
		pm.close();
	}

	public ArrayList<ClientAuth> getAll() {
		return getAll(Auth.class);
	}

	public ClientAuth save(ClientAuth a) {
		Auth auth = null;
		
		PersistenceManager pm = PMF.getPersistenceManager();
		
		if (a.getId() != null) {
			auth = pm.getObjectById(Auth.class, a.getId());
		} else {
			auth = new Auth();
			pm.makePersistent(auth);
		}
		
		auth.update(a);
		
		pm.close();
		
		return auth.toClient();
	}

	public ArrayList<ClientAuth> getAll(String search) {
		PersistenceManager pm = PMF.getPersistenceManager();
		Query query = pm.newQuery();
		//query.setFilter(arg0);
		
		try {
			
		} finally {
			pm.close();
		}
		return null;
	}

	public ArrayList<String> getAllPermissions() {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Query query = pm.newQuery(Permission.class);
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			List<Permission> results = (List<Permission>) query.execute();
			if (!results.iterator().hasNext()) return null;
			
			for (Permission g : results) {
				list.add(g.getId().getName());
			}
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return list;
	}

}
