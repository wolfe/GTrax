package com.norex.gtrax.server;

import java.lang.reflect.Field;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.ClientModel;

public class Model {

    public void save() {
		PersistenceManager pmf = PMF.getPersistenceManager();
		pmf.makePersistent(this);
		pmf.close();
    }
    
    public ClientModel toClient() {
		ClientModel r = new ClientModel();
		
		Class<?> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		
		for (Field f : fields ) {
		    try {
			if (!f.getName().startsWith("jdo")) {
			    if (f.getType().getName().equals("com.google.appengine.api.datastore.Key")) {
				r.set(f.getName(), KeyFactory.keyToString((Key)f.get(this)));
			    } else {
				r.set(f.getName(), f.get(this));
			    }
			}
		    } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    } catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		
		return r;
	
    }
    
}
