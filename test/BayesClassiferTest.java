import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.daslaboratorium.machinelearning.classifier.BayesClassifier;
import de.daslaboratorium.machinelearning.classifier.Classification;
import de.daslaboratorium.machinelearning.classifier.Classifier;


public class BayesClassiferTest {

	private Classifier<Serializable, String> classifer;
	
	@Before
	public void setup() {
		classifer = new BayesClassifier<Serializable, String>();
		
		Collection<Serializable> features = new ArrayList<Serializable>();
		
		String category = "movie";
		Classification classification = new Classification(features, category);
		classifer.learn(classification);
	}
	
	@Test
	public void test() {
		classifer.classify(null);
	}
	
	@Test
	public void testClassify() throws Exception {
		Features<String, Serializable> movieAttrib = Features.create("title", Qualifier.contains("movie"), "duration", Qualifier.greateThan(240));
		String category = null;
		classifer.learn(category, movieAttrib);
//		classifier.learn("movie", movieAttrib);
		Features songAttrib = Features.create("title", Qualifier.contains("song"), "duration", Qualifier.greateThan(60));
//		classifier.train(new Category("song"), songAttrib);
		Features unknown = Features.create("title", Qualifier.contains("movie"), "duration", 270);
		Category cls = classifier.classify(unknown);
	}

}
