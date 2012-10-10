package com.raj.util.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.raj.util.similarity.StringRank;

public class StringFuzzyClassifier {

	private HashMap<String, List<String>> keyMap;
	private double threshold;

	public StringFuzzyClassifier(double threshold) {
		keyMap = new HashMap<String, List<String>>();
		this.threshold = threshold;
	}

	public void addToCluster(String value) {
		boolean isKeyExists = false;
		for (String key : keyMap.keySet()) {
			if (StringRank.compareStrings(key, value) >= threshold) {
				List<String> v = keyMap.get(key);
				v.add(value);
				isKeyExists = true;
			}
		}
		if (!isKeyExists) {
			List<String> l = new ArrayList<String>();
			l.add(value);
			keyMap.put(value, l);
		}
	}

	public List<String> getClusterSize(String key) {
		return keyMap.get(key);
	}
	
	public Set<Entry<String, List<String>>> entrySet() {
		return keyMap.entrySet();
	}
	
	@Override
	public String toString() {
		return keyMap.toString();
	}
	
	public static void main(String[] args) {
		StringFuzzyClassifier f = new StringFuzzyClassifier(0.5);
		f.addToCluster("AAAAA");
		f.addToCluster("AAAAAA");
		f.addToCluster("AAAAAAV");
		f.addToCluster("BBBBBBBBBB");
		System.out.println(f);
	}
}