package ch.akros.workshop.cd.util;

import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(CdiRunner.class)
public class Log4jGameLoggerTest {

	@Inject
	private Log4jGameLogger testee;

	@Mock
	private Player player;

	@Test
	public void testLogs() {
		testee.gameNotReady("Game not Ready");
		testee.gameStarts();
		testee.newSubscribtion("Player 1");

		when(player.getName()).thenReturn("PlayerName");
		testee.playerWon(player);

		testee.turn(player);

	}

}
