package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.auth.ClientContact;
import com.norex.gtrax.client.auth.CompanyService;
import com.norex.gtrax.client.auth.CompanyServiceAsync;

public class ContactView implements ViewInterface {
	
	interface ContactViewCSS extends CssResource {
		String headerFrame();
	}
	
	interface ContactViewResources extends ClientBundle {
		public ContactViewResources INSTANCE = GWT.create(ContactViewResources.class);
		
		@Source("contact.css")
		ContactViewCSS css();
	}
	
	VerticalPanel p = new VerticalPanel();
	HorizontalSplitPanel contactPanel = new HorizontalSplitPanel();
	
	CompanyServiceAsync companyService = GWT.create(CompanyService.class);
	ContactServiceAsync contactService = GWT.create(ContactService.class);

	public Panel getView() {
		StyleInjector.injectStylesheet(ContactViewResources.INSTANCE.css().getText());
		p.clear();
		
		DecoratorPanel decPanel = new DecoratorPanel();

		contactPanel.setSize("900px", "350px");
		contactPanel.setSplitPosition("20%");
		decPanel.setWidget(contactPanel);
		p.add(decPanel);
		
		VerticalPanel contacts = new VerticalPanel();
		VerticalPanel headerFrame = new VerticalPanel();
		Button createContact = new Button("Create New Contact");
		createContact.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final ContactWidget widget = new ContactWidget();
				
				VerticalPanel right = new VerticalPanel();
				right.setWidth("100%");
				HorizontalPanel head = new HorizontalPanel();
				head.addStyleName(ContactViewResources.INSTANCE.css().headerFrame());
				
				Button save = new Button("Save");
				
				head.add(save);
				head.add(new Label("New Contact"));
				
				right.add(head);
				right.add(widget);
				
				save.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						contactService.create(widget.getContact(), new AsyncCallback<ClientContact>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(ClientContact result) {
								widget.setContact(result);
							}
						});
					}
				});
				
				contactPanel.setRightWidget(right);
			}
		});
		
		contactPanel.setLeftWidget(contacts);
		contacts.setWidth("100%");
		headerFrame.add(createContact);
		headerFrame.addStyleName(ContactViewResources.INSTANCE.css().headerFrame());
		contacts.add(headerFrame);
		
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
