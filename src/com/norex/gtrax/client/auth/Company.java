package com.norex.gtrax.client.auth;

import com.norex.gtrax.client.ClientModel;

public class Company extends ClientModel {

    public String getId() {
	return (String) this.get("id");
    }
    
    public String getName() {
	return (String) this.get("name");
    }
    
}
