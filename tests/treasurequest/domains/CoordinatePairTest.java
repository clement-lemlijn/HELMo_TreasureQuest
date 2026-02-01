package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CoordinatePairTest {

	@Test
	void swapTest() {
		List<CoordinatePair> list = new ArrayList<>();
		list.add(new CoordinatePair(new Coordinate(1, 1)));
		list.add(new CoordinatePair(new Coordinate(2, 2)));
		
		CoordinatePair cpSwap;
		
		cpSwap = list.get(0);
		list.set(0, list.get(1));
		list.set(1, cpSwap);
		
		assertEquals(2, list.get(0).getCoordinate().getCol());
	}
	
	@Test
	void gettersAndSettersTest() {
		var cp = new CoordinatePair(new Coordinate(0, 0));
		
		cp.setCoordinate(new Coordinate(2, 3));
		
		assertEquals(2, cp.getCoordinate().getCol());
		assertEquals(3, cp.getCoordinate().getRow());
		
		var cp2 = new CoordinatePair(new Coordinate(0, 0), 0.0);
		assertEquals(0.0, cp2.getDistance());
	}
	
	@Test
	void hashcodeAndEqualsTest() {
		var cp = new CoordinatePair(new Coordinate(9, 3));
		var cp2 = new CoordinatePair(new Coordinate(9, 3));
		var cp3 = new CoordinatePair(new Coordinate(9, 4));
		var cp4 = new Case(CaseType.SAND);
		var cp5 = new CoordinatePair(new Coordinate(9, 3), 0.0);
		var cp6 = new CoordinatePair(new Coordinate(9, 3), 10.0);
		var cp7 = new CoordinatePair(new Coordinate(9, 3), 5.0);
		cp7.setDistance(10.0);
		
		assertEquals(cp.hashCode(), cp2.hashCode());
		
		assertTrue(cp.equals(cp));
		assertTrue(cp.equals(cp2));
		assertFalse(cp.equals(cp3));
		assertFalse(cp.equals(cp4));
		assertFalse(cp.equals(null));
		assertFalse(cp6.equals(cp5));
		assertTrue(cp6.equals(cp7));
		
		assertEquals(0, cp7.compareTo(cp7));
	}

}
