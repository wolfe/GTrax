package com.norex.gtrax.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncRemoteCall<T> implements AsyncCallback<T> {
	interface Resources extends ClientBundle {
		
	}
	
	public void onFailure(Throwable caught) {
		Window.alert("fail");
	}
}
