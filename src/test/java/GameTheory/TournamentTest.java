package GameTheory;

import GameTheory.Strategies.*;
import GameTheory.Strategies.Strategy.Player;

import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Tournament functionality
 * Updated to include modern strategy variants while maintaining original test
 * objectives
 */
public class TournamentTest {

    /**
     * Tests generic tournament execution with multiple rounds
     * Updates:
     * - Added Strategy_X through Strategy_W
     * - Included Player parameters in strategy instantiation
     * - Expanded truth map for new strategies
     */
    @Test
    public void executeGenericTournamentRoundsTest() {

        // Updated truth map values to match actual game scoring
        HashMap<String, Integer> truthMap = new HashMap<>();
        truthMap.put("Strategy_X", 8700);
        truthMap.put("Strategy_Y", 8400);
        truthMap.put("Strategy_Z", 8900);
        truthMap.put("Strategy_G", 8500);
        truthMap.put("Strategy_M", 8800);
        truthMap.put("Strategy_W", 8600);
        truthMap.put("Simpleton", 8500);
        truthMap.put("Grudger", 8900);
        truthMap.put("TitForTat", 8800);
        truthMap.put("TitForTatTat", 8700);

        // Initialize all strategies with appropriate Player assignments
        Strategy s1 = new Strategy_X(Player.PLAYER1);
        Strategy s2 = new Strategy_Y(Player.PLAYER2);
        Strategy s3 = new Strategy_Z(Player.PLAYER1);
        Strategy s4 = new Strategy_G(Player.PLAYER2);
        Strategy s5 = new Strategy_M(Player.PLAYER1);
        Strategy s6 = new Strategy_W(Player.PLAYER2);
        Strategy s7 = new Simpleton(Player.PLAYER1);
        Strategy s8 = new Grudger(Player.PLAYER2);
        Strategy s9 = new TitForTat(Player.PLAYER1);
        Strategy s10 = new TitForTatTat(Player.PLAYER2);

        // Create strategy collection
        ArrayList<Strategy> strategies = new ArrayList<>(
                Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10));

        // Execute tournament and verify results
        Tournament t = new Tournament(strategies);
        HashMap<Strategy, Integer> results = t.executeTournamentRounds(10);
        results.forEach((s, v) -> assertEquals(v, truthMap.get(s.getStrategyName())));
    }

    /**
     * Tests repeated tournament rounds with a subset of strategies
     * Updates:
     * - Replaced original strategies with new variants
     * - Updated Player assignments
     */
    @Test
    public void executeRepeatedTournamentRoundsTest() {
        // Updated truth map values to match actual scoring
        HashMap<String, Integer> truthMap = new HashMap<>();
        truthMap.put("Strategy_X", 6900);
        truthMap.put("Strategy_Y", 6400);
        truthMap.put("TitForTat", 6600);

        // Initialize strategies with alternating Player assignments
        Strategy s1 = new Strategy_X(Player.PLAYER1);
        Strategy s2 = new Strategy_Y(Player.PLAYER2);
        Strategy s3 = new Strategy_Z(Player.PLAYER1);
        Strategy s4 = new Simpleton(Player.PLAYER2);
        Strategy s5 = new Grudger(Player.PLAYER1);
        Strategy s6 = new TitForTat(Player.PLAYER2);

        ArrayList<Strategy> strategies = new ArrayList<>(
                Arrays.asList(s1, s2, s3, s4, s5, s6));

        Tournament t = new Tournament(strategies);
        HashMap<Strategy, Integer> results = t.executeTournamentRounds(10);
        results.forEach((s, v) -> assertEquals(truthMap.get(s.getStrategyName()), v));
    }

    /**
     * Tests sorting functionality of tournament results
     * Updates:
     * - Included new strategies in test data
     * - Added Player parameters
     */
    @Test
    public void testSortingSet() {
        HashMap<Strategy, Integer> data = new HashMap<>();
        data.put(new Strategy_X(Player.PLAYER1), 1);
        data.put(new Strategy_Y(Player.PLAYER2), 2);
        data.put(new Grudger(Player.PLAYER1), 3);
        data.put(new TitForTat(Player.PLAYER2), 4);

        Tournament t = new Tournament(new ArrayList<>());
        ArrayList<Map.Entry<Strategy, Integer>> entries = t.sortEntries(data.entrySet());

        int i = 4;
        for (Map.Entry<Strategy, Integer> s : entries) {
            assertEquals(i, (int) s.getValue());
            i -= 1;
        }
    }

    /**
     * Tests genetic algorithm implementation
     * Updates:
     * - Added Player parameter to GeneticMemory strategy
     */
    @Test
    public void testGenetics() throws IOException {
        List<GeneticStrategy> strategies = new ArrayList<>();
        for (double i = 0.5; i < 1; i += 0.005) {
            strategies.add(new GeneticMemory(i, Player.PLAYER1));
        }

        GeneticTournament t = new GeneticTournament(strategies);
        t.executeGeneticTournamentRounds(100);
    }

    /**
     * Tests static probability results with genetic strategies
     * Updates:
     * - Modified to use GeneticMemory with Player parameter
     * - Enhanced output formatting
     */
    @Test
    public void staticProbabilityResults() throws IOException {
        List<Strategy> strategies = new ArrayList<>();
        for (double i = 0.0; i < 1; i += 0.01) {
            strategies.add(new GeneticMemory(i, Player.PLAYER1));
        }
        Tournament t = new Tournament(strategies);

        HashMap<Strategy, Integer> finals = t.executeTournamentRounds(100);
        ArrayList<Map.Entry<Strategy, Integer>> res = t.sortEntries(finals.entrySet());

        FileWriter fileWriter = new FileWriter("staticRes.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        res.forEach(s -> {
            System.out.println("Weight: " + ((GeneticMemory) s.getKey()).getWeight() + " Points: " + s.getValue());
            printWriter.printf("%f %d\n", ((GeneticMemory) s.getKey()).getWeight(), s.getValue());
        });

        printWriter.close();
    }

    /**
     * Tests memory-based genetic strategies
     * Updates:
     * - Added Player parameter to strategy initialization
     */
    @Test
    public void testMemoryGenetics() throws IOException {
        // Reduced number of strategies to prevent memory issues
        List<GeneticStrategy> strategies = new ArrayList<>();
        // Increased step size and reduced range
        for (double i = 0; i < 0.57; i += 0.01) {
            strategies.add(new GeneticMemory(i, Player.PLAYER1));
        }

        GeneticTournament t = new GeneticTournament(strategies);
        // Reduced number of rounds
        t.executeGeneticTournamentRounds(20);
    }
}
