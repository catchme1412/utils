package com.raj.util.similarity;

import com.raj.util.Tuple;
import com.raj.util.TupleUtils;

public class FittingOfStraightLine {

	/**
	 * @param p
	 *            only 2D tuples
	 * @return the slope
	 */
	public static double getSlope(Tuple... p) {

		double xSum = 0;
		double ySum = 0;
		double xSquare = 0;
		double xySum = 0;
		for (Tuple e : p) {
			double x = e.getElements()[0];
			double y = e.getElements()[1];
			xSum += x;
			ySum += y;
			xySum += x * y;
			xSquare += x * x;
		}

		double m = (p.length * xySum - xSum * ySum) / (p.length * xSquare - xSum * xSum);
		return m;
		// typescriptlang.org
	}

	public static double getSlope(double[] y) {
		double xSum = 0;
		double ySum = 0;
		double xSquare = 0;
		double xySum = 0;

		for (int x = 0; x < y.length; x++) {
			xSum += x;
			ySum += y[x];
			xySum += x * y[x];
			xSquare = x * x;
		}
		double m = (y.length * xySum - xSum * ySum) / (y.length * xSquare - xSum * xSum);
		return m;
	}

	public static double getSlope(double[] x, double[] y) {
		double xSum = TupleUtils.sumAtIndex(0, new Tuple(x), new Tuple(y));
		double ySum = TupleUtils.sumAtIndex(1, new Tuple(x), new Tuple(y));
		double xySum = TupleUtils.dotProduct(x, y);
		double xSquare = TupleUtils.squareSum(x);
		double m = (y.length * xySum - xSum * ySum) / (y.length * xSquare - xSum * xSum);
		return m;
	}

	public static void main(String[] args) {
		double[] x = { 0, 0 };
		double[] y = { 1, 1 };
		System.out.println(getSlope(x, y));
	}

}
