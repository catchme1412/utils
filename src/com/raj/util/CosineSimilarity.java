package com.raj.util;

public class CosineSimilarity {

	public static double similarity(Tuple v1, Tuple v2) {
		return v1.dot(v2)/(v1.euclideanNorm()* v2.euclideanNorm()); 
	}
	
	public static void main(String[] args) {
		Tuple v1 = new Tuple(1,2, 3);
		Tuple v2 = new Tuple(3,104);
		System.out.println(similarity(v1, v2));
		System.out.println(similarity(v2, v1));
	}
}
