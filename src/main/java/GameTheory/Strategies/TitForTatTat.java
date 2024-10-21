// package GameTheory.Strategies;

// public class TitForTatTat extends Strategy {

// /**
// * Strategy from the great Evolution of Trust (https://ncase.me/trust/)
// *
// * This strategy (Tit-For-TatTat) will defect if the previous two moves of the
// * opponent were defections.
// */

// public TitForTatTat() {
// super();
// }

// @Override
// public boolean makeMove() {
// if (opponentMoveHistory.size() == 0 || opponentMoveHistory.size() == 1) {
// return true;
// }
// return opponentMoveHistory.get(opponentMoveHistory.size() - 1)
// && opponentMoveHistory.get(opponentMoveHistory.size() - 2);
// }

// }
