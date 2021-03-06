package engine.evaluator.bet;

import engine.bet.Bet;
import engine.evaluator.handevaluator.HandEvaluator;
import engine.hand.HandOutcome;
import engine.hand.PlayerHand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Bet evaluaotr module, used to evaluate bets at the end of a round
 * @author Max Smith
 */
public class BetEvaluator implements BetEvaluatorInterface {

    private final HandEvaluator myHandEvaluator;

    /**
     * Constructor for a bet evaluator object (either with or without hand evaluator
     * @param handEvaluator evalutes hands (compared to eachother) in competitive circle
     */
    public BetEvaluator(HandEvaluator handEvaluator) {
        this.myHandEvaluator = handEvaluator;
    }
    public BetEvaluator() {
        this.myHandEvaluator = new HandEvaluator();
    }

    /**
     * Iterates over all of the bets in the collection parameter and assigns each one a winning or losing value stores in a boolean instance variable
     * Multiple bets can be winners given the nature of a push
     * Calls the HandEvaluator compare method often to determine the winner of two hands
     * @param bets is a collection of all bets to iterate over from a turn to assign winning and losing values
     */
    @Override
    public void evaluateBets(List<Bet> bets) {
        PriorityQueue<Bet> pq = new PriorityQueue<>(bets.size(), new BetComparator());
        pq.addAll(bets);
        assignSortedBets(pq);
    }

    private void assignSortedBets(PriorityQueue<Bet> pq) {
        Bet first = pq.poll();
        List<Bet> winners = new ArrayList<Bet>(List.of(first));
        while (traversePQ(pq, first)) {
            winners.add(pq.poll());
        }
        assignWinningBets(winners);
        assignLosingBets(pq);
    }

    private boolean traversePQ(PriorityQueue<Bet> pq, Bet first) {
        BetComparator bc = new BetComparator();
        if (!(pq.isEmpty()))
            return (bc.compare(first, pq.peek()) == 0);
        return false;
    }

    private void assignWinningBets(List<Bet> winners) {
        HandOutcome outcome = HandOutcome.WIN;
        if (winners.size() > 1)
            outcome = HandOutcome.TIE;
        for (Bet b: winners)
            assignOutcome(b.getHand(), outcome);
    }

    private void assignLosingBets(PriorityQueue<Bet> pq) {
        while (!(pq.isEmpty()))
            assignLoser(pq.poll().getHand());
    }

    class BetComparator implements Comparator<Bet>{
        @Override
        public int compare(Bet b1, Bet b2) {
            return - new HandEvaluator().compare(b1.getHand(), b2.getHand());
        }
    }

    /**
     * Evaluate hands against eachother (assume all are classified
     * @param h1 that have been classified in the same competitive pool
     */
    @Override
    public void evaluateHands(PlayerHand h1, PlayerHand h2) {
        int compare = this.myHandEvaluator.compare(h1, h2);
        if (compare > 0)
            compareOverZero(h1, h2);
        else if (compare < 0)
            compareUnderZero(h1, h2);
        else
            compareEqualsZero(h1, h2);
    }

    private boolean notSameBet(Bet b1, Bet b2) {
        return !(b1.getID() == b2.getID());
    }

    private void compareOverZero(PlayerHand h1, PlayerHand h2) {
        assignWinner(h1);
        assignLoser(h2);
    }

    private void compareUnderZero(PlayerHand h1, PlayerHand h2) {
        assignLoser(h1);
        assignWinner(h2);
    }

    private void compareEqualsZero(PlayerHand h1, PlayerHand h2) {
        assignTie(h1);
        assignTie(h2);
    }

    private void assignLoser(PlayerHand h) {
        assignOutcome(h, HandOutcome.LOSS);
    }

    private void assignWinner(PlayerHand h) {
        assignOutcome(h, HandOutcome.WIN);
    }

    private void assignTie(PlayerHand h) {
        assignOutcome(h, HandOutcome.TIE);
    }

    private void assignOutcome(PlayerHand playerHand, HandOutcome outcome) {
        playerHand.setOutcome(outcome);
    }

}
