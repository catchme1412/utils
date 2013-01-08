package com.raj.classifier;

import java.io.Serializable;
import java.util.HashMap;

public class Feature extends HashMap<Attribute, Long> {
	
	private Serializable name;
	
	public Feature(Serializable name) {
		this.name = name;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Long getProb(Attribute a) {
		return super.get(a);
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return name.equals(o.toString());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
