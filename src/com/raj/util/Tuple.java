package com.raj.util;

public class Tuple {

	private double[] e;

	private boolean isAvgCalculated;

	private double average;

	private boolean isSumCalculated;

	private double sum;

	private boolean isSquareCalculated;

	private double square;

	public Tuple(double... val) {
		this.e = val;
	}

	/**
	 * Return the dot product of two vectors (tuples)
	 */
	public double dot(Tuple v) {
		double[] other = v.getArray();
		double result = 0.0;
		int i = 0;
		for (double val : e) {
			result += val * other[i];
			i++;
		}
		return result;
	}

	public Tuple multiply(double scalarValue) {
		int i = 0;
		for (double v : e) {
			e[i] = v * scalarValue;
			i++;
		}
		return this;
	}
	
	public Tuple add(Tuple t) {
		return this;
	}
	
	public Tuple add(double scalar) {
		return this;
	}

	public double sum() {
		if (!isSumCalculated) {
			for (double val : e) {
				sum += val;
			}
			isSumCalculated = true;
		}
		return sum;
	}

	public double getAverage() {
		if (!isAvgCalculated) {
			average = sum() / e.length;
			isAvgCalculated = true;
		}
		return average;
	}

	public double square() {
		if (!isSquareCalculated) {
			for (double val : e) {
				square += val * val;
			}
			isSquareCalculated = true;
		}
		return square;
	}

	/**
	 * @return ||v||
	 */
	public double euclideanNorm() {
		return Math.sqrt(square());
	}

	public double[] getArray() {
		return e;
	}

	public void setArray(double[] e) {
		this.e = e;
	}

	public double elementSum() {
		double sum = 0;
		for (double v : e) {
			sum += v;
		}
		return sum;
	}

	public double correlationCoefficient(Tuple y) {
		double sumX = this.sum();
		double sumY = y.sum();
		double sumXY = this.dot(y);
		double xSquare = this.square();
		double ySquare = y.square();
		int n = e.length;
		return (n * sumXY - sumX * sumY)
				/ (Math.sqrt(n * xSquare - sumX * sumX) * Math.sqrt(n * ySquare - sumY * sumY));
	}

	public static void main(String[] args) {
		Tuple t1 = new Tuple(1, 1);
		Tuple t2 = new Tuple(1, 1);
		System.out.println(t1.correlationCoefficient(t2));
	}
}
