package treasurequest.supervisors;

import java.util.List;

import treasurequest.domains.CaseMapFactory;
import treasurequest.domains.FakeRandom;
import treasurequest.domains.GameFactory;
import treasurequest.domains.MyRandom;
import treasurequest.domains.TreasureQuestGameFactory;
import treasurequest.supervisors.views.*;


/**
 * Fournit les données qu'une vue représenter le menu principal doit afficher.
 * 
 * Réagit aux événements de haut niveau signalé par sa vue.
 * */
public class MainMenuSupervisor {

	private static final int NEWGAME_ITEM = 0;
	private static final int RANDOMGAME_ITEM = 1;
	private static final int EXIT_ITEM = 2;
	
//	private static final String PATH_TO_MAP = "resources/maps/big-map.txt";
//	private static final String PATH_TO_MAP = "resources/maps/map-sample-test.txt";
//	private static final String PATH_TO_MAP = "resources/maps/map-sample-2.txt";
	private static final String PATH_TO_MAP = "resources/maps/map-perso-test.txt";
	//	private static final String PATH_TO_MAP = "resources/maps/huge-map.txt";
	
	private MainMenuView view;
	private final GameFactory gameProvider;

	/**
	 * Le superviseur du main menu reçoit l'instance de factory que 
	 * tous les superviseurs ont en commun afin de fonctionner sur la 
	 * même base
	 * 
	 * @param factory
	 */
	public MainMenuSupervisor(GameFactory factory) {
		this.gameProvider = factory;
	}
	
	public void setView(MainMenuView view) {
		if(view == null) {
			return;
		}
		
		this.view = view;
		this.view.setItems(List.of("Nouvelle partie", "Partie aléatoire", "Quitter"));
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur sélectionne un item.
	 * 
	 * @param itemPos la position de l'item sélectionné. {@code item in [0; items.length[}
	 * */
	public void onItemSelected(int itemPos) {
		var random = new MyRandom();
//		var random = new FakeRandom();
		var cmFactory = new CaseMapFactory(random);
		
		if(NEWGAME_ITEM == itemPos) {
			gameProvider.newGame(random, cmFactory.loadMap(PATH_TO_MAP));
			view.goTo(ViewNames.PLAY_GAME);
		}
		
		if(RANDOMGAME_ITEM == itemPos) {
			gameProvider.newGame(random, 
					cmFactory.loadMap("resources/maps/big-map.txt", 16, 16));
			view.goTo(ViewNames.PLAY_GAME);
		}
		
		if(EXIT_ITEM == itemPos) {
			view.confirmExit();
		}
		
		//TODOok : Démarrer une nouvelle partie
		//TODOok : naviguer vers l'écran de jeu
	}

}
