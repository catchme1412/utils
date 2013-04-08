package com.raj.classifier;

import java.io.Serializable;

public class Category implements Serializable  {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    //A feature is one or more attributes
	private Serializable name;
	
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
