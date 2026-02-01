package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class GameRecordTest {

	private char[][] testMap = {
	        "FFPPPPPXX".toCharArray(),
	        "FFPPPPXXX".toCharArray(),
	        "PPPXXXXXX".toCharArray(),
	        "PPXXSSSSS".toCharArray(),
	        "XXXXSPPPP".toCharArray(),
	        "XXXXSPPPP".toCharArray(),
	        "XXXXSSSSS".toCharArray(),
	        "XXXXXXXXX".toCharArray(),
	        "RRXXXXXXX".toCharArray()
	};
	
	@Test
	void statutTest1() {
		var cmFact = new CaseMapFactory(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		tqgf.newGame(new FakeRandom(), cmFact.generateMap(testMap));
		var game = tqgf.getGame();
		
		// Coordinate(x, y)
		game.dig(new Coordinate(0, 0));
		game.dig(new Coordinate(1, 0));
		game.dig(new Coordinate(2, 0));
		game.dig(new Coordinate(0, 1));
		game.dig(new Coordinate(1, 1));
		game.dig(new Coordinate(4, 4));
		game.dig(new Coordinate(4, 5));
		game.dig(new Coordinate(4, 6));
		game.dig(new Coordinate(8, 3));
		game.dig(new Coordinate(8, 4));
		game.dig(new Coordinate(8, 5));
		game.dig(new Coordinate(8, 6));
		
		// Désolé déméter...
		game.getPlayer().setMoreDiggedCaseType(game.getGameRecord().getMoreDiggedCaseType(game.getCaseMap()));
		
		assertEquals(CaseType.FOREST, game.getPlayer().getMoreDiggedCaseType());
		
	}
	
	@Test
	void statutTest2() {
		var cmFact = new CaseMapFactory(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		tqgf.newGame(new FakeRandom(), cmFact.generateMap(testMap));
		var game = tqgf.getGame();
		

		game.dig(new Coordinate(4, 4));
		
		game.dig(new Coordinate(0, 0));
		game.dig(new Coordinate(1, 0));
		game.dig(new Coordinate(2, 0));
//		game.dig(new Coordinate(0, 1));
		game.dig(new Coordinate(1, 1));
		game.dig(new Coordinate(4, 5));
		game.dig(new Coordinate(4, 6));
		game.dig(new Coordinate(8, 3));
		game.dig(new Coordinate(8, 4));
		game.dig(new Coordinate(8, 5));
		game.dig(new Coordinate(8, 6));

	
		// Désolé déméter...
		game.getPlayer().setMoreDiggedCaseType(game.getGameRecord().getMoreDiggedCaseType(game.getCaseMap()));
		
		assertEquals(CaseType.SAND, game.getPlayer().getMoreDiggedCaseType());
		
	}
	
	@Test
	void statutTest3() {
		var cmFact = new CaseMapFactory(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		tqgf.newGame(new FakeRandom(), cmFact.generateMap(testMap));
		var game = tqgf.getGame();
		
		// Coordinate(x, y)
		game.dig(new Coordinate(2, 0));
		game.dig(new Coordinate(3, 0));
		game.dig(new Coordinate(4, 0));
		game.dig(new Coordinate(5, 0));
		game.dig(new Coordinate(6, 0));
		
		game.dig(new Coordinate(4, 3));
		game.dig(new Coordinate(5, 3));
		game.dig(new Coordinate(6, 3));
		game.dig(new Coordinate(7, 3));
		game.dig(new Coordinate(8, 3));
		
		// Désolé déméter...
		game.getPlayer().setMoreDiggedCaseType(game.getGameRecord().getMoreDiggedCaseType(game.getCaseMap()));
		
		assertEquals(CaseType.MEADOW, game.getPlayer().getMoreDiggedCaseType());
		
	}
	
	@Test
	void statutTest4() {
		var cmFact = new CaseMapFactory(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		tqgf.newGame(new FakeRandom(), cmFact.generateMap(testMap));
		var game = tqgf.getGame();
		
		// Coordinate(x, y)
		System.out.println("dig" + game.dig(new Coordinate(4, 3)));
		System.out.println("dig" + game.dig(new Coordinate(5, 3)));
		System.out.println("dig" + game.dig(new Coordinate(6, 3)));
		System.out.println("dig" + game.dig(new Coordinate(7, 3)));
		System.out.println("dig" + game.dig(new Coordinate(8, 3)));
		
		System.out.println("dig" + game.dig(new Coordinate(2, 0)));
		System.out.println("dig" + game.dig(new Coordinate(3, 0)));
		System.out.println("dig" + game.dig(new Coordinate(4, 0)));
		System.out.println("dig" + game.dig(new Coordinate(5, 0)));
		System.out.println("dig" + game.dig(new Coordinate(6, 0)));
		
		
		
		// Désolé déméter...
		game.getPlayer().setMoreDiggedCaseType(game.getGameRecord().getMoreDiggedCaseType(game.getCaseMap()));
		
		assertEquals(CaseType.SAND, game.getPlayer().getMoreDiggedCaseType());
		
	}
	
	@Test
	void statutTest5() {
		var cmFact = new CaseMapFactory(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		tqgf.newGame(new FakeRandom(), cmFact.generateMap(testMap));
		var game = tqgf.getGame();
		
		// Coordinate(x, y)
		game.dig(new Coordinate(2, 1));
		game.dig(new Coordinate(2, 0));
		game.dig(new Coordinate(3, 0));
		game.dig(new Coordinate(4, 0));
		game.dig(new Coordinate(5, 0));
		game.dig(new Coordinate(6, 0));
		
		game.dig(new Coordinate(4, 3));
		game.dig(new Coordinate(5, 3));
		game.dig(new Coordinate(6, 3));
		game.dig(new Coordinate(7, 3));
		game.dig(new Coordinate(8, 3));
		
		
		
		
		
		// Désolé déméter...
		game.getPlayer().setMoreDiggedCaseType(game.getGameRecord().getMoreDiggedCaseType(game.getCaseMap()));
		
		assertEquals(CaseType.MEADOW, game.getPlayer().getMoreDiggedCaseType());
		
	}
	
	
	@Test
	void statutTestAcceptation() {
		var cmFact = new CaseMapFactory(new FakeRandom());
		var tqgf = new TreasureQuestGameFactory();
		tqgf.newGame(new FakeRandom(), cmFact.loadMap("resources/maps/map-perso-test.txt"));
		var game = tqgf.getGame();
		
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(4, 3)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(4, 4)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(4, 5)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(5, 5)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(6, 5)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(6, 4)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(0, 2)));
		System.out.println(game.getCaseMap().getCaseType(new Coordinate(4, 0)));
		System.out.println();
		
		
		game.dig(new Coordinate(4, 3));
		game.dig(new Coordinate(4, 4));
		game.dig(new Coordinate(4, 5));
		game.dig(new Coordinate(5, 5));
		game.dig(new Coordinate(6, 5));
		game.dig(new Coordinate(6, 4));
		game.dig(new Coordinate(0, 2));
		game.dig(new Coordinate(4, 0));
		
		
		
		// Désolé déméter...
		game.getPlayer().setMoreDiggedCaseType(game.getGameRecord().getMoreDiggedCaseType(game.getCaseMap()));
		
		//assertEquals(64, game.getPlayer().getEarnings());
		//assertEquals(13, game.getPlayer().getSpendings());
		//assertEquals(0, game.getRemainingTreasures());
		assertEquals(CaseType.SAND, game.getPlayer().getMoreDiggedCaseType());
		
	}
	
	

}
