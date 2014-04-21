package ch.akros.workshop.cd.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Schedule;
import javax.inject.Inject;

import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

public class Game {
	@Inject
	private GameLogger gameLogger;

	@Inject
	private Dice dice;

	@Inject
	private Board board;

	private Map<Player, Integer> players = new HashMap<Player, Integer>();

	public void subscribe(Player player) {
		Integer oldState = players.put(player, Integer.valueOf(6));
		if (oldState == null) {
			gameLogger.newSubscribtion(player.getName());
		}
	}

	public void run() throws NotEnoughPlayerException {
		if (players.size() < 2) {
			throw new NotEnoughPlayerException("Only " + players.size() + " players registered. At least 2 are needed");
		}

		gameLogger.gameReady();
		board.clear();

		while (true) {
			for (Entry<Player, Integer> currentPlayer : players.entrySet()) {
				gameLogger.turn(currentPlayer.getKey());
				int toss = dice.toss();
				int sticksReturned = board.put(toss);
				updatePlayerSticks(currentPlayer, sticksReturned);

				while ((!won(currentPlayer)) && (sticksReturned == 0) && (currentPlayer.getKey().keepPlaying())) {
					gameLogger.turn(currentPlayer.getKey());
					toss = dice.toss();
					sticksReturned = board.put(toss);
					updatePlayerSticks(currentPlayer, sticksReturned);
				}
				if (won(currentPlayer)) {
					gameLogger.playerWon(currentPlayer.getKey());
					return;
				}
			}
		}
	}

	@Schedule(second = "*/5")
	public void timer() {
		try {
			run();
		} catch (NotEnoughPlayerException e) {
			gameLogger.gameNotReady();
		}
	}

	private boolean won(Entry<Player, Integer> currentPlayer) {
		return currentPlayer.getValue().equals(0);
	}

	private void updatePlayerSticks(Entry<Player, Integer> currentPlayer, int sticksReturned) {
		if (sticksReturned == 0) {
			currentPlayer.setValue(currentPlayer.getValue() - 1);
		} else {
			currentPlayer.setValue(currentPlayer.getValue() + 1);
		}
	}
}
