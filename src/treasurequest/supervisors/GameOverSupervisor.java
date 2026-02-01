package treasurequest.supervisors;

import java.time.LocalTime;

import treasurequest.domains.GameFactory;
import treasurequest.domains.Player;
import treasurequest.domains.TreasureQuestGameFactory;
import treasurequest.supervisors.views.GameOverView;
import treasurequest.supervisors.views.ResultType;
import treasurequest.supervisors.views.ViewNames;

/**
 * Réagit aux événements de haut-niveau de sa vue et lui fournit des données à afficher.
 * 
 * !!!!!
 * La CTT de l'algorithme de propagation se trouve dans la javadoc de la classe GameRecord. 
 * !!!!!
 * 
 * 
 * Une partie peut se finir si :
 * 		
 * 
 * 		Tous les trésors ont été découverts par le joueur, la partie est alors gagnée.
 * 		Dans mon cas, je vérifie que le nombre de trésors restants (stocké dans tqGame) soit égal à 0.
 * 		
 * 		Le joueur n'a plus assez d'argent pour creuser une case de la partie,
 * 			ex : il lui reste 1 "coins" dans sa bourse et il n'y a plus de case sable qui n'est pas creusée dans la partie.
 * 		-> Il a alors fait banqueroute.
 * 		Pour celà, il faut parcourir toutes les cases de la map, et si je tombe sur une case non creusée que le joueur 
 * 		peut se permettre de creuser, je retourne true, sinon je retourne false
 * 		Une amélioration intéressante serait de ne vérifier cette condition que si le joueur a une bourse inférieure à 5, 
 * 		car dans notre cas le prix maximal d'une case est de 5Coins. 
 * 		Il est également impossible qu'un joueur ai creusé TOUTES les cases d'une partie, on ne lui donne pas assez 
 * 		d'argent et il n'en gagne pas assez non plus en trouvant tous les trésors.
 * 
 * 		Le joueur décide d'abandonner la partie et appuye sur escape.
 * 		Dans ce cas, les gains et les pertes du joueur sont mises à jour mais son profil ne sera pas adapté (Touriste, fermier, ...)
 * 
 * */
public class GameOverSupervisor {
	private GameOverView view;
	private GameFactory gameProvider;

	/**
	 * Le superviseur de l'écran game over reçoit l'instance de factory que 
	 * tous les superviseurs ont en commun afin de fonctionner sur la 
	 * même base
	 * 
	 * @param factory
	 */
	public GameOverSupervisor(GameFactory factory) {
		this.gameProvider = factory;
	}

	public void setView(GameOverView view) {
		if(view == null) {
			return;
		}
		
		this.view = view;
	}
	
	/**
	 * Méthode appelée par la vue quand une navigation vers elle est en cours.
	 * 
	 * @param fromView la vue d'origine. Correspond a priori à une constante définie dans ViewNames.
	 * */
	public void onEnter(String fromView) {
		//TODO : générer les résultats et les afficher.
		var game = gameProvider.getGame();
		var player = game.getPlayer();
		
		view.clearPanels();
		
		int earnings = player.getEarnings();
		view.addPanel(ResultType.GAIN, "Gains du joueur : " + earnings);
		
		int spendings = player.getSpendings();
		view.addPanel(ResultType.LOSS, "Dépenses du joueur : " + spendings);
		
		LocalTime localTime = player.getPlayTime();
		view.addPanel(ResultType.DURATION, 
				String.format("Temps joué : %02d:%02d ", localTime.getMinute(), localTime.getSecond()));
		
		view.addPanel(getPlayerStatut(player), "");
	}
	
	private ResultType getPlayerStatut(Player player) {
		switch (player.getMoreDiggedCaseType()) {
		case SAND:
			return ResultType.TOURIST;
		case MEADOW:
			return ResultType.FARMER;
		case FOREST:
			return ResultType.LUMBERJACK;
		case ROCK: 
			return ResultType.MINER;
		default:
			throw new IllegalArgumentException("Ya un problème... ");
		}
	}
	
	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite retourner au menu principal.
	 * */
	public void onGoToMain() {
		view.goTo(ViewNames.MAIN_MENU);
	}
}
