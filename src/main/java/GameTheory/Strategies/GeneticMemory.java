// package GameTheory.Strategies;

// public class GeneticMemory extends GeneticStrategy {

// public GeneticMemory(double n) {
// super(n);
// }

// /**
// * Make a move probabilistically; i.e. generate a random number between 0 and
// 1,
// * and if it is less than this.weight, and if opponentPrevMove is true, then
// * return true
// *
// * @return defect / cooperate depending on the conditions above
// */
// @Override
// public boolean makeMove() {
// boolean opponentPrevMove = this.opponentMoveHistory.size() > 0
// ? this.opponentMoveHistory.get(this.opponentMoveHistory.size() - 1)
// : true;
// return opponentPrevMove && this.generator.nextDouble() < this.weight;
// }

// /**
// * Mutate this genetic strategy by modifying it's weight
// */
// @Override
// public GeneticMemory mutateNew() {
// boolean pm = generator.nextDouble() > 0.5;
// double val = generator.nextDouble() * 0.008;
// double w = pm && (weight + val < 1) ? weight + val : weight - val > 0 ?
// weight - val : weight;
// return new GeneticMemory(w);
// }
// }
