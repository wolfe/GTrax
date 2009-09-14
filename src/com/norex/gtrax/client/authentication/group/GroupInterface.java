package com.norex.gtrax.client.authentication.group;

import java.util.Set;

import com.norex.gtrax.client.ClientModelInterface;

public interface GroupInterface<E> {
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String desc);
	
	public Set<E> getAuthSet();
	public void setAuthSet(Set<E> set);
}