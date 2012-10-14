import org.junit.Test;

import com.raj.util.Tuple;
import com.raj.util.similarity.FittingOfStraightLine;

public class FittingOfStraightLineTest {

	@Test
	public void test() {
		Tuple x = new Tuple(0, 0);
		Tuple y = new Tuple(1, 1);
		double s = FittingOfStraightLine.getSlope(x, y);
		System.out.println(s);

	}

}
