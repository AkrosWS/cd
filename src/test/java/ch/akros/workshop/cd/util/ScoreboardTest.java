package ch.akros.workshop.cd.util;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;

import java.util.HashMap;
import java.util.Map;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ch.akros.workshop.cd.domain.Player;

//@formatter:off
/**
* 
* 1. DONE SP1 has 10 Punkte , SP2,5 => log Scoreboard SP1, SP2
* 2. DONE SP1,10 , SP2,5 + new result SP1,1 , SP2,10 => log Scoreboard SP2,SP1
* 3. DONE When reset player then Player score =0
* 
*/
//@formatter:on

@RunWith(CdiRunner.class)
public class ScoreboardTest {
	@Mock
	private GameLogger gameLogger;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@InjectMocks
	private Scoreboard testee;

	@Test
	public void whenSP1Has10SP2Has5ThenLogSP1First() {
		InOrder inOrder = inOrder(gameLogger);
		Map<Player, Integer> scoreList = new HashMap<Player, Integer>();
		scoreList.put(player2, 5);
		scoreList.put(player1, 10);

		testee.newScore(scoreList);
		inOrder.verify(gameLogger).logScore(eq(player1), eq(10), eq(1));
		inOrder.verify(gameLogger).logScore(eq(player2), eq(5), eq(1));
	}

	@Test
	public void whenSP1Has10SP2Has5AndNewResultSP1Has1SP2Has10ThenLogSP2First() {
		InOrder inOrder = inOrder(gameLogger);
		Map<Player, Integer> scoreList = new HashMap<Player, Integer>();
		scoreList.put(player2, 5);
		scoreList.put(player1, 10);

		testee.newScore(scoreList);

		scoreList.put(player2, 10);
		scoreList.put(player1, 1);

		testee.newScore(scoreList);

		inOrder.verify(gameLogger).logScore(eq(player1), eq(10), eq(1));
		inOrder.verify(gameLogger).logScore(eq(player2), eq(5), eq(1));
		inOrder.verify(gameLogger).logScore(eq(player2), eq(15), eq(2));
		inOrder.verify(gameLogger).logScore(eq(player1), eq(11), eq(2));
	}

	@Test
	public void whenSP1Has5AndResetSP1THenSP1Has0() {
		InOrder inOrder = inOrder(gameLogger);
		Map<Player, Integer> scoreList = new HashMap<Player, Integer>();
		scoreList.put(player2, 5);
		scoreList.put(player1, 10);

		testee.newScore(scoreList);

		testee.reset(player1);

		scoreList.put(player1, 6);
		testee.newScore(scoreList);

		inOrder.verify(gameLogger).logScore(eq(player1), eq(10), eq(1));
		inOrder.verify(gameLogger).logScore(eq(player2), eq(5), eq(1));
		inOrder.verify(gameLogger).logScore(eq(player2), eq(10), eq(2));
		inOrder.verify(gameLogger).logScore(eq(player1), eq(6), eq(2));

	}

}
