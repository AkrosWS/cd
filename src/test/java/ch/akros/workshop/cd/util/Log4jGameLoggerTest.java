package ch.akros.workshop.cd.util;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import ch.akros.workshop.cd.domain.Player;

/**
 * This is not a self validating test. It only helps to easely see the possible log output
 */
@RunWith(CdiRunner.class)
public class Log4jGameLoggerTest {

	@Inject
	private Log4jGameLogger testee;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Test
	public void testLogs() {
		testee.gameNotReady("Game not Ready");
		testee.gameStarts();
		testee.newSubscribtion("Player 1");

		when(player1.getName()).thenReturn("PlayerName");
		testee.playerWon(player1);

		testee.turn(player1);

		testee.timerTriggered();

		Map<Player, Integer> players = new HashMap<Player, Integer>();
		players.put(player1, 5);
		players.put(player2, 10);

		testee.score(players);

		testee.logScore(player1, 4, 5);

	}

}
