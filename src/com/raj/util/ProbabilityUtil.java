package com.raj.util;

public class ProbabilityUtil {

	public static double complimentaryValue(double val) {
		return 1.0 - val;
	}

	public double join(double a, double b) {
		return a * b;
	}
	
	public double aGivenB(double aAndB, double b) {
		return aAndB/b;
	}
	
	
}
