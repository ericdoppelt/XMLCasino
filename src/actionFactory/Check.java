package actionFactory;

import engine.bet.Bet;

public class Check extends Action {

    public Check() {
        super();
        System.out.println("Created a check action");
    }

    @Override
    public void execute(Bet target) {
        target.setActive(false);
    }
}
