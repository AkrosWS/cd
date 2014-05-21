package ch.akros.workshop.cd.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.domain.SmartPlayer;
import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

//@formatter:off
/**
* 
* 1. DONE Call SmartPlayers back with the board.
* 2. DONE Ensure game board can not be changed.
* 
* 
*/
//@formatter:on

@RunWith(CdiRunner.class)
public class SmartPlayerTest {

	@Mock
	private Player playerI;

	@Mock
	private SmartPlayer playerII;

	@Mock
	private GameLogger gameLogger;

	@Mock
	private Dice dice;

	@Spy
	private Board board;

	@Mock
	private ScoreCaluculation scoring;

	@Mock
	private Scoreboard scoreboard;

	@Spy
	private PlayerMap players = new PlayerMap();

	@InjectMocks
	private SimpleGame testee;

	@Test
	public void whenThePlayerIsSmartThenUseTheSmartCallBack() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		setUpTwoPlayerGame();

		testee.run();

		verify(playerII, never()).keepPlaying();
		verify(playerII, atLeastOnce()).keepPlaying((boolean[]) any());

	}

	@Test
	public void whenBoardIsChangedOnTheCallBackThenGameBoardShallNotChange() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		setUpTwoPlayerGame();

		testee.run();

		ArgumentCaptor<boolean[]> boardArgument = ArgumentCaptor.forClass(boolean[].class);
		verify(playerII, atLeastOnce()).keepPlaying(boardArgument.capture());
		Assert.assertEquals("Gameboard should have false before manibulating", false, board.getBoard()[4]);
		boardArgument.getAllValues().get(0)[4] = true;
		Assert.assertEquals("Gameboard has been changed", false, board.getBoard()[4]);
	}

	private void setUpTwoPlayerGame() {
		GameTest.preparePlayerMock(playerI, "Player 1");
		GameTest.preparePlayerMock(playerII, "Smart Player 2");
		when(dice.toss()).thenReturn(1);
		when(board.put(1)).thenReturn(0, 2, 0);

		testee.subscribe(playerI, playerI.getName());
		testee.subscribe(playerII, playerII.getName());
	}
}
