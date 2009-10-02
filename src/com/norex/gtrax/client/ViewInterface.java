package com.norex.gtrax.client;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Panel;

public interface ViewInterface {
    public Panel getView();
    public void updateFromDataSource();
    public void fireSubHistory(String subItem);
}
