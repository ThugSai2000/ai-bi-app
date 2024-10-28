package GameTheory.Strategies;

/**
 * Grudger strategy - Cooperates until opponent defects, then always defects
 * Also known as "Grim Trigger" or "Spiteful" strategy
 */
public class Grudger extends Strategy {

    private boolean hasDefected = false;

    /**
     * Original: Basic constructor
     * Updated: Added Player parameter for new contest structure
     */
    public Grudger(Player player) {
        super(player);
    }

    /**
     * Original: Used boolean makeMove()
     * Updated: Now uses int getStrategy() for contest compatibility
     * Returns 1 (cooperate) if opponent never defected
     * Returns 0 (defect) if opponent has ever defected
     */
    @Override
    public int getStrategy() {
        if (!opponentMoveHistory.isEmpty()) {
            boolean opponentLastMove = opponentMoveHistory.get(opponentMoveHistory.size() - 1);
            if (!opponentLastMove) {
                hasDefected = true;
            }
        }
        return hasDefected ? 0 : 1;
    }

    @Override
    public String getStrategyName() {
        return "Grudger";
    }
}
