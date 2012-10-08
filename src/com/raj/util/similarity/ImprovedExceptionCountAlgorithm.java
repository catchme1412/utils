package com.raj.util.similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImprovedExceptionCountAlgorithm {

	static Map<String, Integer> exceptionCount = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		String sCurrentLine;
		String prevString = "";
		String prevClass = "";
		BufferedReader br = new BufferedReader(new FileReader("/home/rkv/dev/orbitz/dashboard/dump/e.txt"));
		int count = 1;
		while ((sCurrentLine = br.readLine()) != null) {
			String className = sCurrentLine.split("\\|")[8];
			double classRank = className.equalsIgnoreCase(prevClass) == true ? 1 : 0;
			prevClass = className;

			double stringRank = StringRank.compareStrings(sCurrentLine, prevString);
			double totalWeight = stringRank * .4 + classRank * .6;
			System.out.println(className + ": : " + totalWeight);
			
			if (totalWeight > 0.8) {
				count++;
			} else {
				exceptionCount.put(sCurrentLine, count++);
				count = 1;
			}
			prevString = sCurrentLine;
		}
		Iterator it = exceptionCount.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			System.out.println(key + ":::::::::" + exceptionCount.get(key).toString());
		}
		br.close();
	}

}
