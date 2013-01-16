package com.raj.classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//@see http://computersciencesource.wordpress.com/2010/01/28/year-2-machine-learning-naive-bayes-classifier/

public class NavieBayes {

	// Category 1* Features 1* Atribute 1-1 Prob

	private Feature[] features;

	private Map<Category, Long> cateogryCount;
	double totalCategoryCount;
	private Map<Category, Map<Attribute, Long>> attributePerCategoryCount;

	private long featureCount;

	public NavieBayes(Feature... features) {
		this.features = features;
		cateogryCount = new HashMap<Category, Long>();
		totalCategoryCount = 0;
		attributePerCategoryCount = new HashMap<Category, Map<Attribute, Long>>();
	}

	public NavieBayes(long featureCount) {
		cateogryCount = new HashMap<Category, Long>();
		totalCategoryCount = 0;
		attributePerCategoryCount = new HashMap<Category, Map<Attribute, Long>>();
	}

	public void train(Category category, Map<Feature, Attribute> featureValueMap) {
		if (featureValueMap.size() != getFeatures().length) {
			throw new RuntimeException("Feature count is not " + featureCount);
		}
		counter(cateogryCount, category);
		totalCategoryCount++;
		List<Attribute> attributeValues = new ArrayList<Attribute>();
		for (Feature f : getFeatures()) {
			attributeValues.add(featureValueMap.get(f));
			if (attributePerCategoryCount.get(category) == null) {
				attributePerCategoryCount.put(category, new HashMap<Attribute, Long>());
			} else {
				counter(attributePerCategoryCount.get(category), featureValueMap.get(f));
			}
			System.out.print(f);
			System.out.print("\t");
		}

		System.out.println("Category");
		for (Attribute v : attributeValues) {
			System.out.print(v);
			System.out.print("\t\t");
		}
		System.out.println(category);

	}

	public void train(Category category, Attribute... attributes) {
		if (attributes.length != features.length) {
			throw new RuntimeException("Feature count is not " + featureCount);
		}
		totalCategoryCount++;
		List<Attribute> attributeValues = new ArrayList<Attribute>();
		int i = 0;
		for (Attribute attribute : attributes) {
			attributeValues.add(attribute);
			if (attribute.getFeature() == null) {
			    attribute.setFeature(features[i]);
			} else if (!attribute.getFeature().equals(features[i])){
			    throw new RuntimeException("Changing the feature is not permitted");
			}
			Map<Attribute, Long> attributeMap = attributePerCategoryCount.get(category);
			if (attributeMap == null) {
				HashMap<Attribute, Long> value = new HashMap<Attribute, Long>();
				counter(value, attribute);
				attributePerCategoryCount.put(category, value);
			} else {
				counter(attributeMap, attribute);
			}
//			System.out.print(features[i]);
			i++;
//			System.out.print("\t");
		}
		counter(cateogryCount, category);
		System.out.println("\n\n");
		for (Category c: attributePerCategoryCount.keySet()) {
		    
		    System.out.println(c + ":" + attributePerCategoryCount.get(c));
		}
//		System.out.println("Category");
//		for (Attribute v : attributeValues) {
//			System.out.print(v);
//			System.out.print("\t\t");
//		}
//		System.out.println(category);
	}
	
	public Category classify(Attribute... attributes) {
		TreeMap<Double, Category> values = new TreeMap<Double, Category>();
		double categoryProb = 1;
		for (Category c: cateogryCount.keySet()) {
			for (Attribute attribute : attributes) {
				double x = getConditionalProb(attribute, c);
				categoryProb *= x;
			}
			categoryProb *= getPriorProb(c);
			values.put(categoryProb, c);
			System.out.println("Cateogry:" + c + " : " + categoryProb);
		}
		System.out.println(values);
		return values.lastEntry().getValue();
	}

