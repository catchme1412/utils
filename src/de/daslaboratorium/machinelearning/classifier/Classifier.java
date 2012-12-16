package de.daslaboratorium.machinelearning.classifier;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import de.Features;

/**
 * Abstract base extended by any concrete classifier. It implements the basic
 * functionality for storing categories or features and can be used to calculate
 * basic probabilities – both category and feature probabilities. The classify
 * function has to be implemented by the concrete classifier class.
 * 
 * @author Philipp Nolte
 * 
 * @param <T>
 *            A feature class
 * @param <K>
 *            A category class
 */
public abstract class Classifier<T, K> implements IFeatureProbability<T, K> {

	/**
	 * The initial memory capacity or how many classifications are memorized.
	 */
	private int memoryCapacity = 1000;

	/**
	 * A Map mapping features to their number of occurrences in each known
	 * category.
	 */
	private Map<K, Map<T, Integer>> featureCountPerCategory;

	/**
	 * A Map mapping features to their number of occurrences.
	 */
	private Map<T, Integer> totalFeatureCount;

	/**
	 * A Map mapping categories to their number of occurrences.
	 */
	private Map<K, Integer> totalCategoryCount;

	/**
	 * The classifier's memory. It will forget old classifications as soon as
	 * they become too old.
	 */
	private Queue<Classification<T, K>> memoryQueue;

	/**
	 * Constructs a new classifier without any trained knowledge.
	 */
	public Classifier() {
		this.reset();
	}

	/**
	 * Resets the <i>learned</i> feature and category counts.
	 */
	public void reset() {
		this.featureCountPerCategory = new HashMap<K, Map<T, Integer>>();
		this.totalFeatureCount = new HashMap<T, Integer>();
		this.totalCategoryCount = new HashMap<K, Integer>();
		this.memoryQueue = new LinkedList<Classification<T, K>>();
	}

	/**
	 * Returns a <code>Set</code> of features the classifier knows about.
	 * 
	 * @return The <code>Set</code> of features the classifier knows about.
	 */
	public Set<T> getFeatures() {
		return ((HashMap<T, Integer>) this.totalFeatureCount).keySet();
	}

	/**
	 * Returns a <code>Set</code> of categories the classifier knows about.
	 * 
	 * @return The <code>Set</code> of categories the classifier knows about.
	 */
	public Set<K> getCategories() {
		return ((HashMap<K, Integer>) this.totalCategoryCount).keySet();
	}

	/**
	 * Retrieves the total number of categories the classifier knows about.
	 * 
	 * @return The total category count.
	 */
	public int getCategoriesTotal() {
		int toReturn = 0;
		for (Integer e : this.totalCategoryCount.values()) {
			toReturn += e;
		}
		return toReturn;
	}

	/**
	 * Retrieves the memory's capacity.
	 * 
	 * @return The memory's capacity.
	 */
	public int getMemoryCapacity() {
		return memoryCapacity;
	}

	/**
	 * Sets the memory's capacity. If the new value is less than the old value,
	 * the memory will be truncated accordingly.
	 * 
	 * @param memoryCapacity
	 *            The new memory capacity.
	 */
	public void setMemoryCapacity(int memoryCapacity) {
		for (int i = this.memoryCapacity; i > memoryCapacity; i--) {
			this.memoryQueue.poll();
		}
		this.memoryCapacity = memoryCapacity;
	}

	/**
	 * Increments the count of a given feature in the given category. This is
	 * equal to telling the classifier, that this feature has occurred in this
	 * category.
	 * 
	 * @param feature
	 *            The feature, which count to increase.
	 * @param category
	 *            The category the feature occurred in.
	 */
	public void incrementFeature(T feature, K category) {
		Map<T, Integer> features = this.featureCountPerCategory.get(category);
		if (features == null) {
			this.featureCountPerCategory.put(category, new HashMap<T, Integer>());
			features = this.featureCountPerCategory.get(category);
		}
		Integer count = features.get(feature);
		if (count == null) {
			features.put(feature, 0);
			count = features.get(feature);
		}
		features.put(feature, ++count);

		Integer totalCount = this.totalFeatureCount.get(feature);
		if (totalCount == null) {
			this.totalFeatureCount.put(feature, 0);
			totalCount = this.totalFeatureCount.get(feature);
		}
		this.totalFeatureCount.put(feature, ++totalCount);
	}

