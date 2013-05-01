package com.raj.classifier;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Qualifier implements Serializable {

	private static final long serialVersionUID = 1L;

	private Serializable value;

	private boolean isNegation;

	private String toStringText;

	private Qualifier(String toStringText) {
		this.toStringText = toStringText;
	}

	public static Qualifier equalsTo(Serializable i) {
		Qualifier q = new Qualifier("equalsTo " + i);
		q.value = i;
		return q;
	}

	public static Qualifier greateThan(Serializable i) {
		Qualifier q = new Qualifier("greaterThan " + i);
		q.value = i;
		return q;
	}

	public static Qualifier lessThan(Serializable i) {
		Qualifier q = new Qualifier("lessThan " + i);
		q.value = i;
		return q;
	}

	public boolean isGreaterThan(Serializable v) {
		return invokeCompareTo(v) >= 1;
	}

	public boolean isLessThan(Serializable v) {
		return invokeCompareTo(v) <= -1;
	}

	public boolean isEqualTo(Serializable v) {
		return invokeCompareTo(v) == 0;
	}

	public static Qualifier contains(String containsText) {
		Qualifier q = new Qualifier("containsText " + containsText);
		q.value = containsText;
		return q;
	}

	public boolean isContains(String text) {
		return text.contains(value.toString());
	}

	// public static boolean isContainsText(Serializable v) {
	// Qualifier q = new Qualifier("isContainsText " + v);
	// q.value = v;
	// return q;
	// }

	private int invokeCompareTo(Serializable v) {
		Class<?> c = v.getClass();
		try {
			Method main = c.getMethod("compareTo", Object.class);
			Object r = main.invoke(v, value);
			return (Integer) r;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException();
	}

	public static void main(String[] args) {
		Qualifier q = (Qualifier) Qualifier.greateThan(10.5);
		System.out.println(q.equals(12.0));
		System.out.println(q.isGreaterThan(1.5));
		System.out.println(q.isEqualTo(10.5));
		System.out.println(q.isLessThan(10.0));
		Qualifier q2 = (Qualifier) Qualifier.equalsTo(10.5);
		System.out.println(q2.isEqualTo(10.5));
		System.out.println(q2.equals(10.5));
		Qualifier q3 = (Qualifier) Qualifier.contains("movie");
		System.out.println(q3.isContains("da thad movie"));
		// System.out.println(q.isContainsText("DDDD"));

	}

	// @Override
	// public boolean equals(Object obj) {
	// if (toStringText.contains("greaterThan")) {
	// return isGreaterThan((Serializable) obj);
	// }
	// return value.equals(obj);
	// }

	@Override
	public String toString() {
		return toStringText;
	}

	public boolean qualify(Serializable attribute) {
		boolean result = false;
		if (toStringText.contains("containsText")) {
			result =  isContains(attribute.toString());
		} else if (toStringText.contains("greaterThan")) {
			result =  isGreaterThan((Serializable) attribute);
		} else if (toStringText.contains("lessThan")) {
			result =  isLessThan(attribute);
		}
		return (isNegation == true ? !result : result);
	}

	public boolean isNegation() {
		return isNegation;
	}

	public Qualifier setNegation(boolean isNegation) {
		this.isNegation = isNegation;
		return this;
	}

}
