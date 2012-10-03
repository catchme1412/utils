package com.raj.util;

public class Vector {

	private double[] e;

	public Vector(double... val) {
		this.e = val;
	}

	public Vector multiply(Vector v) {
		double[] other = v.getArray();
		double[] result = new double[e.length];
		int i = 0;
		for (double val : e) {
			result[i] = val*other[i];
			i++;
		}
		return new Vector(result); 
	}

	/**
	 * @param v
	 * @return ||v||
	 */
	public double euclideanNorm() {
		double squareSum = 0;
		for (double val : e) {
			squareSum += val*val;
		}
		return Math.sqrt(squareSum);
	}

	public double[] getArray() {
		return e;
	}

	public void setArray(double[] e) {
		this.e = e;
	}
	
	public double elementSum() {
		double sum = 0;
		for (double v: e) {
			sum += v;
		}
		return sum;
	}
	
}
