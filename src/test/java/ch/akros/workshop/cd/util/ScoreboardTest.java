package ch.akros.workshop.cd.util;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ch.akros.workshop.cd.domain.Player;

//@formatter:off
/**
* 
* 1. SP1 has 10 Punkte , SP2,5 => log Scoreboard SP1, SP2
* 2. SP1,10 , SP2,5 + new result SP1,1 , SP2,10 => log Scoreboard SP2,SP1
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
		Map<Player, Integer> scoreList = new HashMap<Player, Integer>();
		scoreList.put(player2, 5);
		scoreList.put(player1, 10);

		testee.newScore(scoreList);

		verify(gameLogger).logScore(eq(player1), eq(10), eq(1));
		verify(gameLogger).logScore(eq(player2), eq(5), eq(1));
	}

}
