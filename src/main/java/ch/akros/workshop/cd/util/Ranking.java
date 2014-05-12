package ch.akros.workshop.cd.util;

import ch.akros.workshop.cd.domain.Player;

/**
 *
 */
public class Ranking implements Comparable<Ranking> {
	private Player player;
	private Integer score;
	private int playedGames = 1;

	public Ranking(Ranking rankedPlayer, Integer newScore) {
		this.player = rankedPlayer.getPlayer();
		this.score = rankedPlayer.getScore() + newScore;
		this.playedGames = rankedPlayer.getPlayedGames() + 1;
	}

	public Ranking(Player player, Integer score) {
		this.player = player;
		this.score = score;
	}

	public Integer getScore() {
		return score;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public int compareTo(Ranking o) {
		if (this.getPlayer().equals(o.getPlayer())) {
			return 0;
		}
		int result;
		if (this.getScore().equals(o.getScore())) {
			result = o.getPlayedGames() - this.getPlayedGames();
		} else {
			result = o.getScore() - this.getScore();
		}

		if (result == 0) {
			return -1;
		} else {
			return result;
		}
	}

	@Override
	public String toString() {
		return "Ranking [player=" + player + ", score=" + score + ", playedGames=" + playedGames + "]";
	}

}
