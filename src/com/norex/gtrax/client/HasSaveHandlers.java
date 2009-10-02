package com.norex.gtrax.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasSaveHandlers extends HasHandlers {
	HandlerRegistration addSaveHandler(SaveHandler handler);
}
