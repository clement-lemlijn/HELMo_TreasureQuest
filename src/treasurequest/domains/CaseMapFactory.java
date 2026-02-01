package treasurequest.domains;


import java.util.Arrays;

import treasurequest.io.CharArrayFileReader;
import treasurequest.io.CharArrayFileReaderTest;

/**
 * Cette classe permet de créér des objets CaseMap en fonction des modes de jeux.
 * 
 * @author cleme
 *
 */
public class CaseMapFactory {

	private final CaseMap caseMap;
	private final IRandomize random;

	/**
	 * Constructeur de caseMapFactory, prend en paramètre un objet qui implémente l'interface IRandomize.
	 * 
	 * @param random
	 */
	public CaseMapFactory(IRandomize random) {
		this.caseMap = new CaseMap(random);
		this.random = random;
	}
	
	/**
	 * Charge une map entière, = mode de jeu 'normal'
	 * 
	 * @param pathToMap
	 * @return
	 */
	public CaseMap loadMap(String pathToMap) {

		// 1. Chargement de la carte
		var sourceMap = parseFile(pathToMap);
		
		// 2. Placer les trésors sur la cases creusables (!= eau) et retourner la CaseMap
		return generateMap(sourceMap);
	}

	/**
	 * Charge une partie d'une map, de la taille définie en paramètre et à une position aléatoire
	 * 
	 * @param pathToMap
	 * @param height
	 * @param length
	 * @return
	 */
	public CaseMap loadMap(String pathToMap, int height, int length) {

		// 1. Chargement de la carte
		var sourceMap = parseFile(pathToMap);
		
		// 2. Extraction du sous-tableau 2D
		var subMap = extractRandomMap(sourceMap, height, length);

	    // 3. Placer les trésors sur la cases creusables (!= eau) et retourner la CaseMap
		return generateMap(subMap);
	}	
	
	private char[][] extractRandomMap(char[][] sourceMap, int height, int length) {
		
		// Calcul du point "(0, 0)" de la sous map par rapport à la grande 
		int rheight = random.randomBetween(0, sourceMap.length-1 - height);
		int rlength = random.randomBetween(0, sourceMap[0].length-1 - length);
		
		// Copie de la sous-map
	    var subMap = new char[height][length];
	    for (int i = 0; i < height; i++) {
	        subMap[i] = Arrays.copyOfRange(sourceMap[rheight+i], rlength, rlength+length);
	    }
	    
	    // Renvoi de la référence de la sous-map
	    return subMap;
	}
	
	/**
	 * Génère un Objet de type CaseMap et principalement sa collection Map<Coordinate, Case>.
	 * 
	 * @param sourceMap
	 * @return
	 */
	public CaseMap generateMap(char[][] sourceMap) {
			
		if(sourceMap.length < 1) throw new IllegalArgumentException("La carte fournie est vide.");
		int mapLength = sourceMap[0].length;
		int mapHeight = sourceMap.length;
		
		for(int i = 0; i < mapLength; ++i)
			for(int j = 0; j < mapHeight; ++j)
				caseMap.addCaseToMap(new Case(charToCaseType(sourceMap[j][i])), new Coordinate(i, j));
		
		return caseMap;
	}
	

	/**
	 * Convertit un charactère en un caseType
	 * 
	 * @param character
	 * @return CaseType
	 */
	private CaseType charToCaseType(char character) {
		switch(character) {
			case 'X':
				return CaseType.WATER;
			case 'S':
				return CaseType.SAND;
			case 'F':
				return CaseType.FOREST;
			case 'P':
				return CaseType.MEADOW;
			case 'R':
				return CaseType.ROCK;
			default :
				return CaseType.VOID;
		}
	}

	private char[][] parseFile(String path){
		return CharArrayFileReader.parseFile(path);
	}
	
}
