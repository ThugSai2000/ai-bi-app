package GameTheory;

import GameTheory.Strategies.GeneticStrategy;
import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Implements a tournament system for genetic strategies with normalization
 */
public class GeneticTournament {
    private HashMap<GeneticStrategy, Integer> points;
    private static final int DEFAULT_BATTLES = 100;

    /**
     * Constructor initializes tournament with list of strategies
     */
    GeneticTournament(List<GeneticStrategy> strategies) {
        this.points = new HashMap<>();
        strategies.forEach(s -> this.points.put(s, 0));
    }

    /**
     * Executes multiple rounds of genetic tournament with strategy evolution
     * 
     * @param numRounds number of tournament rounds to execute
     * @return final tournament results
     */
    public HashMap<GeneticStrategy, Integer> executeGeneticTournamentRounds(int numRounds) throws IOException {
        HashMap<GeneticStrategy, Integer> save = new HashMap<>();
        FileWriter fileWriter = new FileWriter("geneticRes.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int i = 0; i < numRounds; i++) {
            System.out.println("Round: " + i);

            // Execute tournament and update points
            addNewPoints(tournamentRound(DEFAULT_BATTLES));

            // Normalize points to prevent overflow
            normalizePoints();

            // Sort strategies by performance
            ArrayList<Map.Entry<GeneticStrategy, Integer>> sortedEntries = sortEntries(this.points.entrySet());

            // Log initial state
            if (i == 0) {
                logTournamentState(printWriter);
                printWriter.println("-------");
            }

            // Remove bottom performers
            eliminateWeakStrategies(sortedEntries);
            logTournamentState(printWriter);

            // Save final round results
            if (i == numRounds - 1) {
                save = new HashMap<>(this.points);
            }

            // Evolution phase
            evolveStrategies();
            printWriter.println("-------");
        }
        printWriter.close();
        return save;
    }

    /**
     * Normalizes points to prevent numerical overflow
     * Scales points to [0,100] range
     */
    private void normalizePoints() {
        int maxPoints = this.points.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(1);

        this.points.forEach((strategy, points) -> {
            double normalizedValue = (double) points / maxPoints;
            this.points.put(strategy, (int) (normalizedValue * 100));
        });
    }

    /**
     * Executes one round of tournament between all strategies
     * 
     * @param n number of battles per game
     * @return map of strategies to their points
     */
    private HashMap<GeneticStrategy, Integer> tournamentRound(int n) {
        HashMap<GeneticStrategy, Integer> tournamentPoints = new HashMap<>();
        Object[] strategies = this.points.keySet().toArray();

        for (int i = 0; i < strategies.length; i++) {
            for (int j = i + 1; j < strategies.length; j++) {
                Game g = new Game((GeneticStrategy) strategies[i], (GeneticStrategy) strategies[j]);
                List<Integer> gameOutcome = g.executeGame(n);
                updateTournamentPoints(tournamentPoints, strategies[i], strategies[j], gameOutcome);
            }
        }
        return tournamentPoints;
    }

    /**
     * Updates points for a pair of strategies after their game
     */
    private void updateTournamentPoints(HashMap<GeneticStrategy, Integer> tournamentPoints,
            Object strategy1, Object strategy2,
            List<Integer> gameOutcome) {
        int s1PrevPts = tournamentPoints.getOrDefault(strategy1, 0);
        int s2PrevPts = tournamentPoints.getOrDefault(strategy2, 0);

        tournamentPoints.put((GeneticStrategy) strategy1, s1PrevPts + gameOutcome.get(0));
        tournamentPoints.put((GeneticStrategy) strategy2, s2PrevPts + gameOutcome.get(1));
    }

    /**
     * Logs current tournament state to file
     */
    private void logTournamentState(PrintWriter printWriter) {
        this.points.forEach((s, v) -> printWriter.printf("%f %d%n", s.getWeight(), v));
    }

    /**
     * Eliminates bottom 50% of performers
     */
    private void eliminateWeakStrategies(ArrayList<Map.Entry<GeneticStrategy, Integer>> sortedEntries) {
        List<Map.Entry<GeneticStrategy, Integer>> eliminated = sortedEntries.subList(sortedEntries.size() / 2,
                sortedEntries.size());
        eliminated.forEach(s -> this.points.remove(s.getKey()));
    }

    /**
     * Evolves strategies through mutation
     */
    private void evolveStrategies() {
        HashMap<GeneticStrategy, Integer> newStrategies = new HashMap<>();

        // Generate mutations for each surviving strategy
        for (GeneticStrategy strategy : this.points.keySet()) {
            GeneticStrategy mutated = strategy.mutateNew();
            newStrategies.put(mutated, 0);
            strategy.mutate();
            this.points.put(strategy, 0);
        }

        this.points.putAll(newStrategies);
    }

    /**
     * Adds new points to existing strategies
     */
    private void addNewPoints(HashMap<GeneticStrategy, Integer> newPoints) {
        newPoints.forEach((strategy, points) -> this.points.merge(strategy, points, Integer::sum));
    }

    /**
     * Sorts strategies by their points in descending order
     */
    public ArrayList<Map.Entry<GeneticStrategy, Integer>> sortEntries(
            Set<Map.Entry<GeneticStrategy, Integer>> entrySet) {
        ArrayList<Map.Entry<GeneticStrategy, Integer>> sortedEntries = new ArrayList<>(entrySet);
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return sortedEntries;
    }
}
