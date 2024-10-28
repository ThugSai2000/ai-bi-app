package GameTheory.Strategies;

/**
 * TitForTatTat strategy - Considers last two moves of opponent
 * Cooperates if opponent cooperated in both previous moves
 */
public class TitForTatTat extends Strategy {

    /**
     * Original: Basic constructor
     * Updated: Added Player parameter for new contest structure
     */
    public TitForTatTat(Player player) {
        super(player);
    }

    /**
     * Original: Used boolean makeMove()
     * Updated: Now uses int getStrategy() for contest compatibility
     * Returns 1 (cooperate) if:
     * - First move
     * - Second move
     * - Both previous opponent moves were cooperative
     * Returns 0 (defect) otherwise
     */
    @Override
    public int getStrategy() {
        if (opponentMoveHistory.size() < 2) {
            return 1; // Cooperate for first two moves
        }

        // Check last two opponent moves
        boolean lastMove = opponentMoveHistory.get(opponentMoveHistory.size() - 1);
        boolean secondLastMove = opponentMoveHistory.get(opponentMoveHistory.size() - 2);

        return (lastMove && secondLastMove) ? 1 : 0;
    }

    @Override
    public String getStrategyName() {
        return "TitForTatTat";
    }
}
