package treasurequest.domains;
/**
 * Enumère les types cases existantes
 * 
 * @author Lemlijn Clément
 * */
public enum CaseType {	
	SAND(1), MEADOW(2), FOREST(3), ROCK(5), WATER(0), VOID(0);

	private int durability;

	CaseType(int durability) {
		this.durability = durability;
	}

	public int getDurability() {
		return this.durability;
	} 	
}
