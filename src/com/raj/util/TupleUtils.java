package com.raj.util;

public class TupleUtils {

	public static double sum(double[] elements) {
		double sum = 0;
		for (double e : elements) {
			sum += e;
		}
		return sum;
	}
	
	public static double dotProduct(double[] x, double[] y) {
		double sum = 0;
		int i = 0;
		for (double e : x) {
			sum += e*y[i++];
		}
		return sum;
	}
	
	public static double suqreSum(double[] x) {
		double sqSum = 0;
		for (double e : x) {
			sqSum += e*e;
		}
		return sqSum;
	}
	
	public static double average(double[] x) {
		return sum(x)/x.length;
	}
	
	
}
