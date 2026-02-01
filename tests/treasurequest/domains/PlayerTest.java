package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void getCoinsTest() {
		var player = new Player(new Coins(40));
		var player2 = new Player();
		assertEquals(40, player.getCoins());
		player.setCoins(100);
		player.getMoreDiggedCaseType();
		assertEquals(100, player.getCoins());
		
		player.updateCoins(40);
		assertEquals(140, player.getCoins());
		
		player.updateEarnings(10);
		player.updateSpending(15);
		
		assertEquals(10, player.getEarnings());
		assertEquals(15, player.getSpendings());
	}

}
