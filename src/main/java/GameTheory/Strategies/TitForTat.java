package GameTheory.Strategies;

public class TitForTat extends Strategy {

    public TitForTat(Player player) {
        super(player);
    }

    @Override
    public int getStrategy() {
        if (opponentMoveHistory.isEmpty()) {
            return 1; // Cooperate on first move
        }
        // Copy opponent's last move: true/1 for cooperate, false/0 for defect
        return opponentMoveHistory.get(opponentMoveHistory.size() - 1) ? 1 : 0;
    }

    @Override
    public String getStrategyName() {
        return "TitForTat";
    }
}
