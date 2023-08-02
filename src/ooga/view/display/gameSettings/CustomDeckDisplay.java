package ooga.view.display.gameSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.data.GameStarter;
import ooga.data.dataResources.DataConfig;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.dialog.Dialog;
import ooga.view.resources.Resources;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import ooga.view.userAlert.UserAlert;
import org.json.simple.JSONArray;

/**
 * This method allows users to create custom cards for their decks.
 *
 * @author Cate Schick cms168
 */
public final class CustomDeckDisplay extends Display {

  /**
   * Current deck.
   */
  private JSONArray currentDeck;
  /**
   * VBox containing information about a user's new deck.
   */
  private VBox myDeckInfo;

  /**
   * Constructor for Display class.
   *
   * @param v View object associated with Display.
   */
  public CustomDeckDisplay(final View v) {
    super(v);
    initializeDeck();
  }

  /**
   * Initializes card deck.
   */
  private void initializeDeck() {
    try {
      currentDeck = GameStarter.getDrawDeckJSONArr(Config.BASIC_GAME_PATH);
    } catch (Exception e) {
      UserAlert.showError(e);
    }
  }

  /**
   * Updates display with information about added cards.
   *
   * @param userInput User specifications for custom card.
   * @param quantity  Quantity of custom card.
   */
  private void updateDisplay(final String[] userInput, final String quantity) {
    String formatted;

    // NO SECOND TYPE
    if (userInput[2].equals(Resources.getKey(Config.NO_SECOND_TYPE))) {
      String message = Resources.getKey(Config.ONE_TYPE_MESSAGE);
      formatted = String.format(message, quantity, userInput[0], userInput[1], userInput[3]);
    } else {
      String message = Resources.getKey(Config.TWO_TYPE_MESSAGE);
      formatted = String.format(message, quantity, userInput[0], userInput[1], userInput[2],
          userInput[3]);
    }

    // Style label and add to myDeckInfo
    Label label = new Label(formatted);
    label.getStyleClass().addAll(Config.MY_VIEW.getFont(), Config.LABEL_STYLE
        + Config.MY_VIEW.getSize());

    myDeckInfo.getChildren().add(label);
  }

  /**
   * This override method creates a new display for users to create a custom deck.
   *
   * @return Custom deck display.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane myPane = new BorderPane();
    myPane.getStyleClass().add(Config.INFO_DISPLAY);

    myPane.setLeft(Config.MY_VIEW.getMyDisplay().makeBackButton(Config.GAME_SETTINGS_PATH
        + Config.GAME_SETTINGS));
    myPane.setCenter(makeContainer());

    return myPane;
  }

  /**
   * Makes a container for display containing custom deck buttons and information about a user's
   * custom deck.
   *
   * @return HBox with buttons and information about custom deck.
   */
  private HBox makeContainer() {
    HBox h = new HBox();
    h.getChildren().addAll(addButtons(), initializeDeckInfo());
    return h;
  }

  /**
   * This method adds information about a user's custom deck.
   *
   * @return VBox containing information about card deck.
   */
  private VBox initializeDeckInfo() {
    myDeckInfo = new VBox();
    // Add and style title
    Label title = new Label(Resources.getKey(Config.CUSTOM_CARD_LIST));
    title.getStyleClass().addAll(Config.MY_VIEW.getFont(),
        Config.TITLE_STYLE + Config.MY_VIEW.getSize(), Config.TITLE_STYLE);
    myDeckInfo.getChildren().add(title);
    return myDeckInfo;
  }

  /**
   * Adds button options to VBox.
   *
   * @return VBox containing save deck and make card buttons.
   */
  private VBox addButtons() {
    VBox v = new VBox();
    Button finishDeck = ViewButton.makeButton(Resources.getKey(Config.SAVE_DECK), e ->
        saveDeck());
    Button makeCard = ViewButton.makeButton(Resources.getKey(Config.CREATE_CARD), e ->
        customizeCard());
    Button[] buttons = new Button[]{makeCard, finishDeck};
    ViewButton.styleButtons(buttons, v);
    return v;
  }

  /**
   * Returns color options.
   *
   * @return ArrayList of all possible card colors.
   */
  private ArrayList<String> getColorOptions() {
    return new ArrayList<>(
        Arrays.asList(Config.RED, Config.BLUE, Config.GREEN, Config.YELLOW, Config.NONE));
  }

  /**
   * Returns color options.
   *
   * @return ArrayList of all possible card colors.
   */
  private ArrayList<String> getCardTypeOptions() {
    return new ArrayList<>(
        Arrays.asList(Config.NUMBER, Config.REVERSE, Config.SKIP, Config.DRAW));
  }

