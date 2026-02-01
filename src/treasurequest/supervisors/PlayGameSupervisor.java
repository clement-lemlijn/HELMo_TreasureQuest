package treasurequest.supervisors;

import java.util.Map;

import treasurequest.domains.Case;
import treasurequest.domains.CaseMap;
import treasurequest.domains.CaseType;
import treasurequest.domains.Coordinate;
import treasurequest.domains.GameFactory;
import treasurequest.domains.HintType;
import treasurequest.domains.Player;
import treasurequest.domains.TreasureQuestGame;
import treasurequest.domains.TreasureQuestGameFactory;
import treasurequest.supervisors.views.PlayGameView;
import treasurequest.supervisors.views.SpriteType;
import treasurequest.supervisors.views.ViewNames;
import treasurequest.supervisors.views.TileType;
import static java.util.Map.entry;

import java.time.LocalTime;


/**
 * Réagit aux événements utilisateurs de sa vue en mettant à jour une partie en cours et fournit à sa vue les données à afficher.
 * */
public class PlayGameSupervisor {

	private PlayGameView view;
	private final GameFactory gameProvider;
	private TreasureQuestGame game;
	private final Map<HintType, SpriteType> hintToSprite = Map.ofEntries(
				entry(HintType.N, SpriteType.NORTH), entry(HintType.NE, SpriteType.NORTH_EAST), 
				entry(HintType.E, SpriteType.EAST), entry(HintType.SE, SpriteType.SOUTH_EAST),
				entry(HintType.S, SpriteType.SOUTH),entry(HintType.SW, SpriteType.SOUTH_WEST),
				entry(HintType.W, SpriteType.WEST), entry(HintType.NW, SpriteType.NORTH_WEST),
				entry(HintType.NONE, SpriteType.NONE));
	
	/**
	 * Le superviseur de l'écran de jeu reçoit l'instance de factory que 
	 * tous les superviseurs ont en commun afin de fonctionner sur la 
	 * même base
	 * 
	 * @param factory
	 */ 
	public PlayGameSupervisor(GameFactory factory) {
		this.gameProvider = factory;
		this.game = gameProvider.getGame();
	} 

	/**
	 * Définit la vue de ce superviseur à {@code view}.
	 * */
	public void setView(PlayGameView view) {
		if(view == null) {
			return;
		}
		
		this.view = view; 
	}

	/**
	 * Méthode appelée juste avant que la vue de ce superviseur soit affichée à l'écran.
	 * 
	 * Le superviseur affiche les données de départ du jeu (cout de la case active, nombre de trésors, bourse du joueur, etc.).
	 * Il dessine également les cases et indique quelle case est active.
	 * 
	 * Post-Conditions : 
	 *  Player :
	 *  	Le joueur doit avoir en sa possession une bourse. 
	 *  	(qui équivaut à 2x le nombre de trésors cachés dans la carte)
	 *  
	 *  CaseMap : 
	 *  	La map doit contenir au moins une case !
	 *  	Elle doit connaître le nombre de trésors restants
	 *    
	 *  Chaque Case de la carte : 
	 *  	Chaque case doit avoir un type
	 *  	Chaque case doit connaitre son état de creusage (false -> toutes les cases sont non-creusée au début de la partie), 
	 *  	Chaque case doit avoir un coût : celui-ci sera par défaut de 0 et sera augmenté si on cache un trésor dans cette case
	 *  	Chaque case doit savoir si elle est creusable ou pas
	 *  
	 * */
	public void onEnter(String fromView) {
		if (ViewNames.MAIN_MENU.equals(fromView)) {
			view.clearTiles();
			
			var game = gameProvider.getGame();
			var caseMap = game.getCaseMap();
			var player = game.getPlayer();	
			
			// 1. Affichage du compteur de trésors
			displayNbTreasures(game.getRemainingTreasures());
			
			// 2. Affichage de la bourse du joueur
			displayPlayerData(player);
			
			// 3. Données de la case active
			displayActiveCaseData(game, caseMap);
			
			// 4. Affichage des Cases
			
			for(Coordinate coord : caseMap) {
				Case tile = caseMap.getCase(coord);
				view.setTileAt(caseTypeToTileType(tile.getType()), coord.getRow(), coord.getCol());
			}
		}
	}
	
	private void displayNbTreasures(int nbTreasures) {		
		view.setTreasuresCount("Trésors restants : " + nbTreasures);
	}
	
	private void displayPlayerData(Player player) {		
		view.setPlayerCoins("Bourse du joueur : " + player.getCoins());	
	}
	
	/**
	 * Cette méthode affiche toutes les données relatives à la case "active"
	 * 
	 * @param game
	 * @param map
	 */
	private void displayActiveCaseData(TreasureQuestGame game, CaseMap caseMap) {
		Coordinate cos = game.getActiveCaseCoordinate();
		Case tile = caseMap.getCase(cos);
		CaseType tile_type = tile != null ? tile.getType() : CaseType.VOID;
		
		// 3.1 Affichage de la case active
		view.setActiveCase(cos.getRow(), cos.getCol());
		
		// 3.2 Affichage du type de la case active 
		view.setActiveCaseType("Type de la case action : " + (tile_type != null ? tile_type : "Vide"));
		
		// 3.3 Affichage du coût de la case active
		view.setActiveCaseCost("Coût de la case active : " + (tile != null ? tile.getCaseCosts() : "La case n'est pas creusable"));
	}

