package ch.akros.workshop.cd.service;

import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;
import ch.akros.workshop.cd.util.GameLogger;
import ch.akros.workshop.cd.util.SimpleGame;

@Remote
@Stateless
@Startup
public class GameService {

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

}
