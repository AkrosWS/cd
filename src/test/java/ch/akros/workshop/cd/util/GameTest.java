package ch.akros.workshop.cd.util;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

//@formatter:off
/**
* 
* 1. DONE Player can subscribe to the next game
* 1a.DONE Log when a new player subscribes
* 2. Start Game
* 2a.DONE Game can not be started before two players have subscribed
* 2b.DONE Log status change on a game.
* 3. Log which player won
* 4. At the end of a game start a new game with all the subscribed once. Player from the finished game are automatically re-subscribed.
* 5. call back player to decide if he wants to toss again.
* 6. Create a GameLogger to separate the logging from the game so that it can be validated
* 7. Run the game
* 7a.DONE First player manages to put all his stick, win.
* 
* 
*/
//@formatter:on
@RunWith(CdiRunner.class)
public class GameTest {

	@Mock
	private Player playerI;

	@Mock
	private Player playerII;

	@Mock
	private GameLogger gameLogger;

	@Mock
	private Dice dice;

	@Mock
	private Board board;

	@InjectMocks
	private Game testee;

	@Test
	public void ensurePlayerCanSubscribeWithoutException() {
		testee.subscribe(playerI);
	}

	@Test
	public void whenPlayerSubscribesThenGameLoggerIsNotified() {
		preparePlayerIMock();

		testee.subscribe(playerI);

		verify(gameLogger).newSubscribtion(playerI.getName());
	}

	@Test(expected = NotEnoughPlayerException.class)
	public void whenPlayerSubscribedStartGameThenNotEnoughPlayerExceptionThrown() throws NotEnoughPlayerException {
		preparePlayerIMock();

		testee.subscribe(playerI);

		testee.run();

	}

	@Test
	public void whenTwoPlayerAndGameStartThenGameLoggerReady() throws NotEnoughPlayerException {
		prepareTwoPlayerGame();

		testee.run();

		verify(gameLogger).gameReady();
	}

	@Test
	public void whenGameStartAndFirstPlayerPutsAllStickThenThisPlayerWins() throws NotEnoughPlayerException {
		prepareTwoPlayerGame();

		when(dice.toss()).thenReturn(6, 5, 4, 3, 2, 1);
		when(board.put(1)).thenReturn(0, 2);

		testee.run();

		verify(board).clear();

		ArgumentCaptor<Player> winningPlayer = ArgumentCaptor.forClass(Player.class);
		verify(gameLogger).playerWon(winningPlayer.capture());
		verify(winningPlayer.getValue(), times(5)).keepPlaying();
		verify(dice, times(6)).toss();
		verify(board).put(6);
		verify(board).put(5);
		verify(board).put(4);
		verify(board).put(3);
		verify(board).put(2);
		verify(board).put(1);

	}

	private void preparePlayerMock(Player player, String playerName) {
		when(player.getName()).thenReturn(playerName);
		when(player.keepPlaying()).thenReturn(true);
	}

	private void preparePlayerIMock() {
		preparePlayerMock(playerI, "Player 1");
	}

	private void preparePlayerIIMock() {
		preparePlayerMock(playerII, "Player 2");
	}

	private void prepareTwoPlayer() {
		preparePlayerIMock();
		preparePlayerIIMock();
	}

	private void subscribeTwoPlayer() {
		testee.subscribe(playerI);
		testee.subscribe(playerII);
	}

	private void prepareTwoPlayerGame() {
		prepareTwoPlayer();
		subscribeTwoPlayer();
	}
}
