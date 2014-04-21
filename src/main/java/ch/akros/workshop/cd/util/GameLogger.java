package ch.akros.workshop.cd.util;

public interface GameLogger {

	void newSubscribtion(String anyString);

	void gameReady();

	void playerWon(Player key);

	void turn(Player capture);

	void gameNotReady();

}
