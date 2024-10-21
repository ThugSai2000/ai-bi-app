// package GameTheory;

// import GameTheory.Strategies.*;
// import org.junit.Test;

// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.*;

// import static org.junit.Assert.assertEquals;

// public class TournamentTest {

// @Test
// public void executeGenericTournamentRoundsTest() {

// HashMap<String, Integer> truthMap = new HashMap<>();
// truthMap.put("AlwaysDefect", 570);
// truthMap.put("AlwaysCooperate", 700);
// truthMap.put("Grudger", 790);
// truthMap.put("Simpleton", 750);
// truthMap.put("TitForTat", 790);
// truthMap.put("TitForTatTat", 780);

// Strategy s1 = new AlwaysCooperate();
// Strategy s2 = new AlwaysDefect();
// Strategy s3 = new Grudger();
// Strategy s4 = new Simpleton();
// Strategy s5 = new TitForTat();
// Strategy s6 = new TitForTatTat();

// ArrayList<Strategy> strategies = new ArrayList<>(
// Arrays.asList(
// s1, s2, s3, s4, s5, s6
// )
// );

// Tournament t = new Tournament(strategies);
// HashMap<Strategy, Integer> results = t.executeTournamentRounds(10);

// results.forEach((s, v) ->
// assertEquals(v, truthMap.get(s.getStrategyName()))
// );
// }

// @Test
// public void executeRepeatedTournamentRoundsTest() {

// HashMap<String, Integer> truthMap = new HashMap<>();
// truthMap.put("AlwaysDefect", 930);
// truthMap.put("AlwaysCooperate", 400);
// truthMap.put("TitForTat", 580);

// Strategy s1 = new AlwaysCooperate();
// Strategy s2 = new AlwaysCooperate();
// Strategy s3 = new AlwaysCooperate();
// Strategy s4 = new AlwaysDefect();
// Strategy s5 = new AlwaysDefect();
// Strategy s6 = new TitForTat();

// ArrayList<Strategy> strategies = new ArrayList<>(
// Arrays.asList(
// s1, s2, s3, s4, s5, s6
// )
// );

// Tournament t = new Tournament(strategies);
// HashMap<Strategy, Integer> results = t.executeTournamentRounds(10);

// results.forEach((s, v) ->
// assertEquals(truthMap.get(s.getStrategyName()), v)
// );
// }

// @Test
// public void testSortingSet() {

// HashMap<Strategy, Integer> data = new HashMap<>();
// data.put(new AlwaysCooperate(), 1);
// data.put(new AlwaysDefect(), 2);
// data.put(new Grudger(), 3);
// data.put(new TitForTat(), 4);

// Tournament t = new Tournament(new ArrayList<>());
// ArrayList<Map.Entry<Strategy, Integer>> entries =
// t.sortEntries(data.entrySet());

// int i = 4;
// for (Map.Entry<Strategy, Integer> s : entries) {
// assertEquals(i, (int)s.getValue());
// i -= 1;
// }
// }

// @Test
// public void testGenetics() throws IOException {

// List<GeneticStrategy> strategies = new ArrayList<>();
// for (double i = 0.5; i < 1; i += 0.005) {
// strategies.add(new GeneticOneMove(i));
// }

// GeneticTournament t = new GeneticTournament(strategies);

// t.executeGeneticTournamentRounds(100);
// }

// @Test
// public void staticProbabilityResults() throws IOException {

// List<Strategy> strategies = new ArrayList<>();
// for (double i = 0.0; i < 1; i += 0.01) {
// strategies.add(new GeneticOneMove(i));
// }
// Tournament t = new Tournament(strategies);

// HashMap<Strategy, Integer> finals = t.executeTournamentRounds(100);
// ArrayList<Map.Entry<Strategy, Integer>> res =
// t.sortEntries(finals.entrySet());

// FileWriter fileWriter = new FileWriter("staticRes.txt");
// PrintWriter printWriter = new PrintWriter(fileWriter);

// res.forEach(s -> {
// System.out.println("Weight: " + ((GeneticOneMove) s.getKey()).getWeight() + "
// Points: " + s.getValue());
// printWriter.printf("%f %d\n", ((GeneticOneMove) s.getKey()).getWeight(),
// s.getValue());
// });

// printWriter.close();
// }

// @Test
// public void testMemoryGenetics() throws IOException {

// List<GeneticStrategy> strategies = new ArrayList<>();
// for (double i = 0; i < 0.57; i += 0.001) {
// strategies.add(new GeneticMemory(i));
// }

// GeneticTournament t = new GeneticTournament(strategies);

// t.executeGeneticTournamentRounds(50);
// }
// }
