package ch.akros.workshop.cd.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import ch.akros.workshop.cd.domain.Player;

import com.google.common.collect.TreeMultiset;

//@formatter:off
/**
* 
* 1. Add SP1,0 SP2,5 => iterate SP1, SP2
* 2. SP1,5 SP2,0 => SP2, SP1
* 3. SP1,5 SP2,3 SP3,3 => SP2, SP3, SP1

* 
* 
*/
//@formatter:on
@RunWith(CdiRunner.class)
public class OrderRemainingStickMapTest {
	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Mock
	private Player player3;

	@Test
	public void whenSP1Has0SP2Has5ThenSP1SP2() {

		Comparator<Entry<Player, Integer>> comperator = new OrderRemainingSickComperator();
		TreeMultiset<Entry<Player, Integer>> testee = TreeMultiset.create(comperator);

		Map<Player, Integer> playerMap = new HashMap<Player, Integer>();

		playerMap.put(player1, 0);
		playerMap.put(player2, 5);

		for (Entry<Player, Integer> entry : playerMap.entrySet()) {
			testee.add(entry);
		}

		Iterator<Entry<Player, Integer>> iter = testee.iterator();
		Assert.assertEquals("SP1 shall be first", player1, iter.next().getKey());
		Assert.assertEquals("SP2 shall be second", player2, iter.next().getKey());

	}

	@Test
	public void whenSP1Has5SP2Has0ThenSP2SP1() {

		Comparator<Entry<Player, Integer>> comperator = new OrderRemainingSickComperator();
		TreeMultiset<Entry<Player, Integer>> testee = TreeMultiset.create(comperator);

		Map<Player, Integer> playerMap = new HashMap<Player, Integer>();

		playerMap.put(player1, 5);
		playerMap.put(player2, 0);

		for (Entry<Player, Integer> entry : playerMap.entrySet()) {
			testee.add(entry);
		}

		Iterator<Entry<Player, Integer>> iter = testee.iterator();
		Assert.assertEquals("SP2 shall be first", player2, iter.next().getKey());
		Assert.assertEquals("SP1 shall be second", player1, iter.next().getKey());

	}

	@Test
	public void whenSP1Has5SP2Has3SP3Has3ThenSP2ORSP3SP1() {

		Comparator<Entry<Player, Integer>> comperator = new OrderRemainingSickComperator();
		TreeMultiset<Entry<Player, Integer>> testee = TreeMultiset.create(comperator);

		Map<Player, Integer> playerMap = new HashMap<Player, Integer>();

		playerMap.put(player1, 5);
		playerMap.put(player2, 3);
		playerMap.put(player3, 3);

		for (Entry<Player, Integer> entry : playerMap.entrySet()) {
			testee.add(entry);
		}

		Iterator<Entry<Player, Integer>> iter = testee.iterator();
		Player first = iter.next().getKey();
		Player second = iter.next().getKey();
		Player third = iter.next().getKey();

		if (first.equals(player2)) {
			Assert.assertEquals("If first player is SP2 then second player needs to be SP3", player3, second);
		} else {
			Assert.assertEquals("If first player is not SP2 then second player needs to be SP2", player2, second);
			Assert.assertEquals("If first player is not SP2 then first player needs to be SP3", player3, first);

		}

		Assert.assertEquals("SP1 shall be third player", player1, third);

	}
}
