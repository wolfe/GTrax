package com.norex.gtrax.client.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.auth.ClientContact;

public class ContactWidget extends Composite {
	
	private VerticalPanel container = new VerticalPanel();
	private VerticalPanel emailCollectionContainer = new VerticalPanel();
	private TextBox name = new TextBox();
	
	private ClientContact contact = new ClientContact();

	private Map<TextBox, EmailAddress> addressMap = new HashMap<TextBox, EmailAddress>();
	
	public ContactWidget(ClientContact c) {
		initWidget(container);
		
		this.setContact(c);
		
		container.add(new Label("Full Name:"));
		container.add(name);
		name.setValue(c.getName());
		name.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				contact.setName(name.getValue());
			}
		});
		
		container.add(new Label("Email Addresses:"));
		container.add(emailCollectionContainer);
		
		for (EmailAddress address : c.getEmail()) {
			addEmailField(address);
		}
		
		addEmailField(new EmailAddress());
		Anchor addAnother = new Anchor("add another");
		addAnother.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addEmailField(new EmailAddress());
			}
		});
		container.add(addAnother);
	}
	
	public ClientContact getContact() {
		ArrayList<EmailAddress> list = new ArrayList<EmailAddress>();
		for (TextBox sets : addressMap.keySet()) {
			if (!sets.getValue().trim().isEmpty()) {
				list.add(addressMap.get(sets));
			}
		}
		this.contact.setEmail(list);
		
		return this.contact;
	}
	
	public void setContact(ClientContact c) {
		this.contact = c;
	}
	
	private void addEmailField(final EmailAddress address) {
		final HorizontalPanel emailContainer = new HorizontalPanel();
		
		final TextBox emailAddress = new TextBox();
		emailAddress.setValue(address.getAddress());
		
		addressMap.put(emailAddress, address);
		
		emailAddress.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				address.setAddress(emailAddress.getValue());
			}
		});
		
		final ListBox types = new ListBox();
		
		emailContainer.add(emailAddress);
		emailContainer.add(types);
		
		for (EmailAddressType t : EmailAddressType.values()) {
			types.addItem(t.name());
			
			if (t.equals(address.getType())) {
				types.setSelectedIndex(types.getItemCount() - 1);
			}
		}
		
		types.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				address.setType(EmailAddressType.valueOf(types.getValue(types.getSelectedIndex())));
			}
		});
		
		Button x = new Button("x");
		x.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				emailContainer.removeFromParent();
				addressMap.remove(emailAddress);
			}
		});
		
		emailContainer.add(x);
		
		emailCollectionContainer.add(emailContainer);
	}
	
}