	/**
	 * Increments the count of a given category. This is equal to telling the
	 * classifier, that this category has occurred once more.
	 * 
	 * @param category
	 *            The category, which count to increase.
	 */
	public void incrementCategory(K category) {
		Integer count = this.totalCategoryCount.get(category);
		if (count == null) {
			this.totalCategoryCount.put(category, 0);
			count = this.totalCategoryCount.get(category);
		}
		this.totalCategoryCount.put(category, ++count);
	}

	/**
	 * Decrements the count of a given feature in the given category. This is
	 * equal to telling the classifier that this feature was classified once in
	 * the category.
	 * 
	 * @param feature
	 *            The feature to decrement the count for.
	 * @param category
	 *            The category.
	 */
	public void decrementFeature(T feature, K category) {
		Map<T, Integer> features = this.featureCountPerCategory.get(category);
		if (features == null) {
			return;
		}
		Integer count = features.get(feature);
		if (count == null) {
			return;
		}
		if (count.intValue() == 1) {
			features.remove(feature);
			if (features.size() == 0) {
				this.featureCountPerCategory.remove(category);
			}
		} else {
			features.put(feature, --count);
		}

		Integer totalCount = this.totalFeatureCount.get(feature);
		if (totalCount == null) {
			return;
		}
		if (totalCount.intValue() == 1) {
			this.totalFeatureCount.remove(feature);
		} else {
			this.totalFeatureCount.put(feature, --totalCount);
		}
	}

	/**
	 * Decrements the count of a given category. This is equal to telling the
	 * classifier, that this category has occurred once less.
	 * 
	 * @param category
	 *            The category, which count to increase.
	 */
	public void decrementCategory(K category) {
		Integer count = this.totalCategoryCount.get(category);
		if (count == null) {
			return;
		}
		if (count.intValue() == 1) {
			this.totalCategoryCount.remove(category);
		} else {
			this.totalCategoryCount.put(category, --count);
		}
	}

	/**
	 * Retrieves the number of occurrences of the given feature in the given
	 * category.
	 * 
	 * @param feature
	 *            The feature, which count to retrieve.
	 * @param category
	 *            The category, which the feature occurred in.
	 * @return The number of occurrences of the feature in the category.
	 */
	public int featureCount(T feature, K category) {
		Map<T, Integer> features = this.featureCountPerCategory.get(category);
		if (features == null)
			return 0;
		Integer count = features.get(feature);
		return (count == null) ? 0 : count.intValue();
	}

	/**
	 * Retrieves the number of occurrences of the given category.
	 * 
	 * @param category
	 *            The category, which count should be retrieved.
	 * @return The number of occurrences.
	 */
	public int categoryCount(K category) {
		Integer count = this.totalCategoryCount.get(category);
		return (count == null) ? 0 : count.intValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float featureProbability(T feature, K category) {
		if (this.categoryCount(category) == 0)
			return 0;
		return (float) this.featureCount(feature, category) / (float) this.categoryCount(category);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with
	 * overall weight of <code>1.0</code> and an assumed probability of
	 * <code>0.5</code>. The probability defaults to the overall feature
	 * probability.
	 * 
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureProbability(Object,
	 *      Object)
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureWeighedAverage(Object,
	 *      Object, IFeatureProbability, float, float)
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category) {
		return this.featureWeighedAverage(feature, category, null, 1.0f, 0.5f);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with
	 * overall weight of <code>1.0</code>, an assumed probability of
	 * <code>0.5</code> and the given object to use for probability calculation.
	 * 
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureWeighedAverage(Object,
	 *      Object, IFeatureProbability, float, float)
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @param calculator
	 *            The calculating object.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category, IFeatureProbability<T, K> calculator) {
		return this.featureWeighedAverage(feature, category, calculator, 1.0f, 0.5f);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with the
	 * given weight and an assumed probability of <code>0.5</code> and the given
	 * object to use for probability calculation.
	 * 
	 * @see de.daslaboratorium.machinelearning.classifier.Classifier#featureWeighedAverage(Object,
	 *      Object, IFeatureProbability, float, float)
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @param calculator
	 *            The calculating object.
	 * @param weight
	 *            The feature weight.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category, IFeatureProbability<T, K> calculator, float weight) {
		return this.featureWeighedAverage(feature, category, calculator, weight, 0.5f);
	}

