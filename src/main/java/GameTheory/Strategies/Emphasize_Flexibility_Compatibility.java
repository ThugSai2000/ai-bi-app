package GameTheory.Strategies;

public class Emphasize_Flexibility_Compatibility extends Strategy {
    public Emphasize_Flexibility_Compatibility(Player player) {
        super(player);
    }

    @Override
    public int getStrategy() {
        return 5;
    }
}
