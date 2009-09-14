package com.norex.gtrax.client.project;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("project")
public interface ProjectService extends RemoteService {
	public ArrayList<ClientProject> getProjects();
	public ClientProject save(ClientProject project);
}
