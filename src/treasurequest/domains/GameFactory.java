/**
 * 
 */
package treasurequest.domains;

/**
 * @author cleme
 *
 * Représente le processus de création d'une partie, incluant sa suppression lorsqu'elle
 * devient obsolète
 */
public interface GameFactory {


//	/**
//	 * Génère une partie 
//	 * @param random 
//	 */
//	void newGame(IRandomize random, char[][] sourceMap);


	/**
	 * Renvoie la référence vers une partie
	 * @return GameFactory
	 */
	TreasureQuestGame getGame();


	/**
	 * 
	 * Cette méthode crée une partie c'est à dire :
	 *  1. Charge un fichier texte contenant la map à charger
	 *  2. Crée un objet CaseMap contenant notamment cette map
	 *  3. Crée un joueur avec sa bourse
	 *  4. Crée un objet TreasureQuestGame
	 *  5. Ajoute la case Active à cette game
	 *  
	 *  
	 * Préconditions :
	 * 		Un lien valide vers un fichier valide contenant une map à charger.
	 * 		La map contient au minimum une case creusable
	 * 		Aucune case n'est creusée
	 * 		
	 * Postconditions : 
	 * 		La map chargée à partir du fichier est contenue dans un objet CaseMap
	 * 		Les trésors sont placés sur les cases creusables de la carte
	 * 		Le joueur a été instancié et contient une bourse contenant un montant valide de coins.
	 * 		Une instance de TreasureQuestGame est créée
	 * 
	 */
	void newGame(IRandomize random, CaseMap caseMap);
	
	
}
