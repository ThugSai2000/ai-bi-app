package GameTheory.Strategies;

public class Focus_On_High_Performance extends Strategy {
    public Focus_On_High_Performance(Player player) {
        super(player);
    }

    @Override
    public int getStrategy() {
        return 2;
    }
}
