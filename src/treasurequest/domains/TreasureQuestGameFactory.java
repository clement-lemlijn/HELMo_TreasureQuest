package treasurequest.domains;

import java.util.Arrays;
import java.util.List;

import treasurequest.io.CharArrayFileReader;
import treasurequest.io.CharArrayFileReaderTest;
/**
 * @author Lemlijn Clément
 * 
 * 
 * CTT de generateTreasures() : 
 * 
 * for(Map.Entry...) -> O(n) [n -> Nombre d'items dans la map]
 * tile = item.getValue() -> O(1)
 * diggableCos.add() -> O(1) ?
 * 
 * Collections.shuffle(diggableCos) -> O(m) [m -> nombre de trésors dans la map]
 * for(i<remainingTreas) -> O(m) [m -> nombre de trésors dans la map]
 * map.get() -> O(1)
 * diggableCos.get() -> O(1) => (source : ppt du cours d'algo)
 * case.setCoins -> O(1)
 * getTreasureRandomValue() -> O(1), 
 * on considère que math.random est assez bien fait 
 * pour nous sortir un résultat en O(1)
 * 
 * CTT TOTALE : O(n + m)
 * avec n = Nombre d'items dans la map (j'entends pas item un couple clé-valeur, <Coordinate, Case>)
 * et m = nombre de trésors dans la map 
 * (moins de n/10 car nbTrésors = 10% des cases creusables de la map, 
 * donc on peut dire que la CTT sera située entre (n et 1.1 n)
 * 
 * 
 */
public class TreasureQuestGameFactory implements GameFactory{
	
	private Player player;
	private TreasureQuestGame game;
	
	

	
	/**
	 * Constructeur de treasureQuestGameFactory, en plus de la partie courrante, 
	 * il détient maintenant le joueur afin de le passer aux différentes instances 
	 * de TreasureQuestGame qu'il crée.
	 * 
	 */
	public TreasureQuestGameFactory() {
		this.player = new Player();
	}
	
	/**
	 * Renvoie une instance de TreasureQuestGame
	 *
	 * Préconditions : 
	 * 		une instance de TreasureQuestGame est créée
	 * 
	 * Postconditions :
	 * 		Une instance de TreasureQuestGame est renvoyée
	 */
	@Override
	public TreasureQuestGame getGame() {
		return game;
	}
	
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
	@Override
	public void newGame(IRandomize random, CaseMap sourceMap) {
		
		// 2. Placer les trésors sur la cases creusables (!= eau)
		CaseMap caseMap = completeMap(sourceMap, random);
		
		// 3. Créer un joueur
		player.updateCoins(calculatePlayerMoney(caseMap.getRemainingTreas()));
		
		// 4. Stocker la map et le joueur
		game = new TreasureQuestGame(caseMap, 
				(this.game == null ? player : game.getPlayer()), caseMap.getRemainingTreas(), getMapCenter(caseMap));

		// 5. Générer les indices
		generateHints(caseMap);
	}
	
	/**
	 * Cette fonction crée la map, c'est à dire :
	 * 	1. Convertit le tableau 2D de chars en un objet CaseType contenant une collection Map
	 *  2. Calcule le nombre de trésors à cacher (10% du nombre de cases creusables)
	 *  3. Génère autant de trésors qu'il a calculé sur des cases aléatoires
	 *  4. Retourne la map créée
	 * 
	 * @param sourceMap
	 * @return createdMap
	 */
	private CaseMap completeMap(CaseMap caseMap, IRandomize random) {
		
		// 2. Calculer le nombre de trésors à cacher
//		caseMap.setNbTreasures();
		
		// 3. Cacher les trésors
		caseMap.generateTreasures(random);
		
		// Retourner la map
		return caseMap;
	}
	
	
	
	/**
	 * Cette méthode retourne les coordonnées du centre de la map
	 * Si la map ne possède pas de centre, la case 
	 * en haut à gauche(du centre) sera donnée comme centre
	 * 
	 * Précondition : 
	 * 		Le centre de la map est une vraie case, pas du vide.
	 * 
	 * @return le "centre" de la map
	 */
	private Coordinate getMapCenter(CaseMap caseMap) {
		int totalLength = 0;
	    int totalHeight = 0;
	    int numCoords = caseMap.getMapSize();

	    for (Coordinate coord : caseMap) {
	        totalLength += coord.getCol();
	        totalHeight += coord.getRow();
	    }

	    double avgLength = (double) totalLength / numCoords;
	    double avgHeight = (double) totalHeight / numCoords;

	    return new Coordinate((int)avgLength, (int)avgHeight);
	}
	
	
	/**
	 * 
	 * CTT : 
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
	 * @param caseMap
	 */
	private void generateHints(CaseMap caseMap) {
		
		HintFactory hintFactory = new HintFactory();
		
		hintFactory.generateHints(caseMap);
	
	}
	
	/**
	 * Cette fonction calcule la bourse du joueur. 
	 * La bourse du joueur est égale au double du nombre
	 * de trésors à cacher.
	 * 
	 * @param nbTreasures
	 * @return la bourse du joueur
	 */
	private int calculatePlayerMoney(int nbTreasures) {
		return nbTreasures * 200;
	}
	
		
}
