package com.norex.gtrax.client.authentication.group;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.AuthSuggestBox;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.auth.AuthWidget;
import com.norex.gtrax.client.authentication.auth.ClientAuth;

public class GroupOverviewWidget extends Composite {
	
	private GroupWidget parentWidget;
	AuthServiceAsync authService = GWT.create(AuthService.class);
	
	interface MyUiBinder extends UiBinder<Widget, GroupOverviewWidget> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	
	@UiField
	TextBox name;

	@UiField
	Anchor remove;
	
	public GroupOverviewWidget(GroupWidget parentWidget) {
		initWidget(uiBinder.createAndBindUi(this));
		setParentWidget(parentWidget);
		
		final GroupOverviewWidget w = this;
		remove.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!Window.confirm("Do you really want to delete this group?")) return;
				
				authService.deleteGroup(getGroup(), new AsyncRemoteCall() {
					public void onSuccess(Object result) {
						w.removeFromParent();
					}
				});
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
		
		name.setValue(getGroup().getName());
	}

	public GroupWidget getParentWidget() {
		return parentWidget;
	}
	
	public void update() {
		name.setValue(getGroup().getName());
	}
	
}
