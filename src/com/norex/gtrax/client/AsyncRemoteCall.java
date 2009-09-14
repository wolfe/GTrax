package com.norex.gtrax.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class AsyncRemoteCall<T> implements AsyncCallback<T> {
	public void onFailure(Throwable caught) {
		final Label error = new Label("error: " + caught.getMessage());
		error.addStyleName(GTrax.resources.css().errorMessage());
		
		RootPanel.get().add(error);
		
		Timer t = new Timer() {
			public void run() {
				error.removeFromParent();
			}
		};
		t.schedule(5000);
	}
}