  /**
   * This method prompts a user to enter necessary card information.
   */
  private void customizeCard() {
    // Prompt user for color choice
    String color = Dialog.getUserChoice(Resources.getKey(Config.CARD_COLOR_PROMPT),
        getColorOptions(), Config.REQUIRED_KEY);

    // Add type, second type, param, and quantity inputs from user
    String cardType = getCardType(color);
    String secondType = getSecondType(cardType);
    String param = Dialog.getUserChoice(Resources.getKey(Config.CARD_PARAM_PROMPT),
        generateList(Config.MIN_CARD_PARAM, Config.MAX_CARD_PARAM), Config.REQUIRED_KEY);
    String quantity = Dialog.getUserChoice(Resources.getKey(Config.CARD_QUANTITY_PROMPT),
        generateList(Config.MIN_CARD_QUANTITY, Config.MAX_CARD_QUANTITY), Config.REQUIRED_KEY);

    // Alert user card was added to deck
    UserAlert.showMessage(Resources.getKey(Config.ADDED_TO_DECK_MESSAGE), Config.SUCCESS);

    // Create object in myDeck with card information
    createCard(new String[]{color, cardType, secondType, param}, quantity);
  }

  /**
   * This method returns a user's specified card type.
   *
   * @param color Color of card.
   * @return Primary type of card.
   */
  private String getCardType(final String color) {
    String cardType;
    if (color.equals(Config.NONE)) {
      UserAlert.showMessage(Resources.getKey(Config.NONE_COLOR_MESSAGE), Config.SUCCESS);
      cardType = Config.WILD;
    } else {
      cardType = Dialog.getUserChoice(Resources.getKey(Config.CARD_TYPE_PROMPT),
          getCardTypeOptions(), Config.REQUIRED_KEY);
    }
    return cardType;
  }

  /**
   * Get user's second type input.
   *
   * @param cardType Card's primary type.
   * @return Card's second type value.
   */
  private String getSecondType(final String cardType) {
    // For secondary type, add the option for users to NOT select any type
    ArrayList<String> secondTypeChoices = getCardTypeOptions();
    secondTypeChoices.add(Resources.getKey(Config.NO_SECOND_TYPE));
    String secondType = Dialog.getUserChoice(Resources.getKey(Config.SECOND_TYPE_PROMPT),
        secondTypeChoices, Config.OPTIONAL_KEY);
    // No duplicate types
    if (secondType.equals(cardType)) {
      secondType = Resources.getKey(Config.NO_SECOND_TYPE);
    }
    return secondType;
  }

  /**
   * This method generates an Arraylist from specified number to another specified number.
   *
   * @param start Starting number.
   * @param stop  Ending number.
   * @return List of whole numbers from start to stop.
   */
  private List<String> generateList(final int start, final int stop) {
    ArrayList<String> list = new ArrayList<>();
    for (int i = start; i <= stop; i++) {
      list.add(String.valueOf(i));
    }
    return list;
  }

  /**
   * This method creates a card with specified parameters and adds to the deck.
   *
   * @param userInput User's input from ChoiceDialogs.
   * @param quantity  How many of these cards to add to the deck.
   */
  private void createCard(final String[] userInput, final String quantity) {
    String color = userInput[0];
    String type = userInput[1];
    String secondType = userInput[2];
    String param = userInput[3];

    HashMap<String, Object> card = new HashMap<>();
    card.put(DataConfig.COLOR_KEY, color.toUpperCase());

    // Adds CardInfo containing param and type as a JSON Object
    HashMap<String, Object> cardParams = new HashMap<>();
    JSONArray cardInfosArray = enterCardInfos(type, secondType, param,
        cardParams);

    // Add JSON array as value
    card.put(DataConfig.CARD_INFOS_KEY, cardInfosArray);

    // Add specified quantity of custom cards
    for (int i = 0; i < Integer.parseInt(quantity); i++) {
      currentDeck.add(card);
    }

    // Update display with information about added card
    updateDisplay(userInput, quantity);
  }

  /**
   * This method converts a card's cardInfos parameter to a JSON array.
   *
   * @param type       Card type param.
   * @param secondType Card's optional second type.
   * @param param      Card's param value.
   * @param cardParams HashMap to construct card object.
   * @return JSONArray of CardInfo params.
   */
  private JSONArray enterCardInfos(final String type, final String secondType,
      final String param, final HashMap<String, Object> cardParams) {
    JSONArray cardInfosArray = new JSONArray();

    // Add param key
    cardParams.put(DataConfig.PARAM_KEY, Integer.parseInt(param));

    // Add either 1 or 2 types depending on value of secondType
    if (secondType.equals(Resources.getKey(Config.NO_SECOND_TYPE))) {
      cardParams.put(DataConfig.TYPE_KEY, type.toLowerCase());
    } else {
      cardParams.put(DataConfig.TYPE_KEY, new ArrayList<>(
          Arrays.asList(type.toLowerCase(), secondType.toLowerCase())));
    }

    // Add to JSON array
    cardInfosArray.add(cardParams);
    return cardInfosArray;
  }

  /**
   * This method saves the current deck.
   */
  private void saveDeck() {
    // Don't save an empty deck.
    if (currentDeck.size() == 0) {
      UserAlert.showMessage(Resources.getKey(Config.EMPTY_DECK), Config.CANNOT_COMPLETE);
    } else {
      GameSettings.setDeck(currentDeck);
      this.switchDisplay(Config.GAME_SETTINGS_PATH + Config.GAME_SETTINGS);
    }
  }
}
