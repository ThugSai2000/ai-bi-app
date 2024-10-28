package GameTheory.Strategies;

/**
 * A genetic strategy that considers opponent's previous moves
 * Uses memory of past interactions to make decisions
 */
public class GeneticMemory extends GeneticStrategy {

    /**
     * Original: Basic constructor with weight
     * Updated: Added Player parameter to match Strategy base class
     */
    public GeneticMemory(double n, Player player) {
        super(n, player);
    }

    /**
     * Original: Used boolean makeMove() for decisions
     * Updated: Converted to int getStrategy() to match Strategy class
     * Returns 1 for cooperate, 0 for defect
     * Logic considers both opponent history and probability weight
     */
    @Override
    public int getStrategy() {
        boolean opponentPrevMove = this.opponentMoveHistory.size() > 0
                ? this.opponentMoveHistory.get(this.opponentMoveHistory.size() - 1)
                : true;
        return (opponentPrevMove && this.generator.nextDouble() < this.weight) ? 1 : 0;
    }

    /**
     * Original: Creates new mutated instance
     * Updated: Added player parameter to constructor call
     * Mutation rate remains at 0.008 for fine-tuned evolution
     */
    @Override
    public GeneticMemory mutateNew() {
        boolean pm = generator.nextDouble() > 0.5;
        double val = generator.nextDouble() * 0.008;
        double w = pm && (weight + val < 1) ? weight + val : weight - val > 0 ? weight - val : weight;
        return new GeneticMemory(w, this.getPlayer());
    }
}
