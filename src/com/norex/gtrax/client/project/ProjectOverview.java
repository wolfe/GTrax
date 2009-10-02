package com.norex.gtrax.client.project;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.HasSaveHandlers;
import com.norex.gtrax.client.SaveEvent;
import com.norex.gtrax.client.SaveHandler;

public class ProjectOverview extends Composite implements HasSaveHandlers {
	ProjectWidget parent;
	
	interface MyUiBinder extends UiBinder<Panel, ProjectOverview> {
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
	
	public ProjectOverview(ProjectWidget parent) {
		setParentWidget(parent);
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	public ProjectWidget getParentWidget() {
		return this.parent;
	}
	
	public void setParentWidget(ProjectWidget parent) {
		this.parent = parent;
	}
	
	@UiHandler("save")
	public void doSave(ClickEvent event) {
		final ProjectOverview app = this;
		getParentWidget().getProject().setName(name.getValue());
		getParentWidget().getProject().setDescription(description.getValue());
		getParentWidget().getProject().setStatus(status.getValue());
		
		ProjectView.projectService.save(getParentWidget().getProject(), new AsyncRemoteCall<ClientProject>() {
			public void onSuccess(ClientProject result) {
				getParentWidget().setProject(result);
				SaveEvent event = new SaveEvent();
				app.fireEvent(event);
			}
		});
	}
	
	public void update(ClientProject project) {
		if (name.getValue().length() == 0 || !getParentWidget().getProject().getName().equals(project.getName())) {
			name.setValue(project.getName());
		}
		
		if (description.getValue().length() == 0 || !getParentWidget().getProject().getDescription().equals(project.getDescription())) {
			description.setValue(project.getDescription());
		}
		
		status.setValue(project.getStatus());
	}


	public HandlerRegistration addSaveHandler(final SaveHandler event) {
		return this.addHandler(event, SaveEvent.getType());
	}
}
