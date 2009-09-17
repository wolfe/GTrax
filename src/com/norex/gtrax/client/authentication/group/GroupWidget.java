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

public class GroupWidget extends Composite implements HasOpenHandlers<DisclosurePanel>, HasCloseHandlers<DisclosurePanel> {
	
	private ClientGroup group;
	AuthServiceAsync authService = GWT.create(AuthService.class);
	
	interface MyUiBinder extends UiBinder<Widget, GroupWidget> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	
	@UiField
	Label name;

	@UiField
	DisclosurePanel members;
	
	@UiField
	AuthSuggestBox add;
	
	@UiField
	VerticalPanel membersList;
	
	@UiField
	Anchor remove;
	
	public GroupWidget(ClientGroup group) {
		setGroup(group);
		
		initWidget(uiBinder.createAndBindUi(this));
		
		name.setText(getGroup().getName());
		updateMembersHeader();
		
		members.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				add.setFocus(true);
				getGroupMembers();
			}
		});
		
		final GroupWidget w = this;
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
	
	@UiHandler("addButton")
	public void doAddButton(ClickEvent event) {
		doAddButton();
	}
	
	public void doAddButton() {
		authService.addAuthToGroup(add.getAuthValue(), getGroup(), new AsyncRemoteCall<ClientAuth>() {
			public void onSuccess(ClientAuth result) {
				add.setValue(null);
				getGroup().getAuthSet().add(result.getId());
				updateMembersHeader();
				getGroupMembers();
			}
		});
	}
	
	public void updateMembersHeader() {
		members.setHeader(new Anchor(getGroup().getAuthSet().toArray().length + " members (click to expand)"));
	}
	
	public void getGroupMembers() {
		authService.getGroupMembers(getGroup(), new AsyncRemoteCall<ArrayList<ClientAuth>>() {
			public void onSuccess(ArrayList<ClientAuth> result) {
				membersList.clear();
				for (ClientAuth a : result) {
					membersList.add(new AuthWidget(a));
				}
			}
		});
	}

	public void setGroup(ClientGroup group) {
		this.group = group;
	}

	public ClientGroup getGroup() {
		return group;
	}

	public HandlerRegistration addOpenHandler(
			OpenHandler<DisclosurePanel> handler) {
		
		return members.addOpenHandler(handler);
	}

	public HandlerRegistration addCloseHandler(
			CloseHandler<DisclosurePanel> handler) {
		return members.addCloseHandler(handler);
	}
	
	public void setOpen(boolean isOpen) {
		members.setOpen(isOpen);
	}
	
}
