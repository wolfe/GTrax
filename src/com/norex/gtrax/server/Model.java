package com.norex.gtrax.server;

import javax.jdo.PersistenceManager;

@SuppressWarnings("serial")
abstract class Model implements ModelInterface {

    public void save() {
		PersistenceManager pmf = PMF.getPersistenceManager();
		pmf.makePersistent(this);
		pmf.close();
    }
    
}

