package com.norex.gtrax.client.contact;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.norex.gtrax.client.ViewInterface;

public class ContactView implements ViewInterface {
	
	VerticalPanel p = new VerticalPanel();

	public Panel getView() {
		p.add(new Label("This is the contact view"));
		
		return p;
	}

}
