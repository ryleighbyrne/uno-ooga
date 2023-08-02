package ooga.view.display.gameSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ooga.data.GameSaver;
import ooga.data.GameStarter;
import ooga.data.dataResources.DataConfig;
import ooga.data.dataExceptions.InvalidFileNameException;
import ooga.view.dialog.Dialog;
import ooga.view.Config;
import ooga.view.resources.Resources;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import ooga.view.userAlert.UserAlert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class allows a user to create Custom Game Settings.
 *
 * @author Cate Schick cms168
 */
public final class GameSettings extends Display {

  /**
   * Number of cards each hand has in the game (Default 7).
   */
  private int numCards = 7;
  /**
   * Number of players in game (Default 4).
   */
  private int numPlayers = 4;
  /**
   * Current game version.
   */
  private String unoType = "basic";
  /**
   * An arraylist of user preferences.
   */
  private final HashMap<String, Object> customPreferences;
  /**
   * Users deck.
   */
  private static JSONArray myDeck;

  /**
   * Constructor for Display class.
   *
   * @param v The view associated with Game Settings display.
   */
  public GameSettings(final View v) {
    super(v);
    customPreferences = new HashMap<>();
  }

  /**
   * Override method creates Game Preferences display.
   *
   * @return Game Preference display.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane gameSettings = new BorderPane();
    super.setTitle(gameSettings, new Label(
        Resources.getKey(Config.GAME_SETTINGS_TITLE)));
    super.setDisplayStyle(Config.GAME_SETTINGS, gameSettings);

    // Add display content to display and center
    VBox container = new VBox();
    container.getChildren().addAll(addSettings(), generateGameButton());
    gameSettings.setCenter(container);

    return gameSettings;
  }

  /**
   * This button creates Generate game button.
   *
   * @return Generate game button.
   */
  private Button generateGameButton() {
    Button b = ViewButton.makeButton(Resources.getKey(Config.GENERATE_GAME), e ->
        getCustomPreferences());
    ViewButton.setButtonSize(b, Config.BUTTON_STYLE);
    b.getStyleClass().add(Config.GENERATE_GAME);
    return b;
  }

  /**
   * This method sets design setting content options into an organized display.
   *
   * @return GridPane with settings.
   */
  private GridPane addSettings() {
    // Add game options
    GridPane grid = new GridPane();
    grid.getStyleClass().add(Config.GRIDPANE_STYLE);

    Button numCards = ViewButton.makeButton(Resources.getKey(Config.CHANGE_CARDS), e ->
        changeNumCards());
    Button numPlayers = ViewButton.makeButton(Resources.getKey(Config.CHANGE_PLAYERS), e ->
        changeNumPlayers());
    Button gameVersion = ViewButton.makeButton(Resources.getKey(Config.CHANGE_VERSION), e ->
        changeGameVersion());
    Button customDeck = ViewButton.makeButton(Resources.getKey(Config.CUSTOM_DECK), e ->
        this.switchDisplay(Config.GAME_SETTINGS_PATH + Config.CUSTOM_DECK_DISPLAY));

    Button[] buttons = new Button[]{numCards, gameVersion, numPlayers, customDeck};
    addButtons(grid, buttons);

    return grid;
  }

  /**
   * This method adds Game Settings button to display.
   *
   * @param grid    Gridpane buttons are being added to.
   * @param buttons Button array containing all game settings buttons.
   */
  private void addButtons(final GridPane grid, final Button[] buttons) {
    int row = 0;
    int col = 0;
    for (Button b : buttons) {
      ViewButton.setButtonSize(b, Config.BUTTON_STYLE);
      grid.add(b, row, col);
      row++;
      if (row >= Config.GAME_SETTINGS_MAX_PER_ROW) {
        row = 0;
        col++;
      }
    }
  }

  /**
   * This map returns all custom settings requested by the user.
   */
  public void getCustomPreferences() {
    customPreferences.put(DataConfig.NUM_PLAYERS_KEY, getNumPlayers());
    customPreferences.put(DataConfig.STARTED_KEY, false);
    customPreferences.put(DataConfig.PLAY_DECK_KEY, new ArrayList<>());
    customPreferences.put(DataConfig.PLAYERS_KEY, new ArrayList<>());
    customPreferences.put(DataConfig.CARDS_PER_PLAYER_KEY, numCards);
    customPreferences.put(DataConfig.UNO_TYPE_KEY, unoType);

    // If deck is null, fill it with default deck
    fillDeck();

    customPreferences.put(DataConfig.DRAW_DECK_KEY, myDeck);

    // Write to a JSON file
    try {
      JSONObject jsonMap = new JSONObject(customPreferences);
      GameSaver.writeGameObjToFile(jsonMap, Config.CUSTOM_FILENAME);
      Config.MY_VIEW.notifyObservers(ooga.Config.ADD_LOAD_FILE_OBSERVERS, null);
      notifyObservers(ooga.Config.LOAD_FILE_FOR_NEW_GAME, Config.CUSTOM_GAME_FILEPATH);
    } catch (InvalidFileNameException e) {
      UserAlert.showError(e);
    }
  }

  /**
   * This method fills an empty deck with default deck information.
   */
  private void fillDeck() {
    // If user hasn't entered a custom deck, use default
    try {
      if (myDeck == null) {
        myDeck = GameStarter.getDrawDeckJSONArr(Config.BASIC_GAME_PATH);
      }
    } catch (Exception e) {
      UserAlert.showError(e);
    }
  }

  /**
   * Sets new deck to the one created by user.
   */
  public static void setDeck(JSONArray customDeck) {
    myDeck = customDeck;
  }

  /**
   * This method allows the user to change their game version.
   */
  private void changeGameVersion() {
    ArrayList<String> gameList = new ArrayList<>();

    // Add game version options
    Collections.addAll(gameList, Config.BASIC, Config.PIRATE, Config.RANDOM);

    String choice = Dialog.getUserChoice(Resources.getKey(Config.GAME_VERSION),
        gameList, Resources.getKey(Config.REQUIRED_KEY));

    if (choice != null) {
      unoType = choice;
    }
  }

  /**
   * This method adds the default card settings to the display.
   */
  private void changeNumCards() {
    String input = Dialog.getUserChoice(Resources.getKey(Config.NUM_CARDS),
        generateList(Config.MIN_CARDS, Config.MAX_CARDS), Resources.getKey(Config.REQUIRED_KEY));

    if (input != null) {
      numCards = Integer.parseInt(input);
    }
  }

  /**
   * This method generates an Arraylist from specified number to another specified number.
   *
   * @param start Starting number.
   * @param stop  Ending number.
   * @return List of whole numbers from start to stop.
   */
  private List<String> generateList(int start, int stop) {
    ArrayList<String> list = new ArrayList<>();
    for (int i = start; i <= stop; i++) {
      list.add(String.valueOf(i));
    }
    return list;
  }

  /**
   * This method changes the number of players in an UNO game.
   */
  private void changeNumPlayers() {
    String input = Dialog.getUserChoice(Resources.getKey(Config.NUM_PLAYERS),
        generateList(Config.MIN_PLAYERS, Config.MAX_PLAYERS),
        Resources.getKey(Config.REQUIRED_KEY));

    if (input != null) {
      numPlayers = Integer.parseInt(input);
    }
  }

  /**
   * This method returns how many players are in the game.
   *
   * @return numPlayers variable.
   */
  public int getNumPlayers() {
    return numPlayers;
  }
}
