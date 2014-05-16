package ch.akros.workshop.cd.util;

import java.util.HashMap;
import java.util.Map;

import ch.akros.workshop.cd.domain.Player;

public class PlayerMap extends HashMap<Player, Integer> {

	public PlayerMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayerMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		// TODO Auto-generated constructor stub
	}

	public PlayerMap(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public PlayerMap(Map<? extends Player, ? extends Integer> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

}
