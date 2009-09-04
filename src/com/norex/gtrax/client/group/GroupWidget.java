package com.norex.gtrax.client.group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class GroupWidget extends Composite {

	HorizontalPanel container = new HorizontalPanel();
	
	public GroupWidget(ClientGroup group) {
		container.add(new Label(group.getName()));
		
//		final FormPanel form = new FormPanel();
//		form.setAction(GWT.getModuleBaseURL() + "fileupload");
//		form.setMethod(FormPanel.METHOD_POST);
//		form.setEncoding(FormPanel.ENCODING_MULTIPART);
//		
//		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
//			
//			@Override
//			public void onSubmitComplete(SubmitCompleteEvent event) {
//				Window.alert(event.getResults());
//				String results = event.getResults();
//			}
//		});
//		
//		FileUpload file = new FileUpload();
//		file.setName("imageupload");
//		
//		form.add(file);
//		container.add(form);
//		
//		Button b = new Button("submit");
//		b.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				form.submit();
//			}
//		});
//		container.add(b);
		
		initWidget(container);
	}
	
}
