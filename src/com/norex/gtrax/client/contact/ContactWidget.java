package com.norex.gtrax.client.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.GTrax;
import com.norex.gtrax.client.HasSaveHandlers;
import com.norex.gtrax.client.Header;
import com.norex.gtrax.client.SaveEvent;
import com.norex.gtrax.client.SaveHandler;
import com.norex.gtrax.client.contact.EmailWidget;
import com.norex.gtrax.client.contact.WebsiteWidget;
import com.norex.gtrax.client.contact.PhoneWidget;
import com.norex.gtrax.client.project.ClientProject;
import com.norex.gtrax.client.project.ProjectService;
import com.norex.gtrax.client.project.ProjectServiceAsync;
import com.norex.gtrax.client.project.ProjectView;

import java.util.Date;

public class ContactWidget extends Composite implements HasSaveHandlers {

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
	
	@UiField
	VerticalPanel projects;
	
	ContactServiceAsync contactService = GWT.create(ContactService.class);
	
	public ContactWidget(final ClientContact contact) {
		setContact(contact);
		initWidget(uiBinder.createAndBindUi(this));
		
		this.addSaveHandler(new SaveHandler() {
			public void onSave(SaveEvent event) {
				getContact().setName(name.getValue());
				getContact().setNote(notes.getValue());
				getContact().setBirthday(birthday.getValue());
				
				contactService.save(getContact(), new AsyncRemoteCall<ClientContact>() {
					public void onSuccess(ClientContact result) {
						setContact(result);
					}
				});
			}
		});
		
		birthday.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		birthday.setValue(getContact().getBirthday());
		name.setValue(getContact().getName());
		notes.setValue(getContact().getNote());
		contactImage.setUrl(getBlobImageURL());
		
		doEmailAddressBits();
		doPhoneNumberBits();
		doWebsiteBits();
		setUpContactImageHandlers();
		
		contactService.getContactProjects(getContact(), new AsyncRemoteCall<ArrayList<ClientProject>>() {

			public void onSuccess(ArrayList<ClientProject> result) {
				if (!result.iterator().hasNext()) return;
				for (ClientProject p : result) {
					addProjectToList(p);
				}
			}
		});
		
		container.setWidth("100%");
	}
	
	private void addProjectToList(ClientProject p) {
		projects.add(new Hyperlink(p.getName(), "ProjectView/" + p.getId()));
	}
	
	private void setUpContactImageHandlers() {
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
						contactService.save(getContact(), new AsyncRemoteCall<ClientContact>() {
							public void onSuccess(ClientContact result) {
								setContact(result);
								contactImage.setUrl(getBlobImageURL());
								popup.hide();
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
	}
	
	/**
	 * Adds email property fields to the contact and creates "add" element (with {@link ClickHandler})
	 */
	private void doEmailAddressBits() {
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
	}
	
	/**
	 * Adds phone number property fields to the contact and creates "add" element (with {@link ClickHandler})
	 */
	private void doPhoneNumberBits() {
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
	}
	
	/**
	 * Adds website property fields to the contact and creates "add" element (with {@link ClickHandler})
	 */
	private void doWebsiteBits() {
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
	}
	
	/**
	 * Add a new email address to the contact
	 * 
	 * @param email {@link EmailAddress} to be added to contact
	 */
	public void addEmailAddress(final EmailAddress email) {
		final EmailWidget w = new EmailWidget(email);
		Anchor x = new Anchor("remove");
		x.setStyleName(ContactView.ContactViewResources.INSTANCE.css().removeItem());
		x.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getContact().getEmail().remove(email);
				w.removeFromParent();
			}
		});
		w.add(x);
		
		emailaddresses.add(w);
	}
	
	/**
	 * Add a new phone number to the contact
	 * 
	 * @param p {@link PhoneNumber} to be added to contact
	 */
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
	
	/**
	 * Add a new website to the contact
	 * 
	 * @param p {@link Website} to be added to contact
	 */
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
	
	@UiHandler("createDeal")
	public void doCreateDeal(ClickEvent event) {
		
	}
	
	@UiHandler("createProject")
	public void doCreateProject(ClickEvent event) {
		final ProjectView projectView = GTrax.INSTANCE.getViewInstance(Header.getWidgetToken(ProjectView.class));

		projectView.updateFromDataSource();
		
		ClientProject p = new ClientProject();
		p.setContactKey(getContact().getId());
		p.setName("Project for: " + getContact().getName());
		p.setStatus(true);
		
		ProjectServiceAsync projectService = GWT.create(ProjectService.class);
		projectService.save(p, new AsyncRemoteCall<ClientProject>() {
			public void onSuccess(ClientProject result) {
				addProjectToList(result);
				projectView.addProjectToMap(result);
				History.newItem(GTrax.subHistoryToken(ProjectView.class, result.getId()));
			}
		});
	}
	
	/**
	 * Get the contact associated with the widget.
	 * @return {@link ClientContact} associated with widget
	 */
	public ClientContact getContact() {
		return this.contact;
	}
	
	/**
	 * Sets the contact associated with the widget.
	 * @param contact {@link ClientContact} to be associated with widget.
	 */
	public void setContact(ClientContact contact) {
		this.contact = contact;
	}
	
	/**
	 * Gets the url of the contact image
	 * @return String URL of image.
	 */
	public String getBlobImageURL() {
		if (getContact().getPictureBlobKey() == null) {
			ImageResource img = ContactWidgetImages.INSTANCE.noPicture();
			contactImage.setWidth(img.getWidth() + "px");
			return img.getURL();
		}
		contactImage.setWidth("160px");
		return GWT.getModuleBaseURL() + "blobrender?id=" + getContact().getPictureBlobKey() + "&w=" + 160; 
	}

	/**
	 * Adds a save handler to the widget.
	 * @return {@link HandlerRegistration}
	 */
	public HandlerRegistration addSaveHandler(SaveHandler handler) {
		return this.addHandler(handler, SaveEvent.getType());
	}
}
