package com.norex.gtrax.client.contact;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EmailWidget extends Composite implements HasWidgets {
	private EmailAddress email;
	
	interface MyUiBinder extends UiBinder<Widget, EmailWidget> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	HorizontalPanel container;
	
	@UiField
	TextBox address;
	
	@UiField
	ListBox type;
	
	public EmailWidget(EmailAddress email) {
		this.setEmail(email);
		initWidget(uiBinder.createAndBindUi(this));
		
		address.setValue(email.getAddress());
		
		for (EmailAddressType t : EmailAddressType.values()) {
			type.addItem(t.name());
			if (t.equals(email.getType())) {
				type.setSelectedIndex(type.getItemCount() - 1);
			}
		}
		
		address.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				getEmail().setAddress(address.getValue());
			}
			
		});
		
		type.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				getEmail().setType(EmailAddressType.valueOf(type.getValue(type.getSelectedIndex())));
			}
		});
	}

	public EmailAddress getEmail() {
		return email;
	}
	
	public void setEmail(EmailAddress email) {
		this.email = email;
	}

	@Override
	public void add(Widget w) {
		container.add(w);
	}

	@Override
	public void clear() {
		container.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return container.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return container.remove(w);
	}

}
