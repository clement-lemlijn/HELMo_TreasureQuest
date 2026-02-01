package treasurequest.domains;

import java.time.LocalTime;
import java.util.Map;

/**
 * @author Lemlijn Clément
 * 
 * Cette classe représente le jeu et en possède toutes les caractéristiques : 
 * La caseMap, le joueur, le nombre de trésor, et les coordonnées de la case active.
 * 
 * 
 * CTT de la génération d'indices :
 * 
 * new HintFactory : crée une hashmap et y insèrera toujours 
 * 9 valeurs, peu importe la taille de la map -> 0(1)
 * 
 * caseMap.getDiggableCoordinate() : Retourne une version 
 * non modifiable de la liste diggableCos en attribut de CaseMap -> O(1)
 * 
 * for(Coordinate coord : diggableCos) : 
 * parcours toutes les cases creusables de la map O(n) [n = au nombre de cases creusables sur la carte]
 * 
 * 		caseMap.getCase(coord) : fait appel à un map.get(coord), O(1) car map est une HashMap;
 * 		
 * 		tile.setHint(hintFactory.genHint(coord, caseMap)):
 * 			tile.setHint() : est en O(1), il met juste à jour un attribut de Case
 * 			hintFactory.genHint(coord, caseMap) : 
 * 
			 * scanTreasures(currentCo, list, map) : O(1), il scan les 25cases autour d'une case centrale, 
			 * il ne dépend pas du nombre de case que contient la map ou autre, il fera 
			 * toujours 25x les opérations dans la boucle
			 * 		
			 * 		currentCo.set(i, j) : O(1), modifie simplement un attribut d'une instance de Coordinate
			 * 		currentCo.toGlobalCo(centerCo) : O(1), simple calcul arithmétique
			 * 		if(map.getMap().get(currentCo) != null && map.getMap().get(currentCo).hasTreasure()) : O(1), le get(i) sur une hashmap est en O(1)
			 * 		list.add(new CoordinatePair(new Coordinate(currentCo.getCol(), currentCo.getRow()))) : O(1), l'ajout à la fin d'une ArrayList est en O(1)
			 * 
			 * list.size() : O(1) list.size() sur une ArrayList est en O(1) car la classe concrète ArrayList utilise un compteur interne qui compte le nombre d'éléments présents dans celle -ci
			 * 
			 * if(nbTreasFound == 0) return HintType.NONE : O(1), simple branchement conditionnel suivi d'un retour fixe
			 * 
			 * if(nbTreasFound == 1) return getHint(currentCo, list.get(0).getCoordinate()) : O(1)
			 * 		getHint() : O(1)
			 * 		currentCo.toLocalCo(treasureCo) : O(1), simple calcul arithmétique sur une instance de Coordinate
			 * 
			 * 			return hint.getHint(cos) : O(1), 
			 * 			localCo.setCol(localCo.getCol()/ (localCo.getCol() != 0 ? Math.abs(localCo.getCol()) : 1));
			 * 			localCo.setRow(localCo.getRow()/ (localCo.getRow() != 0 ? Math.abs(localCo.getRow()) : 1));
			 * 			 -> Simples opération artihmétiques, O(1)
			 *  
			 * 			hintMap.get(localCo) : le get(i) sur une hashmap est en O(1) 
			 * 
			 * if(nbTreasFound > 1) { 
			 * 		calcDistances(list, currentCo) : 
			 * 			for (CoordinatePair coordPair : list) : O(1), CoordinatePair aura toujours une taille de 25. 
			 * 				Pour autant que les règles du jeu ne changent pas, la zone à chercher sera toujours de 2cases dans chaque direction 
			 * 				
			 * 				calcDistances : O(1)
			 * 					currentCo.toLocalCo(coordPair.getCoordinate()) : O(1), 
			 * 					toLocalCo() : simple calcul arithmétique -> O(1)
			 * 					coordPair.getCoordinate() : get d'une attribut d'une intance d'un objet CoordinatePair -> O(1)
			 * 				
			 * 					distance = coord.distanceCalc() : Calculs arithmétique de distance (Pythagore) à base de 
			 * 					Math.sqrt et Math.pow, je considère de les méthodes Math.sqrt et Math.pow sont suffisament 
			 * 					bien programmées pour être en O(1) -> O(1) 
			 * 			
			 * 					coordPair.setDistance(distance) : simple mise à jour d'un attribut d'une instance de CoordinatePair -> O(1)
			 * 
			 * 			list.sort((pair1, pair2) -> Double.compare(pair1.getDistance(), pair2.getDistance())) : 
			 * 				Dans ce cas d'implémentation, on traite une liste d'objets CoordinatePair qui aura toujours une taille de 25
			 * 				J'ai redéfini la méthode compareTo correctement pour qu'elle me trie ma liste de CoordinatePair par ordre décroissant.
			 * 				La documentation de la méthode sort dit que par défaut, cette dernière utilise un algorithme de tri du nom de 'merge sort', 
			 * 				Merge sort est un algorithme de tri récursif, basé sur l'idée de découper la liste en sous-listes plus petites, puis fusionner 
			 * 				ces paires de sous-listes triées jusqu'à ce qu'il ne reste plus qu'une seule sous-liste triée.
			 *  			L'avantage de cet algorithme est qu'il offre des performances stables et prévisibles.
			 *  			Dans le pire des cas, cet algorithme aura une complexité en O(mlog(m)), ['où m est la taille du tableau à trier']
			 *  			Il faut tout de même noter que cet algorithhme de tri nécessite plus d'espace mémoire que certains autres algorithmes, 
			 *  			ce qui est dû à la création de nouvelles listes triées à chaque étape de fusion. 
			 *  			Bien que dans notre cas avec une liste de 25 objets à trier cet espace mémoire utilisé sera négligeable
			 * 			En conclusion, cet algorithme a une CTT en -> O(mlog(m)), où m est le nombre d'éléments à trier dans la liste, 
			 * 			dans notre cas, celà équivaudra toujours à une CTT en -> O(25log(25))
			 * 
			 * 			
			 * 			verifNotSameDistance(list, map) : O(1)
		
			 * 				map.getCoins(list.get(0+count).getCoordinate()) : O(1)
			 * 
			 * 				while(count+1 < list.size() && list.get(0+count).getDistance() == list.get(1+count).getDistance()) :
			 * 				on pourrait dire que dans le pire des cas, cette boucle bouclera au maximum 24x pour parcourir la 
			 * 				liste qui aura toujours une taille de 25. Mais en réfléchissant un peu plus, on peut déduire qu'il y aura maximum
			 * 				4 trésors autour d'une coordonnée centrale car il n'y a que 4 directions cardinales qui se situent à la même distance 
			 * 				d'un point central puisque nos cases sont carrées. Ce qui signifie que la boucle bouclera au maximum 3x pour évaluer 
			 * 				ces 4 trésors présents à la même distance de la case centrale. 
			 * 				Le nombre d'itérations de la boucle ne dépend donc d'aucun facteur qui varie dans ce jeu -> O(1)
			 * 			
			 * 				tVal2 = map.getCoins(list.get(1+count).getCoordinate()) : O(1)
			 * 
			 * 				if(tVal1 >= tVal2 && count > 0) {
								cpSwap = list.get(0);
								list.set(0, list.get(count));
								list.set(count, cpSwap);
							} : Algorithme de swap classique -> O(1), (le get(index) sur une ArrayList est toujours en O(1), idem pour le set(index))
			 * 
			 * 
						
			 * 			verifNotSameValueAndDistance(list, map) : O(1)
			 * 				Cette méthode est consitutée exactement de la même manière que verifNotSameDistance(), 
			 * 				les explications-ci dessus sont donc également valable pour cette méthode.
		
			 * 			getHint() -> O(1), méthode déjà détaillée plus haut.
			 * 
 * La ctt est donc en O(n) où n est le nombre de cases creusables sur la carte.
 * 
 * 
 * 
 * 
 * 
 * JUSTIFICATIONS COLLECTIONS UTILISEES :
 * 
 * interface de collection : 
 * 
 * Tout d'abord, je ne stocke pas les cases aux alentours d'une autre (le carré de 5x5). 
 * Mon algorithme consiste à pour chaque case creusable, je commence par regarder dans les 2cases dans chaque direction(le carré de 5x5) s'il y a 
 * un trésor, si oui, je stocke les coordonnées globales de ce trésor dans une interface de collection List. Ensuite, je détermine l'indice à donner
 * grâce à une méthode qui me convertit la coordonnée où je me trouve (celle dont je cherche l'indice) en coordonnée locale centrée sur le ou les trésors
 * trouvés. Ensuite, j'applique toutes les règles du jeu afin de déterminer quel indice je dois afficher mais là n'est pas le sujet.
 * Tout celà pour dire que la seule interface de collection dont je me sers est une liste d'interface de collection 'List', d'implémentation concrête ArrayList<> 
 * et que c'est ce choix d'interface de collection que je vais vous détailler.
 * 
 * Premièrement, cette liste m'offre l'avantage d'avoir un possibilité d'accès direct :) , ce qui m'est fort utile puisque une fois que j'ai récupéré tous les trésors dans 
 * la zone de 5x5 centrée sur la case évaluée, je trie cette liste en fonction de toutes les règles du jeu (distance, valeur trésor, ditance avec [0:0]) et à l'aide 
 * de "swaps", je positionne en 1er élément de la liste l'indice à afficher.
 * Je n'ai plus ensuite qu'à récupérer ce 1er élément et le retourner afin de le stocker dans la case adéquate.
 * 
 * Ensuite, pour effectuer le 1er tri (par distance croissante avec le centre (= la case évaluée)), j'utilise la méthode List.sort() qui est une méthode efficace
 * dans notre cas de figure comme je l'ai longuement expliqué dans le calcul de CTT de la génération d'indice :). J'ai dû pour celà redéfinir la méthode
 * compareTo de ma classe CoordinatePair.
 * 
 * 
 * implémentation concrète de collection : 
 * 
 * J'ai utilisé l'implémentation concrète ArrayList<> pour plusieures raisons : 
 * 
 * La raison est évidente, puisque l'implémentation ArrayList me permet d'accéder directement à des éléments grâce à leur index, 
 * sans parcourir toute la liste comme celà aurait été le cas dans une LinkedList par exemple.
 * 
 * Dans l'implémentation ArrayList, l'ajout d'élément en fin de liste est en O(1), (list.add(index) -> O(1)), 
 * et la récupération sur base d'indice est également en O(1), (list.get(index) -> O(1)). 
 * J'utiliserai également la taille de la liste afin de savoir le nombre de trésors qui se trouvent dans la zone autour d'une case, 
 * et l'implémentation ArrayList est parfaite pour celà puisqu'elle contient un compteur interne qui compte le nombre d'éléments
 * présents dans la liste. L'opération list.size() sera donc également en O(1).
 * La méthode list.sort() d'une ArrayList sera également très efficace car elle est de O(nlog(n)) 
 * où n est le nombre d'éléments présents dans la liste. J'ai également détaillé l'implémentation de ce list.sort() qui utilise 
 * l'algorithme merge sort dans la CTT de la génération d'indice. 
 * 
 * Cet algorithme se divise en trois grandes étapes :
 * 		1. il divise la collection en sous-listes (cette partie se fait de manière récursive et jusqu'il n'y ai plus que des listes de 1 éléments maximum)
 * 		2. ensuite, il fusionne ces sous-listes dans l'ordre croissant. (il compare chaque 1er élement de chaque sous-liste et continue (toujours de manière récursive) jusqu'à ce que toutes les listes soient fusionnées en une grande liste, triée cette fois-ci.)
 * 		3. il ne lui reste plus qu'à recopier tous les éléments de cette grande liste triée dans la liste d'origine et de retourner cette dernière.
 * 
 * 
 * 
 * 
 */
