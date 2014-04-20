package ch.akros.workshop.cd.util;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

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

}
