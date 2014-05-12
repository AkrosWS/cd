package ch.akros.workshop.cd.util;

import java.util.Map;

import ch.akros.workshop.cd.domain.Player;

public interface GameLogger {

	void newSubscribtion(String anyString);

	void gameStarts();

	void playerWon(Player winner);

	void turn(Player player);

	void gameNotReady(String explanation);

	void timerTriggered();

	void score(Map<Player, Integer> scoreMap);

}
