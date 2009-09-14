package com.norex.gtrax.client.project;

public interface ProjectInterface<T> {
	String getName();
	void setName(String name);
	T getContactKey();
	void setContactKey(T key);
	String getDescription();
	void setDescription(String desc);
	boolean getStatus();
	void setStatus(boolean flag);
}