public class TreasureQuestGame {
	private final CaseMap caseMap;
	private final Player player;
	private int nbTreasures;
	private Coordinate activeCaseCoordinate;
	private final GameRecord gameRecord;
	
	/**
	 * Une intance de treasureQuestGame doit recevoir une map, 
	 * un joueur et le nombre de trésors restants à trouver 
	 * ( = au total des trésors au début de la partie ). 
	 * 
	 * @param caseMap
	 * @param player
	 * @param remainingTreasures
	 */
	public TreasureQuestGame(CaseMap caseMap, Player player, int remainingTreas, Coordinate activeCaseCoord) {
		this.caseMap = caseMap;
		this.player = player;
		this.nbTreasures = remainingTreas;
		this.activeCaseCoordinate = activeCaseCoord;
		this.gameRecord = new GameRecord();
	}
	
	/**
	 * Cette fonction retourne les coordonées de la case active. 
	 * Celle-ci peut être la case du centre de la map 
	 * [Sera implémenté plus tard]ou celle actuellement "visée" par le joueur
	 * 
	 * @return Active Case coordinates
	 */
	public Coordinate getActiveCaseCoordinate() {
		return activeCaseCoordinate;
	} 
	
	public GameRecord getGameRecord() {
		return gameRecord;
	}

	/**
	 * Déplace la case active en faisant appel à la méthode move de Coordinate
	 * 
	 * @param deltaRow
	 * @param deltaCol
	 */
	public void moveActiveCase(int deltaRow, int deltaCol) {
		this.activeCaseCoordinate.move(deltaCol, deltaRow);
	}
	
