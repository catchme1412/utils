package de.daslaboratorium.machinelearning.classifier;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A concrete implementation of the abstract Classifier class. The Bayes
 * classifier implements a naive Bayes approach to classifying a given set of
 * features: classify(feat1,...,featN) = argmax(P(cat)*PROD(P(featI|cat)
 * 
 * @author Philipp Nolte
 * 
 * @see http://en.wikipedia.org/wiki/Naive_Bayes_classifier
 * 
 * @param <Features>
 *            The feature class.
 * @param <Category>
 *            The category class.
 */
public class BayesClassifier<Features, Category> extends Classifier<Features, Category> {

	/**
	 * Calculates the product of all feature probabilities: PROD(P(featI|cat)
	 * 
	 * @param features
	 *            The set of features to use.
	 * @param category
	 *            The category to test for.
	 * @return The product of all feature probabilities.
	 */
	private float featuresProbabilityProduct(Collection<Features> features, Category category) {
		float product = 1.0f;
		for (Features feature : features)
			product *= this.featureWeighedAverage(feature, category);
		return product;
	}

	/**
	 * Calculates the probability that the features can be classified as the
	 * category given.
	 * 
	 * @param features
	 *            The set of features to use.
	 * @param category
	 *            The category to test for.
	 * @return The probability that the features can be classified as the
	 *         category.
	 */
	private float categoryProbability(Collection<Features> features, Category category) {
		return ((float) this.categoryCount(category) / (float) this.getCategoriesTotal())
				* featuresProbabilityProduct(features, category);
	}

	/**
	 * Retrieves a sorted <code>Set</code> of probabilities that the given set
	 * of features is classified as the available categories.
	 * 
	 * @param features
	 *            The set of features to use.
	 * @return A sorted <code>Set</code> of category-probability-entries.
	 */
	private SortedSet<Classification<Features, Category>> categoryProbabilities(Collection<Features> features) {

		/*
		 * Sort the set according to the possibilities. Because we have to sort
		 * by the mapped value and not by the mapped key, we can not use a
		 * sorted tree (TreeMap) and we have to use a set-entry approach to
		 * achieve the desired functionality. A custom comparator is therefore
		 * needed.
		 */
		SortedSet<Classification<Features, Category>> probabilities = new TreeSet<Classification<Features, Category>>(
				new Comparator<Classification<Features, Category>>() {

					@Override
					public int compare(Classification<Features, Category> o1, Classification<Features, Category> o2) {
						int toReturn = Float.compare(o1.getProbability(), o2.getProbability());
						if ((toReturn == 0) && !o1.getCategory().equals(o2.getCategory()))
							toReturn = -1;
						return toReturn;
					}
				});

		for (Category category : this.getCategories()) {
			float categoryProbability = this.categoryProbability(features, category);
			Classification<Features, Category> e = new Classification<Features, Category>(features, category, categoryProbability);
			probabilities.add(e);
		}
		return probabilities;
	}

	/**
	 * Classifies the given set of features.
	 * 
	 * @return The category the set of features is classified as.
	 */
	@Override
	public Classification<Features, Category> classify(Collection<Features> features) {
		SortedSet<Classification<Features, Category>> probabilites = this.categoryProbabilities(features);

		if (probabilites.size() > 0) {
			return probabilites.last();
		}
		return null;
	}

	/**
	 * Classifies the given set of features. and return the full details of the
	 * classification.
	 * 
	 * @return The set of categories the set of features is classified as.
	 */
	public Collection<Classification<Features, Category>> classifyDetailed(Collection<Features> features) {
		return this.categoryProbabilities(features);
	}

}
