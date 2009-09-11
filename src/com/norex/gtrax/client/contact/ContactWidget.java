package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.norex.gtrax.client.contact.EmailWidget;
import com.norex.gtrax.client.contact.WebsiteWidget;
import com.norex.gtrax.client.contact.PhoneWidget;
import java.util.Date;

public class ContactWidget extends Composite {

	private ClientContact contact;

	interface MyUiBinder extends UiBinder<Widget, ContactWidget> {
	}
	
	interface ContactWidgetImages extends ClientBundle {
		public ContactWidgetImages INSTANCE = GWT.create(ContactWidgetImages.class);
		
		@Source("NoPicture.gif")
		ImageResource noPicture();
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField
	HorizontalPanel container;
	
	@UiField
	TextBox name;
	
	@UiField
	VerticalPanel emailaddresses;
	
	@UiField
	Anchor addNewEmail;
	
	@UiField
	VerticalPanel phonenumbers;
	
	@UiField
	Anchor addNewPhone;
	
	@UiField
	VerticalPanel websites;
	
	@UiField
	Anchor addWebsite;
	
	@UiField 
	Image contactImage;
	
	@UiField
	VerticalPanel leftCol;
	
	@UiField
	VerticalPanel rightCol;
	
	@UiField
	TextArea notes;
	
	@UiField
	DateBox birthday;
	
	public ContactWidget(final ClientContact contact) {
		setContact(contact);
		
		initWidget(uiBinder.createAndBindUi(this));
		
		container.setWidth("100%");
		
		name.setValue(getContact().getName());
		name.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				contact.setName(name.getValue());
			}
		});
		
		notes.setValue(getContact().getNote());
		notes.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				getContact().setNote(notes.getValue());
			}
		});
		
		if (getContact().getEmail() != null) { 
			for (EmailAddress e : getContact().getEmail()) {
				addEmailAddress(e);
			}
		} else {
			getContact().setEmail(new ArrayList<EmailAddress>());
		}
		
		addNewEmail.setText("add");
		addNewEmail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EmailAddress a = new EmailAddress();
				getContact().getEmail().add(a);
				addEmailAddress(a);
			}
		});
		
		
		if (getContact().getPhone() != null) {
			for (PhoneNumber p : getContact().getPhone()) {
				addPhone(p);
			}
		} else {
			getContact().setPhone(new ArrayList<PhoneNumber>());
		}
		
		addNewPhone.setText("add");
		addNewPhone.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PhoneNumber a = new PhoneNumber();
				getContact().getPhone().add(a);
				addPhone(a);
			}
		});
		
		if (getContact().getWebsite() != null) {
			for (Website w : getContact().getWebsite()) {
				addWebsite(w);
			}
		} else {
			getContact().setWebsite(new ArrayList<Website>());
		}
		
		addWebsite.setText("add");
		addWebsite.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Website w = new Website();
				getContact().getWebsite().add(w);
				addWebsite(w);
			}
		});
		
		contactImage.setUrl(getBlobImageURL());
		
		contactImage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final DialogBox popup = new DialogBox();
				popup.setAnimationEnabled(true);
				popup.setText("Upload Contact Image");
				
				VerticalPanel panel = new VerticalPanel();
				
				final FormPanel form = new FormPanel();
				form.setAction(GWT.getModuleBaseURL() + "fileupload");
				form.setEncoding(FormPanel.ENCODING_MULTIPART);
				form.setMethod(FormPanel.METHOD_POST);
				
				FileUpload newImage = new FileUpload();
				newImage.setName("image_" + getContact().getId());
				form.add(newImage);
				
				Button submit = new Button("submit");
				submit.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						form.submit();
					}
				});
				
				form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
					
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						getContact().setPictureBlobKey(event.getResults().trim().replaceAll("\\<.*?>",""));
						ContactServiceAsync contactService = GWT.create(ContactService.class);
						contactService.save(getContact(), new AsyncCallback<ClientContact>() {
							
							@Override
							public void onSuccess(ClientContact result) {
								setContact(result);
								contactImage.setUrl(getBlobImageURL());
								popup.hide();
							}
							
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				});
				panel.add(form);
				panel.add(submit);
				
				Button close = new Button("close", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						popup.hide();
					}
				});
				panel.add(close);
				
				popup.add(panel);
				popup.center();
				popup.show();
			}
		});
		
		birthday.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		birthday.setValue(getContact().getBirthday());
		birthday.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				getContact().setBirthday(birthday.getValue());
			}
		});
	}
	
	public void addEmailAddress(final EmailAddress email) {
		final EmailWidget w = new EmailWidget(email);
		Anchor x = new Anchor("remove");
		x.setStyleName(ContactView.ContactViewResources.INSTANCE.css().removeItem());
		x.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getContact().getEmail().remove(email);
				w.removeFromParent();
			}
		});
		w.add(x);
		
		emailaddresses.add(w);
	}
	
	public void addPhone(final PhoneNumber p) {
		final PhoneWidget w = new PhoneWidget(p);
		Anchor x = new Anchor("remove");
		x.setStyleName(ContactView.ContactViewResources.INSTANCE.css().removeItem());
		x.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getContact().getPhone().remove(p);
				w.removeFromParent();
			}
		});
		w.add(x);
		
		phonenumbers.add(w);
	}
	
	public void addWebsite(final Website website) {
		final WebsiteWidget w = new WebsiteWidget(website);
		Anchor x = new Anchor("remove");
		x.setStyleName(ContactView.ContactViewResources.INSTANCE.css().removeItem());
		x.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getContact().getWebsite().remove(website);
				w.removeFromParent();
			}
		});
		w.add(x);
		
		websites.add(w);
	}
	
	public ClientContact getContact() {
		return this.contact;
	}
	
	public void setContact(ClientContact contact) {
		this.contact = contact;
	}
	
	public String getBlobImageURL() {
		if (getContact().getPictureBlobKey() == null) {
			ImageResource img = ContactWidgetImages.INSTANCE.noPicture();
			contactImage.setWidth(img.getWidth() + "px");
			return img.getURL();
		}
		contactImage.setWidth("160px");
		return GWT.getModuleBaseURL() + "blobrender?id=" + getContact().getPictureBlobKey() + "&w=" + 160; 
	}
}
