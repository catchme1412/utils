package com.raj.util;

public class Duration {

	private long startTime;
	private long endTime;
	
	public Duration(long requestSendTime) {
		startTime = requestSendTime;
	}
	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public long value() {
		return endTime - startTime;
	}
	
}
