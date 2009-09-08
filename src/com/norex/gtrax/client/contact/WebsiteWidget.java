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

public class WebsiteWidget extends Composite implements HasWidgets {
	private Website website;
	
	interface MyUiBinder extends UiBinder<Widget, WebsiteWidget> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	HorizontalPanel container;
	
	@UiField
	TextBox address;
	
	@UiField
	ListBox type;
	
	public WebsiteWidget(Website website) {
		setWebsite(website);
		initWidget(uiBinder.createAndBindUi(this));
		
		address.setValue(getWebsite().getAddress());
		
		for (WebsiteType t : WebsiteType.values()) {
			type.addItem(t.name());
			if (t.equals(getWebsite().getType())) {
				type.setSelectedIndex(type.getItemCount() - 1);
			}
		}
		
		address.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				getWebsite().setAddress(address.getValue());
			}
			
		});
		
		type.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				getWebsite().setType(WebsiteType.valueOf(type.getValue(type.getSelectedIndex())));
			}
		});
	}

	public Website getWebsite() {
		return website;
	}
	
	public void setWebsite(Website website) {
		this.website = website;
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
