package UI.Settings;

import UI.IconFactories.Icon;
import UI.WebView.Browser;

public class HelpButton extends Icon {

    public HelpButton(String helpIconPath) {
        super(helpIconPath);
        myIcon.setOnMouseClicked(e -> {
            try {
                Browser myBrowser = new Browser();
                myBrowser.render();
            } catch (Exception ex) {
               //FIXME: throw an error here
            }
        });
    }
}
