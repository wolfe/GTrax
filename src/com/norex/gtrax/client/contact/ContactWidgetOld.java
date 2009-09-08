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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.norex.gtrax.client.BlobImage;

public class ContactWidgetOld extends Composite {
	
	private VerticalPanel container = new VerticalPanel();
	private VerticalPanel emailCollectionContainer = new VerticalPanel();
	private VerticalPanel phoneCollectionContainer = new VerticalPanel();
	private TextBox name = new TextBox();
	
	private ClientContact contact = new ClientContact();

	private Map<TextBox, EmailAddress> addressMap = new HashMap<TextBox, EmailAddress>();
	private Map<TextBox, PhoneNumber> phoneMap = new HashMap<TextBox, PhoneNumber>();
	
	public ContactWidgetOld(ClientContact c) {
		initWidget(container);
		
		this.setContact(c);
		
		final BlobImage picture = new BlobImage(c.getPictureBlobKey(), 165);
		container.add(picture);
		
		final FormPanel pictureUploadForm = new FormPanel();
		pictureUploadForm.setAction(GWT.getModuleBaseURL() + "fileupload");
		pictureUploadForm.setMethod(FormPanel.METHOD_POST);
		pictureUploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		
		FileUpload pictureUpload = new FileUpload();
		pictureUpload.setName("picture_" + getContact().getId());
		
		pictureUploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				ClientContact c = getContact();
				c.setPictureBlobKey(event.getResults().trim().replaceAll("\\<.*?>",""));
				ContactServiceAsync contactService = GWT.create(ContactService.class);
				contactService.save(c, new AsyncCallback<ClientContact>() {
					
					@Override
					public void onSuccess(ClientContact result) {
						setContact(result);
						picture.setUrl(result.getPictureBlobKey());
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		pictureUploadForm.add(pictureUpload);
		container.add(pictureUploadForm);
		
		Button x = new Button("save pic", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				pictureUploadForm.submit();
			}
		});
		container.add(x);
		
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
		
		Anchor addAnotherEmail = new Anchor("add another");
		addAnotherEmail.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addEmailField(new EmailAddress());
			}
		});
		container.add(addAnotherEmail);
		
		
		container.add(new Label("Phone Numbers:"));
		container.add(phoneCollectionContainer);
		
		for (PhoneNumber phonenumber : c.getPhone()) {
			addPhoneField(phonenumber);
		}
		Anchor addAnotherPhone = new Anchor("add another");
		addAnotherPhone.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addPhoneField(new PhoneNumber());
			}
		});
		container.add(addAnotherPhone);
	}
	
	public ClientContact getContact() {
//		ArrayList<EmailAddress> list = new ArrayList<EmailAddress>();
//		for (TextBox sets : addressMap.keySet()) {
//			if (!sets.getValue().trim().isEmpty()) {
//				list.add(addressMap.get(sets));
//			}
//		}
//		this.contact.setEmail(list);
//		
//		ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
//		for (TextBox sets : phoneMap.keySet()) {
//			if (!sets.getValue().trim().isEmpty()) {
//				phones.add(phoneMap.get(sets));
//			}
//		}
//		this.contact.setPhone(phones);
		
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
				
				ArrayList<EmailAddress> list = new ArrayList<EmailAddress>();
				for (TextBox sets : addressMap.keySet()) {
					if (!sets.getValue().trim().isEmpty()) {
						list.add(addressMap.get(sets));
					}
				}
				contact.setEmail(list);
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
	
	private void addPhoneField(final PhoneNumber phonenumber) {
		final HorizontalPanel phoneContainer = new HorizontalPanel();
		
		final TextBox phone = new TextBox();
		phone.setValue(phonenumber.getNumber());
		
		phoneMap.put(phone, phonenumber);
		
		phone.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				phonenumber.setNumber(phone.getValue());
				
				ArrayList<PhoneNumber> phones = new ArrayList<PhoneNumber>();
				for (TextBox sets : phoneMap.keySet()) {
					if (!sets.getValue().trim().isEmpty()) {
						phones.add(phoneMap.get(sets));
					}
				}
				contact.setPhone(phones);
			}
		});
		
		phoneContainer.add(phone);
		
		final ListBox types = new ListBox();
		
		phoneContainer.add(types);
		
		for (PhoneNumberTypes t : PhoneNumberTypes.values()) {
			types.addItem(t.name());
			
			if (t.equals(phonenumber.getType())) {
				types.setSelectedIndex(types.getItemCount() - 1);
			}
		}
		
		types.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				phonenumber.setType(PhoneNumberTypes.valueOf(types.getValue(types.getSelectedIndex())));
			}
		});
		
		Button x = new Button("x");
		x.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				phoneContainer.removeFromParent();
				phoneMap.remove(phone);
			}
		});
		
		phoneContainer.add(x);
		
		phoneCollectionContainer.add(phoneContainer);
	}
}