	/**
	 * @return la référence vers un Objet CaseMap
	 */
	public CaseMap getCaseMap() {
		return caseMap;
	}

	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return nombre de trésors restants
	 */
	public int getRemainingTreasures() {
		return this.nbTreasures;
	}
	
	/**
	 * Retire un trésors au nombre de trésors restants
	 * 
	 * Précondition :
	 * 		Le nombre de trésors restant est supérieur à 1, sinon il
	 * 		y a un problème dans d'autres méthodes métier.
	 */
	public void withDrawNbTreasures() {
		this.nbTreasures--;
	}

	/**
	 * @param remainingTreasures
	 */
	public void setRemainingTreasures(int remainingTreas) {
		this.nbTreasures = remainingTreas;
	}
	
	/**
	 * Surcharge de dig qui creuse à une coordonnée précise. Principalement utile pour les tests.
	 * 
	 * @param coord
	 * @return
	 */
	public boolean dig(Coordinate coord) {
		this.activeCaseCoordinate = coord;
		return dig();
	}
	
	/**
	 * @param cos
	 */
	public boolean dig() {

		// Si on ne peut pas creuser, retourner faux
		if(!caseMap.isDiggingAllowed(activeCaseCoordinate)) return false;
		
		// Si le joueur n'a pas assez d'argent, retourner faux
		if(player.getCoins() < caseMap.getCaseCosts(activeCaseCoordinate)) return false;
		
		// Si on peut creuser :
		
			// Ajouter un record du creusage effectué
			gameRecord.addRecord(activeCaseCoordinate);
		
			// Creuser
			int costs = caseMap.dig(activeCaseCoordinate);		
			
			// Déterminer si la case possède un trésor 
			int treasureValue = caseMap.getTreasureValue(activeCaseCoordinate);
			
			// Déterminer les "gains" totaux du creusage
			int gains = treasureValue - costs;
			
			// Mettre à jour la bourse du joueur
			player.updateCoins(gains);
			
			// Mettre à jour les dépenses du joueur
			player.updateSpending(costs);
			
			// Mettre à jour les gains du joueur 
			player.updateEarnings(treasureValue);
			
			return true;
	}
	
