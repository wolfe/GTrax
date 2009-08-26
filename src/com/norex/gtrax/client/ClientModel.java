package com.norex.gtrax.client;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ClientModel implements ClientModelInterface {

	private List TEMP = new ArrayList();
    private Map<String, Object> m = new HashMap<String, Object>();
    
    public Object get(String key) {
	return m.get(key);
    }
    
    public void set(String key, Object value) {
	m.put(key, value);
    }
}
