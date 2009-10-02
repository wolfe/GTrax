package com.norex.gtrax.client;

import com.google.gwt.event.shared.GwtEvent;

public class SaveEvent extends GwtEvent<SaveHandler> {

	public static Type<SaveHandler> TYPE = new Type<SaveHandler>();
	
	@Override
	protected void dispatch(SaveHandler handler) {
		handler.onSave(this);
	}

	@Override
	public GwtEvent.Type<SaveHandler> getAssociatedType() {
		return TYPE;
	}
	
	public static  Type<SaveHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<SaveHandler>();
		}
		return TYPE;
	}

}
