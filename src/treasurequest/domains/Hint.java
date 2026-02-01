package treasurequest.domains;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe aide à "calculer" l'indice à stocker.
 * 
 * @author cleme
 *
 */
public class Hint {
	final private Map<Coordinate, HintType> hintMap = new HashMap<>();
	
	/**
	 * Le constructeur ne reçoit aucun arguement, il construit simplement une hintMap qui contient les 8 positions cardianles et une "NONE"
	 */
	public Hint() {
		fillMap();
	}
	
	public Map<Coordinate, HintType> getHintMap(){
		return Collections.unmodifiableMap(hintMap);
	}
	
	private void fillMap() {
//		hintMap.put(new Coordinate(0, 0), HintType.NONE);
//		hintMap.put(new Coordinate(-1, 0), HintType.N);
//		hintMap.put(new Coordinate(-1, 1), HintType.NE);
//		hintMap.put(new Coordinate(0, 1), HintType.E);
//		hintMap.put(new Coordinate(1, 1), HintType.SE);
//		hintMap.put(new Coordinate(1, 0), HintType.S);
//		hintMap.put(new Coordinate(1, -1), HintType.SW);
//		hintMap.put(new Coordinate(0, -1), HintType.W);
//		hintMap.put(new Coordinate(-1, -1), HintType.NW);
		
		hintMap.put(new Coordinate(0, 0), HintType.NONE);
		hintMap.put(new Coordinate(0, -1), HintType.S);
		hintMap.put(new Coordinate(1, -1), HintType.SW);
		hintMap.put(new Coordinate(1, 0), HintType.W);
		hintMap.put(new Coordinate(1, 1), HintType.NW);
		hintMap.put(new Coordinate(0, 1), HintType.N);
		hintMap.put(new Coordinate(-1, 1), HintType.NE);
		hintMap.put(new Coordinate(-1, 0), HintType.E);
		hintMap.put(new Coordinate(-1, -1), HintType.SE);
	}
	
	/**
	 * 
	 * localCo.setCol(localCo.getCol()/ (localCo.getCol() != 0 ? Math.abs(localCo.getCol()) : 1));
	 * localCo.setRow(localCo.getRow()/ (localCo.getRow() != 0 ? Math.abs(localCo.getRow()) : 1));
	 *  -> Simples opération artihmétiques, O(1)
	 *  
	 * hintMap.get(localCo) : le get(i) sur une hashmap est en O(1)  
	 * 
	 * @param localCo
	 * @return
	 */
	public HintType getHint(Coordinate localCo) {
		localCo.setCol(localCo.getCol()/ (localCo.getCol() != 0 ? Math.abs(localCo.getCol()) : 1));
		localCo.setRow(localCo.getRow()/ (localCo.getRow() != 0 ? Math.abs(localCo.getRow()) : 1));
		return hintMap.get(localCo);
	}
	
}
