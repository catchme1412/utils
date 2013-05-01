package com.raj.classifier;

import java.io.Serializable;
import java.util.HashMap;

public class Feature extends HashMap<Attribute, Long> {

	private Serializable name;


	public Feature(Serializable name) {
		this.name = name;
	}

//	public Feature(Serializable name, Qualifier qualifier) {
//		this.name = name;
//		this.setQualifier(qualifier);
//	}

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
		boolean isNameEqual = name.equals(o.toString());
//		if (qualifier != null) {
//			return qualifier.qualify((Serializable) o) && isNameEqual;
//		}
		return isNameEqual;
	}

	@Override
	public int hashCode() {
		return name.hashCode();// + (qualifier != null ? qualifier.hashCode() : 0);
	}

//	public Qualifier getQualifier() {
//		return qualifier;
//	}
//
//	public void setQualifier(Qualifier qualifier) {
//		this.qualifier = qualifier;
//	}

}
