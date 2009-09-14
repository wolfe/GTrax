package com.norex.gtrax.client.project;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.norex.gtrax.client.AsyncRemoteCall;
import com.norex.gtrax.client.ViewInterface;
import com.norex.gtrax.client.contact.ClientContact;
import com.norex.gtrax.client.contact.ContactService;
import com.norex.gtrax.client.contact.ContactServiceAsync;
import com.norex.gtrax.client.contact.ContactView;

public class ProjectView implements ViewInterface {
	
	public static ProjectServiceAsync projectService = GWT.create(ProjectService.class);
	HashMap<String, ClientContact> contacts = new HashMap<String, ClientContact>();
	HashMap<String, ClientProject> projects = new HashMap<String, ClientProject>();
	HashMap<String, ProjectWidget> projectWidgetMap = new HashMap<String, ProjectWidget>();
	
	interface MyUiBinder extends UiBinder<Panel, ProjectView> {
	}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	interface WidgetResources extends ClientBundle {
		interface Css extends CssResource {
			String widgetContainer();
		}
		
		@Source("ProjectWidget.css")
		Css css();
	}
	
	public WidgetResources resources = GWT.create(WidgetResources.class);
	
	@UiField
	TextBox newProjectName;
	
	@UiField
	ListBox newProjectContact;
	
	@UiField
	FlowPanel projectsContainer;
	
	Panel container;
	
	public Panel getView() {
		container = uiBinder.createAndBindUi(this);
		updateFromDataSource();
		
		StyleInjector.injectStylesheet(resources.css().getText());
		
		return container;
	}

	@UiHandler("addNewProject")
	public void doAddNewProject(ClickEvent event) {
		doAddNewProject();
	}
	
	public void doAddNewProject() {
		ClientProject p = new ClientProject();
		p.setName(newProjectName.getValue());
		p.setContactKey(newProjectContact.getValue(newProjectContact.getSelectedIndex()));
		
		projectService.save(p, new AsyncRemoteCall<ClientProject>() {
			public void onSuccess(ClientProject result) {
				addProjectToMap(result);
				newProjectName.setValue(null);
				newProjectContact.setSelectedIndex(0);
			}
		});
	}
	
	public void updateFromDataSource() {
		ContactView.contactService.getContacts(new AsyncRemoteCall<ArrayList<ClientContact>>() {
			public void onSuccess(ArrayList<ClientContact> result) {
				for (ClientContact c : result) {
					if (contacts.get(c.getId()) == null) {
						newProjectContact.addItem(c.getName(), c.getId());
					}
					contacts.put(c.getId(), c);
				}
				
				for (ClientContact c : contacts.values()) {
					if (!result.contains(c)) {
						contacts.remove(c.getId());
						for (int i = 0; i <= newProjectContact.getItemCount(); i++) {
							if (newProjectContact.getValue(i).equals(c.getId())) {
								newProjectContact.removeItem(i);
							}
						}
					}
				}
				
				for (int i = 0; i <= newProjectContact.getItemCount(); i++) {
					newProjectContact.setItemText(i, contacts.get(newProjectContact.getValue(i)).getName());
				}
			}
		});
		
		projectService.getProjects(new AsyncRemoteCall<ArrayList<ClientProject>>() {
			public void onSuccess(ArrayList<ClientProject> result) {
				for (ClientProject p : result) {
					addProjectToMap(p);
				}
			}
		});
	}
	
	private void addProjectToMap(ClientProject p) {
		projects.put(p.getId(), p);
		
		if (!projectWidgetMap.containsKey(p.getId())) {
			ProjectWidget w = new ProjectWidget(p);
			w.setContact(contacts.get(p.getContactKey()));
			
			projectWidgetMap.put(p.getId(), w);
			
			w.addStyleName(resources.css().widgetContainer());
			projectsContainer.add(w);
		} else {
			ProjectWidget w = projectWidgetMap.get(p.getId());
			w.setProject(p);
			w.setContact(contacts.get(p.getContactKey()));
		}
	}

}
