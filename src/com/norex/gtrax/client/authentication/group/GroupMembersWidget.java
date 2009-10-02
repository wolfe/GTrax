package com.norex.gtrax.client.authentication.group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.AuthSuggestBox;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.auth.AuthWidget;
import com.norex.gtrax.client.authentication.auth.ClientAuth;

public class GroupMembersWidget extends Composite {
	private GroupWidget parentWidget;
	
	interface MyUiBinder extends UiBinder<Widget, GroupMembersWidget> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	VerticalPanel membersList;
	
	@UiField
	AuthSuggestBox user;
	
	public GroupMembersWidget(GroupWidget parentWidget) {
		initWidget(uiBinder.createAndBindUi(this));
		setParentWidget(parentWidget);
	}

	@UiHandler("addNewMember")
	public void doAddNewMember(ClickEvent event) {
		ClientAuth a = new ClientAuth();
		a.setId(user.getValue());
		AuthServiceAsync authService = GWT.create(AuthService.class);
		authService.addAuthToGroup(a, getGroup(), new AsyncRemoteCall<ClientAuth>() {
			public void onSuccess(ClientAuth result) {
				getParentWidget().getGroupMembers().add(result);
				update();
			}
		});;
	}
	
	public void setParentWidget(GroupWidget parentWidget) {
		this.parentWidget = parentWidget;
	}

	public GroupWidget getParentWidget() {
		return parentWidget;
	}
	
	public void setGroup(ClientGroup group) {
		getParentWidget().setGroup(group);
	}

	public ClientGroup getGroup() {
		return getParentWidget().getGroup();
	}
	
	public void update() {
		membersList.clear();
		for (ClientAuth a : getParentWidget().getGroupMembers()) {
			AuthWidget w = new AuthWidget(a);
			membersList.add(w);
		}
	}
}
