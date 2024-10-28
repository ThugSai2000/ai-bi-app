package GameTheory.Strategies;

/**
 * Implementation of single-move genetic strategy
 */
public class GeneticOneMove extends GeneticStrategy {

    public GeneticOneMove(double n, Player player) {
        super(n, player);
    }

    /**
     * Converts probabilistic weight-based decision into strategy integer
     * 
     * @return 1 for cooperate, 0 for defect based on weight probability
     */
    @Override
    public int getStrategy() {
        return this.generator.nextDouble() < this.weight ? 1 : 0;
    }

    /**
     * Create new genetic strategy by modifying this strategy's weight
     * 
     * @return New GeneticOneMove instance with mutated weight
     */
    @Override
    public GeneticOneMove mutateNew() {
        boolean pm = generator.nextDouble() > 0.5;
        double val = generator.nextDouble() * 0.01;
        double w = pm && (weight + val < 1) ? weight + val : weight - val > 0 ? weight - val : weight;
        return new GeneticOneMove(w, this.getPlayer());
    }
}
