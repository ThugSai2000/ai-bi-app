package GameTheory;

// import GameTheory.Strategies.AlwaysCooperate;
// import GameTheory.Strategies.AlwaysDefect;
// import GameTheory.Strategies.Strategy;
// import GameTheory.Strategies.TitForTat;
import org.junit.Test;

import GameTheory.Strategies.Strategy;
import GameTheory.Strategies.Strategy_G;
import GameTheory.Strategies.Strategy_M;
import GameTheory.Strategies.Strategy_W;
import GameTheory.Strategies.Strategy_X;
import GameTheory.Strategies.Strategy_Y;
import GameTheory.Strategies.Strategy_Z;

import java.util.List;

import static org.junit.Assert.*;

public class GameTests {

	@Test
	public void testPlayer1StrategiesVsPlayer2Strategies() {
		// Define arrays of strategies for Player 1 and Player 2
		Strategy[] player1Strategies = {
				new Strategy_X(Strategy.Player.PLAYER1),
				new Strategy_Y(Strategy.Player.PLAYER1),
				new Strategy_Z(Strategy.Player.PLAYER1)
		};

		Strategy[] player2Strategies = {
				new Strategy_G(Strategy.Player.PLAYER2),
				new Strategy_M(Strategy.Player.PLAYER2),
				new Strategy_W(Strategy.Player.PLAYER2)
		};

		// Test all combinations of Player 1 and Player 2 strategies
		for (Strategy p1Strategy : player1Strategies) {
			for (Strategy p2Strategy : player2Strategies) {
				// Create a new game with the current strategies
				Game game = new Game(p1Strategy, p2Strategy);
				// Execute the game for 100 rounds
				List<Integer> outcomes = game.executeGame(100);

				// Print the results of the game
				System.out.printf("%s vs %s: Player1 Score = %d, Player2 Score = %d%n",
						p1Strategy.getStrategyName(), p2Strategy.getStrategyName(),
						outcomes.get(0), outcomes.get(1));

				// Add assertions based on expected outcomes
				assertTrue("Player1 score should be non-negative", outcomes.get(0) >= 0);
				assertTrue("Player2 score should be non-negative", outcomes.get(1) >= 0);
			}
		}
	}

	@Test
	public void testRandomStrategySelection() {
		// Get random strategies for both players
		Strategy player1 = Strategy.getRandomStrategy(Strategy.Player.PLAYER1);
		Strategy player2 = Strategy.getRandomStrategy(Strategy.Player.PLAYER2);

		// Assert that Player 1's strategy is one of X, Y, or Z
		assertTrue("Player1 strategy should be X, Y, or Z",
				player1 instanceof Strategy_X || player1 instanceof Strategy_Y || player1 instanceof Strategy_Z);
		// Assert that Player 2's strategy is one of G, M, or W
		assertTrue("Player2 strategy should be G, M, or W",
				player2 instanceof Strategy_G || player2 instanceof Strategy_M || player2 instanceof Strategy_W);

		// Create a new game with the random strategies
		Game game = new Game(player1, player2);
		// Execute the game for 100 rounds
		List<Integer> outcomes = game.executeGame(100);

		// Print the results of the game with random strategies
		System.out.printf("Random strategies: %s vs %s: Player1 Score = %d, Player2 Score = %d%n",
				player1.getStrategyName(), player2.getStrategyName(),
				outcomes.get(0), outcomes.get(1));

		// Assert that both players' scores are non-negative
		assertTrue("Player1 score should be non-negative", outcomes.get(0) >= 0);
		assertTrue("Player2 score should be non-negative", outcomes.get(1) >= 0);
	}

	// @Test
	// public void testCooperateDefect() {
	// Strategy s1 = new AlwaysCooperate();
	// Strategy s2 = new AlwaysDefect();

	// Game war = new Game(s1, s2);

	// List<Integer> warOutcomes = war.executeGame(100);
	// assertEquals(-100, (int) warOutcomes.get(0));
	// assertEquals(300, (int) warOutcomes.get(1));
	// }

	// @Test
	// public void testCooperateCooperate() {
	// Strategy s1 = new AlwaysCooperate();
	// Strategy s2 = new AlwaysCooperate();

	// Game war = new Game(s1, s2);

	// List<Integer> warOutcomes = war.executeGame(100);
	// assertEquals(200, (int) warOutcomes.get(0));
	// assertEquals(200, (int) warOutcomes.get(1));
	// }

	// @Test
	// public void testDefectDefect() {
	// Strategy s1 = new AlwaysDefect();
	// Strategy s2 = new AlwaysDefect();

	// Game war = new Game(s1, s2);

	// List<Integer> warOutcomes = war.executeGame(100);
	// assertEquals(0, (int) warOutcomes.get(0));
	// assertEquals(0, (int) warOutcomes.get(1));
	// }

	// /**
	// * Tit-for-tat should always cooperate with the 'Always Cooperate' strategy.
	// * Therefore, this pairing should be equivalent to Always Cooperate vs. Always
	// Cooperate
	// */
	// @Test
	// public void testTitForTatCooperate() {
	// Strategy s1 = new TitForTat();
	// Strategy s2 = new AlwaysCooperate();

	// Game war = new Game(s1, s2);

	// List<Integer> warOutcomes = war.executeGame(100);
	// assertEquals(200, (int) warOutcomes.get(0));
	// assertEquals(200, (int) warOutcomes.get(1));
	// }

	// /**
	// * Tit-for-tat should always cooperate with the 'Always Cooperate' strategy.
	// * Therefore, this pairing should be equivalent to Always Cooperate vs. Always
	// Cooperate
	// */
	// @Test
	// public void testTitForTatDefect() {
	// Strategy s1 = new TitForTat();
	// Strategy s2 = new AlwaysDefect();

	// Game war = new Game(s1, s2);

	// List<Integer> warOutcomes = war.executeGame(100);
	// assertEquals(-1, (int) warOutcomes.get(0));
	// assertEquals(3, (int) warOutcomes.get(1));
	// }
}