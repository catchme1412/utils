package com.raj.util.similarity;

import com.raj.util.Tuple;

/**
 * Cosine Distance measure or Cosine similarity
 * 
 * @author rkv
 * 
 */
public class CosineSimilarity {

	public static double similarity(Tuple v1, Tuple v2) {
		double dotProduct = v1.dot(v2);
		double denominator = v1.euclideanNorm() * v2.euclideanNorm();
		// correct for floating-point rounding errors
		if (denominator < dotProduct) {
			denominator = dotProduct;
		}
		return dotProduct / denominator;
	}

	public static double distance(double[] p1, double[] p2) {
		double dotProduct = 0.0;
		double lengthSquaredp1 = 0.0;
		double lengthSquaredp2 = 0.0;
		for (int i = 0; i < p1.length; i++) {
			lengthSquaredp1 += p1[i] * p1[i];
			lengthSquaredp2 += p2[i] * p2[i];
			dotProduct += p1[i] * p2[i];
		}
		double denominator = Math.sqrt(lengthSquaredp1) * Math.sqrt(lengthSquaredp2);

		// correct for floating-point rounding errors
		if (denominator < dotProduct) {
			denominator = dotProduct;
		}

		return 1.0 - (dotProduct / denominator);
	}

	public static void main(String[] args) {
		//
		Tuple v1 = new Tuple(1, 2, 3);
		Tuple v2 = new Tuple(1, 3, 3);
		System.out.println(similarity(v1, v2));
		System.out.println(similarity(v2, v1));
		System.out.println(distance(v1.getArray(), v2.getArray()));
	}
}
