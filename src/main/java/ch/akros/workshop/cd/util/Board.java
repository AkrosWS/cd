package ch.akros.workshop.cd.util;

public class Board {
	private boolean[] places = new boolean[5];

	public int put(int i) {
		int position = i - 1;
		if (places[position]) {
			places[position] = false;
			return 2;
		}
		places[position] = true;
		return 0;
	}

}
