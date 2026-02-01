package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoinsTest {

	@Test
	void getCoinsTest() {
		var coins = new Coins(0);
		assertEquals(0, coins.getCoins());
	}
	
	@Test
	void addCoinsTest() {
		var coins = new Coins(0);
		coins.addCoins();
		assertEquals(1, coins.getCoins());
		coins.addCoins(2);
		assertEquals(3, coins.getCoins());
	}

	@Test
	void withDrawCoinsTest() {
		var coins = new Coins(10);
		coins.withDrawCoins();
		assertEquals(9, coins.getCoins());
		coins.withDrawCoins(2);
		assertEquals(7, coins.getCoins());
	}
	
	@Test
	void hashcodeTest() {
		var bourse1 = new Coins(40);
		var bourse2 = new Coins(40);
		
		assertEquals(bourse1.hashCode(), bourse2.hashCode());
	}
	
	@Test
	void equalsTest() {
		Case tile = new Case(CaseType.WATER);
		Coins bourse = null;
		var bourse1 = new Coins(40);
		var bourse2 = new Coins(40);
		var bourse3 = new Coins(30);
		
		assertTrue(bourse1.equals(bourse1));
		assertTrue(bourse1.equals(bourse2));
		assertFalse(bourse1.equals(bourse3));
		assertFalse(bourse1.equals(bourse));
		assertFalse(bourse1.equals(tile));
		
	}
}
