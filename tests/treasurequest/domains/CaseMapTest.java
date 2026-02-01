package treasurequest.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.thirdparty.com.google.common.base.Objects;

class CaseMapTest {	
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
	private char[][] fausse_carte = {
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
	        "FFFFFFFFFFFFFFFFFFFSSSSXXXXXXXXZ".toCharArray()
	};
	private char[][] carte_eau = {
			"SXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(), 
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(), 
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(), 
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(), 
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray(),
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".toCharArray()
	};
	
	private char[][] petite_carte = {"S".toCharArray()};
	
	final private int MAP_LENGTH = 32;
	final private int MAP_HEIGHT = 16;
	private int NB_TREASURES = 20;
		
	@Test
	void caseMapGettersAndSettersTests() {
		
		
		CaseMap cm = new CaseMap(new MyRandom());
		var tqgf = new TreasureQuestGameFactory();
		var cmf = new CaseMapFactory(new FakeRandom());
		tqgf.newGame(new FakeRandom(), cmf.loadMap("resources/maps/map-sample.txt"));
		
		cm.setNbTreasures();
		
		assertTrue(cm.isDiggingPossible(6));
		
		
		
		var list = cm.getDiggableCoordinate();
		
		
	}
	
	
	
	
}
