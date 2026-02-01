package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ZoneTest {

	@Test
	void test() {
		var zone = new Zone(0);
		zone.addSize();
		assertEquals(1, zone.getSize());
		zone.setCaseType(CaseType.FOREST);
		assertEquals(CaseType.FOREST, zone.getCaseType());
	}

}
