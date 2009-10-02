package com.norex.gtrax.server;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.ClientModel;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Permission extends Model {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	public ClientModel toClient() {
		// TODO Auto-generated method stub
		return null;
	}

	public Key getId() {
		return this.id;
	}

	public void setId(Key key) {
		this.id = key;
	}

	public static void setUpPermissions() {
		String[] perms = {"admin", "manage projects", "manage contacts", "manage groups", "log timesheet"};
		
		for (String perm : perms) {
			if (getPermissionsByName(perm) == null) createPermissionByName(perm);
		}
	}
	
	public static void createPermissionByName(String name) {
		PersistenceManager pmf = PMF.getPersistenceManager();
		
		Permission p = new Permission();
		p.setId(KeyFactory.createKey(Permission.class.getSimpleName(), name));
		
		pmf.makePersistent(p);
		
		pmf.close();
	}

	public static Permission getPermissionsByName(String name) {
		PersistenceManager pmf = PMF.getPersistenceManager();
		
		Permission p = null;
		
		try {
			p = pmf.getObjectById(Permission.class, KeyFactory.createKey(Permission.class.getSimpleName(), name));
		} catch (Exception e) {
			// do nothing;
		} finally {
			pmf.close();
		}
		
		return p;
	}
}
