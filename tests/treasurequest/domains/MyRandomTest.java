package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyRandomTest {
	
	
	
	@Test
	void test() {
		var random = new MyRandom();
		
		for(int i = 0; i < 1000 ; ++i) {
			int randomInt = random.randomBetween(10, 20);
			//System.out.println(randomInt);
			assertTrue(randomInt <= 20 && randomInt >=10);
		}
	}

}
