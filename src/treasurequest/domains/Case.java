package treasurequest.domains;

import java.util.Objects;
/**
 * @author Lemlijn Clément
 * 
 * Cette classe possède 4 attributs :
 * 		caseType
 * 		isDigged
 * 		coins
 * 		hint
 * 
 * Elle permet de récupérer et de modifier leurs valeurs, 
 * et de déterminer si elle possède un trésor.
 * 
 * 
 * Responsabilités :
 * 		Sait s'il est creusé
 * 		Possède un type de case
 * 		Possède une quantité de pièces faisant office de trésor
 * 		Connaît son orientation
 * 		Connaît l'indice à afficher 
 * 
 * 
 */
public class Case {
	private final CaseType caseType;
	private boolean isDigged;
	private Coins coins;
	private HintType hint;

	/**
	 * Constructeur d'une case.
	 * 
	 * Une case sera toujours non-creusée par défaut
	 * Ses coins seront initialisés à 0, ils seront 
	 * éventuellement remplacés plus tard par la valeur
	 * d'un trésor
	 * Par défaut, l'indice d'une case est "HintType.NONE"
	 * 
	 * @param caseType
	 */
	public Case(CaseType caseType) {
		this.caseType = caseType;
		// Par défaut, aucune case n'est creusée
		this.isDigged = false;
		this.coins = new Coins(0);
		this.hint = HintType.NONE;
	}
	
	public CaseType getType() {
		return caseType;
	}

	public HintType getHint() {
		return hint;
	}

	public void setHint(HintType hint) {
		this.hint = hint;
	}
	
	public int getCoins() {
		return coins.getCoins();
	}

	public void setCoins(int coins) {
		if(this.getType() == CaseType.WATER) return;
		this.coins = new Coins(coins);
	}
	
	/**
	 * Une case a 2 états relatifs à son creusage.
	 * isDigged = True(creusé), False (non-creusé)
	 * 
	 * @return L'état de la case 
	 */
	public boolean isDigged() {
		return isDigged;
	}

	/**
	 * Note une case comme creusée
	 */
	public void setDigged() {
		this.isDigged = true;
	}
	
	/**
	 * @return Le coût de creusage de la case
	 */
	public int getCaseCosts() {
		return this.getType().getDurability();
	}
	
	/**
	 * Cette méthode permet de déterminer si une case possède un trésor.
	 * La règle dit que si une case possède un montant égal à 0, elle ne 
	 * possède pas de trésor.
	 * Je considère que si une case possède un montant supérieur à 0, 
	 * elle contient un trésor même si dans les règles du jeu telles 
	 * qu'elles sont actuelles, un trésor aura toujours une valeur entre 
	 * 10 et 20 pièces incluses
	 * 
	 * @return boolean (true -> trésor, false -> pas trésors)
	 */
	public boolean hasTreasure() {
		return getCoins() > 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coins, isDigged, caseType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		return Objects.equals(coins, other.coins) && isDigged == other.isDigged && caseType == other.caseType;
	}

}
