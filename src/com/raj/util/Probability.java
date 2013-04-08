package com.raj.util;

public class Probability extends Number {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double value;

	public Probability(double prob) {
		if (prob < 0 || prob > 1) {
			new AssertionError("Out of range probability:" + prob);
		}
		this.value = prob;
	}

	@Override
	public int intValue() {
		return (int) value;
	}

	@Override
	public long longValue() {
		return (long) value;
	}

	@Override
	public float floatValue() {
		return (float) value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

}
