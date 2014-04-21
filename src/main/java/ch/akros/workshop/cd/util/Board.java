package ch.akros.workshop.cd.util;

public class Board {
	private boolean[] places = new boolean[5];

	public int put(int place) {
		if (place == 6) {
			return 0;
		}

		int position = place - 1;

		if (places[position]) {
			places[position] = false;
			return 2;
		}
		places[position] = true;
		return 0;
	}

	public void clear() {
		places = new boolean[5];

	}

}
