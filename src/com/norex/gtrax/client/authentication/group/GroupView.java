package com.norex.gtrax.client.authentication.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;

public class GroupView implements ViewInterface {
	
	AuthServiceAsync authService = GWT.create(AuthService.class);
	
	Map<String, GroupWidget> map = new HashMap<String, GroupWidget>();
	
	interface MyUiBinder extends UiBinder<Panel, GroupView> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	TextBox newGroupName;
	
	@UiField
	VerticalPanel groupsContainer;
	
	public Panel getView() {
		updateFromDataSource();
		
		Panel p = uiBinder.createAndBindUi(this);
		
		newGroupName.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER && newGroupName.getValue().trim().length() > 0) {
					doAddNewGroup();
				}
			}
		});
		
		return p;
	}

	@UiHandler("addNewGroup")
	public void doAddNewGroup(ClickEvent click) {
		doAddNewGroup();
	}
	
	public void doAddNewGroup() {
		ClientGroup group = new ClientGroup();
		group.setName(newGroupName.getValue());
		
		authService.createGroup(group, new AsyncRemoteCall<ClientGroup>() {

			@Override
			public void onSuccess(ClientGroup result) {
				GroupWidget w = updateSingleGroup(result);
				w.setOpen(true);
				newGroupName.setValue(null);
			}
		});
	}
	
	private GroupWidget updateSingleGroup(ClientGroup grp) {
		if (map.get(grp.getId()) == null) {
			GroupWidget w = new GroupWidget(grp);
			map.put(grp.getId(), w);
			
			groupsContainer.add(w);
		} else {
			map.get(grp.getId()).setGroup(grp);
			map.get(grp.getId()).updateMembersHeader();
		}
		
		return map.get(grp.getId());
	}
	
	public void updateFromDataSource() {
		authService.getGroups(new AsyncRemoteCall<ArrayList<ClientGroup>>() {

			public void onSuccess(ArrayList<ClientGroup> results) {
				for (ClientGroup grp : results) {
					updateSingleGroup(grp);
				}
				
				for ( GroupWidget w : map.values() ) {
					if (!results.contains(w.getGroup())) {
						map.remove(w);
						w.removeFromParent();
					}
				}
			}
		});
	}

}
