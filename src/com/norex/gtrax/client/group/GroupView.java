package com.norex.gtrax.client.group;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.auth.CompanyService;
import com.norex.gtrax.client.auth.CompanyServiceAsync;

public class GroupView implements ViewInterface {

	VerticalPanel container = new VerticalPanel();
	VerticalPanel groupsContainer = new VerticalPanel();
	VerticalPanel addGroupContainer = new VerticalPanel();
	
	CompanyServiceAsync companyService = GWT.create(CompanyService.class);
	
	@Override
	public Panel getView() {
		container.clear();
		groupsContainer.clear();
		addGroupContainer.clear();
		
		companyService.getGroups(new AsyncRemoteCall<ArrayList<ClientGroup>>() {
			
			@Override
			public void onSuccess(ArrayList<ClientGroup> result) {
				for (ClientGroup g : result) {
					addGroupToList(g);
				}
			}
		});
		
		container.add(groupsContainer);
		container.add(addGroupContainer);
		
		final TextBox name = new TextBox();
		Button add = new Button("add");
		add.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ClientGroup g = new ClientGroup();
				g.setName(name.getValue());
				
				companyService.addGroup(g, new AsyncRemoteCall<ClientGroup>() {
					public void onSuccess(ClientGroup result) {
						name.setValue(null);
						addGroupToList(result);
					}
				});
			}
		});
		
		addGroupContainer.add(name);
		addGroupContainer.add(add);
		
		return container;
	}

	public void addGroupToList(ClientGroup group) {
		groupsContainer.add(new GroupWidget(group));
	}

	@Override
	public void updateFromDataSource() {
		// TODO Auto-generated method stub
		
	}
}
