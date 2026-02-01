package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TreasureQuestGameTest {
	private char[][] carte = {
	        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
	        "XSSSSSSSXXXXXXSSSSXXXXXXXXXXXXXX".toCharArray(),
	        "XSPPPPSXXXXXXXSSXSSSXXXXXXXXXXXX".toCharArray(),
	        "XSSSSSXXXXXXSSPFFFFSSSXXXXXXXXXX".toCharArray(),
	        "XXXXXXXXXXXXSPPFRRFPPSXXXXXXXXXX".toCharArray(),
	        "PPPPPPPXXXXXSSFRRRFPSXXXXXXXXXXX".toCharArray(),
	        "RRRPPPPXXXXXXSFFRFPSXXXXXXXXXXXX".toCharArray(),
	        "RRRPPXXXXXXXXSSPPPSXXXXXXXXXXXXX".toCharArray(),
	        "RRPPPXXXXXXXXXXSSSXXXXXXXXXXXXXX".toCharArray(),
	        "PPPXXXXSSSSSSXXXXXXXXXXXXXXXXXXX".toCharArray(),
	        "XXXXXSSPPPPSSSSXXXXXXXXXXXXXXXXX".toCharArray(),
	        "XXXSSSSPPPPPPSSXXXXXXXXXXXXXXXXX".toCharArray(),
	        "XSSSSFFFFFFFFFSSXXXXXXXXXXXXXXXX".toCharArray(),
	        "SSFFFFFFFFFFFFFSSSXXXXXXXXXXXXXX".toCharArray(),
	        "FFFFFFFFFFFFFFFFFSSSSXXXXXXXXXXX".toCharArray(),
	        "FFFFFFFFFFFFFFFFFFFSSSSXXXXXXXXX".toCharArray()
	};
	
	@Test
	void gettersAndSettersTests() {
		CaseMap cm = new CaseMap(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		var cmf = new CaseMapFactory(new FakeRandom());
		tqgf.newGame(new FakeRandom(), cmf.loadMap("resources/maps/map-sample.txt"));
		var tqg = tqgf.getGame();
		
		assertNotNull(tqg.getCaseMap());
		assertNotNull(tqg.getPlayer());
		assertTrue(tqg.getRemainingTreasures() >= 1);
		tqg.setRemainingTreasures(30);
		assertEquals(30, tqg.getRemainingTreasures());
		
		tqg.isTreasure();
		tqg.withDrawNbTreasures();
		
		assertEquals(29, tqg.getRemainingTreasures());
		
		assertFalse(tqg.checkGameEnd());
		
		
		tqg.getActiveCaseCoordinate();
		
		
	}
	
	@Test
	void test() {
		CaseMap cm = new CaseMap(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		var cmf = new CaseMapFactory(new FakeRandom());
		tqgf.newGame(new FakeRandom(), cmf.loadMap("resources/maps/map-sample.txt", 16, 16));
		var tqg = tqgf.getGame();
		
		tqg.moveActiveCase(7, 15);
		tqg.dig();
		
		assertFalse(tqg.checkGameEnd());
		
	}

}
