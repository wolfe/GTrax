package com.norex.gtrax.client.authentication.group;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.AuthSuggestBox;
import com.norex.gtrax.client.GTrax;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.auth.AuthWidget;
import com.norex.gtrax.client.authentication.auth.ClientAuth;

public class GroupPermissionsWidget extends Composite {
	
	private GroupWidget parentWidget;
	AuthServiceAsync authService = GWT.create(AuthService.class);
	
	interface MyUiBinder extends UiBinder<Widget, GroupPermissionsWidget> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	
	@UiField
	VerticalPanel container;
	
	public GroupPermissionsWidget(GroupWidget parentWidget) {
		initWidget(uiBinder.createAndBindUi(this));
		setParentWidget(parentWidget);
		
		authService.getAllPermissions(new AsyncRemoteCall<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> result) {
				container.clear();
				
				Grid table = new Grid(result.size(), 2);
				
				int counter = 0;
				for (final String perm : result) {
					table.setWidget(counter, 0, new Label(perm));
					if (getGroup().getPermSet().contains(perm)) {
						table.setWidget(counter, 1, new Label("allowed"));
					} else {
						final Label check = new Label("forbidden");
						check.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								getGroup().getPermSet().add(perm);
								authService.saveGroup(getGroup(), new AsyncRemoteCall<ClientGroup>() {
									public void onSuccess(ClientGroup result) {
										setGroup(result);
										check.setText("allowed");
									}
								});
							}
						});
						table.setWidget(counter, 1, check);
					}
					counter++;
				}
				
				container.add(table);
			}
		});
	}

	public void setGroup(ClientGroup group) {
		getParentWidget().setGroup(group);
	}

	public ClientGroup getGroup() {
		return getParentWidget().getGroup();
	}

	public void setParentWidget(GroupWidget parentWidget) {
		this.parentWidget = parentWidget;
	}

	public GroupWidget getParentWidget() {
		return parentWidget;
	}
	
	public void update() {
	}
	
}
