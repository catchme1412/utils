import java.io.Serializable;
import java.util.Map;

import de.Features;


public class NaiveBayesClassifer<K,T> {

	/**
	 * A Map mapping features to their number of occurrences in each known
	 * category.
	 */
	private Map<K, Map<T, Integer>> featureCountPerCategory;

	/**
	 * A Map of features to their number of occurrences.
	 */
	private Map<T, Integer> totalFeatureCount;

	/**
	 * A Map of categories to their number of occurrences.
	 */
	private Map<K, Integer> totalCategoryCount;
	

	public NaiveBayesClassifer() {
	}

	public void learn(String category, Features<String, Serializable> movieAttrib) {
		for (String feature : movieAttrib.keySet()) {
			this.incrementFeature(feature, movieAttrib.get(feature));
		}
//		this.incrementCategory(classification.getCategory());
//
//		this.memoryQueue.offer(classification);
//		if (this.memoryQueue.size() > this.memoryCapacity) {
//			Classification<T, K> toForget = this.memoryQueue.remove();
//
//			for (T feature : toForget.getFeatureset())
//				this.decrementFeature(feature, toForget.getCategory());
//			this.decrementCategory(toForget.getCategory());
//		}
	}

	private void incrementFeature(String feature, Serializable serializable) {
		// TODO Auto-generated method stub
		
	}

	public Category classify(Features unknown) {
		return null;
	}


}
