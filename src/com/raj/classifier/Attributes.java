package com.raj.classifier;

import java.io.Serializable;
import java.util.HashMap;

public final class Attributes extends HashMap<Feature, Serializable> {

	private static final long serialVersionUID = 5737931426134041728L;

	public static Attributes create(final Serializable... inputs) {
		final Attributes a = new Attributes();
		for (int x = 0; x < inputs.length; x += 2) {
			a.put((Feature) inputs[x], inputs[x + 1]);
		}
		return a;
	}

	public static void create(Feature wind, Feature humidity, Attribute highHumidity) {
		// TODO Auto-generated method stub
		
	}

}