	/**
	 * Retrieves the weighed average <code>P(feature|category)</code> with the
	 * given weight, the given assumed probability and the given object to use
	 * for probability calculation.
	 * 
	 * @param feature
	 *            The feature, which probability to calculate.
	 * @param category
	 *            The category.
	 * @param calculator
	 *            The calculating object.
	 * @param weight
	 *            The feature weight.
	 * @param assumedProbability
	 *            The assumed probability.
	 * @return The weighed average probability.
	 */
	public float featureWeighedAverage(T feature, K category, IFeatureProbability<T, K> calculator, float weight,
			float assumedProbability) {

		/*
		 * use the given calculating object or the default method to calculate
		 * the probability that the given feature occurred in the given
		 * category.
		 */
		final float basicProbability = (calculator == null) ? this.featureProbability(feature, category) : calculator
				.featureProbability(feature, category);

		Integer totals = this.totalFeatureCount.get(feature);
		if (totals == null)
			totals = 0;
		return (weight * assumedProbability + totals * basicProbability) / (weight + totals);
	}

	/**
	 * Train the classifier by telling it that the given features resulted in
	 * the given category.
	 * 
	 * @param category
	 *            The category the features belong to.
	 * @param features
	 *            The features that resulted in the given category.
	 */
	public void learn(K category, Collection<T> features) {
		this.learn(new Classification<T, K>(features, category));
	}

	/**
	 * Train the classifier by telling it that the given features resulted in
	 * the given category.
	 * 
	 * @param classification
	 *            The classification to learn.
	 */
	public void learn(Classification<T, K> classification) {

		for (T feature : classification.getFeatureset()) {
			this.incrementFeature(feature, classification.getCategory());
		}
		this.incrementCategory(classification.getCategory());

		this.memoryQueue.offer(classification);
		if (this.memoryQueue.size() > this.memoryCapacity) {
			Classification<T, K> toForget = this.memoryQueue.remove();

			for (T feature : toForget.getFeatureset())
				this.decrementFeature(feature, toForget.getCategory());
			this.decrementCategory(toForget.getCategory());
		}
	}

	/**
	 * The classify method. It will retrieve the most likely category for the
	 * features given and depends on the concrete classifier implementation.
	 * 
	 * @param features
	 *            The features to classify.
	 * @return The category most likely.
	 */
	public abstract Classification<T, K> classify(Collection<T> features);

	public void learn(String category, Map<String, Serializable> attrib) {


		for (String feature : attrib.keySet()) {
			this.incrementFeature(feature, attrib.get(feature));
		}
		this.incrementCategory(classification.getCategory());

		this.memoryQueue.offer(classification);
		if (this.memoryQueue.size() > this.memoryCapacity) {
			Classification<T, K> toForget = this.memoryQueue.remove();

			for (T feature : toForget.getFeatureset())
				this.decrementFeature(feature, toForget.getCategory());
			this.decrementCategory(toForget.getCategory());
		}
	
		
	}

	private void incrementFeature(String feature, Serializable serializable) {
		// TODO Auto-generated method stub
		
	}

}