	/**
	 * Regarde à la coordonnée active s'il y a un trésor.
	 * 
	 * @return true s'il y a un trésor, false sinon
	 */
	public boolean isTreasure() {
		return caseMap.getTreasureValue(activeCaseCoordinate) > 0;
	}
	
	/**
	 * Regarde si tous les trésors sont découverts ou, à défaut
	 * si le joueur peut encore creuser.
	 * 
	 * @return true si la partie est finie, false sinon
	 */
	public boolean checkGameEnd() {
		
		// Si tous les trésors ont été découverts, la partie est finie
		if(checkAllTreasDiscovered()) return true;

		// Si le joueur n'a plus assez d'argent pour creuser une case de la carte, la partie est finie
		return checkBanqueroute(player.getCoins());
		
	}
	
	/**
	 * Cette méthode va chercher le startime en paramètre de gameRecord car cette classe comportait déjà 5 attibuts.
	 * 
	 * @return le temps de début de la partie
	 */
	public LocalTime getStartTime() {
		return gameRecord.getStartTime();
	}

	private boolean checkAllTreasDiscovered() {
		return getRemainingTreasures() <= 0;
	}
	
	/**
	 * Cette méthode vérifie s'il reste une case dans la map qui n'est pas creusée 
	 * et que le joueur peut se permettre de creuser (= s'il a assez de coins)
	 * 
	 * @param playerCoins
	 * @return
	 */
	private boolean checkBanqueroute(int playerCoins) {
		return !caseMap.isDiggingPossible(playerCoins);
		
	}
	
}
