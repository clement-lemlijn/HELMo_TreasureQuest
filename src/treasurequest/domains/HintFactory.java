package treasurequest.domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author cleme
 *	
 * Générateur d'indices, est appelé lors de la génération de la map dans TreasureQuestGameFactory
 * 
 */
public class HintFactory {
	
//	final private Map<Coordinate, Case> map = new HashMap<>();
	final private List<Coordinate> diggableCos = new ArrayList<>();
	final private Hint hint;
	

	/**
	 * Constructeur de HintFactory, ne contient aucun argument. 
	 * 
	 * récupère une instance de la classe Hint qui contient une HashMap qui lie une "coordonnée" ou plutôt direction à un indice à donner.
	 */
	public HintFactory() {
		this.hint = new Hint();
	}
	
	/**
	 * Cette méthode permet de générer un indice à placer dans toutes les cases creusables de la map.
	 * 
	 * @param caseMap
	 */
	public void generateHints(CaseMap caseMap) {
		List<Coordinate> diggableCos = caseMap.getDiggableCoordinate();
		
		for(Coordinate coord : diggableCos) {
			Case tile = caseMap.getCase(coord);
			tile.setHint(genHint(coord, caseMap));
		}
	}
	
	/**
	 * 
	 * CTT : 
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
	 *  			Dans le pire des cas, cet algorithme aura une complexité en O(nlog(n)), ['où n est la taille du tableau à trier']
	 *  			Il faut tout de même noter que cet algorithhme de tri nécessite plus d'espace mémoire que certains autres algorithmes, 
	 *  			ce qui est dû à la création de nouvelles listes triées à chaque étape de fusion. 
	 *  			Bien que dans notre cas avec une liste de 25 objets à trier cet espace mémoire utilisé sera négligeable
	 * 			En conclusion, cet algorithme a une CTT en -> O(nlog(n)), où n est le nombre d'éléments à trier dans la liste, 
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
	 * 
	 * 
	 * @param currentCo
	 * @param map
	 * @return
	 */
	private HintType genHint(Coordinate currentCo, CaseMap map) {
		List<CoordinatePair> list = new ArrayList<>();
		
		// Recherche de trésors aux alentours
		scanTreasures(currentCo, list, map);
		int nbTreasFound = list.size();
		
		// Si on ne trouve pas de trésors  
		if(nbTreasFound == 0) return HintType.NONE;
		
		// Si on ne trouve qu'un seul trésor
		if(nbTreasFound == 1) return getHint(currentCo, list.get(0).getCoordinate());
		
		// Si on trouve plusieurs trésors, pointer le plus proche.
		if(nbTreasFound > 1) { 
			
			// 1.0 Récupération des distances
			calcDistances(list, currentCo);
			
			// 1.1 Triage de la liste dans l'ordre croissant			
			list.sort((pair1, pair2) -> Double.compare(pair1.getDistance(), pair2.getDistance()));
			
			// 2.0 Vérification que les premiers trésors de la liste n'ont pas la même distance par rapport à la case et valeur de tréors.
			verifyTreasureNotSameDistanceAndValue(list, map);
			
			// Renvoi du hint du trésor sélectionné
			return getHint(currentCo, list.get(0).getCoordinate());
		}
		
		return HintType.NONE;
		
	}
	
	private void verifyTreasureNotSameDistanceAndValue(List<CoordinatePair> list, CaseMap map) {
		verifNotSameDistance(list, map);
		verifNotSameValueAndDistance(list, map);
	}
	
	/**
	 * 
	 * CTT : 
	 * 
	 * for (CoordinatePair coordPair : list) : O(1), CoordinatePair aura toujours une taille de 25. 
	 * Pour autant que les règles du jeu ne changent pas, la zone à chercher sera toujours de 2cases dans chaque direction 
	 * 
	 * 		currentCo.toLocalCo(coordPair.getCoordinate()) : O(1), 
	 * 			toLocalCo() : simple calcul arithmétique -> O(1)
	 * 			coordPair.getCoordinate() : get d'une attribut d'une intance d'un objet CoordinatePair -> O(1)
	 * 			
	 * 			distance = coord.distanceCalc() : Calculs arithmétique de distance (Pythagore) à base de 
	 * 			Math.sqrt et Math.pow, je considère de les méthodes Math.sqrt et Math.pow sont suffisament 
	 * 			bien programmées pour être en O(1) -> O(1) 
	 * 			
	 * 			coordPair.setDistance(distance) : simple mise à jour d'un attribut d'une instance de CoordinatePair -> O(1)
	 * 
	 * 
	 * @param list
	 * @param currentCo
	 */
	private void calcDistances(List<CoordinatePair> list, Coordinate currentCo) {
		double distance;
		for (CoordinatePair coordPair : list) {
			
			// Réupération de la coordonnée + transformation en coordonnée "locale"
			Coordinate coord = currentCo.toLocalCo(coordPair.getCoordinate());
			
			// Calcul de la distance entre le 0, 0 local et cette coordonnée
			distance = coord.distanceCalc();
			coordPair.setDistance(distance);
		}
	}
	
