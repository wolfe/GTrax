package com.norex.gtrax.client.widgets;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.widgets.ViewHeader.ViewHeaderResources.ViewHeaderCSS;

public class ViewHeader extends Composite implements HasWidgets {
	
	public static boolean StylesAlreadyInjected = false;
	
	FlowPanel header = new FlowPanel();
	FlowPanel clearElement = new FlowPanel();
	
	interface ViewHeaderResources extends ClientBundle {
		public static final ViewHeaderResources INSTANCE = GWT.create(ViewHeaderResources.class);
		
		@Source("viewheader.css")
		ViewHeaderCSS css();
		
		interface ViewHeaderCSS extends CssResource {
			String widgetContainer();
			String headerContainer();
			String clearElement();
		}
	}
	
	ViewHeaderResources resources = ViewHeaderResources.INSTANCE;
	ViewHeaderCSS css = resources.css();
	
	public ViewHeader() {
		initWidget(header);
		header.addStyleName(css.headerContainer());
		
		clearElement.addStyleName(css.clearElement());
		header.add(clearElement);
		
		if (!ViewHeader.StylesAlreadyInjected) {
			StyleInjector.injectStylesheet(ViewHeaderResources.INSTANCE.css().getText());
			ViewHeader.StylesAlreadyInjected = true;
		}
	}

	public void add(Widget w) {
		FlowPanel p = new FlowPanel();
		p.add(w);
		p.addStyleName(css.widgetContainer());
		
		header.insert(p, header.getWidgetIndex(clearElement));
	}

	public void clear() {
	}

	public Iterator<Widget> iterator() {
		return header.iterator();
	}

	public boolean remove(Widget w) {
		if (w.equals(clearElement)) return false;
		
		Widget parent = w.getParent();
		if (parent instanceof FlowPanel) {
			return header.remove(parent);
		}
		return header.remove(w);
	}
}
