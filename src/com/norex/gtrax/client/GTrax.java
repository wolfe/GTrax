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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.norex.gtrax.client.authentication.auth.ClientAuth;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GTrax implements EntryPoint {
	final static Resources resources = Resources.INSTANCE;
	public static GTrax INSTANCE = null;

	interface SiteCSS extends CssResource {
		String content();
		String errorMessage();
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
	
	private Header header;
	private static ClientAuth auth;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		GTrax.INSTANCE = this;
		
		StyleInjector.injectStylesheet(resources.css().getText());
		
		header =  new Header();

		RootPanel.get().clear();
		RootPanel.get("content").addStyleName(resources.css().content());
		RootPanel.get("header").add(header.getHeader());

		HandlerRegistration registration = History.addValueChangeHandler(header.historyHandler);
		History.fireCurrentHistoryState();
	}

	public static native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;

	public static void setContent(Panel content) {
		GTrax.content = content;
	}
	
	public Header getHeader() {
		return header;
	}

	public static Panel getContent() {
		return GTrax.content;
	}

	public <T> T getViewInstance(String type) {
		return (T)getHeader().getViewInstance(type);
	}
	
	public static String subHistoryToken(Class cls, String token) {
		return Header.getWidgetToken(cls) + "/" + token;
	}

	public static void setAuth(ClientAuth auth) {
		GTrax.auth = auth;
	}

	public static ClientAuth getAuth() {
		return auth;
	}
}
