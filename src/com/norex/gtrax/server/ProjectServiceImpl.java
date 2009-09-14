package com.norex.gtrax.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.norex.gtrax.client.project.ClientProject;
import com.norex.gtrax.client.project.ProjectService;

public class ProjectServiceImpl extends GeneralServiceImpl implements
		ProjectService {

	public ArrayList<ClientProject> getProjects() {
		PersistenceManager pm = PMF.getPersistenceManager();
		Query query = pm.newQuery(Project.class);
		
		ArrayList<ClientProject> results = new ArrayList<ClientProject>();
		
		try {
			List<Project> projects = (List<Project>) query.execute();
			if (!projects.iterator().hasNext()) return null;
			
			for (Project p : projects) {
				results.add(p.toClient());
			}
		} finally {
			query.closeAll();
			pm.close();
		}
		
		return results;
	}

	public ClientProject save(ClientProject project) {
		PersistenceManager pm = PMF.getPersistenceManager();
		Project p = null;

		if (project.getId() != null) {
			p = pm.getObjectById(Project.class, project.getId());
		} else {
			p = new Project();
			pm.makePersistent(p);
		}
		
		p.update(project);
		
		pm.close();
		return p.toClient();
	}

}
