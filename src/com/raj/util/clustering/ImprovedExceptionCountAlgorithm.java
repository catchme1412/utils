package com.raj.util.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImprovedExceptionCountAlgorithm {

	static Map<String, Integer> exceptionCount = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		String sCurrentLine;
		BufferedReader br = new BufferedReader(new FileReader("/home/rkv/dev/orbitz/dashboard/dump/e.txt"));
		StringFuzzyClusterCounter f = new StringFuzzyClusterCounter();
		while ((sCurrentLine = br.readLine()) != null) {
			f.addToCluster(sCurrentLine);
		}
		System.out.println(f);
		br.close();
	}

}
