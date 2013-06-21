package com.raj.util.clustering;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.raj.util.similarity.StringRank;

public class StringPairFuzzyClusterCounter {

	private HashMap<StringPair, Long> keyMap;
	private double threshold;

	public StringPairFuzzyClusterCounter() {
		keyMap = new HashMap<StringPair, Long>();
		threshold = 0.8;
	}

	public void addToCluster(StringPair value) {
		boolean isKeyExists = false;
		for (StringPair key : keyMap.keySet()) {
			if (StringRank.compareStrings(key.getKey(), value.getKey()) > threshold) {
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
	
	public Set<Map.Entry<StringPair,Long>> entrySet() {
		return keyMap.entrySet();
	}
	
	@Override
	public String toString() {
		return keyMap.toString();
	}
	
	public static void main(String[] args) {
		StringPairFuzzyClusterCounter f = new StringPairFuzzyClusterCounter();
		f.addToCluster(new StringPair("Sreenivasan", "DDDDDDDD"));
		f.addToCluster(new StringPair("sreenivasan", "DDDDDDDD"));
		f.addToCluster(new StringPair("Mohanlal", "DDDDDDDD"));
		System.out.println(f);
	}
}