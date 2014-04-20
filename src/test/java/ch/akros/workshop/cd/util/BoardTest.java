package ch.akros.workshop.cd.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

//@formatter:off
/**
 * 
 * 1. Board has 5 places which can hold only stick (1-5) 
 * 2. Board has a bucket which has no limitation on how many stick it can hold (6) 
 * 3. Try to put a stick on a specific number, if place is empty it returns 0, otherwise 2 sticks. 
 * 3a. DONE Put one in a empty Board shall return zero.
 * 4. If place was not empty it is afterwards as the stick was returned to the player. 
 * 5. list the state of the board. (1-5)
 * 5a. DONE list size is 5
 * 5b. DONE new Board all places are empty
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
		assertEquals("Board size is not 5", 5, board.length);
	}

	@Test
	public void whenNewBoardThenListEmpty() {
		boolean board[] = testee.getBoardList();

		for (int i = 0; i < board.length; i++) {
			assertFalse("Board place " + (i + 1) + " is not empty", board[i]);
		}
	}

	@Test
	public void whenEmptyBoardAndPutInPlaceOneThenSticksReturnedZero() {
		int stickReturned = testee.put(1);

		assertEquals("Returned sticks are not zero", 0, stickReturned);
	}

}
