package treasurequest.domains;

/**
 * Cette classe représente une zone de cases uniformes (leur type) et mitoyennes.
 * 
 * @author cleme
 *
 */
public class Zone {
	private int size;
	private CaseType caseType;
	
	
	/**
	 * Constructeur de l'objet Zone, recoit 2 paramètres, size (int) et caseType (CaseType)
	 * 
	 * @param size
	 * @param caseType
	 */
	public Zone(int size, CaseType caseType) {
		this.size = size;
		this.caseType = caseType;
	}
	
	/**
	 * Constructeur de l'objet Zone recoit uniquement la taille de la zone 
	 * et définis le caseType en CaseType.WATER
	 * 
	 * @param size
	 */
	public Zone(int size) {
		this(size, CaseType.WATER);
	}
	
	public int getSize() {
		return size;
	}

	/**
	 * Ajoute un certain entier à une zone.
	 * (concrètement, celà correspond souvent à la taille d'une seconde zone)
	 * 
	 * @param size
	 */
	public void addSize(int size) {
		this.size += size;
	}
	
	/**
	 * Surcharge de addSize(int size) qui ajoute 1 à la taille d'une zone.
	 * 
	 */
	public void addSize() {
		this.addSize(1);
	}

	public CaseType getCaseType() {
		return caseType;
	}


	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}
	
	
}
