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
import com.norex.gtrax.client.contact.PhoneNumber.PhoneNumberTypes;

public class PhoneWidget extends Composite implements HasWidgets {
	private PhoneNumber phonenumber;
	
	interface MyUiBinder extends UiBinder<Widget, PhoneWidget> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	HorizontalPanel container;
	
	@UiField
	TextBox phone;
	
	@UiField
	ListBox type;
	
	public PhoneWidget(PhoneNumber p) {
		setPhone(p);
		initWidget(uiBinder.createAndBindUi(this));
		
		phone.setValue(getPhone().getNumber());
		
		for (PhoneNumberTypes t : PhoneNumberTypes.values()) {
			type.addItem(t.name());
			if (t.equals(getPhone().getType())) {
				type.setSelectedIndex(type.getItemCount() - 1);
			}
		}
		
		phone.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				getPhone().setNumber(phone.getValue());
			}
			
		});
		
		type.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				getPhone().setType(PhoneNumberTypes.valueOf(type.getValue(type.getSelectedIndex())));
			}
		});
	}

	public PhoneNumber getPhone() {
		return phonenumber;
	}
	
	public void setPhone(PhoneNumber phone) {
		this.phonenumber = phone;
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
