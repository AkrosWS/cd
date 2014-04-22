package ch.akros.workshop.cd.service;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;
import ch.akros.workshop.cd.util.Game;
import ch.akros.workshop.cd.util.GameLogger;

@Stateless
public class GameService {

	@Inject
	Game game;

	@Inject
	GameLogger gameLogger;

	@Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
	public void timer() {
		try {
			System.out.println("timer");
			game.run();
		} catch (NotEnoughPlayerException e) {
			gameLogger.gameNotReady("Not enough player");
		} catch (GameAlreadyInPlayException e) {
			gameLogger.gameNotReady("Game already running");
		}
	}

}
