package com.norex.gtrax.server;

import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.project.ClientProject;
import com.norex.gtrax.client.timesheet.ClientTimesheet;
import com.norex.gtrax.client.timesheet.TimesheetInterface;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Timesheet extends Model implements TimesheetInterface<Key, Project> {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
	
	@Persistent
	private double hours;
	
	@Persistent
	private Key projectKey;
	
	@Persistent
	private String notes;
	
	@Persistent
	private Date date;

	public ClientTimesheet toClient() {
		ClientTimesheet t = new ClientTimesheet();
		t.setDate(getDate());
		t.setHours(getHours());
		t.setId(KeyFactory.keyToString(getId()));
		t.setNotes(getNotes());
		t.setProjectKey(KeyFactory.keyToString(getProjectKey()));
		t.setProject(getProject().toClient());
		
		return t;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key key) {
		this.id = key;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public Key getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(Key projectKey) {
		this.projectKey = projectKey;
	}
	
	public Project getProject() {
		PersistenceManager pmf = PMF.getPersistenceManager();
		Project p = pmf.getObjectById(Project.class, getProjectKey());
		
		pmf.close();
		return p;
	}
	
	public void setProject(Project p) {
		setProjectKey(p.getId());
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
