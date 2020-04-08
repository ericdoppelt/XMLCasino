package GameView;

import Utility.CardTriplet;
import Utility.Formatter;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class HandView implements ViewInterface {

    private HBox myHand;
    private List<CardView> myCards;
    private Formatter myFormatter = new Formatter();


    public HandView(List<CardTriplet> allCards) {
        myHand = new HBox();
        myFormatter.formatUnfixedHBox(myHand);
        myCards = new ArrayList<>();

        for (CardTriplet cardInfo: allCards) {
            CardView tempCardView = new CardView(cardInfo);
            myCards.add(tempCardView);
            myHand.getChildren().add(tempCardView.getView());
        }
    }

    //TODO: copy this? make this unmodifiable...
    public HBox getView() {
        return myHand;
    }

    /**
     * shows first card of its kind
     */
    public void showCard(int id) {
        for (CardView tempCard : myCards) {
            if (tempCard.hasSameID(id)) {
                tempCard.showCard();
                return;
            }
        }
    }

    /**
     *
     * @param newCard
     */
    public void addCardView(CardTriplet newCard) {
        CardView addedCardView = new CardView(newCard);
        myHand.getChildren().add(addedCardView.getView());
    }

}
