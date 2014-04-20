package ch.akros.workshop.cd.util;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

//@formatter:off
/**
 * 
 * 1. Board has 5 places which can hold only stick (1-5) 
 * 2. Board has a bucket which has no limitation on how many stick it can hold (6) 
 * 3. Try to put a stick on a specific number, if place is empty it returns 0, otherwise 2 sticks. 
 * 4. If place was not empty it is afterwards as the stick was returned to the player. 
 * 5. list the state of the board. (1-5)
 * 5a. DONE list size is 5
 * 
 */
//@formatter:on

@RunWith(CdiRunner.class)
public class BoardTest {
	@Inject
	private Board testee;

	@Test
	public void ensureBoardListSizeIs5() {
		boolean board[] = testee.getBoardList();
		Assert.assertEquals("Board size is not 5", 5, board.length);
	}

}
