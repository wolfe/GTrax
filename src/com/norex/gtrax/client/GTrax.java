package com.norex.gtrax.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.CssResource.Strict;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GTrax implements EntryPoint {
	final static Resources resources = Resources.INSTANCE;

	interface SiteCSS extends CssResource {
		String logo();
	}

	interface Resources extends ClientBundle {
		public static final Resources INSTANCE = GWT.create(Resources.class);

		@Source("site.css")
		@Strict
		SiteCSS css();

		@ImageOptions(repeatStyle = RepeatStyle.None)
		@Source("logo.png")
		ImageResource logo();

	}
	
	private static Panel content = null;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		StyleInjector.injectStylesheet(resources.css().getText());

		Header header = new Header();
		RootPanel.get().clear();
		RootPanel.get("header").add(header.getHeader());

		History.addValueChangeHandler(header.historyHandler);
		History.fireCurrentHistoryState();
		
	}

	public static native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;

	public static void setContent(Panel content) {
		GTrax.content = content;
	}

	public static Panel getContent() {
		return GTrax.content;
	}

}
