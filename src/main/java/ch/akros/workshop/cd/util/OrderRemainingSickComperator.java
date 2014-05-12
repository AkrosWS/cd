package ch.akros.workshop.cd.util;

import java.util.Comparator;
import java.util.Map.Entry;

import ch.akros.workshop.cd.domain.Player;

public class OrderRemainingSickComperator implements Comparator<Entry<Player, Integer>> {

	@Override
	public int compare(Entry<Player, Integer> o1, Entry<Player, Integer> o2) {
		int first = o1.getValue();
		int second = o2.getValue();
		if (first - second == 0) {
			return -1;
		}
		return first - second;
	}
}
