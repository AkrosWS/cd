package ch.akros.workshop.cd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jGameLogger implements GameLogger {
	Logger logger = LoggerFactory.getLogger(GameLogger.class);

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

}
