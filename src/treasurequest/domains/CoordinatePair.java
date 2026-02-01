package treasurequest.domains;

import java.util.Objects;

/**
 * Cette classe permet de coupler une coordonnée et une distance.
 * 
 * @author cleme
 *
 */
public class CoordinatePair implements Comparable<CoordinatePair>{
	
//	private Coordinate centerCo
	
	// Coordonnées "globales d'un trésor"
	private Coordinate coordinate;
	
	private double distance;
	
	
	/**
	 * Constructeur de coordinate pair nécessitant uen coordonnée et une distance
	 * 
	 * @param coordinate
	 * @param distance
	 */
	public CoordinatePair(Coordinate coordinate, double distance) {
		this.coordinate = coordinate;
		this.distance = distance;
	}
	
	/**
	 * Constructeur de coordinatePair nécessitant une coordonnée et initialisant la distance à Integer.MAX_VALUE afin de ne pas interférer avec les autres distanes en cas d'erreur
	 * 
	 * @param coordinate
	 */
	public CoordinatePair(Coordinate coordinate) {
		this(coordinate, Integer.MAX_VALUE);
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}



	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}



	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}


	@Override
	public int hashCode() {
		return Objects.hash(coordinate, distance);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoordinatePair other = (CoordinatePair) obj;
		return Objects.equals(coordinate, other.coordinate)
				&& Double.doubleToLongBits(distance) == Double.doubleToLongBits(other.distance);
	}

	/**
	 * Redéfinission de compareTo, de manière à ce que l'on puisse trier une liste d'instances de cet objet par ordre croissant de ses "distances"
	 */
	@Override
	public int compareTo(CoordinatePair other) {
		// TODO Auto-generated method stub
		return Double.compare(this.distance, other.distance);
	}

	

	
}
