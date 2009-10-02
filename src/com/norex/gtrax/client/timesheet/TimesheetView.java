package com.norex.gtrax.client.timesheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.GTrax;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.project.ClientProject;
import com.norex.gtrax.client.project.ProjectService;
import com.norex.gtrax.client.project.ProjectServiceAsync;

public class TimesheetView implements ViewInterface {
	
	ProjectServiceAsync projectService = GWT.create(ProjectService.class);
	ArrayList<ClientProject> projects = new ArrayList<ClientProject>();

	VerticalPanel container = new VerticalPanel();
	ListBox projectsList = new ListBox();
	TextBox hours = new TextBox();
	TextArea notes = new TextArea();
	Button createNew = new Button("add");
	
	public void fireSubHistory(String subItem) {
		// TODO Auto-generated method stub

	}

	public Panel getView() {
		updateFromDataSource();
		
		hours.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String val = hours.getValue();
				hours.setValue(Double.parseDouble(val) + "");
			}
		});
		
		createNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert(GTrax.getAuth().getPerms().toString());
			}
		});
		
		HorizontalPanel newItem = new HorizontalPanel();
		newItem.add(projectsList);
		newItem.add(hours);
		newItem.add(notes);
		newItem.add(createNew);
		
		container.add(newItem);
		return container;
	}

	public void updateFromDataSource() {
		projectService.getProjects(new AsyncRemoteCall<ArrayList<ClientProject>>() {
			public void onSuccess(ArrayList<ClientProject> result) {
				projects = result;
				projectsList.clear();
				for (ClientProject p : projects) {
					projectsList.addItem(p.getName(), p.getId());
				}
			}
		});
	}

}
