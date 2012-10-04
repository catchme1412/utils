package com.raj.util;

public class Frequency {

	private long occurance;
	
	private Duration timeDuration;

	public Frequency(Duration duration, long occurance) {
		this.occurance = occurance;
		this.timeDuration = duration;
	}

	/**
	 * @return the occurrence
	 */
	public long getOccurance() {
		return occurance;
	}

	/**
	 * @param occurance the occurrence to set
	 */
	public void setOccurance(long occurance) {
		this.occurance = occurance;
	}

	/**
	 * @return the timeDuration
	 */
	public Duration getTimeDuration() {
		return timeDuration;
	}

	/**
	 * @param timeDuration the timeDuration to set
	 */
	public void setTimeDuration(Duration timeDuration) {
		this.timeDuration = timeDuration;
	}
	
	public double value() {
		return timeDuration.value() > 0 ? occurance/timeDuration.value() : occurance;
	}

}
