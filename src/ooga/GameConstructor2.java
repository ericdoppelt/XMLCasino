package ooga;

import GameView.NodeViews.GameView;
import controller.Controller;
import data.xmlreader.Pair;
import data.xmlreader.XMLReader;
import engine.dealer.Dealer;
import engine.dealer.Deck;
import engine.evaluator.BetEvaluator;
import engine.evaluator.HandClassifier;
import engine.evaluator.HandEvaluator;
import engine.player.Player;
import engine.player.PlayerList;
import engine.table.Table;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class GameConstructor2 extends Application {

    private static final String testFile = "src/data/game/blackjackGame.xml";

    private static List<Player> createPlayerList(Map<String, Double> playerMap) {
        PlayerList myPlayers = new PlayerList(playerMap);
        return myPlayers.getPlayers();
    }

    private static void traverseList(List<String> list) {
        for (String s: list) {
            System.out.println(s);
        }
    }

    private static Table constructTable(XMLReader myReader) {
        Map<String, Double> playerMap = myReader.getPlayers();
        List<Player> playerList = createPlayerList(playerMap);
        List<Pair> deckList = myReader.getDeck();
        Deck myDeck = new Deck(deckList);
        Dealer myDealer = new Dealer(myDeck);

        List<String> myWinningHands = myReader.getWinningHands();
        List<String> myLosingHands = myReader.getLosingHands();
        HandClassifier myHandClassifier = new HandClassifier(myWinningHands, myLosingHands);
        HandEvaluator myHandEvaluator = new HandEvaluator();
        BetEvaluator myBetEvaluator = new BetEvaluator(myHandEvaluator);
        Table myTable = new Table(playerList, myDealer, myBetEvaluator, myHandClassifier);
        return myTable;
    }

    private static Controller constructController(XMLReader myReader, Table myTable, GameView myGameView) {
        String myEntryBet = myReader.getEntryBet();
        List<String> myPlayerActions = myReader.getPlayerAction();
        Pair myDealerAction = myReader.getDealerAction();
        return new Controller(myTable, myGameView, myEntryBet, myPlayerActions, myDealerAction);
    }

    // TODO - refactor game code into non static objects, currently running through main must be static
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        XMLReader myReader = new XMLReader(testFile);
        Table myTable = constructTable(myReader);
        Controller myController = constructController(myReader, myTable, new GameView());
        myController.startGame();
    }
}