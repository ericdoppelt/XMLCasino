package actionFactory;

import engine.bet.Bet;

public class Hit extends Action {

    public Hit() {
        super();
        System.out.println("Created a hit action");
    }

    // TODO have a setter for all methods with what they need
    @Override
    public void execute(Bet target) {
        target.setNeedsCard(true);
    }
}
