package treasurequest.domains;

import java.util.List;

/**
 * @author cleme
 *
 * L'interface IRandomize définit une méthode pour générer un entier aléatoire dans une plage spécifiée.
 *
 */
public interface IRandomize {
	/**
	 * Returns a randomValue between min and max
	 * 
	 * @param min
	 * @param max
	 * @return A random value 
	 */
	public int randomBetween(int min, int max);

	
	public void listShuffle(List<Coordinate> list);
	

}
