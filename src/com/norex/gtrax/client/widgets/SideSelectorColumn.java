package com.norex.gtrax.client.widgets;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.norex.gtrax.client.widgets.SideSelectorColumn.SideSelectorColumnResources.SideSelectorColumnCSS;

public class SideSelectorColumn extends Composite implements HasWidgets {
	public static boolean StylesAlreadyInjected = false;
	
	FlowPanel container = new FlowPanel();
	FlowPanel columnHeader = new FlowPanel();
	FlowPanel columnContainer = new FlowPanel();
	
	interface SideSelectorColumnResources extends ClientBundle {
		
		@Source("sideselectorcolumn.css")
		SideSelectorColumnCSS css();
		
		interface SideSelectorColumnCSS extends CssResource {
			String column();
			String columnHeader();
			String columnContainer();
			String selectorItem();
			
			@ClassName("selectorItem-selected")
			String selectorItemSelected();
		}
	}
	
	SideSelectorColumnResources resources = GWT.create(SideSelectorColumnResources.class);
	SideSelectorColumnCSS css = resources.css();
	
	public class SideSelectColumnItem extends Composite implements HasText, HasClickHandlers {
		private String selectedMarkerString = "+ ";
		
		HTML item = new HTML();
		public SideSelectColumnItem() {
			initWidget(item);
			
			item.setStyleName(css.selectorItem());
			
			addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					clearSelected();
					setSelected();
				}
			});
		}
		public String getText() {
			return item.getHTML();
		}
		public void setText(String text) {
			item.setHTML(text);
		}
		public HandlerRegistration addClickHandler(ClickHandler handler) {
			return item.addClickHandler(handler);
		}
		
		public void setSelected() {
			if (!getText().startsWith(selectedMarkerString)) {
				item.setText(selectedMarkerString + item.getText());
			}
			item.addStyleName(css.selectorItemSelected());
		}
		
		public void unsetSelected() {
			if (getText().startsWith(selectedMarkerString)) {
				setText(getText().substring(selectedMarkerString.length()));
			}
			item.removeStyleName(css.selectorItemSelected());
		}
	}
	
	public SideSelectorColumn() {
		initWidget(container);
		container.addStyleName(css.column());
		
		columnHeader.addStyleName(css.columnHeader());
		container.add(columnHeader);
		
		columnContainer.addStyleName(css.columnContainer());
		container.add(columnContainer);
		
		if (!SideSelectorColumn.StylesAlreadyInjected) {
			StyleInjector.injectStylesheet(css.getText());
			SideSelectorColumn.StylesAlreadyInjected = true;
		}
	}

	private void clearSelected() {
		Iterator<Widget> itr = iterator();
		while (itr.hasNext()) {
			((SideSelectColumnItem)itr.next()).unsetSelected();
		}
		
		Iterator<Widget> headItr = columnHeader.iterator();
		while (headItr.hasNext()) {
			((SideSelectColumnItem)headItr.next()).unsetSelected();
		}
	}
	
	public void add(Widget w) {
		columnContainer.add(w);
	}
	
	public void setColumnHeader(SideSelectColumnItem w) {
		columnHeader.clear();
		columnHeader.add(w);
	}

	public void clear() {
		columnContainer.clear();
	}

	public Iterator<Widget> iterator() {
		return columnContainer.iterator();
	}

	public boolean remove(Widget w) {
		return columnContainer.remove(w);
	}
}
