package treasurequest.domains;

import java.time.Duration;
import java.time.LocalTime;

/**
 * @author LEMLIJN Clément
 *
 * Cette classe player contient un attribut bourse, qui représente 
 * le capital du joueur. Elle permet de récupérer et de mettre à jour
 * ce captial.
 * 
 * 
 * Responsabilités :
 * 		Possède un montant
 * 		Met à jour sa bourse
 * 
 *
 */
public class Player {
	
	private Coins bourse;
	private Coins spending;
	private Coins earnings;
	private LocalTime playTime;
	private CaseType moreDiggedCaseType;
	
	
	/**
	 * Constructeur de player qui l'initialise à 0, comme tout nouveau joueur
	 */
	public Player() {
		this.bourse = new Coins(0);
		this.spending = new Coins(0);
		this.earnings = new Coins(0);
		this.playTime = LocalTime.MIN;
	}
	
	/**
	 * Constructeur de player qui l'initialise directement avec sa 1ere bourse.
	 * 
	 * @param coins
	 */
	public Player(Coins coins) {
		this.bourse = coins;
		this.spending = new Coins(0);
		this.earnings = new Coins(0);
		this.playTime = LocalTime.MIN;
	}
	
	public CaseType getMoreDiggedCaseType() {
		return moreDiggedCaseType;
	}

	public void setMoreDiggedCaseType(CaseType moreDiggedCaseType) {
		this.moreDiggedCaseType = moreDiggedCaseType;
	}

	/**
	 * @return coins
	 */
	public int getCoins() {
		return bourse.getCoins();
	}

	/**
	 * @param coins
	 */
	public void setCoins(int coins) {
		this.bourse = new Coins(coins);
	}
	
	/**
	 * Met à jour la bourse du joueur. 
	 * Accepte les valeurs négative,(ex : creusage dans le vide)
	 * 
	 * @param coins
	 */
	public void updateCoins(int coins) {
		this.bourse.addCoins(coins);
	}
	
	/**
	 * Met à jour les dépenses du joueur. 
	 * 
	 * @param coins
	 */
	public void updateSpending(int spending) {
		if(spending < 1) throw new IllegalArgumentException("Les dépenses doivent être strictement plus grandes que 0");
		this.spending.addCoins(spending);
	}
	
	/**
	 * Met à jour les gains du joueur. 
	 * 
	 * @param coins
	 */
	public void updateEarnings(int earnings) {
		if(earnings < 0) throw new IllegalArgumentException("Les gains ne peuvent pas être négatifs ! (" + earnings + ")");
		this.earnings.addCoins(earnings);
	}
	
	public int getEarnings() {
		return earnings.getCoins();
	}

	/**
	 * @return coins
	 */
	public int getSpendings() {
		return spending.getCoins();
	}
	
	public LocalTime getPlayTime() {
		return playTime;
	}

	/**
	 * Cette méthode stocke le temps de jeu total du joueur sur base d'un temps de début stocké dans TreasureQuestGame, 
	 * et du temps de fin qui vaut généralement le temps actuel lorsque la méthode est appellée
	 * 
	 * @param start
	 * @param end
	 */
	public void storeGameTime(LocalTime start, LocalTime end) {
		int secondes = Duration.between(start, end).toSecondsPart();
		int minutes = secondes / 60;
		secondes %= 60;
		this.playTime = this.playTime.plusMinutes(minutes);
		this.playTime = this.playTime.plusSeconds(secondes);
	}
	
}
