	package treasurequest.domains;
	
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;
	import java.util.Objects;
	
	/**
	 * @author Lemlijn Clément 
	 * 
	 * Cette classe 
	 * 
	 * 
	 * 
	 * Justifications interface de collection : 
	 * 	
	 * Map<Coordinate, Case> : 
	 * 
	 * J'avais besoin d'une collection qui associe une case à une coordonnée. La manière et l'ordre danslequel
	 * les items étaient stockés m'importait très peu, pour autant que je puisse accéder facilement à une Case
	 * sur base de sa coordonnée (map.get(Coordinate cos)). J'avais également besoin de pouvoir itérer 
	 * facilement dans ma collection afin de récupérer diverses informations sur toutes les cases. 
	 * 
	 * 
	 * Voici les atouts de la collection Map<> :
	 * 
	 * Accès rapide par clé : La Map est une collection clé-valeur qui permet une recherche rapide 
	 * en utilisant des clés. Dans notre cas, il est donc facile de rechercher une case sur base 
	 * de sa coordonnée.
	 * 
	 * Chaque clé est unique : Dans une map, chaque clé est unique ce qui nous garanti que jamais 
	 * nous ne retrouverons une coordonnée associée à 2 cases. => Pas de problème de Doublons
	 * 
	 * Itération facile : Il est facile d'itérer sur tous les éléments de la collection grâce à une 
	 * simple boucle "foreach()" et d'en récupérer des datas. Nombre de cases creusables, valeur 
	 * totale des trésors, ...
	 * 
	 * Et il va de soi qu'il est très facile d'y ajouter/ retirer des valeurs 
	 * (toujours sur base de leurs clés ou valeur) grâce aux méthodes get() et remove()
	 * 
	 * 
	 * Justifications implémentation de collection : 
	 * 
	 * Hashmap<> :
	 * 
	 *  Avantages : 
	 *  	les méthodes put, get et containsKey s'exécutent en un temps constant -> O(1)
	 * 
	 *  Inconvénients :
	 *  	le parcours de l'ensemble des éléments à l'aide d'un itérateur se fait en O(n), 
	 *  	avec n = au nombre d'éléments dans la map
	 *  	
	 * Mais, (en tout cas pour l'itération 1), je ne devrai parcourir qu'une seule fois
	 * les élements de la table pour compter les cases creusables [Optimisation possible en
	 * les collectant lors de la création de la map mais pas encore implémentée]. 
	 * J'utiliserai essentielement la méthode get(key) pour modifier l'état d'une case.
	 * 
	 * CTT des opérations de HashMap :
	 * 	containsKey(k) O(1)
	 * 	put(k, v) => O(1)
	 * 	get(k) => O(1)
	 * 	remove(k) => O(1)
	 * 	size() => O(1)
	 * 	isEmpty() => O(1)
	 * 	iterator() => O(1)
	 * 	containsValue => O(n) [n = nombre d'items dans la collection]
	 * 
	 * Les principales opérations utilisées lors de l'itération 1 sont : put(k, v) et get(k)
	 * 
	 */
	public final class CaseMap implements Iterable<Coordinate>{

		
		final private Map<Coordinate, Case> map = new HashMap<>();
		final private List<Coordinate> diggableCos = new ArrayList<>();
		private int nbPlacedTreasures;
		private final IRandomize random;
	
		/**
		 * Constructeur de caseMap
		 * 
		 * Une map vide jettera une IllegalArgumentException
		 * 
		 * @param map
		 */
		public CaseMap(IRandomize random) {
			this.random = random;
		}
		
		/**
		 * @param coord
		 * @return
		 */
		public Case getCase(Coordinate coord) {
			return map.get(coord);
		}
		
		/**
		 * Cette méthode récupère une case à une coordonnée donnée et y initialise un indice.
		 * 
		 * @param coord
		 * @param hint
		 */
		public void setHint(Coordinate coord, HintType hint) {
			this.getCase(coord).setHint(hint);
		}
		
		/**
		 * Retourne le nombre de coins qu'une case contient en fonction de sa coordonnée.
		 * 
		 * @param coord
		 * @return
		 */
		public int getCoins(Coordinate coord) {
			return this.getCase(coord).getCoins();
		}
		
		/**
		 * Mémorise un nombre de pièce dans une case en fonction de sa coordonnée.
		 * 
		 * @param coord
		 * @param coins
		 */
		public void setCoins(Coordinate coord, int coins) {
			this.getCase(coord).setCoins(coins);
		}
		
		/**
		 * Retourne une copie immodifiable de la liste
		 * 
		 * @return diggableCos
		 */
		public List<Coordinate> getDiggableCoordinate() {
			return Collections.unmodifiableList(diggableCos);
		}
		
		public int getRemainingTreas() {
			return nbPlacedTreasures;
		}
	
		/**
		 * Calcule le nombre de trésors à placer en foncction de la taille de la map et l'initialise.
		 * 
		 * @param nbTreasures
		 */
		public void setNbTreasures() {
			int nbTreasures = getNbTreasuresToPlace();
			if(nbTreasures < 1) throw new IllegalArgumentException("Impossible d'initialiser un nombre négatif de trésors");
			this.nbPlacedTreasures = nbTreasures;
		}
	
		/**
		 * 
		 * @param src_map
		 */
		public void addCaseToMap(Case tile, Coordinate cos){
			map.put(cos, tile);
		}

		/**
		 * Détermine si une case est creusable sur base de 
		 * coordonnées (key)
		 * 
		 * Précondition :
		 * 		La coordonnée est interne à la map.		
		 * 
		 * @param coord
		 * @return si la case est creusable -> true sinon false
		 */
		public boolean isDiggingAllowed(Coordinate coord) {
			return map.get(coord) != null ? isDiggingAllowed(map.get(coord)) : false;
		}
		
		/**
		 * Détermine si une case est creusable sur base d'une 
		 * case (value)
		 * 
		 * Précondition :
		 * 		Le type de la case n'est pas null (la case n'est pas vide)
		 * 
		 * @param tile
		 * @return si la case est creusable -> true sinon false
		 */
		public boolean isDiggingAllowed(Case tile) {
			CaseType caseType = tile.getType();
			return caseType != CaseType.WATER 
					&& caseType != CaseType.VOID
					&& tile.isDigged() == false;
		}
		
	
		/**
		 * Surchage de getCaseCosts afin de respecter notre bon vieux déméter :)
		 * 
		 * @param cos
		 * @return
		 */
		public int getCaseCosts(Coordinate cos) {
			return getCaseCosts(map.get(cos));
		}
		
		/**
		 * @param tile
		 * @return
		 */
		public int getCaseCosts(Case tile) {
			return tile.getCaseCosts();
		}
		
		/**
		 * Retourne l'indice situé sur la case se trouvant aux coordonnées renseignées
		 * 
		 * @param coord
		 * @return
		 */
		public HintType getCaseHint(Coordinate coord) {
			return this.getCase(coord).getHint();
		}
		
		/**
		 * Compte le nombre de cases creusables dans la collection
		 * 
		 * @return le nombre de case creusables
		 */
		public int getNbCasesDiggable() {
			int nbCases = 0;
			for(Map.Entry<Coordinate, Case> item : map.entrySet()) {
				Case tile = item.getValue();
				if(isDiggingAllowed(tile)) ++nbCases;
			}
			return nbCases;
		}
		
		/**
		 * Calcule le nombre de trésors à placer, qui équivaut à 
		 * 10% du nombre de cases creusables
		 * 
		 * Précondition : 
		 * 		Au moins une case est creusable sur toute la carte.
		 * 
		 * @return le nombre de trésors à placer
		 */
		public int getNbTreasuresToPlace() {
			int nbDiggableCases = getNbCasesDiggable();
			int nbTreasToPlace = (int)((double)nbDiggableCases/10.0);
			return nbTreasToPlace > 1 ? nbTreasToPlace : 1;
		}
		
		/**
		 * CTT : 
		 * O(1), on considère que math.random est assez bien fait 
		 * pour nous sortir un résultat en O(1)
		 * 
		 * @return a random value
		 */
		private int getTreasureRandomValue() {
			return this.random.randomBetween(10, 20);
		}
		
		/**
		 * Génère des trésors aléatoirement 
		 * 
		 * CTT : 
		 * 
		 * extractDiggableCos()-> O(n) (voir javadoc méthode)
		 * 
		 * Collections.shuffle(diggableCos) -> O(m) [m -> n/10 (nombre de trésors dans la map)]
		 * 
		 * for(i<remainingTreas) -> O(m) [m -> n/10 (nombre de trésors dans la map)]
		 * map.get() -> O(1)
		 * diggableCos.get() -> O(1)
		 * case.setCoins -> O(1)
		 * getTreasureRandomValue() -> O(1) (voir javadoc méthode)
		 * 
		 * 
		 * CTT TOTALE : O(n + m) ou techniqument : O(1.1n)
		 * 
		 */
		public void generateTreasures(IRandomize random) {
			setNbTreasures();
			extractDiggableCos();
			random.listShuffle(diggableCos);
			for(int i = 0; i < nbPlacedTreasures ; ++i)
				map.get(diggableCos.get(i)).setCoins(getTreasureRandomValue());	
		}
		
		/**
		 * Cette méthode parcours toutes les cases de la map, et stocke les Coordonnées
		 * de toutes les cases creusables dans une liste "diggableCos"
		 * 
		 * CTT : 
		 * 
		 * for(Map.Entry...) -> O(n) [n -> Nombre d'items dans la map]
		 * tile = item.getValue() -> O(1)
		 * diggableCos.add() -> O(1) ?
		 * 
		 */
		private void extractDiggableCos() {
			for(Map.Entry<Coordinate, Case> item : map.entrySet()) {
				Case tile = item.getValue();
				if(isDiggingAllowed(tile)) diggableCos.add(item.getKey());;
			}
		}
		
		/**
		 * Retourne le type d'une case associée à une coordonnée.
		 * 
		 * Précondition : 
		 * 		La coordonnée fait référence à une case de la map.
		 * 
		 * Postcondition : 
		 * 		retourne le type de la case, null si la case n'est pas dans la carte.
		 * 
		 * @param Coordonnées
		 * @return le type de la case sélectionnée (CaseType)
		 */
		public CaseType getCaseType(Coordinate cos) {
			return map.get(cos) != null ? map.get(cos).getType() : null;
		}
		
		/**
		 * Cette méthode creuse et retourne la valeur du trésor s'il y en avait un, sinon elle retourne 0
		 * 
		 * @param cos
		 * @return le prix à payer
		 */
		public int dig(Coordinate cos) {
			Case tile = map.get(cos);
			tile.setDigged();
			System.out.println(tile.getType());
			return tile.getCaseCosts();
		}
		
		/**
		 * Retourne la valeur d'un trésor sur une case, 0 s'il n'y a pas de trésor
		 * 
		 * @param cos
		 * @return la valeur du trésor, 0 s'il n'y en a pas.
		 */
		public int getTreasureValue(Coordinate cos) {
			return map.get(cos) != null ? map.get(cos).getCoins() : 0;
		}

		/**
		 * Cette méthode va itérer sur toutes les coordonnées creusable de la map et récupérer la case correspondante.
		 * Si lors de l'itération, je tombe sur une case qui n'est pas encore creusée et que le joueur 
		 * peut se permettre de creuser, je retourne true.
		 * Si je arrive à la fin de la boucle, je retourne true
		 * 
		 * @param playerCoins
		 * @return true si le joueur peut encore creuser une case, false sinon
		 */
		public boolean isDiggingPossible(int playerCoins) {
			if(playerCoins >= 5) return true;
			Case tile;
			for (Coordinate coord : diggableCos) {
				tile = map.get(coord);
		        if (!tile.isDigged() && tile.getCaseCosts() <= playerCoins) {
		            return true;
		        }
		    }
		    return false;
		}	
		
		/**
		 * Sert à itérer directement sur la caseMap 
		 *
		 */
		@Override
		public Iterator<Coordinate> iterator() {
			return map.keySet().iterator();
		}

		/**
		 * Retourne la taille de la map, 
		 * Opération en O(1).
		 * 
		 * @return la taille de la map.
		 */
		public int getMapSize() {
			return map.size();
		}
	}
