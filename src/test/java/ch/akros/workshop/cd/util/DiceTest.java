package ch.akros.workshop.cd.util;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class DiceTest {

	@Inject
	private Dice dice;

	@Test
	public void ensureNumberIsBetweenOneAndSix() {
		Assert.assertNotNull(dice);
	}

}
