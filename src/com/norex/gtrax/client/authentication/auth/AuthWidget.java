package com.norex.gtrax.client.authentication.auth;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;

public class AuthWidget extends Composite {
	
	HorizontalPanel p = new HorizontalPanel();
	Label email = new Label();
	TextBox firstName = new TextBox();
	TextBox lastName = new TextBox();
	Button save = new Button("save");
	private ClientAuth auth;
	
	private AuthServiceAsync authService = GWT.create(AuthService.class);

	public AuthWidget(ClientAuth a) {
		this.setAuth(a);
		initWidget(p);
		
		p.add(email);
		p.add(firstName);
		p.add(lastName);
		p.add(save);
		
		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getAuth().setFirstName(firstName.getValue());
				getAuth().setLastName(lastName.getValue());
				authService.save(getAuth(), new AsyncRemoteCall<ClientAuth>() {
					public void onSuccess(ClientAuth result) {
						setAuth(result);
					}
				});
			}
		});
	}

	public void setAuth(ClientAuth auth) {
		this.auth = auth;
		
		email.setText(getAuth().getEmail());
		firstName.setValue(getAuth().getFirstName());
		lastName.setValue(getAuth().getLastName());
	}

	public ClientAuth getAuth() {
		return auth;
	}
	
}