	/**
	 * 
	 * CTT : 
	 * 
	 * map.getCoins(list.get(0+count).getCoordinate()) : O(1)
	 * 
	 * while(count+1 < list.size() && list.get(0+count).getDistance() == list.get(1+count).getDistance()) :
	 * on pourrait dire que dans le pire des cas, cette boucle bouclera au maximum 24x pour parcourir la 
	 * liste qui aura toujours une taille de 25. Mais en réfléchissant un peu plus, on peut déduire qu'il y aura maximum
	 * 4 trésors autour d'une coordonnée centrale car il n'y a que 4 directions cardinales qui se situent à la même distance 
	 * d'un point central puisque nos cases sont carrées. Ce qui signifie que la boucle bouclera au maximum 3x pour évaluer 
	 * ces 4 trésors présents à la même distance de la case centrale. 
	 * 		
	 * 		tVal2 = map.getCoins(list.get(1+count).getCoordinate()) : O(1)
	 * 
	 * 		if(tVal1 >= tVal2 && count > 0) {
				cpSwap = list.get(0);
				list.set(0, list.get(count));
				list.set(count, cpSwap);
			} : Algorithme de swap classique -> O(1), (le get(index) sur une ArrayList est toujours en O(1), idem pour le set(index))
	 * 
	 * @param list
	 * @param map
	 */
	private void verifNotSameDistance(List<CoordinatePair> list, CaseMap map) {
		int count = 0;
		CoordinatePair cpSwap;
		int tVal1 = map.getCoins(list.get(0+count).getCoordinate());
		int tVal2;
		while(count+1 < list.size() && list.get(0+count).getDistance() == list.get(1+count).getDistance()){

			tVal2 = map.getCoins(list.get(1+count).getCoordinate());
			
			if(tVal1 >= tVal2 && count > 0) {
				cpSwap = list.get(0);
				list.set(0, list.get(count));
				list.set(count, cpSwap);
			}
			count++;
		}
	}
	
	private void verifNotSameValueAndDistance(List<CoordinatePair> list, CaseMap map){
		int count = 0;
		CoordinatePair cpSwap;
		double tDistFrom00 = list.get(0+count).getCoordinate().distanceCalc();
		double tDistFrom002;
		while(count+1 < list.size() && map.getCoins(list.get(0+count).getCoordinate()) 
				== map.getCoins(list.get(1+count).getCoordinate())){
			
			tDistFrom002 = list.get(1+count).getCoordinate().distanceCalc();
			
			if(tDistFrom00 >= tDistFrom002 && count > 0) {
				cpSwap = list.get(0);
				list.set(0, list.get(count));
				list.set(count, cpSwap);
			}
			count++;
		}		
	}
	
	/**
	 * 
	 * CTT : 
	 * 
	 * currentCo.toLocalCo(treasureCo) : O(1), simple calcul arithmétique sur une instance de Coordinate
	 * 
	 * return hint.getHint(cos) : O(1), 
	 * 		localCo.setCol(localCo.getCol()/ (localCo.getCol() != 0 ? Math.abs(localCo.getCol()) : 1));
	 * 		localCo.setRow(localCo.getRow()/ (localCo.getRow() != 0 ? Math.abs(localCo.getRow()) : 1));
	 * 		 -> Simples opération artihmétiques, O(1)
	 *  
	 * 		hintMap.get(localCo) : le get(i) sur une hashmap est en O(1)  
	 * 
	 * @param currentCo
	 * @param treasureCo
	 * @return
	 */
	private HintType getHint(Coordinate currentCo, Coordinate treasureCo) {
		Coordinate cos = new Coordinate(0, 0);
		cos = currentCo.toLocalCo(treasureCo);
		return hint.getHint(cos);
	}
	
	private void scanTreasures(Coordinate centerCo, List<CoordinatePair> list, CaseMap map) {		
		list.clear();
		Coordinate currentCo = new Coordinate(0, 0);		
		
		for(int i = - 2 ; i<=2 ; ++i) {
			for(int j = - 2 ; j<=2 ; ++j) {
				currentCo.set(i, j);
			
				// Passage des coordonnées Locales (i, j) à des coordonnées globales de centre centerCO
				currentCo = currentCo.toGlobalCo(centerCo);				
				if(map.getCase(currentCo) != null && map.getCase(currentCo).hasTreasure()) {
					list.add(new CoordinatePair(new Coordinate(currentCo.getCol(), currentCo.getRow())));	
				} 	
			}
		}
	}
}
