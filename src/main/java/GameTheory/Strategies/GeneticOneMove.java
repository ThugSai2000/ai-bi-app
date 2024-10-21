// package GameTheory.Strategies;

// public class GeneticOneMove extends GeneticStrategy {

// public GeneticOneMove(double n) {
// super(n);
// }

// /**
// * make a move probabilistically; i.e. generate a random
// * number between 0 or 1, and return if it is
// * less than this.weight. Therefore, if this.weight is low,
// * false will be returned more often and this strategy will display a
// * pseudo-always-defect strategy.
// *
// * @return defect / cooperate depending on if
// * the random number is less than weight
// */
// @Override
// public boolean makeMove() {
// return this.generator.nextDouble() < this.weight;
// }

// /**
// * Create new genetic strategy by modifying this strategy's weight
// */
// @Override
// public GeneticOneMove mutateNew() {
// boolean pm = generator.nextDouble() > 0.5;
// double val = generator.nextDouble() * 0.01;
// double w = pm && (weight + val < 1) ? weight + val : weight - val > 0 ?
// weight - val : weight;
// return new GeneticOneMove(w);
// }
// }
