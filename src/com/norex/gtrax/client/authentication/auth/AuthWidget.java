package com.norex.gtrax.client.authentication.auth;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class AuthWidget extends Composite {
	
	HorizontalPanel p = new HorizontalPanel();
	private ClientAuth auth;

	public AuthWidget(ClientAuth a) {
		this.auth = a;
		
		p.add(new Label(a.getEmail()));
		initWidget(p);
	}
	
}