	public static void main(String[] args) {
		Category Yes = new Category("Yes");
		Category No = new Category("No");

		Feature outlook = new Feature("Outlook");
		Feature temperature = new Feature("Temperature");
		Feature humidity = new Feature("Humidity");
		Feature wind = new Feature("Wind");

		NavieBayes navieBayes = new NavieBayes(outlook, temperature, humidity, wind);

		Attribute Sunny = new Attribute("Sunny");
		Attribute Overcast = new Attribute("Overcast");
		Attribute Rain = new Attribute("Rain");

		Attribute Hot = new Attribute("Hot");
		Attribute Mild = new Attribute("Mild");
		Attribute Cool = new Attribute("Cool");

		Attribute High = new Attribute("High");
		Attribute Normal = new Attribute("Normal");

		Attribute Weak = new Attribute("Weak");
		Attribute Strong = new Attribute("Strong");

		navieBayes.train(Yes, Rain, Cool, Normal, Weak);
		navieBayes.train(No, Rain, Cool, Normal, Strong);
		navieBayes.train(Yes, Overcast, Cool, Normal, Strong);
		navieBayes.train(No, Sunny, Mild, High, Weak);
		navieBayes.train(Yes, Sunny, Cool, Normal, Weak);
		
		navieBayes.train(No, Sunny, Hot, High, Weak);
		navieBayes.train(No, Sunny, Hot, High, Strong);
		navieBayes.train(Yes, Overcast, Hot, High, Weak);
		navieBayes.train(Yes, Rain, Mild, High, Weak);
		navieBayes.train(Yes, Rain, Mild, Normal, Weak);
		navieBayes.train(Yes, Sunny, Mild, Normal, Strong);
		navieBayes.train(Yes, Overcast, Mild, High, Strong);
		navieBayes.train(Yes, Overcast, Hot, Normal, Weak);
		navieBayes.train(No, Rain, Mild, High, Strong);

//		navieBayes.getPriorProb(No);
//		navieBayes.getPriorProb(Yes);

//		navieBayes.getConditionalProb(Sunny, Yes);
//		navieBayes.getConditionalProb(Overcast, Yes);
//		navieBayes.getConditionalProb(High, Yes);
		
//		System.out.println(navieBayes.classify(Sunny, Cool, High, Strong));
		System.out.println(navieBayes.classify(Sunny, Hot, High, Weak));
	}

	public static void main2(String[] args) {
		Category yes = new Category("Yes");
		Category no = new Category("No");

		Feature wind = new Feature("Wind");
		Feature humidity = new Feature("Humidity");
		Feature isCloudy = new Feature("Cloud");

		NavieBayes navieBayes = new NavieBayes(humidity, wind, isCloudy);

		Attribute strongWind = new Attribute("Strong");
		Attribute weakWind = new Attribute("Weak");
		Attribute highHumidity = new Attribute("High");
		Attribute lowHumidity = new Attribute("Low");

		Attribute isCloudyYes = new Attribute(true);
		Attribute noCloud = new Attribute(false);
		// featureValueMap.put(wind, strongWind);
		// featureValueMap.put(humidity, highHumidity);
		//
		//
		// navieBayes.train(c, featureValueMap);
		// featureValueMap = new HashMap<Feature, Attribute>();
		// featureValueMap.put(wind, weakWind);
		// featureValueMap.put(humidity, lowHumidity);
		// navieBayes.train(no, featureValueMap);
		navieBayes.train(no, lowHumidity, weakWind, noCloud);
		navieBayes.train(yes, highHumidity, strongWind, isCloudyYes);
		navieBayes.train(yes, highHumidity, weakWind, isCloudyYes);
		// featureValueMap = new HashMap<Feature, Attribute>();
		// featureValueMap.put(wind, weakWind);
		// featureValueMap.put(humidity, highHumidity);
		// navieBayes.train(no, featureValueMap);
		// System.out.println(navieBayes.cateogryCount);
		System.out.println(navieBayes.getPriorProb(no));
		System.out.println(navieBayes.getPriorProb(yes));
		System.out.println(navieBayes.getConditionalProb(highHumidity, no));
		System.out.println(navieBayes.getConditionalProb(highHumidity, yes));
		System.out.println(navieBayes.getConditionalProb(strongWind, yes));
		System.out.println(navieBayes.getConditionalProb(noCloud, yes));
		System.out.println(navieBayes.getConditionalProb(noCloud, no));
		System.out.println("Finished");
	}

	@SuppressWarnings(value = { "all" })
	public void counter(Map map, Serializable key) {
		Long v = (Long) map.get(key);
		map.put(key, v == null ? 1L : ++v);
	}

	public double getPriorProb(Category cat) {
		return cateogryCount.get(cat) / totalCategoryCount;
	}

	public double getConditionalProb(Attribute att, Category category) {
		double prob = 0;
		Map<Attribute, Long> v = attributePerCategoryCount.get(category);
		if (v == null) {
			prob = 0L;
		}
		Long val = 0L;
		if (v != null && v.containsKey(att)) {
		    val = v.get(att);
		} 
		
		val = val == null ? 0L : val;
		prob = val.doubleValue();
		System.out.println("\t" + prob + "/" + cateogryCount.get(category) + "=" + prob / cateogryCount.get(category));
		return prob / cateogryCount.get(category);
	}

	public Feature[] getFeatures() {
		return features;
	}

	public void setFeatures(Feature[] features) {
		this.features = features;
	}
}
