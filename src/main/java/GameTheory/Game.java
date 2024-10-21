package GameTheory;

import GameTheory.Strategies.Strategy;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private final Strategy player1;
	private final Strategy player2;

	// PAYOFF_MATRIX to accommodate 3 strategies for each player
	private static final int[][][] PAYOFF_MATRIX = {
			{ { 3, 13 }, { 4, 15 }, { 4, 15 } },
			{ { 4, 15 }, { 3, 13 }, { 15, 4 } },
			{ { 13, 3 }, { 15, 4 }, { 4, 15 } }
	};

	// Constructor
	public Game(Strategy player1, Strategy player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	// Execute the game for a specified number of rounds
	public List<Integer> executeGame(int numRounds) {
		List<Integer> outcomes = new ArrayList<>();
		int player1Points = 0;
		int player2Points = 0;

		for (int i = 0; i < numRounds; i++) {
			// Get strategies for both players
			int player1Move = player1.getStrategy();
			int player2Move = player2.getStrategy();

			// Calculate outcome for this round
			int[] roundOutcome = calculateOutcome(player1Move, player2Move);
			player1Points += roundOutcome[0];
			player2Points += roundOutcome[1];

			// Add outcomes to each player's history
			player1.addOutcome(roundOutcome[0]);
			player2.addOutcome(roundOutcome[1]);

			// Add opponent's move to each player's history
			player1.addOpponentMove(player2Move != 0);
			player2.addOpponentMove(player1Move != 0);
		}

		// Add total points to the outcomes list
		outcomes.add(player1Points);
		outcomes.add(player2Points);
		return outcomes;
	}

	// Calculate the outcome for a single round
	private int[] calculateOutcome(int player1Move, int player2Move) {
		// Ensure moves are within bounds (0, 1, or 2)
		player1Move = Math.min(Math.max(player1Move, 0), 2);
		player2Move = Math.min(Math.max(player2Move, 0), 2);

		int[] outcome = new int[2];
		outcome[0] = PAYOFF_MATRIX[player1Move][player2Move][0];
		outcome[1] = PAYOFF_MATRIX[player1Move][player2Move][1];
		return outcome;
	}
}
