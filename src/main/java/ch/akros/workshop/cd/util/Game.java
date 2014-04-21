package ch.akros.workshop.cd.util;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

public class Game {
	@Inject
	private GameLogger gameLogger;

	private Set<Player> players = new HashSet<Player>();

	public void subscribe(Player player) {
		boolean isNew = players.add(player);
		if (isNew) {
			gameLogger.newSubscribtion(player.getName());
		}
	}

	public void start() throws NotEnoughPlayerException {
		if (players.size() < 2) {
			throw new NotEnoughPlayerException("Only " + players.size() + " players registered. At least 2 are needed");
		}

		gameLogger.gameReady();
	}

}
