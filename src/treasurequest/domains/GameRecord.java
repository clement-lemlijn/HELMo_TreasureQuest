package treasurequest.domains;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe permet d'enregister différentes informations sur la partie lors de son déroulement, 
 * principalement les cases creusées lors de la partie.
 * 
 * @author cleme
 *
 */
public class GameRecord {
	final private List<Coordinate> diggedCos = new ArrayList<>();
	final private List<Coordinate> visitedCos = new ArrayList<>();
	private LocalTime startTime;
	
	public GameRecord() {
		this.startTime = LocalTime.now();
	}

	/**
	 * Ajoute une coordonnée en fin de liste diggedCos 
	 * Méthode appellée à chaque fois qu'une case est creusée
	 * 
	 * @param cos
	 */
	public void addRecord(Coordinate cos) {
		diggedCos.add(cos);
	}
	
	/**
	 * 
	 * CTT :
	 * return getBiggestZone(caseMap).getCaseType(); getCaseType O(1) car il va juste appeler un getteur d'un objet.
	 * 
	 * var biggestZone = new Zone(0); 0(1)
	 * var exploreZone = new Zone(0); O(1)
	 * 
	 * for(Coordinate coord : diggedCos) { O(n) [où n est le nombre de cases creusées dans la partie]
	 * (n est quasiment imprévisible si ce n'est qu'il sera toujorus inférieur au nombre de cases de la carte)
	 * 
	 * exploreZone = new Zone(0, caseMap.getCaseType(coord)); O(1), appel à un getter qui fait un get (value) 
	 * sur une hashmap à partir de sa clé (Coordinate) (-> opération en O(1) si la fonction de hashage est efficace biensûr)
	 * 
	 * if(visitedCos.size() < diggedCos.size()) O(1), simple condition, le .size sur une arraylist est en O(1) puisque celle-ci comprend un compteur interne
	 * 
	 * exploreZone = exploreAdjCases(caseMap, coord);
	 * 		visitedCos.add(co); O(1), le .add en fin de liste sur une arraylist est en 0(1)
	 * 			
	 * 		var zone = new Zone(1, caseMap.getCaseType(co)); 0(1), appel à un getter qui fait un get (value) 
	 * 		sur une hashmap à partir de sa clé (Coordinate) (-> opération en O(1) si la fonction de hashage est efficace biensûr)
	 * 
 * 					for(Coordinate coord : diggedCos) { O(n) [où n est le nombre de cases creusées dans la partie]
 * 					(n est quasiment imprévisible si ce n'est qu'il sera toujours inférieur au nombre de cases de la carte)
					
					if(visitedCos.indexOf(coord) == -1  -> O(m) ['où m est le nombre de coordonnées visitées]
														(m part de 0 et croît de 1 à chaque appel récursif)
					&& caseMap.getCaseType(coord) ==    -> O(1), get sur une hashmap à l'aide de sa clé, comme déjà vu avant
					zone.getCaseType()                  -> O(1) simple appel à un getter d'un objet
					&& coord.distanceCalc(co) < 2.0){  -> O(1) simple calcul arithmétique avec des Math.sqrt et Math.pow qui sont tous 2 en O(1)
					
					Zone newZone = exploreAdjCases(caseMap, coord); -> Appel récursif

					zone.addSize(newZone.getSize()); O(1), Simple addition avec des getters d'objet
				
			return zone; O(1)


						
	 *	if(exploreZone.getSize() > biggestZone.getSize()) { -> O(1), comparaison sur de simples getters d'objet.
     *
	 *	biggestZone = new Zone(exploreZone.getSize(), exploreZone.getCaseType()); O(1), instanciation d'une nouvelle zone à l'aide de getters d'objet 
	 *				
	 *  return biggestZone; O(1)
	 *  
	 *  
	 *  CTT totale : 
	 *  On a donc une 1ere boucle qui va faire n itérations (n = nombre de cases creusées dans la partie).
	 *  Dans cette boucle, on a un appel récursif d'une méthode qui elle même va comporter une boucle qui fera n itérations.
	 *  Et dans cette seconde boucle impliquée j'ai encore une boucle cachée lorsque je fais indexOf(coord) sur mon arraylist, 
	 *  le indexOf va parcourir toute la liste jusqu'à trouver l'emplacement de l'objet Coordinate s'il est déjà présent dans la liste.
	 *  
	 *  => Une bonne amélioration de CTT serait de décaller cette partie de la condition à la fin de cette dernière, ainsi la condition 
	 *  évaluerait d'abord les autres sous-conditions qui sont en O(1) avant d'en finir avec le indexOf qui est assez lourd.
	 *  
	 *  mon arraylist visitedCos surlaquelle je fais indexOf ne sera jamais supérieure au nombre de cases creusées (c'est à dire 'n')
	 *  on peut donc dire que dans le pire des cas sa complexité sera en O(n) aussi.
	 *  
	 *  On peut donc en conclure que la CTT totale serait toujours inférieure à O(n³). Avec l'amélioration que je propose, 
	 *  elle serait toujours en théorie en O(n³) mais en pratique on peut être sûr qu'elle sera plus proche de O(n²).
     *
     *
     *
     *
     *
     *
     * INTERFACE DE COLLECTION :
     * J'ai choisi l'interface List. 
     * Il me semblait logique de stocker une liste de cases visitées dans une liste car le add() en bout de liste est en O(1).
     * Après réflexion, le list.indexOf() (que j'utilise comme un .contains) est en o(n) où n est le nombre d'élément de la liste.
     * Je me sers également du .size() qui est en O(1) dans la liste comme dans le map.
     * Si j'avais choisi une Map, le .contains/.get aurait été en o(1)... 
     * 
     * Cependant, ce n'est pas un drame, je ne me sers qu'une seule fois du .indexOf et notre bourse de coins n'étant pas très fournie, nous n'aurons 
     * jamais l'opportunité de creuser toutes les cases de la map. 
     * 
     * 
     * IMPLEMENTATION DE COLLECTION : 
     * J'ai choisi l'arraylist, malgré que mon choix d'interface ne soit pas forcément le meilleur, je trouve que l'implémentaion 
     * concrète que j'ai choisie est la bonne. L'arraylist comprend un compteur interne ce qui permet le .size() en O(1) 
     * et je m'en sers plusieures fois dans mon algorithme. Le indexOf est en O(n) où n est la taille de la liste, mais la 
     * liste commence à la taille 0 et fini à la taille n ['qui vaut le nombre de cases creusées'].
     * 
     * Le .add en bout de liste est biensûr en O(1), je me sers également beaucoup du .add puisque ma liste passe de la taille 0 à n.
     * 
     *
	 * @param caseMap
	 * @return CaseType
	 */
	public CaseType getMoreDiggedCaseType(CaseMap caseMap) {
		return getBiggestZone(caseMap).getCaseType();
	}
	
