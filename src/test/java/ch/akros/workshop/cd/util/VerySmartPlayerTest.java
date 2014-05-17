package ch.akros.workshop.cd.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import ch.akros.workshop.cd.domain.Player;
import ch.akros.workshop.cd.domain.VerySmartPlayer;
import ch.akros.workshop.cd.exception.GameAlreadyInPlayException;
import ch.akros.workshop.cd.exception.NotEnoughPlayerException;

//@formatter:off
/**
* 
* 1. DONE Call VerySmartPlayers back with the board and the number of sticks.
* 
* 
*/
//@formatter:on

@RunWith(CdiRunner.class)
public class VerySmartPlayerTest {

	@Mock
	private Player playerI;

	@Mock
	private VerySmartPlayer playerII;

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
	public void whenThePlayerIsVerySmartThenUseTheVerySmartCallBack() throws NotEnoughPlayerException, GameAlreadyInPlayException {
		setUpTwoPlayerGame();

		testee.run();

		verify(playerII, never()).keepPlaying();
		verify(playerII, atLeastOnce()).keepPlaying((boolean[]) any(), (int[]) any(), any(int.class));

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
