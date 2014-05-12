package ch.akros.workshop.cd.util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import ch.akros.workshop.cd.domain.Player;

@RunWith(CdiRunner.class)
public class ScoreCalculationTest {

	//@formatter:off
	/**
	* 
	* 1. DONE SP1 = 0H, SP2 = 5 => SP1 = 5P
	* 2. DONE SP1 = 0H, SP2 = 5 => SP2 = 2.5P =>3P
	* 3. SP1 = 5H, SP2 = 0 => SP1 = 3P
	* 4. SP1 = 5H, SP2 = 0 => SP0 = 5P
	* 5. SP1 = 0H, SP2 = 5, SP3 = 7 => SP3 = 5P
	* 6. Mit N Spieler
	* 7. ignore negatrive hölzchen 

	* 
	* 
	*/
	//@formatter:on

	@Inject
	private ScoreCaluculation testee;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Test
	public void whenSp1Has0AndSp2Has5ThenSp1Get5() {
		Map<Player, Integer> playerMap = new HashMap<Player, Integer>();

		playerMap.put(player1, 0);
		playerMap.put(player2, 5);

		Map<Player, Integer> playerPoints = testee.score(playerMap);

		Assert.assertEquals("Player 1 shall have 5 points", new Integer(5), playerPoints.get(player1));

	}

	@Test
	public void whenSp1Has0AndSp2Has5ThenSp2Get3() {
		Map<Player, Integer> playerMap = new HashMap<Player, Integer>();

		playerMap.put(player1, 0);
		playerMap.put(player2, 5);
		Map<Player, Integer> playerPoints = testee.score(playerMap);

		Assert.assertEquals("Player 2 shall have 3 points", new Integer(3), playerPoints.get(player2));

	}
}
