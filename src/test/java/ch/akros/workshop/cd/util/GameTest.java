package ch.akros.workshop.cd.util;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

//@formatter:off
/**
* 
* 1. DONE Player can subscribe to the next game
* 2. Start a new game once there are two player subscribed.
* 3. Log which player won
* 4. At the end of a game start a new game with all the subscribed once. Player from the finished game are automatically re-subscribed.
* 5. call back player to decide if he wants to toss again.
* 
* 
*/
//@formatter:on
@RunWith(CdiRunner.class)
public class GameTest {

	@Inject
	private Game testee;

	@Mock
	private Player playerI;

	@Test
	public void ensurePlayerCanSubscribeWithoutException() {
		testee.subscribe(playerI);
	}

}
