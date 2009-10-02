package com.norex.gtrax.client.timesheet;

import java.util.Date;

import com.norex.gtrax.client.ClientModel;
import com.norex.gtrax.client.ClientModelInterface;
import com.norex.gtrax.client.project.ClientProject;

public class ClientTimesheet extends ClientModel implements
		TimesheetInterface<String, ClientProject>, ClientModelInterface {
	
	private String id;
	private Date date;
	private double hours;
	private String notes;
	private String projectKey;
	private ClientProject project;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getHours() {
		return hours;
	}
	public void setHours(double hours) {
		this.hours = hours;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public ClientProject getProject() {
		return project;
	}
	public void setProject(ClientProject project) {
		this.project = project;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String id) {
		this.projectKey = id;
	}


}
