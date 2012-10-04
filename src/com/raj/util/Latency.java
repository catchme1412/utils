package com.raj.util;

/**
 * @author rkv
 * @deprecated Need to correct the logic
 */
public class Latency {

	private Duration referenceDuration;

	private Duration extendedDuration;

	public double value() {
		return extendedDuration == null || referenceDuration == null ? 0 : extendedDuration.value()
				- referenceDuration.value();
	}
}
