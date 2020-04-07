package GameView;

import Formatting.Formatter;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ResourceBundle;

public class WagerView implements ViewInterface {

    private VBox myWager;
    private static final int VALUE_INDEX = 1;
    private static final double WAGER_HEIGHT = 40;
    private static final double WAGER_WIDTH = 100;
    private static final double CORNER_RADIUS = 5;
    private static final Color backgroundColor = Color.web("2FC436");

    private static final Formatter myFormatter = new Formatter();

    //TODO: make a language option that changes this, keeping it static final to draw attention
    private static final String RESOURCE_LANGUAGE = "English";
    private static final String HEADER_KEY = "WagerHeader";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCE_LANGUAGE);

    public WagerView(double wager) {
        myWager = new VBox();
        myWager.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(5), null)));
        Label wagerHeaderLabel = new Label(myResources.getString(HEADER_KEY));
        Label valueLabel = new Label(String.valueOf(wager));
        myWager.getChildren().addAll(wagerHeaderLabel, valueLabel);
        myFormatter.formatVBox(myWager, WAGER_HEIGHT, WAGER_WIDTH);
    }

    public void updateWager(double newWager) {
        myWager.getChildren().remove(VALUE_INDEX);
        myWager.getChildren().add(VALUE_INDEX, new Label(String.valueOf(newWager)));
    }

    public VBox getView() {
        return myWager; }
    }