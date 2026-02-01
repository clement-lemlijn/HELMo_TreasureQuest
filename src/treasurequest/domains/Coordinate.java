package treasurequest.domains;

import java.util.Objects;


/**
 * @author clement LEMLIJN
 * 
 * Cette classe sert à représenter une coordonnée à 2 dimensions  
 * (contenant une abscisse et une ordonnée).
 * 
 * Elle permet de récupérer les composantes de cette coordonnée et
 * de les modifier librement, mais encore de gérer un déplacement 
 * et de modifier la coordonnée en conséquence. Elle permet également 
 * de calculer une distance entre 2 coordonnées avec une formule classique.
 * Elel permet également de passer d'une coordonnée globale à une locale 
 * (et inversément ) en se basant sur un point central.
 * 
 * 
 * Responsabilités : 
 * 		Associe une ligne à une colonne
 * 		Calculer les coordonnées avoisinantes
 * 		Calculer la disance entre 2 coordonnées
 *
 */
public class Coordinate {
	private int col;
	private int row;
	
	/**
	 * Constructeur de Coordinate, prenant 2 arguments, l'ordonnée et l'abscisse du point
	 * 
	 * Aucune restriction n'est faite, tout entier est accepté
	 * 
	 * @param col
	 * @param row
	 */
	public Coordinate(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * @param col
	 * @param row
	 */
	public void set(int col, int row) {
		setCol(col);
		setRow(row);
	}
	/**
	 * Cette méthode permet d'actualiser une coordonnée (en pratique, 
	 * elle sera toujours utilisée pour déplacer la case active). 
	 * Elle accepte les valeurs négatives (pour se déplacer vers le haut/gauche)
	 * 
	 * @param col
	 * @param row
	 */
	public void move(int col, int row) {
		this.col += col;
		this.row += row;
	}
	
	/**
	 * Caclcule la distance entre un point et 0, 0.
	 *
	 * @return distance entre les 2 coordonnées
	 */
	public double distanceCalc() {
		return distanceCalc(new Coordinate(0, 0));
	}
	
	/**
	 * Caclcule la distance entre deux points.
	 * 
	 * @param otherCos
	 * @return distance entre les 2 coordonnées
	 */
	public double distanceCalc(Coordinate otherCos) {
		return Math.sqrt( Math.pow((this.getRow()-otherCos.getRow()), 2) + 
							Math.pow((this.getCol()-otherCos.getCol()), 2));
	}
	
	/**
	 * 
	 * Tranforme une coordonnée globale en une coordonnée locale sur base d'un point central
	 * 
	 * ex : vous êtes en 0, 0. 
	 * Vous prenez comme référentiel la case 2, 2
	 * Votre coordonnée "locale" est -2, -2
	 * 
	 * @param globalCo
	 * @param centerCo
	 * @return localCoordinate
	 */
	public Coordinate toLocalCo(Coordinate centerCo) {
		int col = this.getCol() - centerCo.getCol();
		int row = this.getRow() - centerCo.getRow();
		return new Coordinate(col, row);
	}
	
	/**
	 * Transforme une coordonnée locale en une coordonnée globale sur base d'un point central.
	 * 
	 * @param centerCo
	 * @param localCo
	 * @return globalCoordinate
	 */
	public Coordinate toGlobalCo(Coordinate centerCo) {
		int col = this.getCol() + centerCo.getCol();
		int row = this.getRow() + centerCo.getRow();
		return new Coordinate(col, row);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(col, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return col == other.col && row == other.row;
	}
	
	/**
	 * Redéfinission de toString afin de faciliter les tests et d'afficher facilement une coordonnée en string.
	 */
	@Override
	public String toString() {
	    return "(" + col + ", " + row + ")";
	}

}
