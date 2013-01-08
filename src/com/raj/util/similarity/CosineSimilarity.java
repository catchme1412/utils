package com.raj.util.similarity;

import com.raj.util.Tuple;
import com.raj.util.TupleUtils;

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

	public static double similartity(double[] p1, double[] p2) {
		return 1.0 - distance(p1, p2);
	}

	public static double distance(double[] p1, double[] p2) {
		double dotProduct = TupleUtils.dotProduct(p1, p2);
		double lengthSquaredp1 = TupleUtils.squareSum(p1);
		double lengthSquaredp2 = TupleUtils.squareSum(p2);

		double denominator = Math.sqrt(lengthSquaredp1) * Math.sqrt(lengthSquaredp2);

		// correct for floating-point rounding errors
		if (denominator < dotProduct) {
			denominator = dotProduct;
		}

		return 1.0 - (dotProduct / denominator);
	}

	public static void main(String[] args) {
		//full movie, movie, song, duration, rating
		Tuple movie = new Tuple(1, 1, 0, 7, 5);
		Tuple song = new Tuple(0, 1, 1, 7, 5);
		Tuple v1 = new Tuple(0, 1, 0, 7, 4);
		Tuple v2 = new Tuple(0, 1, 1, 6, 3);
		System.out.println(similarity(v1, movie));
		System.out.println(similarity(v1, song));
		System.out.println(similarity(v2, v1));
		System.out.println(distance(v1.getElements(), v2.getElements()));
	}
}
