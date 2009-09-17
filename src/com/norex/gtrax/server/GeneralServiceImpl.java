package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.norex.gtrax.client.ClientModel;

abstract public class GeneralServiceImpl extends RemoteServiceServlet {

	public <T> ArrayList<T> getAll(Class type) {
		PersistenceManager pm = PMF.getPersistenceManager();
		
		ArrayList<T> list = new ArrayList<T>();
		Query query = pm.newQuery(type);
		
		try {
			List<Model> results = (List<Model>) query.execute();
			if (results.iterator().hasNext()) {
	            for (Model e : results) {
	                list.add((T)e.toClient());
	            }
	        }
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return list;
	}

}
