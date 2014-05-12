package ch.akros.workshop.cd.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.akros.workshop.cd.domain.Player;

public class ScoreCaluculation {
	private static final Integer ZERO = new Integer(0);

	public Map<Player, Integer> score(Map<Player, Integer> playerMap) {
		Player winner = null;
		Integer totalScore = ZERO;
		for (Entry<Player, Integer> entry : playerMap.entrySet()) {
			if (ZERO.equals(entry.getValue())) {
				winner = entry.getKey();
			}
			totalScore += entry.getValue();
		}
		Map<Player, Integer> resultMap = new HashMap<Player, Integer>();
		resultMap.put(winner, totalScore);
		return resultMap;
	}

}
