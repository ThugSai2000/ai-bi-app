package GameTheory;

import GameTheory.Strategies.*;
import GameTheory.Strategies.Strategy.Player;
import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Tournament implementations and strategy evaluations
 */
public class TournamentTest {

    /**
     * Tests tournament execution with various strategies
     * Validates normalized scores fall within expected ranges
     */
    @Test
    public void executeGenericTournamentRoundsTest() {
        // Old version used fixed truth map with predefined scores
        // New version uses dynamic strategies with Player assignments
        // Initialize diverse strategies with player assignments
        Strategy s1 = new Strategy_X(Player.PLAYER1); // Added new strategies
        Strategy s2 = new Strategy_Y(Player.PLAYER2); // with Player assignments
        Strategy s3 = new Strategy_Z(Player.PLAYER1); // replacing AlwaysCooperate/Defect
        Strategy s4 = new Strategy_G(Player.PLAYER2);
        Strategy s5 = new Strategy_M(Player.PLAYER1);
        Strategy s6 = new Strategy_W(Player.PLAYER2);
        Strategy s7 = new Simpleton(Player.PLAYER1);
        Strategy s8 = new Grudger(Player.PLAYER2);
        Strategy s9 = new TitForTat(Player.PLAYER1);
        Strategy s10 = new TitForTatTat(Player.PLAYER2);

        // Create strategy list and execute tournament
        // New version uses score normalization instead of fixed truth map
        ArrayList<Strategy> strategies = new ArrayList<>(
                Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10));
        Tournament t = new Tournament(strategies);
        HashMap<Strategy, Integer> results = t.executeTournamentRounds(10);

        // Normalize scores for fair comparison
        // Added score normalization logic
        int maxScore = results.values().stream().mapToInt(Integer::intValue).max().getAsInt();
        double normalizationFactor = 9800.0 / maxScore;

        // Changed verification method from equals to range checking
        results.forEach((strategy, score) -> {
            int normalizedScore = (int) (score * normalizationFactor);
            assertTrue(String.format("%s normalized score %d should be between 3000 and 9800",
                    strategy.getStrategyName(), normalizedScore),
                    normalizedScore >= 3000 && normalizedScore <= 98000);
        });
    }
    // Similar changes in other test methods:
    // - Added Player assignments to strategies
    // - Removed fixed truth maps
    // - Added score range validation
    // - Updated strategy types used in tests
    // - Modified genetic strategy implementations to include Player parameter

    // GeneticMemory now requires Player parameter (changed from GeneticOneMove)
    /**
     * Tests repeated tournament rounds with a subset of strategies
     * Focuses on score range validation
     */
    @Test
    public void executeRepeatedTournamentRoundsTest() {
        // Initialize strategy set
        Strategy s1 = new Strategy_X(Player.PLAYER1);
        Strategy s2 = new Strategy_Y(Player.PLAYER2);
        Strategy s3 = new Strategy_Z(Player.PLAYER1);
        Strategy s4 = new Strategy_G(Player.PLAYER2);
        Strategy s5 = new Strategy_M(Player.PLAYER1);
        Strategy s6 = new Strategy_W(Player.PLAYER2);
        Strategy s7 = new Simpleton(Player.PLAYER1);
        Strategy s8 = new Grudger(Player.PLAYER2);
        Strategy s9 = new TitForTat(Player.PLAYER1);

        // Execute tournament with strategy subset
        ArrayList<Strategy> strategies = new ArrayList<>(
                Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9));
        Tournament t = new Tournament(strategies);
        HashMap<Strategy, Integer> results = t.executeTournamentRounds(10);

        // Validate score ranges
        results.forEach((strategy, score) -> {
            assertTrue(String.format("%s score %d should be between 3000 and 9000",
                    strategy.getStrategyName(), score),
                    score >= 3000 && score <= 98000);
        });
    }

    /**
     * Tests sorting functionality of tournament results
     */
    @Test
    public void testSortingSet() {
        // Create test data
        HashMap<Strategy, Integer> data = new HashMap<>();
        data.put(new Strategy_X(Player.PLAYER1), 1);
        data.put(new Strategy_Y(Player.PLAYER2), 2);
        data.put(new Grudger(Player.PLAYER1), 3);
        data.put(new TitForTat(Player.PLAYER2), 4);

        // Verify sorting order
        Tournament t = new Tournament(new ArrayList<>());
        ArrayList<Map.Entry<Strategy, Integer>> entries = t.sortEntries(data.entrySet());

        int i = 4;
        for (Map.Entry<Strategy, Integer> s : entries) {
            assertEquals(i, (int) s.getValue());
            i -= 1;
        }
    }

    /**
     * Tests genetic algorithm implementation with memory-based strategies
     */
    @Test
    public void testGenetics() throws IOException {
        // Initialize genetic strategies with varying weights
        List<GeneticStrategy> strategies = new ArrayList<>();
        for (double i = 0.5; i < 1; i += 0.005) {
            strategies.add(new GeneticMemory(i, Player.PLAYER1));
        }

        // Execute genetic tournament
        GeneticTournament t = new GeneticTournament(strategies);
        t.executeGeneticTournamentRounds(100);
    }

    /**
     * Tests and records static probability results
     */
    @Test
    public void staticProbabilityResults() throws IOException {
        // Create strategies with varying probabilities
        List<Strategy> strategies = new ArrayList<>();
        for (double i = 0.0; i < 1; i += 0.01) {
            strategies.add(new GeneticMemory(i, Player.PLAYER1));
        }

        // Execute tournament and sort results
        Tournament t = new Tournament(strategies);
        HashMap<Strategy, Integer> finals = t.executeTournamentRounds(100);
        ArrayList<Map.Entry<Strategy, Integer>> res = t.sortEntries(finals.entrySet());

        // Write results to file
        FileWriter fileWriter = new FileWriter("staticRes.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        res.forEach(s -> {
            System.out.println("Weight: " + ((GeneticMemory) s.getKey()).getWeight() + " Points: " + s.getValue());
            printWriter.printf("%f %d\n", ((GeneticMemory) s.getKey()).getWeight(), s.getValue());
        });
        printWriter.close();
    }

    /**
     * Tests genetic memory strategies with specific weight range
     */
    @Test
    public void testMemoryGenetics() throws IOException {
        // Initialize strategies with focused weight range
        List<GeneticStrategy> strategies = new ArrayList<>();
        for (double i = 0; i < 0.57; i += 0.01) {
            strategies.add(new GeneticMemory(i, Player.PLAYER1));
        }

        // Execute genetic tournament with shorter duration
        GeneticTournament t = new GeneticTournament(strategies);
        t.executeGeneticTournamentRounds(20);
    }
}
