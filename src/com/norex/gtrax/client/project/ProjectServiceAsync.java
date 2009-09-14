package com.norex.gtrax.client.project;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServiceAsync {
	public void getProjects(AsyncCallback<ArrayList<ClientProject>> async);
	public void save(ClientProject project, AsyncCallback<ClientProject> async);
}
