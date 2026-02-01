package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FakeRandomTest {

	@Test
	void test() {
		var fk = new FakeRandom();
		var fk2 = new FakeRandom(new Coordinate[] {new Coordinate(0, 0), new Coordinate(1, 1)});
		var fk3 = new FakeRandom(new int[] {1, 2, 3, 4, 5});
		var fk4 = new FakeRandom(new int[] {1, 2, 3, 4, 5}, new Coordinate[] {new Coordinate(0, 0), new Coordinate(1, 1)});
		fk.randomBetween(0, 0);
	}

}
