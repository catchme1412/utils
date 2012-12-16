package de.daslaboratorium.machinelearning.classifier;

import java.util.Arrays;


public class RunnableExample {

	public static void main(String[] args) {

		/*
		 * Create a new classifier instance. The context features are Strings
		 * and the context will be classified with a String according to the
		 * featureset of the context.
		 */
		final Classifier<String, String> bayes = new BayesClassifier<String, String>();

		/*
		 * The classifier can learn from classifications that are handed over to
		 * the learn methods. Imagin a tokenized text as follows. The tokens are
		 * the text's features. The category of the text will either be positive
		 * or negative.
		 */
		final String[] negativeText2 = "full video".split("\\s");
		bayes.learn("other", Arrays.asList(negativeText2));
		final String[] positiveText = "full movie film part".split("\\s");
		bayes.learn("movie", Arrays.asList(positiveText));

		final String[] negativeText = "song clip ".split("\\s");
		bayes.learn("song", Arrays.asList(negativeText));

		final String[] negativeText3 = "movie trailer clip".split("\\s");
		bayes.learn("trailer", Arrays.asList(negativeText3));
		/*
		 * Now that the classifier has "learned" two classifications, it will be
		 * able to classify similar sentences. The classify method returns a
		 * Classification Object, that contains the given featureset,
		 * classification probability and resulting category.
		 */
		final String[] unknownText1 = "Movie part 1".split("\\s");
		final String[] unknownText2 = "full video".split("\\s");

		System.out.println( // will output "positive"
				bayes.classify(Arrays.asList(unknownText1)).getCategory());
		System.out.println( // will output "negative"
				bayes.classify(Arrays.asList(unknownText2)).getCategory());

		/*
		 * The BayesClassifier extends the abstract Classifier and provides
		 * detailed classification results that can be retrieved by calling the
		 * classifyDetailed Method.
		 * 
		 * The classification with the highest probability is the resulting
		 * classification. The returned List will look like this. [
		 * Classification [ category=negative, probability=0.0078125,
		 * featureset=[today, is, a, sunny, day] ], Classification [
		 * category=positive, probability=0.0234375, featureset=[today, is, a,
		 * sunny, day] ] ]
		 */
		((BayesClassifier<String, String>) bayes).classifyDetailed(Arrays.asList(unknownText1));

		/*
		 * Please note, that this particular classifier implementation will
		 * "forget" learned classifications after a few learning sessions. The
		 * number of learning sessions it will record can be set as follows:
		 */
		bayes.setMemoryCapacity(500); // remember the last 500 learned
										// classifications
	}

}
