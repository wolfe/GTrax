package com.norex.gtrax.server;

import com.google.appengine.api.datastore.Key;
import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;

public interface ModelInterface extends ClientModelInterface {
	public ClientModel toClient();
	public Key getId();
	public void setId(Key key);
}
