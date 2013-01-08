package com.raj.classifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Category implements Serializable  {
	// feature is one or more attributes
	private Serializable name;
	private Map<Feature, List<Attribute>> map;
	
	public Category(Serializable name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return name.equals(obj.toString());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
