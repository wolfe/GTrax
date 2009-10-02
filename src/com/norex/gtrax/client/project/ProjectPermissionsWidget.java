package com.norex.gtrax.client.project;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.group.ClientGroup;

public class ProjectPermissionsWidget extends Composite {
	private ProjectWidget parentWidget;
	VerticalPanel panel = new VerticalPanel();
	
	AuthServiceAsync authService = GWT.create(AuthService.class);
	
	public ProjectPermissionsWidget(ProjectWidget parent) {
		setParentWidget(parent);
		initWidget(panel);
	}
	
	public void update(ClientProject p) {
		authService.getGroups(new AsyncRemoteCall<ArrayList<ClientGroup>>() {
			public void onSuccess(ArrayList<ClientGroup> result) {
				for (ClientGroup g : result) {
					panel.add(new Label(g.getName()));
				}
			}
		});
	}

	public void setParentWidget(ProjectWidget parentWidget) {
		this.parentWidget = parentWidget;
	}

	public ProjectWidget getParentWidget() {
		return parentWidget;
	}
}
