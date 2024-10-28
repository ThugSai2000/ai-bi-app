package GameTheory;

import GameTheory.Strategies.Strategy;
import java.util.*;
import java.io.*;

public class Tournament {
	// Stores the cumulative points for each strategy
	private HashMap<Strategy, Integer> points;
	// Default number of battles per game
	private final int DEFAULT_BATTLES = 10;

	/**
	 * Constructor initializes tournament with list of competing strategies
	 * 
	 * @param strategies List of strategies to compete in tournament
	 */
	public Tournament(List<Strategy> strategies) {
		this.points = new HashMap<>();
		strategies.forEach(s -> this.points.put(s, 0));
	}

	/**
	 * Executes multiple tournament rounds
	 * 
	 * @param numRounds Number of tournament rounds to execute
	 * @return HashMap containing final points for each strategy
	 */
	public HashMap<Strategy, Integer> executeTournamentRounds(int numRounds) {
		for (int i = 0; i < numRounds; i++) {
			addNewPoints(tournamentRound(DEFAULT_BATTLES));
		}
		return this.points;
	}

	/**
	 * Executes tournament rounds with detailed logging to file
	 * 
	 * @param numRounds Number of tournament rounds
	 * @param logFile   Path to output log file
	 * @return HashMap containing final points for each strategy
	 */
	public HashMap<Strategy, Integer> executeTournamentWithLogging(int numRounds, String logFile) throws IOException {
		FileWriter fileWriter = new FileWriter(logFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		for (int i = 0; i < numRounds; i++) {
			HashMap<Strategy, Integer> roundResults = tournamentRound(DEFAULT_BATTLES);
			addNewPoints(roundResults);

			// Log round results
			printWriter.printf("Round %d Results:\n", i + 1);
			roundResults
					.forEach((strategy, score) -> printWriter.printf("%s: %d\n", strategy.getStrategyName(), score));
			printWriter.println("---------------");
		}

		printWriter.close();
		return this.points;
	}

	/**
	 * Executes a single tournament round where each strategy plays against all
	 * others
	 * 
	 * @param battlesPerGame Number of battles in each game
	 * @return HashMap containing points earned in this round
	 */
	private HashMap<Strategy, Integer> tournamentRound(int battlesPerGame) {
		HashMap<Strategy, Integer> tournamentPoints = new HashMap<>();
		Object[] strategies = this.points.keySet().toArray();

		for (int i = 0; i < strategies.length; i++) {
			for (int j = i + 1; j < strategies.length; j++) {
				Game g = new Game((Strategy) strategies[i], (Strategy) strategies[j]);
				List<Integer> gameOutcome = g.executeGame(battlesPerGame);

				updateTournamentPoints(tournamentPoints, (Strategy) strategies[i], gameOutcome.get(0));
				updateTournamentPoints(tournamentPoints, (Strategy) strategies[j], gameOutcome.get(1));
			}
		}
		return tournamentPoints;
	}

	/**
	 * Updates points for a strategy in the tournament
	 * 
	 * @param tournamentPoints Points map to update
	 * @param strategy         Strategy to update points for
	 * @param points           Points to add
	 */
	private void updateTournamentPoints(HashMap<Strategy, Integer> tournamentPoints, Strategy strategy, int points) {
		tournamentPoints.merge(strategy, points, Integer::sum);
	}

	/**
	 * Adds new round points to cumulative points
	 * 
	 * @param newPoints Points from latest round
	 */
	private void addNewPoints(HashMap<Strategy, Integer> newPoints) {
		newPoints.forEach((strategy, points) -> this.points.merge(strategy, points, Integer::sum));
	}

	/**
	 * Returns sorted tournament results
	 * 
	 * @return ArrayList of strategy-points entries sorted by points
	 */
	public ArrayList<Map.Entry<Strategy, Integer>> getResults() {
		return sortEntries(this.points.entrySet());
	}

	/**
	 * Sorts entries by points in descending order
	 * 
	 * @param entrySet Set of entries to sort
	 * @return Sorted ArrayList of entries
	 */
	public ArrayList<Map.Entry<Strategy, Integer>> sortEntries(Set<Map.Entry<Strategy, Integer>> entrySet) {
		ArrayList<Map.Entry<Strategy, Integer>> sortedEntries = new ArrayList<>(entrySet);
		sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
		return sortedEntries;
	}

	/**
	 * Resets all strategy points to zero
	 */
	// public void resetTournament() {
	// this.points.replaceAll((k, v) -> 0);
	// }
}
