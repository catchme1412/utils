package com.raj.util.clustering;

import java.io.IOException;

import com.raj.util.Tuple;

public class TestingOfHypothesis {
	public static void main(String[] args) throws IOException {
		double x[] = {163,156, 140, 130};
		double avgX = new Tuple(x).getAverage();
		System.out.println(avgX);
		double populationMean = 137;
		double sd = 10;
		double z = (avgX - populationMean) / (sd/Math.sqrt(x.length));
		System.out.println(z);
		//Ho u <= 163
		//H1 u >= 163
		if (z > 1.64) {
			System.out.println("mean < " +populationMean +" Rejected");
		} else {
			System.out.println("mean < " +populationMean +" Accepted");
		} 
	}
}