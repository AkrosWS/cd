package ch.akros.workshop.cd.util;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.akros.workshop.cd.domain.Player;

import com.google.common.collect.TreeMultiset;

public class ScoreCaluculation {
	private static final Integer ZERO = new Integer(0);

	public Map<Player, Integer> score(Map<Player, Integer> playerMap) {
		Comparator<Entry<Player, Integer>> comperator = new OrderRemainingSickComperator();
		TreeMultiset<Entry<Player, Integer>> playerList = TreeMultiset.create(comperator);
		Integer totalScore = ZERO;
		for (Entry<Player, Integer> entry : playerMap.entrySet()) {
			playerList.add(entry);
			totalScore += entry.getValue();
		}

		Map<Player, Integer> resultMap = new HashMap<Player, Integer>();
		BigDecimal totalScoreD = new BigDecimal(totalScore);
		BigDecimal counter = BigDecimal.ONE;
		int previousRemainingSticks = -1;
		int counterAdjustmet = 0;
		for (Entry<Player, Integer> entry : playerList) {
			int currentRemaining = playerMap.get(entry.getKey());
			if (previousRemainingSticks == currentRemaining) {
				counterAdjustmet++;
			} else {
				counterAdjustmet = 0;
			}

			resultMap.put(entry.getKey(), totalScoreD.divide(counter.subtract(new BigDecimal(counterAdjustmet)), BigDecimal.ROUND_UP).intValue());
			counter = counter.add(BigDecimal.ONE);
			previousRemainingSticks = currentRemaining;
		}

		return resultMap;
	}

}
