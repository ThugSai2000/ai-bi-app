package GameTheory.Strategies;

import java.util.Random;

/**
 * Abstract class implementing genetic algorithm functionality
 * Extends base Strategy class for tournament play
 */
public abstract class GeneticStrategy extends Strategy {
    // Weight determines probability of strategy choices
    protected double weight;
    // Random number generator for mutations
    protected Random generator;

    /**
     * Constructor initializes genetic strategy
     * 
     * @param n      Initial weight value
     * @param player Player enum value (PLAYER1 or PLAYER2)
     */
    GeneticStrategy(double n, Player player) {
        super(player); // Initialize base Strategy class
        weight = n;
        generator = new Random();
    }

    /**
     * Mutates the current strategy's weight
     * Uses small random adjustments bounded by 0 and 1
     */
    public void mutate() {
        boolean pm = generator.nextDouble() > 0.5;
        double val = generator.nextDouble() * 0.004;
        weight = pm && weight + val < 1 ? weight + val : weight - val > 0 ? weight - val : weight;
    }

    /**
     * Creates new mutated instance of strategy
     * 
     * @return New GeneticStrategy with mutated values
     */
    public abstract GeneticStrategy mutateNew();

    /**
     * Gets current weight value
     * 
     * @return Current strategy weight
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Required implementation from Strategy class
     * 
     * @return Integer representing chosen strategy
     */
    @Override
    public abstract int getStrategy();
}
