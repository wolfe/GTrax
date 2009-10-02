package com.norex.gtrax.client.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.SaveEvent;
import com.norex.gtrax.client.SaveHandler;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.contact.ContactWidget;

public class ContactView implements ViewInterface {
	
	public Map<String, ClientContact> contactsMap = new HashMap<String, ClientContact>();
	
	public interface ContactViewResources extends ClientBundle {
		public ContactViewResources INSTANCE = GWT.create(ContactViewResources.class);
		
		@Source("contact.css")
		ContactViewCSS css();
		
		interface ContactViewCSS extends CssResource {
			String headerFrame();
			String contactWidget();
			String nodeLabel();
			String nodeContainer();
			String addNew();
			String removeItem();
			String elementsContainer();
		}
	}
	
	VerticalPanel p = new VerticalPanel();
	HorizontalSplitPanel contactPanel = new HorizontalSplitPanel();
	final VerticalPanel left = new VerticalPanel();
	final VerticalPanel right = new VerticalPanel();
	
	final VerticalPanel contactsList = new VerticalPanel();
	
	public static ContactServiceAsync contactService = GWT.create(ContactService.class);

	public Panel getView() {
		left.clear();
		right.clear();
		StyleInjector.injectStylesheet(ContactViewResources.INSTANCE.css().getText());
		p.clear();
		p.setWidth("100%");
		
		DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setWidth("100%");

		contactPanel.setSize("1200px", "550px");
		contactPanel.setSplitPosition("20%");
		decPanel.setWidget(contactPanel);
		p.add(decPanel);
		
		right.setWidth("100%");
		left.setWidth("100%");
		
		VerticalPanel headerFrame = new VerticalPanel();
		Button createContact = new Button("Create New Contact");
		createContact.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editContact(new ClientContact());
			}
		});
		contactPanel.setLeftWidget(left);
		contactPanel.setRightWidget(right);
		
		
		headerFrame.add(createContact);
		headerFrame.addStyleName(ContactViewResources.INSTANCE.css().headerFrame());
		left.add(headerFrame);
		left.add(contactsList);
		
		updateFromDataSource();
		
		return p;
	}
	
	public void buildList() {
		contactsList.clear();
		
		for (ClientContact contact : contactsMap.values()) {
			Label l = addContactToList(contact);
		}
	}

	public Label addContactToList(final ClientContact contact) {
		Label label = new Label(contact.getName());
		label.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editContact(contact);
			}
		});
		
		contactsList.add(label);
		
		return label;
	}
	
	public void editContact(ClientContact contact) {
		right.clear();
		
		final ContactWidget widget = new ContactWidget(contact);
		widget.setStyleName(ContactViewResources.INSTANCE.css().contactWidget());
		
		HorizontalPanel head = new HorizontalPanel();
		head.addStyleName(ContactViewResources.INSTANCE.css().headerFrame());
		
		Button save = new Button("Save");
		
		head.add(save);
		
		if (contact.getId() == null) {
			head.add(new Label("New Contact"));
		}
		
		right.add(head);
		right.add(widget);
		
		widget.addSaveHandler(new SaveHandler() {
			public void onSave(SaveEvent event) {
				ClientContact c = widget.getContact();
				contactsMap.put(c.getId(), c);
				right.clear();
				buildList();
			}
		});
		
		save.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				widget.fireEvent(new SaveEvent());
			}
		});
		
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				right.clear();
			}
		});
		head.add(cancel);
		
		right.add(widget);
	}

	public void updateFromDataSource() {
		contactService.getContacts(new AsyncRemoteCall<ArrayList<ClientContact>>() {
			public void onSuccess(ArrayList<ClientContact> result) {
				for (ClientContact contact : result) {
					contactsMap.put(contact.getId(), contact);
				}
				buildList();
			}
		});
	}

	public void fireSubHistory(String subItem) {
		// TODO Auto-generated method stub
		
	}
}
