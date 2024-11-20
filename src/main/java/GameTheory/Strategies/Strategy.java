/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameTheory.Strategies;

/**
 *
 * @author saiki
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author saiki
 */

public abstract class Strategy {

	protected List<Boolean> opponentMoveHistory;
	protected List<Integer> outcomes;
	private final Player player; // Added player field
	private static final Map<Player, List<Class<? extends Strategy>>> strategyMap;

	static {
		strategyMap = new HashMap<>();
		// Register Player 1 strategies here
		strategyMap.put(Player.PLAYER1, new ArrayList<>());
		strategyMap.get(Player.PLAYER1).add(AI_Powered_Autonomus_Cloud_DB.class);
		strategyMap.get(Player.PLAYER1).add(Focus_On_High_Performance.class);
		strategyMap.get(Player.PLAYER1).add(Vertical_Integration_With_Oracles.class);

		// Register Player 2 strategies here
		strategyMap.put(Player.PLAYER2, new ArrayList<>());
		strategyMap.get(Player.PLAYER2).add(Integrated_AI_ML_Services.class);
		strategyMap.get(Player.PLAYER2).add(Emphasize_Flexibility_Compatibility.class);
		strategyMap.get(Player.PLAYER2).add(Azure_Hybrid_Cloud_Solutions.class);
	}

	public Strategy(Player player) throws IllegalArgumentException {
		if (!strategyMap.containsKey(player)) {
			throw new IllegalArgumentException("Invalid player type");
		}
		this.opponentMoveHistory = new ArrayList<>();
		this.outcomes = new ArrayList<>();
		this.player = player;
	}

	// Static method to get a random strategy for a player
	public static Strategy getRandomStrategy(Player player) throws IllegalArgumentException {
		if (!strategyMap.containsKey(player)) {
			throw new IllegalArgumentException("Invalid player type");
		}

		List<Class<? extends Strategy>> playerStrategies = strategyMap.get(player);
		int randomIndex = (int) (Math.random() * playerStrategies.size());
		try {
			return playerStrategies.get(randomIndex).getConstructor(Player.class).newInstance(player);
		} catch (Exception e) {
			throw new RuntimeException("Error creating strategy instance", e);
		}
	}

	// replaced abstract boolean makeMove() with int getStrategy();
	public abstract int getStrategy();

	/**
	 * For ease of keeping track of strategies
	 *
	 * @return name of strategy
	 */
	public String getStrategyName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Add the opponent's previous move to this strategy's
	 * opponent history
	 *
	 * @param opponentMove the opponent's previous move
	 */

	public void addOpponentMove(boolean opponentMove) {
		this.opponentMoveHistory.add(opponentMove);
	}

	/**
	 * Get total points from outcomes
	 *
	 * @return sum of outcomes
	 */
	public int getPoints() {
		return this.outcomes.stream().reduce(0, (a, b) -> a + b);
	}

	/**
	 * Clears the outcomes and opponentMoveHistory arrays
	 */
	public void clearStrategy() {
		this.outcomes.clear();
		this.opponentMoveHistory.clear();
	}

	/**
	 * Add the outcome of the battle and add the opponent's
	 * previous move to this strategy's opponent history
	 *
	 * @param outcome the outcome of the battle
	 */
	public void addOutcome(int outcome) {
		this.outcomes.add(outcome);
	}

	/**
	 * Returns an unmodifiable list of the outcomes of
	 * this strategy's battles
	 */
	public List<Integer> getOutcomes() {
		return Collections.unmodifiableList(outcomes);
	}

	public Player getPlayer() {
		return player;
	}

	public enum Player {
		PLAYER1,
		PLAYER2
	}
}
