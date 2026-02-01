package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoordinateTest {
	
	@Test
	void gettersAndSettersTests() {
	   var cos = new Coordinate(0, 0);
	   assertEquals(0, cos.getCol());
	   assertEquals(0, cos.getRow());
	   cos.setCol(10);
	   cos.setRow(20);
	   assertEquals(10, cos.getCol());
	   assertEquals(20, cos.getRow());	   
	}
	
	@Test 
	void hashcodeTest() {
		var cos1 = new Coordinate(1, 2);
		var cos2 = new Coordinate(1, 2);
		assertEquals(cos1.hashCode(), cos2.hashCode());
	}
	
	@Test 
	void equalsTest() {
		Case tile = new Case(CaseType.FOREST);
		Coordinate cos = null;
		var cos1 = new Coordinate(1, 2);
		var cos2 = new Coordinate(1, 2);
		var cos3 = new Coordinate(1, 3);
		var cos4 = new Coordinate(2, 2);
		
		assertTrue(cos1.equals(cos1));
		assertTrue(cos2.equals(cos1));
		assertFalse(cos2.equals(cos3));
		assertFalse(cos1.equals(cos));
		assertFalse(cos1.equals(tile));
		
		assertFalse(cos1.equals(cos3));
		assertFalse(cos1.equals(cos4));
		
		
	}
	
	@Test
	void moveTest() {
		var cos = new Coordinate(0, 0);
		cos.move(2, 5);
		assertEquals(new Coordinate(2, 5), cos);
	}
	
	@Test
	void distanceCalculatorTest() {
		var co1 = new Coordinate(0, 0);
		var co2 = new Coordinate(1, 1);
		var co3 = new Coordinate(0, 1);
		var co4 = new Coordinate(0, 2);
		var co5 = new Coordinate(1, 2);
		var co6 = new Coordinate(2, 1);
		var co7 = new Coordinate(2, 2);
		
		// Calcul distance "voisine"
		assertEquals(Math.sqrt(2), co1.distanceCalc(co2));
		assertEquals(2, co1.distanceCalc(co4));
		
		// Calcul distance "diagonale"
		assertEquals(Math.sqrt(2), co1.distanceCalc(co2));
		assertEquals(Math.sqrt(5), co1.distanceCalc(co5));
		assertEquals(Math.sqrt(5), co1.distanceCalc(co6));
		assertEquals(Math.sqrt(8), co1.distanceCalc(co7));
	}
	
	@Test
	void globalCoToLocalCoTest() {
		var co = new Coordinate(3, 5);
		assertEquals(new Coordinate(1, 3), co.toLocalCo(new Coordinate(2, 2)));
		
		var co2 = new Coordinate(0, 0);
		assertEquals(new Coordinate(-2, -2), co2.toLocalCo(new Coordinate(2, 2)));
	}
	
	@Test
	void localCoToGlobalCoTest() {
		var co = new Coordinate(1, 3);
		assertEquals(new Coordinate(3, 5), co.toGlobalCo(new Coordinate(2, 2)));
	}
	
	@Test 
	void distanceCalcTest() {
		var co1 = new Coordinate(0, 0);
		var co2 = new Coordinate(1, 1);
		var co3 = new Coordinate(2, 2);
		
		assertEquals(Math.sqrt(2), co2.distanceCalc());
		
		assertEquals(Math.sqrt(2), co2.distanceCalc(co3));
	}

}
