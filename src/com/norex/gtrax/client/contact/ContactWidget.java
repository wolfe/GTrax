package com.norex.gtrax.client.contact;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.auth.ClientContact;

public class ContactWidget extends Composite {
	
	private VerticalPanel container = new VerticalPanel();
	private TextBox name = new TextBox();
	private TextBox email = new TextBox();
	
	private ClientContact contact = new ClientContact();

	public ContactWidget() {
		initWidget(container);
		
		container.add(new Label("Full Name"));
		container.add(name);
		
		container.add(new Label("Email Address"));
		container.add(email);
		
		name.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				contact.setName(name.getValue());
			}
		});
		
		email.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				contact.setEmail(email.getValue());
			}
		});
	}
	
	public ContactWidget(ClientContact c) {
		super();
		
		this.contact = c;
		
		name.setValue(c.getName());
		email.setValue(c.getEmail());
	}
	
	public ClientContact getContact() {
		return this.contact;
	}
	
	public void setContact(ClientContact c) {
		this.contact = c;
	}
	
}
