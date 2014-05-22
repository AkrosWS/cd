package ch.akros.workshop.cd.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

public class SimpleGame {
	@Inject
	private GameLogger gameLogger;

	@Inject
	private Dice dice;

	@Inject
	private Board board;

	@Inject
	private ScoreCaluculation scroing;

	@Inject
	private Scoreboard scoreboard;

	@Inject
	private volatile PlayerMap players;

	private AtomicBoolean gameRunning = new AtomicBoolean(false);
	private volatile boolean keepGameRunning = true;

	public void subscribe(Player player, String playerName) {
		Integer oldState = players.put(player, Integer.valueOf(6));
		if (oldState == null) {
			gameLogger.newSubscribtion(playerName);
		}
	}

	public void run() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		try {
			if (!gameRunning.compareAndSet(false, true)) {

				throw new GameAlreadyInPlayException();
			}
			if (players.size() < 2) {
				throw new NotEnoughPlayerException("Only " + players.size() + " players registered. At least 2 are needed");
			}

			gameLogger.gameStarts();
			board.clear();
			resetPlayerStick();

			while (keepGameRunning) {
				Set<Entry<Player, Integer>> playerSet = players.entrySet();
				for (Iterator<Entry<Player, Integer>> iter = playerSet.iterator(); iter.hasNext();) {
					Entry<Player, Integer> currentPlayer = iter.next();
					gameLogger.turn(currentPlayer.getKey());
					int toss = dice.toss();
					int sticksReturned = board.put(toss);
					updatePlayerSticks(currentPlayer, sticksReturned);

					while (keepPlaying(currentPlayer, sticksReturned, iter)) {
						gameLogger.turn(currentPlayer.getKey());
						toss = dice.toss();
						sticksReturned = board.put(toss);
						updatePlayerSticks(currentPlayer, sticksReturned);
					}
					if (won(currentPlayer)) {
						gameLogger.playerWon(currentPlayer.getKey());
						Map<Player, Integer> score = scroing.score(players);
						gameLogger.score(score);
						scoreboard.newScore(score);
						gameRunning.set(false);
						return;
					}
				}
			}
		} finally {
			gameRunning.set(false);
		}
	}

	public void stop() {
		keepGameRunning = false;
	}

	private void resetPlayerStick() {
		for (Entry<Player, Integer> entry : players.entrySet()) {
			entry.setValue(6);
		}
	}

	private boolean keepPlaying(Entry<Player, Integer> currentPlayer, int sticksReturned, Iterator<Entry<Player, Integer>> iter) {

		boolean keepPlaying = (!won(currentPlayer)) && (sticksReturned == 0);
		boolean playerDecisionKeepPlaying = false;
		if (keepPlaying) {
			try {
				playerDecisionKeepPlaying = currentPlayer.getKey().keepPlaying();
			} catch (Throwable t) {
				// players.remove(currentPlayer.getKey());
				iter.remove();
				scoreboard.reset(currentPlayer.getKey());
			}
		}
		return keepPlaying && playerDecisionKeepPlaying;
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
