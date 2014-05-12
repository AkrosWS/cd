package ch.akros.workshop.cd.test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.util.GameLogger;

public class MockGameLogger implements GameLogger {
	private Logger logger = LoggerFactory.getLogger(GameLogger.class);

	private volatile static AtomicBoolean timerTriggered = new AtomicBoolean();

	public MockGameLogger() {
		super();
		logger.info("MockGameLogger created " + timerTriggered.get());
	}

	@Override
	public void newSubscribtion(String anyString) {

	}

	@Override
	public void gameStarts() {

	}

	@Override
	public void playerWon(Player winner) {

	}

	@Override
	public void turn(Player player) {

	}

	@Override
	public void gameNotReady(String explanation) {

	}

	@Override
	public void timerTriggered() {
		timerTriggered.set(true);
	}

	public boolean isTimerTriggered() {
		logger.info("timerTriggered called");
		return timerTriggered.get();
	}

	@Override
	public void score(Map<Player, Integer> scoreMap) {

	}
}
