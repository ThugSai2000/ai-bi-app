// package GameTheory.Strategies;

// import java.util.Random;

// public abstract class GeneticStrategy extends Strategy {

// /**
// * This class is a genetic algorithm that acts on no knowledge of previous
// * moves.
// */

// protected double weight;
// protected Random generator;

// GeneticStrategy(double n) {
// weight = n;
// generator = new Random();
// }

// /**
// * Mutate this genetic strategy by modifying it's weight
// */
// public void mutate() {
// boolean pm = generator.nextDouble() > 0.5;
// double val = generator.nextDouble() * 0.004;
// weight = pm && weight + val < 1 ? weight + val : weight - val > 0 ? weight -
// val : weight;
// }

// /**
// * Mutate this genetic strategy
// */
// public abstract GeneticStrategy mutateNew();

// /**
// * Return the weight of the GeneticStrategy
// */
// public double getWeight() {
// return this.weight;
// }
// }