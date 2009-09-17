package com.norex.gtrax.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.norex.gtrax.client.authentication.AuthService;
import com.norex.gtrax.client.authentication.AuthServiceAsync;
import com.norex.gtrax.client.authentication.auth.ClientAuth;

@SuppressWarnings("deprecation")
public class AuthSuggestBox extends Composite implements HasValue<String>, HasKeyPressHandlers, HasFocus {
	private SuggestBox box;
	public static Map<String, ClientAuth> authMap = new HashMap<String, ClientAuth>();
	
	final SuggestOracle oracle = new SuggestOracle() {
		public void requestSuggestions(Request request, Callback callback) {
			Response resp = new Response(matching(request.getQuery(), request.getLimit()));
			callback.onSuggestionsReady(request, resp);
		}
		
		public Collection<AuthSuggestion> matching(String query, int limit) {
			String prefixToMatch = query.toLowerCase();
			int count = 0;
			List<AuthSuggestion> matches = new ArrayList<AuthSuggestion>(limit);
			for (ClientAuth a : AuthSuggestBox.authMap.values()) {
				AuthSuggestion s = new AuthSuggestion(a);
				
				if (s.getDisplayString().toLowerCase().contains(prefixToMatch) && count < limit) {
					matches.add(s);
					count++;
				}
			}
			return matches;
		}
		
	};
	
	AuthServiceAsync authService = GWT.create(AuthService.class);
	
	private static class AuthSuggestion implements SuggestOracle.Suggestion {
		private ClientAuth auth;
		
		public AuthSuggestion(ClientAuth auth) {
			this.auth = auth;
		}
		
		public ClientAuth getAuth() {
			return this.auth;
		}

		public String getDisplayString() {
			return getAuth().toString();
		}

		public String getReplacementString() {
			return getAuth().getEmail();
		}
	}
	
	public static boolean RPCinitialized = false;
	
	public AuthSuggestBox() {
		if (AuthSuggestBox.authMap.isEmpty() && AuthSuggestBox.RPCinitialized == false) {
			AuthSuggestBox.RPCinitialized = true;
			authService.getAll(new AsyncRemoteCall<ArrayList<ClientAuth>>() {
				public void onSuccess(ArrayList<ClientAuth> result) {
					for (ClientAuth a : result) {
						AuthSuggestBox.authMap.put(a.getEmail(), a);
					}
				}
			});
		} else {
		}
		box = new SuggestBox(oracle);
		initWidget(box);
	}

	public String getValue() {
		return box.getValue();
	}
	
	public ClientAuth getAuthValue() {
		return AuthSuggestBox.authMap.get(getValue());
	}

	public void setValue(String value) {
		box.setValue(value);
	}

	public void setValue(String value, boolean fireEvents) {
		setValue(value);
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return box.addValueChangeHandler(handler);
	}

	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return box.addKeyPressHandler(handler);
	}

	public int getTabIndex() {
		return box.getTabIndex();
	}

	public void setAccessKey(char key) {
		box.setAccessKey(key);
	}

	public void setFocus(boolean focused) {
		box.setFocus(focused);
	}

	public void setTabIndex(int index) {
		box.setTabIndex(index);
	}

	public void addFocusListener(FocusListener listener) {
		box.addFocusListener(listener);
	}

	public void removeFocusListener(FocusListener listener) {
		box.addFocusListener(listener);
	}

	public void addKeyboardListener(KeyboardListener listener) {
		box.addKeyboardListener(listener);
	}

	public void removeKeyboardListener(KeyboardListener listener) {
		box.removeKeyboardListener(listener);
	}

}
