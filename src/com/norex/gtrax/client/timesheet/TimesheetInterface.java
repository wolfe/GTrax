package com.norex.gtrax.client.timesheet;

import java.util.Date;

public interface TimesheetInterface<E, M> {
	public void setId(E id);
	public E getId();
	public void setProjectKey(E id);
	public E getProjectKey();
	public M getProject();
	public void setHours(double hours);
	public double getHours();
	public void setNotes(String notes);
	public String getNotes();
	public void setDate(Date date);
	public Date getDate();
}
