package treasurequest.domains;

import java.util.Objects;
/**
 * @author Lemlijn Clément
 *
 * Cette classe sert à représenter une bourse de pièces. 
 * Elle permet d'ajouter, retirer et obtenir le nombre de pièces 
 * dans la bourse. 
 * 
 * 
 * Responsabilités : 
 * 		Possède un montant
 * 		Ajoute/retire un certaine quantité à ce montant
 *
 */
public class Coins {
	
	private int coins;
	
	/**
	 * Le constructeur de Coins, coins ne reçoit qu'un seul argument et c'est la valeur qu'il doit stocker au départ.
	 * 
	 * Aucune restriction n'est faite, tout entier est accepté. 
	 * 
	 * @param coins
	 */
	public Coins(int coins) { 
		// La gestion d'erreur des coins sur de l'eau se fait dans "Case"
		this.coins = coins;
	}
	
	public int getCoins() {
		return coins;
	}
	
	/**
	 * Ajoute un "coins" à la bourse.
	 */
	public void addCoins() {
		this.coins ++;
	}
	
	/**
	 * Ajoute un ou plusieurs "coins" à la bourse.
	 * @param coins
	 */
	public void addCoins(int coins) {
		this.coins += coins;
	}
	
	/**
	 * Retire un "coins" de la bourse.
	 * 
	 * Précondition : 
	 * 		La bourse est suffisament remplie pour ne pas tomber en négatif
	 */
	public void withDrawCoins() {
		this.coins --;
	}
	
	/**
	 * Retire un ou plusieurs "coins" de la bourse.
	 * 
	 * Précondition : 
	 * 		La bourse est suffisament remplie pour ne pas tomber en négatif
	 * 
	 * @param coins
	 */
	public void withDrawCoins(int coins) {
		this.coins -= coins;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coins);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coins other = (Coins) obj;
		return coins == other.coins;
	}
	
}

	
