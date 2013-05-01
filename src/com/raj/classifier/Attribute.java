package com.raj.classifier;

import java.io.Serializable;

public class Attribute implements Serializable {
	
	private Serializable name;

	private Feature feature;

	private Qualifier qualifier;

	public Attribute(Attribute attribute) {
		this.name = attribute.name;
		this.feature = attribute.feature;
	}

	public Attribute(Serializable name) {
		this.name = name;
	}

	public Attribute(Serializable name, Qualifier qualifier) {
		this.name = name;
		this.qualifier = qualifier;
	}

	@Override
	public String toString() {
		return feature.toString() + "_" + name.toString() ;
	}

	@Override
	public boolean equals(Object o) {
		boolean partialEqual = name.equals(o.toString()) && ((Attribute) o).getFeature().equals(feature);
//		if (this.qualifier != null) {
//			partialEqual &= this.qualifier.qualify((Serializable) o);
//		}
		return partialEqual;
	}

	@Override
	public int hashCode() {
		return name.hashCode() + 17 * feature.hashCode();
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Qualifier getQualifier() {
		return qualifier;
	}
}
