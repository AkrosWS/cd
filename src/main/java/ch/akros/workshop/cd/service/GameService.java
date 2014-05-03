package ch.akros.workshop.cd.service;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.akros.workshop.cd.domain.Game;
import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;
import ch.akros.workshop.cd.util.GameLogger;
import ch.akros.workshop.cd.util.SimpleGame;

@Remote
@LocalBean
@Stateless
@Startup
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
	public void subscribe(Player player) {
		game.subscribe(player);

	}

	@Override
	public void run() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		game.run();

	}

}
