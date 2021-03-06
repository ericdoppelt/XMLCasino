package UI.WebView;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Browswer to frame our website using JavaFX
 * @author Max Smith and Robert Duvall
 */
public class Browser implements BrowserInterface {

    private static final String TEST_PAGE = "http://www.casino308.com";
    private static final int BROWSER_SIZE = 600;

    private WebView myWebView;
    private WebEngine myWebEngine;

    /**
     * Construct a browser pointing to a specific URL
     * @param webURL url string we want to visit
     */
    public Browser(String webURL) {
        this.myWebView = new WebView();
        this.myWebEngine = this.myWebView.getEngine();
        this.myWebEngine.load(webURL);
    }

    public Browser() {
        this.myWebView = new WebView();
        this.myWebEngine = this.myWebView.getEngine();
        this.myWebEngine.load(TEST_PAGE);
    }

    /**
     * Render the browswer in a new pane
     */
    @Override
    public void render() {
        Pane root = new Pane();
        root.getChildren().add(makePageDisplay());
        Scene scene = new Scene(root, BROWSER_SIZE, BROWSER_SIZE);
        Stage renderedStage = new Stage();
        renderedStage.setScene(scene);
        renderedStage.show();
    }

    private Node makePageDisplay() {
        return this.myWebView;
    }
}
