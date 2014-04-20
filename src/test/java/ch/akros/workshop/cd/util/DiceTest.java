package ch.akros.workshop.cd.util;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class DiceTest {

	@Inject
	private Dice testee;

	@Test
	public void ensureNumberIsBetweenOneAndSix() {

		int number = testee.toss();

		Assert.assertTrue("Number is not bigger than 0", 0 < number);
		Assert.assertTrue("Number is not lower than 7", 7 > number);

	}

	@Test(timeout = 10000)
	public void ensureDiceReturnsAllValidNumbersWithin10s() {

		for (int i = 1; i < 7; i++) {
			while (i != testee.toss())
				;
		}
	}
}
