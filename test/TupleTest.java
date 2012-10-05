import junit.framework.Assert;

import org.junit.Test;

import com.raj.util.Tuple;


public class TupleTest {

	@Test
	public void testDot() {
		
		Tuple t1 = new Tuple (1,2,3);
		Tuple t2 = new Tuple (1, 2, 3);
		Assert.assertEquals(14.0, t1.dot(t2));
	}

}