	private Zone getBiggestZone(CaseMap caseMap) {
//		System.out.println("Digged : " + diggedCos.toString());
		
		// 1. Initialiser la plus grande zone courrante comme étant une zone vide de type Eau
		var biggestZone = new Zone(0);
		var exploreZone = new Zone(0);
		
		// 2. Pour chaque case creusée qui n'a pas encore été visitée
			for(Coordinate coord : diggedCos) {
				
				// 2.1  Explorer la zone àlaquelle cette case appartient
				exploreZone = new Zone(0, caseMap.getCaseType(coord));
				if(visitedCos.size() < diggedCos.size())
					exploreZone = exploreAdjCases(caseMap, coord);
				
				// 2.2 Si la zone explorée est plus grande que la plus grande zone, remplacer la plus grande zone par la zone explorée
				if(exploreZone.getSize() > biggestZone.getSize()) {
					// Copie défensive
					biggestZone = new Zone(exploreZone.getSize(), exploreZone.getCaseType());
				}
			}		
		// Retourner la plus grande zone

		return biggestZone;		
	}

	private Zone exploreAdjCases(CaseMap caseMap, Coordinate co) {
		
		// 1. Retirer la case visitée des cases à visiter
		visitedCos.add(co);
		
		// 2. Initialiser une zone composée de cette case
		var zone = new Zone(1, caseMap.getCaseType(co));
		
		// 3. Pour chaque case adjacente à la case à explorer de même type et pas encore visitée
			for(Coordinate coord : diggedCos) {
				if(visitedCos.indexOf(coord) == -1 && caseMap.getCaseType(coord) == zone.getCaseType() && coord.distanceCalc(co) < 2.0){
				
				// 3.1 Explorer la case adjacente
				Zone newZone = exploreAdjCases(caseMap, coord);
				
				// 3.2 Fusionner la zone retournée avec la zone de la case à explorer
				zone.addSize(newZone.getSize());
				}
			}
		return zone;
		
	}

	public LocalTime getStartTime() {
		return startTime;
	}
	
	
	
	
}
