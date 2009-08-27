package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.norex.gtrax.client.auth.AuthService;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;

public class AuthServiceImpl extends GeneralServiceImpl implements
		AuthService {

	public ClientAuth create(ClientCompany company, ClientAuth m) {
		Auth c = null;
		PersistenceManager pm = PMF.getPersistenceManager();
		
		Company e = pm.getObjectById(Company.class, company.getId());
		
		try {
			c = new Auth();
			c.setEmail(m.getEmail());
			
			e.getAuthSet().add(c);
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
		
		return c.toClient();
	}

}
