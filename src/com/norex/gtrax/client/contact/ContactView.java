package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.auth.ClientContact;
import com.norex.gtrax.client.auth.CompanyService;
import com.norex.gtrax.client.auth.CompanyServiceAsync;

public class ContactView implements ViewInterface {
	
	VerticalPanel p = new VerticalPanel();
	HorizontalSplitPanel contactPanel = new HorizontalSplitPanel();
	
	CompanyServiceAsync companyService = GWT.create(CompanyService.class);

	public Panel getView() {
		p.clear();
		
		DecoratorPanel decPanel = new DecoratorPanel();

		contactPanel.setSize("900px", "350px");
		contactPanel.setSplitPosition("30%");
		decPanel.setWidget(contactPanel);
		p.add(decPanel);
		
		VerticalPanel contacts = new VerticalPanel();
		Button createContact = new Button("Create New Contact");
		createContact.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				contactPanel.setRightWidget(new ContactWidget());
			}
		});
		
		contactPanel.setLeftWidget(contacts);
		contacts.add(createContact);
		
		companyService.getContacts(new AsyncCallback<ArrayList<ClientContact>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<ClientContact> result) {
				for (ClientContact contact : result) {
					p.add(new Label(contact.toString()));
				}
			}
		});
		
		return p;
	}

}
