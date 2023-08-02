package ooga.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.PropertyChangeObservable;
import ooga.Config;
import ooga.view.button.ViewButton;
import ooga.view.resources.Resources;

public class CenterDecks extends PropertyChangeObservable {

  /**
   * Card that tracks the current card in the discard pile
   */
  private Card discardCard;
  /**
   * Label that tracks and displays game narration;
   */
  private Label activeText;
  /**
   * Card that tracks the draw card button
   */
  private Button drawCardButton;
  /**
   * String for Draw button
   */
  private static final String DRAW = "Draw";
  /**
   * String for Pass button
   */
  private static final String PLAY = "Play";
  /**
   * Int for default deck width
   */
  private static final int deckWidth = 125;
  /**
   * Int for default deck height
   */
  private static final int deckHeight = 225;
  /**
   * String key for Discard Pile
   */
  private static final String DISCARD_DECK_STYLE = "DiscardPile";
  /**
   * String key for Pirate Swap message
   */
  private static final String PIRATE_SWAP_START = "EXECUTING PIRATE SWAP! Player ";
  /**
   * String key for Pirate Swap message end
   */
  private static final String PIRATE_SWAP_END = " and a random player are swapping hands!";
  /**
   * String key for alerting the user of their turn
   */
  private static final String YOUR_TURN_MESSAGE = "It's your turn!";
  /**
   * Key used for button on style sheet.
   */
  private static final String BUTTON_STYLE = "Button";
  /**
   * Key used for button on style sheet.
   */
  private static final String NARRATION_STYLE = "Narration_Text";
  /**
   * Key used for button on style sheet.
   */
  private static final String DRAW_CARD_NOW_STYLE = "Draw_Card_Now";
  /**
   * String key for Draw Deck
   */
  private static final String DRAW_DECK_STYLE = "DrawDeck";
  /**
   * String key for Center Decks
   */
  private static final String CENTER_DECKS_STYLE = "CenterDecks";

  /**
   * Resources object
   */
  private final ResourceBundle myResources;
  /**
   * Player Hand object
   */
  private PlayerHand myPlayerHand;
  private HBox deckDisplay;

  public CenterDecks() {
    myResources = Resources.getResources(Resources.getPath());
    myPlayerHand = new PlayerHand();
  }

  public HBox updateDiscardDeckTopCard(Card card){
    discardCard = card;
    discardCard.setDimensions(deckWidth, deckHeight);
    return deckDisplay;
  }

  public HBox createCenterDecks(PlayerHand playerHand, String backOfCard) {
    myPlayerHand = playerHand;
    HBox centerDecks = new HBox();
    displayPlayCardMessage();
    centerDecks.getStyleClass().add(CENTER_DECKS_STYLE);
    centerDecks.getChildren().addAll(createDiscardPile(), createDrawDeck(backOfCard), createButtonPanel(playerHand), activeText);
    deckDisplay = centerDecks;
    return centerDecks;
  }

  private Card createDiscardPile() {
    if (discardCard==null) {
      discardCard = new Card(List.of(" "), "NONE", List.of(2));
    }
    discardCard.setDimensions(deckWidth, deckHeight);
    return discardCard;
  }

//  public void tellPlayerToDrawCard(){
//    drawCardButton.getStyleClass().add(DRAW_CARD_NOW_STYLE);
//    createCenterDecks(myPlayerHand);
//    drawCardButton.getStyleClass().remove(DRAW_CARD_NOW_STYLE);
//  }
//
//  public void makeDrawDeckUnavailable(){
//    drawCardButton.setDisable(true);
//  }

  private Card createDrawDeck(String backOfCard) {
    List<String> testValues = new ArrayList<>();
    List<Integer> testParameters = new ArrayList<>();
    testValues.add("number");
    testParameters.add(3);
    Card drawDeck;
    if (backOfCard != null) {
      drawDeck = new Card(testValues, "red", true, testParameters, backOfCard);
    } else {
      drawDeck = new Card(testValues, "red", true, testParameters);
    }
    drawDeck.setDimensions(deckWidth, deckHeight);
    return drawDeck;
  }

  private VBox createButtonPanel(PlayerHand playerHand) {
    myPlayerHand = playerHand;
    VBox playButtons = new VBox();
//    drawCardButton = ViewButton.makeButton(DRAW,
//        e -> {notifyObservers(Config.CARD_DRAWN_BY_VIEW, null);});
//    drawCardButton.getStyleClass().add(BUTTON_STYLE);
    Button playCard = ViewButton.makeButton(PLAY,
        e -> {if (playerHand.getSelectedCard()!= null && playerHand.getCurrentPlayer()==0) {
          System.out.format("Card just played by real player: %s %s %s\n",playerHand.getSelectedCard().getColor(),
                  playerHand.getSelectedCard().getParameters(), playerHand.getSelectedCard().getValue());
          Card card = playerHand.getSelectedCard();
          //playerHand.removePlayedCard();
          notifyObservers(Config.CARD_SELECTED_TO_PLAY, card);
        }
        });
    playCard.getStyleClass().add(BUTTON_STYLE);
    playButtons.getChildren().addAll(playCard);
    return playButtons;
  }

  public void setDeckDisplay(HBox newDeckDisplay) {
    deckDisplay = newDeckDisplay;
    ooga.view.Config.MY_VIEW.getRoot().setCenter(
        ooga.view.Config.MY_VIEW.getMyDisplay().createDisplay());
  }

  public void displayPirateSwapMessage() {
    activeText.setText(PIRATE_SWAP_START + myPlayerHand.getCurrentPlayer() + PIRATE_SWAP_END);
  }

  public void displayPlayCardMessage() {
    activeText = new Label();
    activeText.setText(YOUR_TURN_MESSAGE);
    activeText.getStyleClass().add(NARRATION_STYLE);
  }

  public Label getActiveText() {
    return activeText;
  }

  public HBox getDeckDisplay() {
    return deckDisplay;
  }

  public Card getDiscardCard() {
    return discardCard;
  }
}
