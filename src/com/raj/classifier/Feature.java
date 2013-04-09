package com.raj.classifier;

import java.io.Serializable;
import java.util.HashMap;

public class Feature extends HashMap<Attribute, Long> {
	
	private Serializable name;
	
	private Qualifier qualifer;
	
	public Feature(Serializable name) {
		this.name = name;
	}
	
	public Feature(Serializable name, Qualifier qualifier) {
		this.name = name;
		this.qualifer = qualifer;
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

	public Qualifier getQualifer() {
		return qualifer;
	}

	public void setQualifer(Qualifier qualifer) {
		this.qualifer = qualifer;
	}
}
