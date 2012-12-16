import java.io.Serializable;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.Features;


public class NaiveBayesClassiferTest {
	
	private NaiveBayesClassifer<String, Serializable> classifier;
	@Before
	public void setup() {
		classifier = new NaiveBayesClassifer<String, Serializable>();
	}
	@Test
	public void testClassify() throws Exception {
		Features<String, Serializable> movieAttrib = Features.create("title", Qualifier.contains("movie"), "duration", Qualifier.greateThan(240));
		String category = null;
		classifier.learn(category, movieAttrib);
//		classifier.learn("movie", movieAttrib);
		Features songAttrib = Features.create("title", Qualifier.contains("song"), "duration", Qualifier.greateThan(60));
//		classifier.train(new Category("song"), songAttrib);
		Features unknown = Features.create("title", Qualifier.contains("movie"), "duration", 270);
		Category cls = classifier.classify(unknown);
	}

}
