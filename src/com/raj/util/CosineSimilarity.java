package com.raj.util;

public class CosineSimilarity {

	public static double similarity(Vector v1, Vector v2) {
		return v1.multiply(v2).elementSum()/v1.euclideanNorm()*v2.euclideanNorm();
	}
}
