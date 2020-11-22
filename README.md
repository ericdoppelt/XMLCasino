final
====

This project implements a player for multiple data-driven, card-based games.

Names: Max Smith, Eric Doppelt

### Timeline

Start Date: March 30

Finish Date: April 27

Hours Spent: 200 Hours (4 weeks, 50 hours per week, 25 hours per person)

### Primary Roles
Both Max and Eric spent time designing the overall game, with an emphasis on game engine and data as well as touching on the view macro functionality.

Max was the backend developer. He implemented the game engine and handled the design of the game engine, controller and xml generation, parsing, and validation with regular input from Eric.

Eric was the frontend developer. He implemented the UI and its related classes (GameView, LobbyView, Validation, Settings, Utilities, Icons). He handled the overall design of the View with regular input from Max. Note that Max handled the WebView package and the Help Button functionality.

### Resources Used

For project management, we primarily relied on [Trello](https://trello.com/twentyone8/members?utm_source=eval-email&utm_medium=email&utm_campaign=team-invite), `Facebook`, `iMessage`, and `FaceTime`.

Backend/Data
* [XML Schema W3Schools](https://www.w3schools.com/xml/schema_intro.asp)
* [Priority Queue Implement Custom Comparator](https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/)
* [AWS Host Static Website](https://aws.amazon.com/getting-started/hands-on/host-static-website/)
* [Stack Overflow Multiple File Chooser](https://stackoverflow.com/questions/42972059/javafx-filechooser-open-multiple-files-in-order-of-selection)

Additionaly resources include StackOverflow (all the time), prior CS308 readings (particularly on Command Design patterns and inheritance), as well as code analyzed and developed in class [lab_browser](https://coursework.cs.duke.edu/max.smith/lab_browser) and `Pair`. 

Frontend
* A plethora of Stack Overflow posts used to debug!
* Prof. Duvall's demo code for TestFX
* [Geeks for Geeks on Functional Interface](https://www.geeksforgeeks.org/functional-interfaces-java/)
* [Info on Unary Operators from StackOverflow](https://stackoverflow.com/questions/40472668/numeric-textfield-for-integers-in-javafx-8-with-textformatter-and-or-unaryoperat)
* [Guide to Regular Expressions by Lincoln Baxter](https://www.ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/)
* [Oracle CSS Style Guide](https://openjfx.io/javadoc/11/javafx.graphics/javafx/scene/doc-files/cssref.html)
* [Flat Icon](https://www.flaticon.com/home)

### Running the Program

Main class: `ooga.Main.java`

Data files needed: 
* XML Files
    * Deck
    * View
    * Players
    * LobbyView
    * Hands
    * Game
* Images
	* Are implemented through ResourceBundles in the iconBundle subpackage.
		* fileStatuses (used in Validation)
		* fileTypes (used in Validation)
		* gameBundles (used in GameView)
		* lobbyBundles (used in LobbyView)
	* Actual images can be found in iconImages subdirectory, but displayed icons are always referenced through Properties.
		
* Properties
    *  Languages
    *  Icon bundle tag references
*  CSS Stylesheet
    *  LobbyView styling
    *  GameView styling
    *  Note that Error styling is imbedded in each View XML tag.

Features implemented:
* Data driven
    * Game Logic
    * Lobby View
    * XML Validation
    * Error/Icon images and styling
* Real time view modifications
    * Language updates
    * Darkmode (plus many other CSS stylesheets)
* Preferences
    * View, players, styling all configured through data
* Multiple Games at same time
    * Renderable, cannot make simultaneous actions
* Web Help Page
* File validation cycle

### Notes/Assumptions

Assumptions or Simplifications:
* No changes will occur mid game (once you enter a table, those conditions will hold for the duration of gameplay).
* Aces are either ones or elevens, and cannot exist as both.
* Poker has a simplified set of hands, but it would easy to extend the others.

Interesting data files:
* data/xml/good/VIEW_funnyIcons.XML
* data/xml/good/GAME_pokerxblackjack.XML

Known Bugs:
* Multiple games can be rendered; however, only the most recent game can be played. Once an action is selected, the old game will existt visually, but not prompt for action anymore.
* Very specific ways to break file validation (however defaults assumed with try catch so program won't crash)
* Group game pot is calculated by active wagers. When a player folds, their wager is removed from the pot (should be collected as it is placed)
* HandClassification at end game in the view does not show up for all players (only the last player to act)
* Occasional game will not render correctly when actions occur very quickly

Extra credit:
* Extensive file validation performed via XML Schema
    * Enumeration of known files, validating file formats
* [Website](http://casino308.com/)


### Impressions

Overall, we found this project to be interesesting, fun, and enriching! We specifically enjoyed the ability to pick our own game genre. Since we both love poker, it was definitely more enjoyable to code in a genre that we knew a lot about and felt passionate about.

Additionally, we were grateful that we were able to choose our own partners. We found that we were able to create a postive and collaborative culture in our team, and this was mainly predicated on our existing friendship. Due to the remote aspect of the COVID crisis, we further felt that it generally made the project run smoother. 

It was extremely gratifying seeing all of our work over the semester culminate in a project of this magnitude and scale. We also enjoyed getting experience in other aspects of the "coding" design world, such as Agile/Scrum development, setting our own priorities and timelines, as well as demoing code (that we built!).

Lastly, we feel that this project was further a positive experience because it really honed in our technical and practical skills. We were able to integrate knowledge from across the semester (from JavaFX to lambas!) and gain experience giving demonstrations. Going forward, we believe that this project—more so than any of the past three—will be useful in our future technical careers.

