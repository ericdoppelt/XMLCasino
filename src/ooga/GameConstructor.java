package ooga;

import UI.GameView.GameView;
import UI.Validation.XMLFileType;
import Utility.StringPair;
import Utility.handbundle.HandBundle;
import controller.bundles.ControllerBundle;
import controller.enums.Cardshow;
import controller.enums.EntryBet;
import controller.enums.Goal;
import controller.gametype.Controller;
import engine.dealer.Dealer;
import engine.deck.Deck;
import engine.deck.DeckFactory;
import engine.evaluator.bet.BetEvaluator;
import engine.evaluator.handclassifier.HandClassifier;
import engine.evaluator.handevaluator.HandEvaluator;
import engine.player.Player;
import engine.player.PlayerList;
import engine.table.Table;
import exceptions.GeneralXMLException;
import exceptions.ReflectionException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import xml.xmlreader.readers.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * GIven pointers to set of files (from LobbyView), constructs the game
 * @author Max Smith
 */
public class GameConstructor {

    private static final String CONTROLLER_PATH = "controller.gametype";
    private static final String CONTROLLER_SUFFX = "Controller";
    private final Consumer<Exception> myExceptionShow;

    private final File deckFile;
    private final File gameFile;
    private final File playerFile;
    private final File handFile ;
    private final File viewFile;

    private static final String TYPE_KEY = "Type";

    /**
     * Given a set of files and exception thrower, create a game
     * @param myFiles map of xmlfiles to files
     * @param exceptionShow consumer to throw exceptions in the view
     */
    public GameConstructor(Map<XMLFileType, File> myFiles, Consumer<Exception> exceptionShow) {
        this.myExceptionShow = exceptionShow;
        deckFile = myFiles.get(XMLFileType.DECK);
        gameFile = myFiles.get(XMLFileType.GAME);
        playerFile = myFiles.get(XMLFileType.PLAYERS);
        handFile = myFiles.get(XMLFileType.HANDS);
        viewFile = myFiles.get(XMLFileType.VIEW);
        createGame();
    }

    private void createGame() {
        try {
            GameReader gameReader = new GameReader(gameFile);
            HandReader handReader = new HandReader(handFile);
            PlayerReader playerReader = new PlayerReader(playerFile);
            ViewReader viewReader = new ViewReader(viewFile);
            DeckReader deckReader = new DeckReader(deckFile);

            Table myTable = constructTable(gameReader, playerReader, deckReader);
            GameView myGameView = constructGameView(viewReader);

            Controller myController = constructController(gameReader, handReader, myTable, myGameView);
            myController.startGame();
        } catch (GeneralXMLException | ReflectionException e) {
            this.myExceptionShow.accept(e);
        }

    }

    private Collection<Player> createPlayerList(PlayerReader playerReader) {
        Collection<Utility.Pair> playerCollection = playerReader.getPlayers();
        PlayerList myPlayers = new PlayerList(playerCollection);
        return myPlayers.getPlayers();
    }

    private Table constructTable(GameReader gameReader, PlayerReader playerReader, DeckReader deckReader) {
        Collection<Player> playerList = createPlayerList(playerReader);
        List<StringPair> deckList = deckReader.getDeck();
        double tableMin = gameReader.getTableMin();
        double tableMax = gameReader.getTableMax();
        String deckType = deckReader.getType();
        Deck myDeck = createDeck(deckList, deckType);
        Dealer myDealer = new Dealer(myDeck);
        Table myTable = new Table(playerList, myDealer, tableMin, tableMax);

        return myTable;
    }

    private Deck createDeck(List<StringPair> deckList, String deckType) throws ReflectionException {
        try {
            DeckFactory factory = new DeckFactory();
            return factory.createDeck(deckList, deckType);
        } catch (Exception e) {
            throw new ReflectionException();
        }
    }

    private Controller constructController(GameReader gameReader, HandReader handReader, Table table, GameView gameView) throws ReflectionException {
        Map<String, String> myParams = gameReader.getCompetition();
        String myCompetition = myParams.get(TYPE_KEY);
        ControllerBundle myBundle = createControllerBundle(gameReader, handReader, table, gameView);

        try {
            String controllerPath = createControllerPath(myCompetition);
            Class clazz = Class.forName(controllerPath);
            Constructor ctor = clazz.getConstructor(ControllerBundle.class, Map.class);
            return (Controller) ctor.newInstance(myBundle, myParams);
        } catch (Exception e) {
            this.myExceptionShow.accept(new ReflectionException(e));
            throw new ReflectionException(e);
        }
    }

    private ControllerBundle createControllerBundle(GameReader gameReader, HandReader handReader, Table table, GameView gameView) {
        Collection<String> myPlayerActions = gameReader.getPlayerAction();
        List<StringPair> myDealerAction = gameReader.getDealerAction();
        HandClassifier myHandClassifier = createHandClassifier(handReader);
        HandEvaluator myHandEvaluator = new HandEvaluator();
        BetEvaluator myBetEvaluator = new BetEvaluator(myHandEvaluator);

        String myEntryBet = gameReader.getEntryBet();
        String myCardShow = gameReader.getCardShow();
        String myGoal = gameReader.getGoal();

        return new ControllerBundle(
                table, gameView, myEntryBet, myPlayerActions, myDealerAction,
                myHandClassifier, myBetEvaluator,
                myCardShow, myGoal);
    }

    private HandClassifier createHandClassifier(HandReader handReader) {
        List<HandBundle> myWinningHands = handReader.getWinningHands();
        List<HandBundle> myLosingHands = handReader.getLosingHands();
        int myCardsInHand = Integer.MAX_VALUE;
        try {
            myCardsInHand = handReader.getCardsInHand();
        } catch (Exception ignored) {
            ;
        }
        return new HandClassifier(myWinningHands, myLosingHands, myCardsInHand);
    }

    private String createControllerPath(String competition) {
        return String.format("%s.%s%s", CONTROLLER_PATH, competition, CONTROLLER_SUFFX);
    }

    private GameView constructGameView(ViewReader viewReader) {
        List<String> stylesheets = viewReader.getStylesheets();
        List<String> languages = viewReader.getLanguages();
        String iconImages = viewReader.getIconBundle();
        String exceptionCSS = viewReader.getErrorStylesheet();
        int width = viewReader.getScreenWidth();
        int height = viewReader.getScreenHeight();

        GameView gameView = new GameView(stylesheets, languages, iconImages, exceptionCSS, width, height);
        Stage newGameStage = new Stage();
        newGameStage.setScene(new Scene(gameView.getView(), width, height));
        newGameStage.show();
        return gameView;
    }

}
