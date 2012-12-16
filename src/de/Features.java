package de;

import java.io.Serializable;
import java.util.HashMap;

public final class Features<K, T> extends HashMap<String, Serializable> {

	private static final long serialVersionUID = 5737931426134041728L;

	public static Features<String, Serializable> create(final Serializable... inputs) {
		final Features<String, Serializable> a = new Features<String, Serializable>();
		for (int x = 0; x < inputs.length; x += 2) {
			a.put((String) inputs[x], inputs[x + 1]);
		}
		return a;
	}

}
