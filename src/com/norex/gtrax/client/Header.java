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
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.NotLoggedInException;
import com.norex.gtrax.client.authentication.auth.ClientAuth;
import com.norex.gtrax.client.authentication.group.GroupView;
import com.norex.gtrax.client.contact.ContactView;

public class Header {
	
	interface AppCSS extends CssResource {
		String header();
	}
	
	interface AppImageBundle extends ImageBundle {
		public AppImageBundle INSTANCE = GWT.create(AppImageBundle.class);
		
		@Resource("norexLogo.png")
		AbstractImagePrototype logo();
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
    private HashMap<Hyperlink, ViewInterface> itemWidgets = new HashMap<Hyperlink, ViewInterface>();
    
    /**
     * A mapping of the ViewInterface to the generated Panel (HTML history)
     */
    private HashMap<ViewInterface, Panel> itemContent = new HashMap<ViewInterface, Panel>();

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
		
		AuthServiceAsync authService = GWT.create(AuthService.class);
		authService.login(Window.Location.getHref(), new AsyncRemoteCall<ClientAuth>() {
			
			@Override
			public void onSuccess(ClientAuth result) {
				if (result.getAuthSubToken() == null) {
					if (Window.Location.getParameter("token") != null) {
						AuthServiceAsync authService = GWT.create(AuthService.class);
						authService.exchangeAuthSubToken(Window.Location.getParameter("token"), new AsyncRemoteCall<ClientAuth>() {
							@Override
							public void onSuccess(ClientAuth auth) {
								doSuccessfulLogin(auth);
							}
						});
						return;
					} else {
						Anchor a = new Anchor("AuthSub", result.getAuthSubURL());
						login.add(a);
						return;
					}
				}
				doSuccessfulLogin(result);
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				if (caught instanceof NotLoggedInException) {
					Anchor a = new Anchor("Login", ((NotLoggedInException) caught).getLoginURL());
					login.add(a);
				} else {
					super.onFailure(caught);
				}
			}
		});
		
		this.historyHandler = new ValueChangeHandler<String>() {
		    public void onValueChange(final ValueChangeEvent<String> event) {
		    	RootPanel.get("content").clear();
				Hyperlink item = itemTokens.get(event.getValue());
				if (item == null) {
					return;
				}
				ViewInterface i = itemWidgets.get(item);
				
				if (itemContent.containsKey(i)) {
					i.updateFromDataSource();
					RootPanel.get("content").add(itemContent.get(i));
				} else {
					Panel view = i.getView();
					itemContent.put(i, view);
					RootPanel.get("content").add(view);
				}
				
		    }
		};
		
		Timer t = new Timer() {
			
			@Override
			public void run() {
				//Window.alert(History.getToken());
				itemWidgets.get(itemTokens.get(History.getToken())).updateFromDataSource();
			}
		};
		t.scheduleRepeating(15000);
    }
    
    
    public Panel getHeader() {
    	return header;
    }
    
    public void doSuccessfulLogin(ClientAuth result) {
    	login.add(new Label("Logged in as " + result.getEmail()));
		
		addViewInterface("Contacts", new ContactView());
		addViewInterface("Groups", new GroupView());
		
		History.fireCurrentHistoryState();
    }

    public void addViewInterface(String text, ViewInterface viewInterface) {
		String token = getWidgetToken(viewInterface);
	
		Hyperlink widget = new Hyperlink(text, token);
	
		itemWidgets.put(widget, viewInterface);
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
