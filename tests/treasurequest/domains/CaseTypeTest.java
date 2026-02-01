package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CaseTypeTest {

	@Test
	void getDurabilityTest() {
		assertEquals(0, CaseType.WATER.getDurability());
		assertEquals(1, CaseType.SAND.getDurability());
		assertEquals(2, CaseType.MEADOW.getDurability());
		assertEquals(3, CaseType.FOREST.getDurability());
		assertEquals(5, CaseType.ROCK.getDurability());
	}

}
