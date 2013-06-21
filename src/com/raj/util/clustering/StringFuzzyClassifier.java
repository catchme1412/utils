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
		StringBuffer text=new StringBuffer();
//	      for (String key : keyMap.keySet()) {
//	          text.append(key).append(":");
//	          List data= keyMap.get(key);
//	          text.append(data.size());
//	          //text.append(data.toString());
//	          text.append("*********\n");
//	      }
//	    return text.toString();
	    
	    return keyMap.toString();
	}
	
	public static void main(String[] args) {
		StringFuzzyClassifier f = new StringFuzzyClassifier(.70);
		f.addToCluster("AAAAA");
		f.addToCluster("AAABAA");
		f.addToCluster("AAAAAAV");
		f.addToCluster("BBBBBBBBBB");
		f.addToCluster("AABBBBBBBB");
		f.addToCluster("AABBABABBB");

		
		System.out.println(f);
	}
}