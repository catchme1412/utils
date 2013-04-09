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
        // System.out.print("\nTRAINING=>"+ category + ":");
        for (Attribute attribute : attributes) {
            attributeValues.add(attribute);
            if (attribute.getFeature() == null) {
                attribute.setFeature(features[i]);
            } else if (!attribute.getFeature().equals(features[i])) {
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
            // System.out.print(attribute + ", ");
            // System.out.print(features[i]);
            i++;
            // System.out.print("\t");
        }
        counter(cateogryCount, category);
        // System.out.println("\nLEARNED:");
        // for (Category c: attributePerCategoryCount.keySet()) {
        // System.out.println(c + ":" + attributePerCategoryCount.get(c));
        // }
        // System.out.println("Category");
        // for (Attribute v : attributeValues) {
        // System.out.print(v);
        // System.out.print("\t\t");
        // }
        // System.out.println(category);
    }

    public Category classify(Attribute... attributes) {
        TreeMap<Double, Category> values = new TreeMap<Double, Category>();
        for (Category c : cateogryCount.keySet()) {
            double categoryProb = 1;
            for (Attribute attribute : attributes) {
                double x = getConditionalProb(attribute, c);
                categoryProb *= x;
            }
            System.out.println("conditional:" + categoryProb);
            categoryProb *= getPriorProb(c);
            System.out.println("proirProb:" + categoryProb);
            values.put(categoryProb, c);
            System.out.println("CLASSIFICATION=>Cateogry:" + c + " : " + categoryProb);
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
        // Day Outlook Temperature Humidity Wind Play Tennis?

        // 3 Overcast Hot High Weak Yes
        navieBayes.train(Yes, Overcast, Hot, High, Weak);// 3
        // 4 Rain Mild High Weak Yes
        navieBayes.train(Yes, Rain, Mild, High, Weak);// 4
        // 9 Sunny Cool Normal Weak Yes
        navieBayes.train(Yes, Sunny, Cool, Normal, Weak);// 9
        // 10 Rain Mild Normal Weak Yes
        navieBayes.train(Yes, Rain, Mild, Normal, Weak);// 10
        // 13 Overcast Hot Normal Weak Yes
        navieBayes.train(Yes, Overcast, Hot, Normal, Weak);// 13

        // 1 Sunny Hot High Weak No
        navieBayes.train(No, Sunny, Hot, High, Weak);// 1
        // 2 Sunny Hot High Strong No
        navieBayes.train(No, Sunny, Hot, High, Strong);// 2
        // 5 Rain Cool Normal Weak Yes
        navieBayes.train(Yes, Rain, Cool, Normal, Weak);// 5
        // 6 Rain Cool Normal Strong No
        navieBayes.train(No, Rain, Cool, Normal, Strong);// 6Weak
        // 7 Overcast Cool Normal Strong Yes
        navieBayes.train(Yes, Overcast, Cool, Normal, Strong);// 7
        // 8 Sunny Mild High Weak No
        navieBayes.train(No, Sunny, Mild, High, Weak);// 8
        // 11 Sunny Mild Normal Strong Yes
        navieBayes.train(Yes, Sunny, Mild, Normal, Strong);// 11
        // 12 Overcast Mild High Strong Yes
        navieBayes.train(Yes, Overcast, Mild, High, Strong);// 12
        // 14 Rain Mild High Strong No
        navieBayes.train(No, Rain, Mild, High, Strong);// 14

        // navieBayes.getPriorProb(No);
        // navieBayes.getPriorProb(Yes);

        // navieBayes.getConditionalProb(Sunny, Yes);
        // navieBayes.getConditionalProb(Overcast, Yes);
        // navieBayes.getConditionalProb(High, Yes);

        // System.out.println(navieBayes.classify(Sunny, Cool, High, Strong));
        // X = (Outlook=Sunny, Temperature=Cool, Humidity=High, Wind=Strong)
        System.out.println(navieBayes.classify(Sunny, Cool, High, Strong));
        
        /////////////////////////
        Category low = new Category("low");
        Category high = new Category("high");
        Category medium = new Category("medium");
        
//        Feature noOfOperators = new Feature("noOfOperators", Qualifier.greateThan(2));
        Feature noOfUniqueOperators = new Feature("noOfUniqueOperators");
        Feature literalCount = new Feature("literalCount");
//        NavieBayes navieBayes2 = new NavieBayes(noOfOperators, noOfUniqueOperators, literalCount);
        Attribute operatorCount = new Attribute(2);
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
        System.out.println("P(" + cat+") = "+ cateogryCount.get(cat) + "/" + totalCategoryCount +" = " + cateogryCount.get(cat) / totalCategoryCount);
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
        System.out.println("P(" + att + "/" + category + ") = \t" + prob + "/" + cateogryCount.get(category) + "=" + prob
                / cateogryCount.get(category));
        return prob / cateogryCount.get(category);
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
    }
}
