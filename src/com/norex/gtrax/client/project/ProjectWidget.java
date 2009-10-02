package com.norex.gtrax.client.project;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.norex.gtrax.client.HasSaveHandlers;
import com.norex.gtrax.client.SaveHandler;
import com.norex.gtrax.client.contact.ClientContact;

public class ProjectWidget extends Composite implements HasSaveHandlers {
	private ClientProject project = new ClientProject();
	private ClientContact contact = new ClientContact();
	
	private ProjectOverview overview = new ProjectOverview(this);
	private ProjectPermissionsWidget permissions = new ProjectPermissionsWidget(this);
	
	DecoratedTabPanel panel = new DecoratedTabPanel();
	
	public ProjectWidget(ClientProject project) {
		initWidget(panel);
		setProject(project);
		
		panel.add(overview, getProject().getName());
		panel.add(permissions, "Permissions");
		panel.selectTab(0);
	}

	public void setProject(ClientProject project) {
		overview.update(project);
		permissions.update(project);
		
		this.project = project;
		
	}

	public ClientProject getProject() {
		return project;
	}

	public void setContact(ClientContact contact) {
		this.contact = contact;
		getProject().setContactKey(getContact().getId());
		
		overview.contactLabel.setText("(belongs to " + getContact().getName() + ")");
	}

	public ClientContact getContact() {
		return contact;
	}

	public HandlerRegistration addSaveHandler(SaveHandler event) {
		return overview.addSaveHandler(event);
	}
}
