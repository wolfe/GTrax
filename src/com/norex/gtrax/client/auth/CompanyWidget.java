package com.norex.gtrax.client.auth;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.ClientModel;

public class CompanyWidget extends Composite implements HasWidgets {

	final DisclosurePanelImages images = (DisclosurePanelImages)GWT.create(DisclosurePanelImages.class);
	
	class DisclosurePanelHeader extends HorizontalPanel {
		public DisclosurePanelHeader(boolean isOpen, String html) {
			add(isOpen ? images.disclosurePanelOpen().createImage() : images.disclosurePanelClosed().createImage());
	    	add(new HTML(html));
		}
	}

	private DisclosurePanel container;
	private VerticalPanel p = new VerticalPanel();
	private HorizontalPanel editpanel = new HorizontalPanel();
	private TextBox name = new TextBox();
	private DisclosurePanelHeader label;
	private Button edit = new Button("edit");
	private VerticalPanel publicPanel = new VerticalPanel();
	
	private ClientCompany company = new ClientCompany();
	private ClickHandler deleteHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
	private ClickHandler saveHandler = new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
	private OpenHandler<DisclosurePanel> openHandler = new OpenHandler<DisclosurePanel>() {

		@Override
		public void onOpen(OpenEvent<DisclosurePanel> event) {
		}
	};
	private CloseHandler<DisclosurePanel> closeHandler = new CloseHandler<DisclosurePanel>() {

		@Override
		public void onClose(CloseEvent<DisclosurePanel> event) {
		}
	};
	
	@SuppressWarnings("deprecation")
	public CompanyWidget(ClientCompany c) {
		this.company = c;
		
		container = new DisclosurePanel((String) company.get("name"));
		container.setAnimationEnabled(true);
		container.setContent(p);

		container.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				setName(company.getName());
				openHandler.onOpen(event);
			}
		});
		container.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				setName((String) company.getName());
				closeHandler.onClose(event);
			}
		});
		edit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				edit();
			}
		});
		show();
		
		p.add(editpanel);
		p.add(publicPanel);
		initWidget(container);
	}

	public void show() {
		editpanel.clear();
		
		setName(company.getName());
		
		editpanel.add(edit);
	}
	
	public void edit() {
		final CompanyWidget w = this;
		editpanel.clear();
		editpanel.add(name);
		
		name.setValue(company.getName());
		name.setFocus(true);
		
		Button save = new Button();
		save.setText("Save");
		save.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(final ClickEvent event) {
				if (name.getValue().equals(company.getName())) {
					show();
					return;
				}
				
				CompanyServiceAsync companyService = GWT.create(CompanyService.class);
				company.setName(name.getValue());
				companyService.save(company, new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Object result) {
						saveHandler.onClick(event);
						show();
					}
				});
			}
		});
		Button delete = new Button("Delete");
		delete.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(final ClickEvent event) {
				if (Window.confirm("Are you sure??")) {
					CompanyServiceAsync companyService = GWT.create(CompanyService.class);
					companyService.delete(company, new AsyncCallback() {

						@Override
						public void onSuccess(Object result) {
							deleteHandler.onClick(event);
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}
		});
		editpanel.add(save);
		editpanel.add(delete);
	
	}
	
	public void setName(String name) {
		container.setHeader(new DisclosurePanelHeader(container.isOpen(), name));
	}
	
	public void addDeleteHandler(ClickHandler gwtEvent) {
		this.deleteHandler = gwtEvent;
	}
	
	public void addSaveHander(ClickHandler gwtEvent) {
		this.saveHandler = gwtEvent;
	}
	
	public void addOpenHandler(OpenHandler<DisclosurePanel> event) {
		this.openHandler = event;
	}

	public void addCloseHandler(CloseHandler<DisclosurePanel> event) {
		this.closeHandler = event;
	}
	
	@Override
	public void add(Widget w) {
		publicPanel.add(w);
	}

	@Override
	public void clear() {
		publicPanel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return publicPanel.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return publicPanel.remove(w);
	}
}

