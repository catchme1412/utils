package com.raj.classifier;

import java.io.Serializable;

public class Attribute implements Serializable {
	private Serializable name;
	
	private Feature feature;
	
	public Attribute(Serializable name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return name.equals(o.toString()) && ((Attribute)o).getFeature().equals(feature);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + 17*feature.hashCode();
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}
}
