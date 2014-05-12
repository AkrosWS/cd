package ch.akros.workshop.cd.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.inject.Inject;

import ch.akros.workshop.cd.domain.Player;

public class Scoreboard {
	@Inject
	private GameLogger gameLogger;

	private TreeSet<Ranking> ranking = new TreeSet<Ranking>();

	public void newScore(Map<Player, Integer> scoreList) {

		TreeSet<Ranking> newRanking = new TreeSet<Ranking>();

		for (Ranking rankedPlayer : ranking) {
			Player player = rankedPlayer.getPlayer();
			Integer newScore = scoreList.remove(player);
			if (newScore != null) {
				Ranking newRankedPlayer = new Ranking(rankedPlayer, newScore);
				newRanking.add(newRankedPlayer);
			}
		}

		for (Entry<Player, Integer> entry : scoreList.entrySet()) {
			Ranking newRankedPlayer = new Ranking(entry.getKey(), entry.getValue());
			newRanking.add(newRankedPlayer);
		}

		ranking = newRanking;
		for (Ranking rankedPlayer : ranking) {
			gameLogger.logScore(rankedPlayer.getPlayer(), rankedPlayer.getScore(), rankedPlayer.getPlayedGames());
		}

	}
}
