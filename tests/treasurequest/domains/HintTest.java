package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HintTest {

	@Test
	void test() {
		var hints = new Hint();
		var map = hints.getHintMap();
		
		System.out.println(hints.getHint(new Coordinate(-3, 4)));
	}

}