	private void displaySprite(SpriteType spriteType, Coordinate cos) {
		view.setSpriteAt(spriteType, cos.getRow(), cos.getCol());
	}
	
	/**
	 * Convertis l'énumération CaseType en TileType
	 * 
	 * @param caseType 
	 * @return TileType
	 */
	private TileType caseTypeToTileType(CaseType caseType) {
		switch (caseType) {
			case WATER :
				return TileType.WATER;
			case SAND :
				return TileType.SAND;
			case MEADOW:
				return TileType.GRASSLAND;
			case FOREST:
				return TileType.FOREST;
			case ROCK :
				return TileType.ROCK;
			default :
				return TileType.UNKNOWN;	
		}
	}
	
	private SpriteType hintTypeToSpriteType(HintType hintType) {
		return hintToSprite.get(hintType);
	}
	
	/**
	 * Tente de déplacer la case active de {@code deltaRow} lignes et de {@code deltaRow} colonnes.
	 * 
	 * Cette méthode doit vérifier que les coordonnées calculées correspondent bien à une case du terrain.
	 * */
	public void onMove(int deltaRow, int deltaCol) {		
		
		// Récupérer le jeu
		var game = gameProvider.getGame();
		
		// Déplacer la case active
		game.moveActiveCase(deltaRow, deltaCol);
		
		// Afficher les modifications
		displayActiveCaseData(game, game.getCaseMap());
	}

	/**
	 * Cette méthode réagit à la demande de creusage d'une case.
	 * 
	 * Tente de creuser la case active et met à jour l'affichage en conséquence.
	 * 
	 * Ne fais rien si la case active a déjà été creusee ou si elle est de type Eau.
	 * 
	 * Post-Conditions du gain de creusage : 
	 * 		Le gain peut être négatif dans le cas où la case creusée ne contient pas de trésor.
	 * 		Le gain ne pourra jamais être supérieur à 19, étant donné que selon les règles du jeu, 
	 * 		les coûts de creusage varient de 1 à 5 (inclus, pour les cases non-creusables, il est de 0 forcément) 
	 * 		et que les valeurs des trésors varient de 10 à 20 pièces (incluses). Dans le meilleur des cas
	 * 		le joueur gagnera donc 19 pièces et dans le pire des cas il en perdra 5 (si il creuse une roche sans trésor).
	 * 
	 * 		-> la bourse du joueur sera modifiée en fonction de ce gain
	 * 		
	 * */ 
	public void onDig() {
		
		// Récupérer le jeu
		var game = gameProvider.getGame();
		var caseMap = game.getCaseMap();
		var player = game.getPlayer();
		var gameRecord = game.getGameRecord();
		
		// Déterminer s'il est possible de creuser et si oui creuser
		boolean dig = game.dig();
		
		// Déterminer s'il y avait un trésor
		boolean isTreasure = game.isTreasure();
		
		// S'il y avait un trésor et que l'on peut creuser: 
		if(isTreasure && dig) {
			discoverTreasure(game);
		}
					
		// Affichage sprites
		Coordinate cos = game.getActiveCaseCoordinate();
				
		if(dig && !isTreasure) {
			displayHint(cos, caseMap, game);
		} 
			
		// Si la case comprenait un trésor, afficher le sprite "Treasure"
		if(dig && isTreasure) 
			displaySprite(SpriteType.TREASURE, cos);
		
		// Si la partie est finie, rediriger vers le GameOverScreen
		if(game.checkGameEnd()) {
			
			// Enregistrer le temps de jeu
			storePlayTime();
			
			// Enregistrer le type de case que le joueur a le plus creusé
			player.setMoreDiggedCaseType(gameRecord.getMoreDiggedCaseType(caseMap));
			
			// Aller à l'écran de fin
			view.goTo(ViewNames.GAME_OVER);
		}
	}

	private void storePlayTime() {
		var game = gameProvider.getGame();
		var player = game.getPlayer();
		player.storeGameTime(game.getStartTime(), LocalTime.now());
	}
	
	private void discoverTreasure(TreasureQuestGame game) {
		
		// Diminuer le compteur de trésors
		game.withDrawNbTreasures();
		
		// Afficher le nombre de trésors restants à trouver
		displayNbTreasures(game.getRemainingTreasures());
		
		// Afficher la bourse du joueur
		displayPlayerData(game.getPlayer());
	}
	
	private void displayHint(Coordinate cos, CaseMap caseMap, TreasureQuestGame game) {
		
		// Calcul de l'indice de la case active
		HintType hintType = caseMap.getCaseHint(cos);
		
		// Si la case est creusée et ne contenait pas de trésor
		if(hintType == HintType.NONE)
			
			// Afficher le sprite "Dug" à l'emplacement creusé
			displaySprite(SpriteType.DUG, cos);
		else 
			
			// Afficher l'indice
			displaySprite(hintTypeToSpriteType(hintType), cos);
		
		// Afficher la bourse du joueur
		displayPlayerData(game.getPlayer());
		
	}
	
	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite interrompre la partie.
	 * 
	 * Ce superviseur demande à sa vue de naviguer vers le menu principal.
	 * */
	public void onStop() {
		//TODOok : naviguer vers le menu principal
		storePlayTime();
		view.goTo(ViewNames.MAIN_MENU);
	}

}
