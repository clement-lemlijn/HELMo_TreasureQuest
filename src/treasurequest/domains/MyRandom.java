
package treasurequest.domains;

import java.util.Collections;
import java.util.List;

/**
 * @author Clément LEMLIJN
 * 
 * La classe MyRandom implémente l'interface IRandomize et fournit un 
 * générateur de nombres aléatoires.
 * 
 * Le générateur de nombres aléatoires génère des valeurs entières entre 
 * une valeur minimale et une valeur maximale.
 * 
 */
public class MyRandom implements IRandomize {
    
    /**
     * Cette méthode renvoie un entier aléatoire entre les valeurs 
     * minimale et maximale spécifiées (incluses).
     *
     * @param min la valeur entière minimale de l'intervalle
     * @param max la valeur entière maximale de l'intervalle
     * @return un entier aléatoire entre min et max (inclus)
     */
    @Override
    public int randomBetween(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
    
    /**
     * Méthode qui applique simplement le vrai Collections.shuffle
     */
    @Override 
    public void listShuffle(List<Coordinate> list){
    	Collections.shuffle(list);
    }

}
