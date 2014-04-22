package ch.akros.workshop.cd.util;

public interface GameLogger {

	void newSubscribtion(String anyString);

	void gameStarts();

	void playerWon(Player winner);

	void turn(Player player);

	void gameNotReady(String explanation);

	void timerTriggered();

}
