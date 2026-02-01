package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TreasureQuestGameFactoryTest {

	@Test
	void createGameTest() {
		var factory = new TreasureQuestGameFactory();
		var cmf = new CaseMapFactory(new FakeRandom());
		factory.newGame(new MyRandom(), cmf.loadMap("resources/maps/map-sample.txt"));
		var game = factory.getGame();
		assertNotNull(game);
	}

}
