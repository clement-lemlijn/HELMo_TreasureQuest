package treasurequest.domains;

import java.util.Collections;
import java.util.List;

/**
 * Classe de random uniquement utile pour les tests unitaires.
 * 
 * @author cleme
 *
 */
public class FakeRandom implements IRandomize{
	    
		private int[] fakeRandomList = {12, 18, 15, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,};
		private final Coordinate[] fakeRandomCoords;
		private int count;
	
		/**
		 * Constructeur de FakeRandom qui ne nécessite pas de paramètre et qui définit tous les Tableaux à 0.
		 */
		public FakeRandom() {
			this(new int[0], new Coordinate[0]);
		}
		
		/**
		 * Constructeur de FakeRandom qui demande une liste de coordonnées en paramètre afin de tromper le Collections.shuffle().
		 * Initialise le tableau de int à 0.
		 * 
		 * @param fakeRandomCoords
		 */
		public FakeRandom(Coordinate[] fakeRandomCoords) {
			this(new int[0], fakeRandomCoords);
		}
		
		/**
		 * Constructeur de FakeRandom qui demande une liste d'entiers, afin de tromper la méthode randomBetween qui permet 
		 * principalement de calculer la valeur d'un trésor.
		 * Initialise la liste de coordonnée à 0.
		 * 
		 * @param fakeRandomList
		 */
		public FakeRandom(int[] fakeRandomList) {
			this(fakeRandomList, new Coordinate[0]);
		}
		
		/**
		 * Constructeur de FakeRandom qui demande une liste d'entiers et une liste de Coordonnées prédéfinies.
		 * 
		 * @param fakeRandomList
		 * @param fakeRandomCoords
		 */
		public FakeRandom(int[] fakeRandomList, Coordinate[] fakeRandomCoords) {
			this.fakeRandomList = fakeRandomList;
			this.fakeRandomCoords = fakeRandomCoords;
			this.count = 0;
		}

		/**
	     * Cette méthode va renvoyer un entier pseudo aléatoire 
	     *
	     * @param min la valeur entière minimale de l'intervalle
	     * @param max la valeur entière maximale de l'intervalle
	     * @return un entier aléatoire entre min et max (inclus)
	     */
	
	    @Override
	    public int randomBetween(int min, int max) {
	    	count ++;
	        return fakeRandomList.length >= count ? 
	        		fakeRandomList[count] :
	        		(int) (Math.random() * (max - min + 1)) + min;
	    }

		/**
		 *	Méthode qui trompe le Collections.shuffle
		 *
		 */
		@Override
		public void listShuffle(List<Coordinate> list) {
			Collections.shuffle(list);
			list.add(0, new Coordinate(0, 4));
			list.add(1, new Coordinate(2, 0));
			list.add(2, new Coordinate(4, 4));
			list.add(3, new Coordinate(4, 6));
			
//			System.out.println("liste des trésors : ");
//			for(Coordinate coord : list)
//				System.out.print("(" + coord.getRow() + ", " + coord.getCol() + " ) " + " | ");
		}

}
