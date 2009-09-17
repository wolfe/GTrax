package com.norex.gtrax.client.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.AuthSuggestBox;
import com.norex.gtrax.client.contact.ClientContact;

public class ProjectWidget extends Composite {
	private ClientProject project = new ClientProject();
	private ClientContact contact = new ClientContact();
	
	interface MyUiBinder extends UiBinder<Panel, ProjectWidget> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	TextBox name;
	
	@UiField
	Label contactLabel;
	
	@UiField
	TextArea description;
	
	@UiField
	CheckBox status;
	
	@UiField
	HorizontalPanel addauthcontainer;
	
	public ProjectWidget(ClientProject project) {
		initWidget(uiBinder.createAndBindUi(this));
		setProject(project);
		
		AuthSuggestBox box = new AuthSuggestBox();
		addauthcontainer.add(box);
	}
	
	@UiHandler("save")
	public void doSave(ClickEvent event) {
		getProject().setName(name.getValue());
		getProject().setDescription(description.getValue());
		getProject().setStatus(status.getValue());
		
		ProjectView.projectService.save(getProject(), new AsyncRemoteCall<ClientProject>() {
			public void onSuccess(ClientProject result) {
				setProject(result);
			}
		});
	}

	public void setProject(ClientProject project) {
		if (name.getValue().length() == 0 || !getProject().getName().equals(project.getName())) {
			name.setValue(project.getName());
		}
		
		if (description.getValue().length() == 0 || !getProject().getDescription().equals(project.getDescription())) {
			description.setValue(project.getDescription());
		}
		
		status.setValue(project.getStatus());
		
		this.project = project;
	}

	public ClientProject getProject() {
		return project;
	}

	public void setContact(ClientContact contact) {
		this.contact = contact;
		getProject().setContactKey(getContact().getId());
		
		contactLabel.setText("(belongs to " + getContact().getName() + ")");
	}

	public ClientContact getContact() {
		return contact;
	}
}
