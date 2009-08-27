package com.norex.gtrax.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.auth.Main;
import com.norex.gtrax.client.contact.ContactView;

public class Header {
    /**
     * A mapping of history tokens to their associated menu items.
     */
    private Map<String, Hyperlink> itemTokens = new HashMap<String, Hyperlink>();

    /**
     * A mapping of menu items to the widget display when the item is selected.
     */
    private Map<Hyperlink, Class> itemWidgets = new HashMap<Hyperlink, Class>();

    final DockPanel header = new DockPanel();
    HorizontalPanel menu = new HorizontalPanel();

    final ValueChangeHandler<String> historyHandler;

    public Header() {
//	addViewInterface("timesheet", (ViewInterface) GWT
//		.create(TimesheetView.class));
//	addViewInterface("customers", (ViewInterface) GWT
//		.create(ContactView.class));
//	addViewInterface("projects", (ViewInterface) GWT
//		.create(ProjectView.class));
//	addViewInterface("permissions", (ViewInterface) GWT
//		.create(PermissionsView.class));

	addViewInterface("main", Main.class);
	
	addViewInterface("contact", ContactView.class);

	
	header.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
	header.setWidth("100%");
	header.setSpacing(0);

	menu.getElement().setId("menu_table");

	header.add(menu, DockPanel.EAST);

	this.historyHandler = new ValueChangeHandler<String>() {
	    public void onValueChange(ValueChangeEvent<String> event) {
		RootPanel.get("content").clear();
		Hyperlink item = itemTokens.get(event.getValue());
		if (item == null) {
		    return;
		}

		ViewInterface i = GWT.create(itemWidgets.get(item));
		RootPanel.get("content").add(i.getView());
	    }
	};
    }
    
    public Panel getHeader() {
	return header;
    }

    public void addViewInterface(String text, Class cls) {
	String token = getWidgetToken(cls);

	Hyperlink widget = new Hyperlink(text, token);

	itemWidgets.put(widget, cls);
	itemTokens.put(token, widget);

	this.addMenuItem(widget);
    }

    private String getWidgetToken(Class cls) {
	String className = cls.getName();
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
