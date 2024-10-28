package GameTheory.Strategies;

/**
 * Simpleton strategy - A simple strategy that changes behavior based on
 * opponent's last move
 * If opponent cooperated, repeat previous move
 * If opponent defected, change previous move
 */
public class Simpleton extends Strategy {

    private boolean lastMove = true; // Start with cooperation

    /**
     * Original: Basic constructor
     * Updated: Added Player parameter for new contest structure
     */
    public Simpleton(Player player) {
        super(player);
    }

    /**
     * Original: Used boolean makeMove()
     * Updated: Now uses int getStrategy() for contest compatibility
     * Returns 1 (cooperate) or 0 (defect) based on:
     * - First move: cooperate
     * - If opponent cooperated: repeat last move
     * - If opponent defected: switch move
     */
    @Override
    public int getStrategy() {
        if (opponentMoveHistory.isEmpty()) {
            lastMove = true;
            return 1;
        }

        boolean opponentLastMove = opponentMoveHistory.get(opponentMoveHistory.size() - 1);
        if (opponentLastMove) {
            // Keep same move if opponent cooperated
            return lastMove ? 1 : 0;
        } else {
            // Switch move if opponent defected
            lastMove = !lastMove;
            return lastMove ? 1 : 0;
        }
    }

    @Override
    public String getStrategyName() {
        return "Simpleton";
    }
}
