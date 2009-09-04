package com.norex.gtrax.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;

public class BlobImage extends Image {
	private String key = null;
	private int w;
	
	public BlobImage(String key) {
		this.key = key;
		this.setUrl(key);
	}
	
	public BlobImage(String key, int w) {
		this.key = key;
		this.w = w;
		
		this.setUrl(key);
		this.setWidth(w + "px");
	}
	
	@Override
	public void setUrl(String key) {
		if (key == null) {
			super.setUrl(null);
		} else {
			super.setUrl(GWT.getModuleBaseURL() + "blobrender?id=" + key + "&w=" + this.w);
		}
	}
}
