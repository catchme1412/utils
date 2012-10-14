package com.raj.util;

public class Tuple {

	private double[] elements;

	private boolean isAvgCalculated;

	private double average;

	private boolean isSumCalculated;

	private double sum;

	private boolean isSquareCalculated;

	private double square;

	public Tuple(double... val) {
		this.elements = val;
	}

	/**
	 * Return the dot product of two vectors (tuples)
	 */
	public double dot(Tuple v) {
		double[] other = v.getElements();
		double result = 0.0;
		int i = 0;
		for (double val : elements) {
			result += val * other[i];
			i++;
		}
		return result;
	}

	public Tuple multiply(double scalarValue) {
		int i = 0;
		for (double v : elements) {
			elements[i] = v * scalarValue;
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
			for (double val : elements) {
				sum += val;
			}
			isSumCalculated = true;
		}
		return sum;
	}

	public double getAverage() {
		if (!isAvgCalculated) {
			average = sum() / elements.length;
			isAvgCalculated = true;
		}
		return average;
	}

	public double square() {
		if (!isSquareCalculated) {
			for (double val : elements) {
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

	public double[] getElements() {
		return elements;
	}

	public void setArray(double[] e) {
		this.elements = e;
	}

	public double elementSum() {
		double sum = 0;
		for (double v : elements) {
			sum += v;
		}
		return sum;
	}
	
	public int length() {
		return elements.length;
	}

	public double correlationCoefficient(Tuple y) {
		double sumX = this.sum();
		double sumY = y.sum();
		double sumXY = this.dot(y);
		double xSquare = this.square();
		double ySquare = y.square();
		int n = elements.length;
		return (n * sumXY - sumX * sumY)
				/ (Math.sqrt(n * xSquare - sumX * sumX) * Math.sqrt(n * ySquare - sumY * sumY));
	}

	public static void main(String[] args) {
		Tuple t1 = new Tuple(1, 1);
		Tuple t2 = new Tuple(1, 1);
		System.out.println(t1.correlationCoefficient(t2));
	}
}
