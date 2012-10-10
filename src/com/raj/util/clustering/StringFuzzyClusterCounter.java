package com.raj.util.clustering;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.raj.util.similarity.StringRank;

public class StringFuzzyClusterCounter {

	private HashMap<String, Long> keyMap;
	private double threshold;

	public StringFuzzyClusterCounter() {
		keyMap = new HashMap<String, Long>();
		threshold = 0.8;
	}

	public void addToCluster(String value) {
		boolean isKeyExists = false;
		for (String key : keyMap.keySet()) {
			if (StringRank.compareStrings(key, value) > threshold) {
				Long count = keyMap.get(key);
				count++;
				keyMap.put(key, count);
				isKeyExists = true;
			}
		}
		if (!isKeyExists) {
			keyMap.put(value, 1L);
			
		}
	}

	public Long getClusterSize(String key) {
		return keyMap.get(key);
	}
	
	public Set<Map.Entry<String,Long>> entrySet() {
		return keyMap.entrySet();
	}
	
	@Override
	public String toString() {
		return keyMap.toString();
	}
	
	public static void main(String[] args) {
		StringFuzzyClusterCounter f = new StringFuzzyClusterCounter();
		f.addToCluster("AAAAA");
		f.addToCluster("AAAAAA");
		f.addToCluster("BBBBBBBBBB");
		System.out.println(f);
	}
}