package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CaseMapFactoryTest {

	@Test
	void test() {
		var gameProvider = new TreasureQuestGameFactory();
		var cmFactory = new CaseMapFactory(new FakeRandom());
		
		gameProvider.newGame(new FakeRandom(), 
				cmFactory.loadMap("resources/maps/big-map.txt", 16, 16));
		
	}

}
