package com.raj.util;

/**
 * There are several characteristics that can be used to describe the quality
 * and usefulness of a test. Accuracy is one characteristic.
 * 
 * Accuracy can be expressed through sensitivity and specificity, positive and
 * negative predictive values, or positive and negative diagnostic likelihood
 * ratios. Each measure of accuracy should be used in combination with its
 * complementary measure:
 * 
 * @author rkv
 * @see http://en.wikipedia.org/wiki/Accuracy
 * @see http://en.wikipedia.org/wiki/Type_I_and_type_II_errors
 * @see http://faculty.vassar.edu/lowry/clin1.html
 * @see http://faculty.vassar.edu/lowry/clin1.html#def
 * @see http://mathworld.wolfram.com/StatisticalTest.html
 * @see http://www.rapid-diagnostics.org/accuracy.htm
 */
public class AccuracyAndPrecision {

	// http://upload.wikimedia.org/wikipedia/en/math/8/5/f/85fb106488e3cb8c02e397c917222ad4.png
	private int truePositive;
	private int trueNegative;
	private int falsePositive;
	private int falseNegative;

	public AccuracyAndPrecision(int truePositive, int trueNegative, int falsePositive, int falseNegative) {
		this.truePositive = truePositive;
		this.trueNegative = trueNegative;
		this.falsePositive = falsePositive;
		this.falseNegative = falseNegative;
	}

	public double getAccuracy() {
		double sum = truePositive + trueNegative + falseNegative + falsePositive;
		return sum > 0 ? (truePositive + trueNegative) / sum : 0;
	}

	public double getPrecision() {
		double sum = truePositive + falsePositive;
		return sum > 0 ? truePositive / sum : 0;
	}

	/**
	 * @return the truePositive
	 */
	public int getTruePositive() {
		return truePositive;
	}

	/**
	 * @param truePositive
	 *            the truePositive to set
	 */
	public void setTruePositive(int truePositive) {
		this.truePositive = truePositive;
	}

	/**
	 * @return the trueNegative
	 */
	public int getTrueNegative() {
		return trueNegative;
	}

	/**
	 * @param trueNegative
	 *            the trueNegative to set
	 */
	public void setTrueNegative(int trueNegative) {
		this.trueNegative = trueNegative;
	}

	/**
	 * @return the falsePositive
	 */
	public int getFalsePositive() {
		return falsePositive;
	}

	/**
	 * @param falsePositive
	 *            the falsePositive to set
	 */
	public void setFalsePositive(int falsePositive) {
		this.falsePositive = falsePositive;
	}

	/**
	 * @return the falseNegative
	 */
	public int getFalseNegative() {
		return falseNegative;
	}

	/**
	 * @param falseNegative
	 *            the falseNegative to set
	 */
	public void setFalseNegative(int falseNegative) {
		this.falseNegative = falseNegative;
	}

	/**
	 * The sensitivity of a test is the probability that it will produce a true
	 * positive result when used on an infected population (as compared to a
	 * reference or "gold standard").
	 * 
	 * @return
	 */
	public double getSensitivity() {
		return ((double) truePositive) / (truePositive + falseNegative);
	}

	/**
	 * The specificity of a test is the probability that a test will produce a
	 * true negative result when used on a noninfected population (as determined
	 * by a reference or "gold standard").
	 */
	public double getSpecificity() {
		return ((double) trueNegative) / (trueNegative + falsePositive);
	}

}
