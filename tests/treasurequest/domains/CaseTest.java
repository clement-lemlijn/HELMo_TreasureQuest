package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CaseTest {
	
	@Test
	void getTypeTests() {
		var tile = new Case(CaseType.WATER);
		assertEquals(CaseType.WATER, tile.getType());
		var tile2 = new Case(CaseType.SAND);
		assertEquals(CaseType.SAND, tile2.getType());
		var tile3 = new Case(CaseType.MEADOW);
		assertEquals(CaseType.MEADOW, tile3.getType());
		var tile4 = new Case(CaseType.FOREST);
		assertEquals(CaseType.FOREST, tile4.getType());
		var tile5 = new Case(CaseType.ROCK);
		assertEquals(CaseType.ROCK, tile5.getType());
	}
	
	@Test
	void isDiggedAndSetDiggedTests() {
		var tile = new Case(CaseType.WATER);
		assertEquals(false, tile.isDigged());
		tile.setDigged();
		assertEquals(true, tile.isDigged());
	}
	
	@Test 
	void setCoinsTests() {
		var tile = new Case(CaseType.WATER);
		assertEquals(0, tile.getCoins());
		var tile2 = new Case(CaseType.SAND);
		assertEquals(0, tile2.getCoins());
		tile.setCoins(15);
		assertEquals(0, tile.getCoins());
		tile2.setCoins(15);
		assertEquals(15, tile2.getCoins());
	}
	
	@Test
	void equalsCaseTest() {
		Case tile = null;
		var cos = new Coordinate(0, 0);
		var tile1 = new Case(CaseType.WATER);
		var tile2 = new Case(CaseType.WATER);
		var tile3 = new Case(CaseType.SAND);
		var tile4 = new Case(CaseType.SAND);
		var tile5 = new Case(CaseType.SAND);
		var tile6 = new Case(CaseType.SAND);
		
		assertTrue(tile1.equals(tile1));
		assertFalse(tile1.equals(tile3));
		assertFalse(tile1.equals(tile));
		assertFalse(tile1.equals(cos));
		assertFalse(cos.equals(tile1));
		
		tile4.setCoins(14);
		assertFalse(tile3.equals(tile4));
		
		tile5.setDigged();
		assertFalse(tile3.equals(tile5));
		
		assertFalse(tile1.equals(tile3));
		
		tile6.setDigged();
		assertTrue(tile5.equals(tile6));
	}
	
	@Test
	void setCoinsTest() {
		var tile1 = new Case(CaseType.WATER);
		var tile2 = new Case(CaseType.SAND);
		tile1.setCoins(15);
		tile2.setCoins(15);
		
		assertEquals(0, tile1.getCoins());
		assertEquals(15, tile2.getCoins());
	}
	
	@Test
	void getCaseCostsTest() {
		var tile1 = new Case(CaseType.WATER);
		var tile2 = new Case(CaseType.SAND);
		var tile3 = new Case(CaseType.MEADOW);
		var tile4 = new Case(CaseType.FOREST);
		var tile5 = new Case(CaseType.ROCK);
		
		assertEquals(0, tile1.getCaseCosts());
		assertEquals(1, tile2.getCaseCosts());
		assertEquals(2, tile3.getCaseCosts());
		assertEquals(3, tile4.getCaseCosts());
		assertEquals(5, tile5.getCaseCosts());
	}
	
	@Test
	void hashcodeTest() {
		var tile1 = new Case(CaseType.SAND);
		var tile2 = new Case(CaseType.SAND);
		assertEquals(tile1.hashCode(), tile2.hashCode());
	}
	
	

}
