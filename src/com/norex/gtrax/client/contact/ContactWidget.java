package com.norex.gtrax.client.contact;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.auth.ClientContact;

public class ContactWidget extends Composite {
	
	private VerticalPanel container = new VerticalPanel();
	private TextBox name = new TextBox();
	private TextBox email = new TextBox();

	public ContactWidget() {
		initWidget(container);
		
		container.add(new Label("Full Name"));
		container.add(name);
		
		container.add(new Label("Email Address"));
		container.add(email);
	}
	
	public ContactWidget(ClientContact c) {
		super();
		
		name.setValue(c.getName());
		email.setValue(c.getEmail());
	}
	
}
