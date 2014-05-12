package ch.akros.workshop.cd.util;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.akros.workshop.cd.domain.Player;

public class Log4jGameLogger implements GameLogger {
	private Logger logger = LoggerFactory.getLogger(GameLogger.class);

	@Override
	public void newSubscribtion(String playerName) {
		logger.info("Player " + playerName + " is now subscribed");
	}

	@Override
	public void gameStarts() {
		logger.info("Game starts");
	}

	@Override
	public void playerWon(Player winner) {
		logger.info("Winner is " + winner.getName());
	}

	@Override
	public void turn(Player player) {
		logger.info("Player " + player.getName() + " has next turn");

	}

	@Override
	public void gameNotReady(String explanation) {
		logger.info("Game not Ready: " + explanation);
	}

	@Override
	public void timerTriggered() {
		logger.info("Game trigger fired, about to start a new Game");
	}

	@Override
	public void score(Map<Player, Integer> scoreMap) {
		for (Entry<Player, Integer> entry : scoreMap.entrySet()) {
			logger.info("Player:" + entry.getKey() + " Scores:" + entry.getValue());
		}

	}

}
