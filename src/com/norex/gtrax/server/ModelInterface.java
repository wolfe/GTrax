package com.norex.gtrax.server;

import com.google.appengine.api.datastore.Key;
import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.DataInterchangeModelInterface;

public interface ModelInterface extends DataInterchangeModelInterface<Key> {
	public ClientModel toClient();
}
