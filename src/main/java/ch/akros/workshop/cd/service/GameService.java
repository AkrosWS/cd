package ch.akros.workshop.cd.service;

import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.akros.workshop.cd.domain.Game;
import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;
import ch.akros.workshop.cd.util.GameLogger;
import ch.akros.workshop.cd.util.SimpleGame;

@Remote
@Startup
@Singleton
public class GameService implements Game {

	@Inject
	SimpleGame game;

	@Inject
	GameLogger gameLogger;

	@Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
	public void timer() {
		try {
			gameLogger.timerTriggered();
			game.run();
		} catch (NotEnoughPlayerException e) {
			gameLogger.gameNotReady("Not enough player");
		} catch (GameAlreadyInPlayException e) {
			gameLogger.gameNotReady("Game already running");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void subscribe(String jndiName, String playerName) {
		Player player;
		try {
			player = (Player) new InitialContext().lookup(jndiName);
			game.subscribe(player, playerName);
		} catch (NamingException e) {
			System.out.println(e);
		}

	}

	@Override
	public void run() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		game.run();

	}

}
