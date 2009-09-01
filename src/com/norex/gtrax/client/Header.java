package com.norex.gtrax.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.auth.ClientAuth;
import com.norex.gtrax.client.auth.ClientCompany;
import com.norex.gtrax.client.auth.CompanyService;
import com.norex.gtrax.client.auth.CompanyServiceAsync;
import com.norex.gtrax.client.auth.Main;
import com.norex.gtrax.client.auth.NotLoggedInException;
import com.norex.gtrax.client.contact.ContactView;

public class Header {
	
	interface AppCSS extends CssResource {
		String header();
	}
	
	interface AppImageBundle extends ImageBundle {
		public AppImageBundle INSTANCE = GWT.create(AppImageBundle.class);
		
		@Resource("norexLogo.png")
		AbstractImagePrototype logo();
		
		@Resource("logo.png")
		AbstractImagePrototype logoWhite();
		
	}
	
	interface AppResource extends ClientBundle {
		public AppResource INSTANCE = GWT.create(AppResource.class);
		
		@Source("app.css")
		AppCSS css();
		
		@Source("top-bg.png")
		@ImageOptions(repeatStyle=RepeatStyle.Horizontal)
		ImageResource background();
	}
	
    /**
     * A mapping of history tokens to their associated menu items.
     */
    private Map<String, Hyperlink> itemTokens = new HashMap<String, Hyperlink>();

    /**
     * A mapping of menu items to the widget display when the item is selected.
     */
    private Map<Hyperlink, ViewInterface> itemWidgets = new HashMap<Hyperlink, ViewInterface>();

    final DockPanel header = new DockPanel();
    HorizontalPanel menu = new HorizontalPanel();
    final HorizontalPanel login = new HorizontalPanel();

    ValueChangeHandler<String> historyHandler;

    public Header() {
    	StyleInjector.injectStylesheet(AppResource.INSTANCE.css().getText());

    	menu.add(AppImageBundle.INSTANCE.logo().createImage());
    	
		header.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
		header.setWidth("100%");
		header.setSpacing(0);
		header.addStyleName(AppResource.INSTANCE.css().header());
		
		menu.getElement().setId("menu_table");
	
		header.add(login, DockPanel.EAST);
		header.add(menu, DockPanel.EAST);
		
		CompanyServiceAsync companyService = GWT.create(CompanyService.class);
		companyService.login(Window.Location.getHref(), new AsyncCallback<ClientAuth>() {
			
			@Override
			public void onSuccess(ClientAuth result) {
				login.add(new Label("Logged in as " + result.getEmail()));
				
				//addViewInterface("Companies", new Main());
				addViewInterface("Contacts", new ContactView());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof NotLoggedInException) {
					//Window.Location.replace(((NotLoggedInException) caught).getLoginURL());
				}
			}
		});
		
		addViewInterface("Companies", new Main());
		
		this.historyHandler = new ValueChangeHandler<String>() {
		    public void onValueChange(final ValueChangeEvent<String> event) {
		    	RootPanel.get("content").clear();
				Hyperlink item = itemTokens.get(event.getValue());
				if (item == null) {
					return;
				}
				//ViewInterface i = GWT.create(itemWidgets.get(item));
				RootPanel.get("content").add(itemWidgets.get(item).getView());
		    }
		};
    }
    
    
    public Panel getHeader() {
    	return header;
    }

    public void addViewInterface(String text, ViewInterface cls) {
		String token = getWidgetToken(cls);
	
		Hyperlink widget = new Hyperlink(text, token);
	
		itemWidgets.put(widget, cls);
		itemTokens.put(token, widget);
	
		this.addMenuItem(widget);
    }

    private String getWidgetToken(ViewInterface cls) {
		String className = cls.getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className;
    }

    public DockPanel getComposite() {
    	return this.header;
    }

    public void addMenuItem(Hyperlink item) {
    	menu.add(item);
    }

    public void addItem(Widget item) {
    	header.add(item, DockPanel.WEST);
    }
}
