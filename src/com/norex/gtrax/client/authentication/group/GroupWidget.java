package com.norex.gtrax.client.authentication.group;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.GTrax;
import com.norex.gtrax.client.HasSaveHandlers;
import com.norex.gtrax.client.SaveEvent;
import com.norex.gtrax.client.SaveHandler;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.contact.ContactServiceAsync;

public class GroupWidget extends Composite implements HasSaveHandlers {
	
	private ClientGroup group;
	private ArrayList<ClientAuth> groupMembers = new ArrayList<ClientAuth>();
	
	DecoratedTabPanel panel = new DecoratedTabPanel();
	
	private GroupOverviewWidget overview;
	private GroupMembersWidget members;
	private GroupPermissionsWidget permissions;
	
	public GroupWidget(ClientGroup group) {
		initWidget(panel);
		setGroup(group);
		
		overview = new GroupOverviewWidget(this);
		members = new GroupMembersWidget(this);
		permissions = new GroupPermissionsWidget(this);
		
		panel.add(overview, "Overview");
		panel.add(members, "Group Members");
		panel.add(permissions, "Group Permissions");
		panel.setWidth("100%");
		panel.selectTab(0);
		
		this.addSaveHandler(new SaveHandler() {
			public void onSave(SaveEvent event) {
				getGroup().setName(overview.name.getValue());
				AuthServiceAsync authService = GWT.create(AuthService.class);
				authService.saveGroup(getGroup(), new AsyncRemoteCall<ClientGroup>() {
					public void onSuccess(ClientGroup result) {
						setGroup(result);
						triggerSubWidgetUpdate();
					}
				});
			}
		});
		
		AuthServiceAsync authService = GWT.create(AuthService.class);
		authService.getGroupMembers(getGroup(), new AsyncRemoteCall<ArrayList<ClientAuth>>() {
			public void onSuccess(ArrayList<ClientAuth> result) {
				for (ClientAuth a : result) {
					if (a.getEmail().equals(GTrax.getAuth().getEmail())) {
						GTrax.setAuth(a);
					}
				}
				setGroupMembers(result);
				members.update();
			}
		});
	}

	public void setGroup(ClientGroup group) {
		this.group = group;
	}

	private void triggerSubWidgetUpdate() {
		if (overview != null) {
			overview.update();
		}
	}
	
	public ClientGroup getGroup() {
		return group;
	}

	public HandlerRegistration addSaveHandler(SaveHandler handler) {
		return this.addHandler(handler, SaveEvent.getType());
	}

	public void setGroupMembers(ArrayList<ClientAuth> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public ArrayList<ClientAuth> getGroupMembers() {
		return groupMembers;
	}
	

}
