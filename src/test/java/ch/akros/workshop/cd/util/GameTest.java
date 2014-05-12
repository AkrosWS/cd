package ch.akros.workshop.cd.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

//@formatter:off
/**
* 
* 1. DONE Player can subscribe to the next game
* 1a.DONE Log when a new player subscribes
* 2. Start Game
* 2a.DONE Game can not be started before two players have subscribed
* 2b.DONE Log status change on a game.
* 2c.DONE Start Game every five second. 
* 3. DONE Log which player won
* 4. DONE At the end of a game start a new game with all the subscribed once. Player from the finished game are automatically re-subscribed.(Not a good Idea, new Game shall starts ever 5s)
* 5. DONE call back player to decide if he wants to toss again.
* 6. DONECreate a GameLogger to separate the logging from the game so that it can be validated
* 7. DONE Run the game
* 7a.DONE First player manages to put all his stick, win.
* 7b.DONE Second player manages to put all his stick, win.
* 7c.DONE First player wins in second round.
* 8. DONE Ensure not two games can run at the same time.
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

	@Mock
	private ScoreCaluculation scoring;

	@InjectMocks
	private SimpleGame testee;

	@Test
	public void ensurePlayerCanSubscribeWithoutException() {
		testee.subscribe(playerI, playerI.getName());
	}

	@Test
	public void whenPlayerSubscribesThenGameLoggerIsNotified() {
		preparePlayerIMock();

		testee.subscribe(playerI, playerI.getName());

		verify(gameLogger).newSubscribtion(playerI.getName());
	}

	@Test(expected = NotEnoughPlayerException.class)
	public void whenPlayerSubscribedStartGameThenNotEnoughPlayerExceptionThrown() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		preparePlayerIMock();

		testee.subscribe(playerI, playerI.getName());

		testee.run();

	}

	@Test
	public void whenTwoPlayerAndGameStartThenGameLoggerReady() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		prepareTwoPlayerGame();

		testee.run();

		verify(gameLogger).gameStarts();
	}

	@Test
	public void whenGameStartAndFirstPlayerPutsAllStickThenThisPlayerWins() throws NotEnoughPlayerException, GameAlreadyInPlayException {
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

	@Test
	public void whenGameStartAndSecondPlayerPutsAllStickThenThisPlayerWins() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		prepareTwoPlayerGame();

		when(dice.toss()).thenReturn(1, 1, 6);
		when(board.put(1)).thenReturn(0, 2);

		testee.run();

		verify(board).clear();

		ArgumentCaptor<Player> winningPlayer = ArgumentCaptor.forClass(Player.class);
		verify(gameLogger).playerWon(winningPlayer.capture());
		verify(winningPlayer.getValue(), times(5)).keepPlaying();
		verify(dice, times(8)).toss();
		ArgumentCaptor<Player> playersTurn = ArgumentCaptor.forClass(Player.class);
		verify(gameLogger, atLeastOnce()).turn(playersTurn.capture());

		List<Player> playersTurnList = playersTurn.getAllValues();
		assertEquals("First and second turn are not played be the same player", playersTurnList.get(0), playersTurnList.get(1));
		assertTrue("The next six turns are not played by the same player or not the winning player",
				((winningPlayer.getValue().equals(playersTurnList.get(2))) && (winningPlayer.getValue().equals(playersTurnList.get(3)))
						&& (winningPlayer.getValue().equals(playersTurnList.get(4))) && (winningPlayer.getValue().equals(playersTurnList.get(5)))
						&& (winningPlayer.getValue().equals(playersTurnList.get(6))) && (winningPlayer.getValue().equals(playersTurnList.get(7)))));
	}

	@Test
	public void whenGameStartAndFirstPlayerPutsAllStickInSecondRoundThenThisPlayerWins() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		prepareTwoPlayerGame();

		when(dice.toss()).thenReturn(1, 1, 2, 2, 6);
		when(board.put(1)).thenReturn(0, 2);
		when(board.put(2)).thenReturn(0, 2);

		testee.run();

		verify(board).clear();

		ArgumentCaptor<Player> winningPlayer = ArgumentCaptor.forClass(Player.class);
		verify(gameLogger).playerWon(winningPlayer.capture());
		verify(winningPlayer.getValue(), times(6)).keepPlaying();
		verify(dice, times(10)).toss();
		ArgumentCaptor<Player> playersTurn = ArgumentCaptor.forClass(Player.class);
		verify(gameLogger, atLeastOnce()).turn(playersTurn.capture());

		List<Player> playersTurnList = playersTurn.getAllValues();
		assertEquals("First turn are not played be the winning player", winningPlayer.getValue(), playersTurnList.get(0));

	}

	@Test(expected = GameAlreadyInPlayException.class)
	public void whenTwoGamesStartedThenExceptionIsThown() throws NotEnoughPlayerException, GameAlreadyInPlayException, InterruptedException {
		prepareTwoPlayerGame();
		when(dice.toss()).thenReturn(1);
		when(board.put(1)).thenReturn(0, 2);

		Thread myThread = new Thread(new MyTestRunner(testee));
		myThread.start();
		Thread.sleep(1000);// ensure that first thread starts the game
		testee.run();

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
		testee.subscribe(playerI, playerI.getName());
		testee.subscribe(playerII, playerI.getName());
	}

	private void prepareTwoPlayerGame() {
		prepareTwoPlayer();
		subscribeTwoPlayer();
	}

	private class MyTestRunner implements Runnable {
		private SimpleGame testee;

		public MyTestRunner(SimpleGame testee) {
			super();
			this.testee = testee;
		}

		@Override
		public void run() {
			try {
				testee.run();
			} catch (NotEnoughPlayerException e) {
				e.printStackTrace();
			} catch (GameAlreadyInPlayException e) {
				e.printStackTrace();
			}

		}

	}
}
