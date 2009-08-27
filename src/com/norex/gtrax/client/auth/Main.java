package com.norex.gtrax.client.auth;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.ViewInterface;

public class Main implements ViewInterface {
	
	final TextBox name = new TextBox();
	final Button create = new Button("Create Company");
	final VerticalPanel p = new VerticalPanel();
	
	AuthServiceAsync authService = GWT.create(AuthService.class);
	final CompanyServiceAsync companyService = GWT.create(CompanyService.class);

    public VerticalPanel getView() {
    	
    	create.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ClientCompany m = new ClientCompany();
				m.set("name", name.getValue());
				companyService.create(m, new AsyncCallback<ClientCompany>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ClientCompany result) {
						addToList(result);
						name.setText(null);
						name.setFocus(true);
					}
				});
			}
		});
    	p.add(name);
    	p.add(create);
	
		companyService.getAll(new AsyncCallback<ArrayList<ClientCompany>>() {
	
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
	
			public void onSuccess(ArrayList<ClientCompany> results) {
				for (ClientCompany m : results) {
					final CompanyWidget w = addToList(m);
				}
			}
			
		});
	
		return p;
    }
    
    private CompanyWidget addToList(final ClientCompany m) {
    	final CompanyWidget w = new CompanyWidget(m);
    	w.addDeleteHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				w.removeFromParent();
			}
		});
    	
    	w.addOpenHandler(new OpenHandler<DisclosurePanel>() {

			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				companyService.getAuthMembers(m, new AsyncCallback<ArrayList<ClientAuth>>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(final ArrayList<ClientAuth> authlist) {
						w.clear();
						

				    	HorizontalPanel newUser = new HorizontalPanel();
				    	final TextBox email = new TextBox();
				    	Button add = new Button("add");
				    	w.add(newUser);
				    	
				    	newUser.add(new Label("Add a new user to this company: "));
				    	newUser.add(email);
				    	newUser.add(add);
				    	add.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								ClientAuth auth = new ClientAuth();
								auth.setEmail(email.getValue());
								authService.create(m, auth, new AsyncCallback<ClientAuth>() {

									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(ClientAuth result) {
										if (authlist.size() == 0) w.add(new Label("Current Members:"));
										addUserToCompany(w, result);
									}
								});
							}
						});
						
				    	
				    	if (authlist.size() > 0) {
							w.add(new Label("Current Members:"));
							for (ClientAuth a : authlist) {
								addUserToCompany(w, a);
							}
				    	}
					}
				});
			}
		});
    	
    	
    	p.insert(w, p.getWidgetIndex(name));
    	
    	return w;
    }
    
    private void addUserToCompany(CompanyWidget w, ClientAuth a) {
    	w.add(new AuthWidget(a));
    }

}
