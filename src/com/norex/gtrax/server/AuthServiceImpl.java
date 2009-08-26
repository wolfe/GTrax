package com.norex.gtrax.server;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.auth.AuthService;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;

public class AuthServiceImpl extends GeneralServiceImpl implements
		AuthService {

	public ClientAuth create(ClientCompany company, ClientAuth m) {
		Auth c = new Auth();

		c.setEmail(m.getEmail());
		
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Company e = pm.getObjectById(Company.class, company.getId());
			e.getAuthSet().add(c);
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
		
		return c.toClient();
	}

}
