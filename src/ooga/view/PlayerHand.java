package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.Config;
import ooga.PropertyChangeObservable;
import ooga.view.resources.Resources;

public class PlayerHand extends PropertyChangeObservable {

  /**
   * Object that tracks the current player (whose hand it is)
   */
  private int myCurrentPlayer;
  /**
   * Object that tracks the number of players in the game
   */
  private int myNumberOfPlayers;
  /**
   * Object that contains all vertical hands (left and right)
   */
  private List<VBox> verticalHands;
  /**
   * Object that contains all horizontal hands (top and bottom)
   */
  private List<HBox> horizontalHands;
  /**
   * Object that tracks the valid cards
   */
  private List<Integer> myValidCards;
  /**
   * Object that tracks the card colors by player
   */
  private List<List<String>> myHandCardColors;
  /**
   * Object that tracks the card types by player
   */
  private List<List<List<String>>> myHandCardTypes;
  /**
   * Object that tracks the card numbers by player
   */
  private List<List<List<Integer>>> myHandCardParameters;
  /**
   * String for PlayerHand
   */
  private static final String PLAYER_HAND_STYLE = "PlayerHand";
  /**
   * CardFactory object
   */
  private final FactoryComponents myFactoryComponents;
  private String cardBackImg;

  /**
   * Constructor for PlayerHand
   */
  public PlayerHand() {
    myFactoryComponents = new FactoryComponents();
    horizontalHands = new ArrayList<HBox>();
    verticalHands = new ArrayList<VBox>();
    myValidCards = new ArrayList<>();
  }

  public void initializePlayerHandsAtStart(List<List<String>> handCardColors,
                                           List<List<List<String>>> handCardTypes,
                                           List<List<List<Integer>>> handCardParameters) {
    myHandCardColors = handCardColors;
    myHandCardTypes = handCardTypes;
    myHandCardParameters = handCardParameters;
    myNumberOfPlayers = handCardColors.size();
    populateAllHands();
  }

  public void updateCurrentPlayersHand(List<String> handCardColor, List<List<String>> handCardTypes,
      List<List<Integer>> handCardParameters) {
    populateOneHand(handCardColor, handCardTypes, handCardParameters, myCurrentPlayer);
  }

  /**
   * Method that creates the user's hands
   *
   * @param name name of player
   * @return HBox of user cards
   */
  public HBox createUserHand(Object name) {
    horizontalHands.get(0).getStyleClass().add(PLAYER_HAND_STYLE);
    if (horizontalHands.get(0).getChildren().size() == myHandCardColors.get(0).size()) {
      horizontalHands.get(0).getChildren().add(myFactoryComponents.createLabel(name.toString()));
    }
    setCardClickAction();
    colorValidCards();
    return horizontalHands.get(0);
  }

  /**
   * Method that creates the side opponent's hands
   *
   * @param name name of player
   * @return VBox of cards
   */
  public VBox createLeftOpponentHand(String name) {
    verticalHands.get(1).getStyleClass().add(PLAYER_HAND_STYLE);
    if (verticalHands.get(1).getChildren().size() == myHandCardColors.get(1).size()) {
      verticalHands.get(1).getChildren().add(myFactoryComponents.createLabel(name));
    }
    return verticalHands.get(1);
  }

  /**
   * Method that creates the top opponent's hand
   *
   * @param name name of player
   * @return HBox of cards
   */
  public HBox createTopOpponentHand(String name) {
    horizontalHands.get(2).getStyleClass().add(PLAYER_HAND_STYLE);
    if (horizontalHands.get(2).getChildren().size() == myHandCardColors.get(0).size()) {
      horizontalHands.get(2).getChildren().add(myFactoryComponents.createLabel(name));
    }
    return horizontalHands.get(2);
  }

  /**
   * Method that creates the side opponent's hands
   *
   * @param name name of player
   * @return VBox of cards
   */
  public VBox createRightOpponentHand(String name) {
    verticalHands.get(3).getStyleClass().add(PLAYER_HAND_STYLE);
    if (verticalHands.get(3).getChildren().size() == myHandCardColors.get(1).size()) {
      verticalHands.get(3).getChildren().add(myFactoryComponents.createLabel(name));
    }
    return verticalHands.get(3);
  }

  // Deselects all cards in the hand
  private void deselectOtherCards() {
    for (Object o : horizontalHands.get(0).getChildren()) {
      if (o.getClass() == Card.class) {
        ((Card) o).setIsSelected(false);
      }
    }
  }

//  /**
//   * Method that returns the user's hand
//   *
//   * @return HBox myUserHand
//   */
//  public HBox getUserHand() {
//    return horizontalHands.get(0);
//  }

  /**
   * Method that sets action when a card is clicked When a card is clicked, all other cards become
   * deselected, and it is "Selected"
   */
  private void setCardClickAction() {
    for (Object o : horizontalHands.get(0).getChildren()) {
      if (o.getClass() == Card.class) {
        EventHandler<MouseEvent> event = event1 -> {
          Boolean state = ((Card) o).getIsSelected();
          deselectOtherCards();
          ((Card) o).setIsSelected(!state);
        };
        ((Card) o).setOnMouseClicked(event);
      }
    }
  }

  private void colorValidCards() {
    for (int i = 0; i < horizontalHands.get(0).getChildren().size(); i++) {
      Object o = horizontalHands.get(0).getChildren().get(i);
      if (o.getClass() == Card.class && myValidCards.contains(i)) {
        ((Card) o).setIsValid(true);
      }
    }
  }

  /**
   * Method that returns the selected card in a hand
   *
   * @return Card that is currently selected
   */
  public Card getSelectedCard() {
    for (Object o : horizontalHands.get(0).getChildren()) {
      if (o.getClass() == Card.class && ((Card) o).getIsSelected()) {
        return (Card) o;
      }
    }
    return null;
  }

//  /**
//   * Method that returns the selected card in a hand
//   */
//  public void removePlayedCard() {
//    for (Object o : horizontalHands.get(0).getChildren()) {
//      if (o.getClass() == Card.class && ((Card) o).getIsSelected()) {
//        horizontalHands.get(0).getChildren().remove(o);
//      }
//    }
//  }

  private void populateAllHands() {
    for (int playerNumber = 0; playerNumber < myNumberOfPlayers; playerNumber++) {
      int playerCardCount = myHandCardColors.get(playerNumber).size();
      HBox currentHandH = new HBox();
      VBox currentHandV = new VBox();
      for (int i = 0; i < playerCardCount; i++) {
        Card card;
        if (cardBackImg != null) {
          card = playerNumber == 0 ? new Card(myHandCardTypes.get(playerNumber).get(i),
              myHandCardColors.get(playerNumber).get(i),
              myHandCardParameters.get(playerNumber).get(i))
              : makeCardWithBackImg(myHandCardTypes.get(playerNumber).get(i),
              myHandCardColors.get(playerNumber).get(i), playerNumber,
              myHandCardParameters.get(playerNumber).get(i));
        } else {
          card = playerNumber == 0 ? new Card(myHandCardTypes.get(playerNumber).get(i),
              myHandCardColors.get(playerNumber).get(i),
              myHandCardParameters.get(playerNumber).get(i))
              : new Card(myHandCardTypes.get(playerNumber).get(i),
                  myHandCardColors.get(playerNumber).get(i), playerNumber == 2,
                  myHandCardParameters.get(playerNumber).get(i));
        }
        if (playerNumber % 2 == 0) {
          currentHandH.getChildren().add(card);
        } else {
          currentHandV.getChildren().add(card);
        }
      }
      horizontalHands.add(currentHandH);
      verticalHands.add(currentHandV);
    }
  }

  public void populateOneHand(List<String> handCardColor, List<List<String>> handCardTypes,
    List<List<Integer>> handCardParameters, int playerNumber) {
    int playerCardCount = handCardColor.size();
    HBox currentHandH = new HBox();
    VBox currentHandV = new VBox();
    System.out.format(Resources.getKey(ooga.Config.PLAYER_ID_CARDS_IN_HAND), playerNumber);
    for (int i = 0; i < playerCardCount; i++) {
      System.out.format(
              Config.TWO_STRING_FORMAT + Config.NEW_LINE, handCardColor.get(i), handCardParameters.get(i),
                      handCardTypes.get(i));
      Card card;
      if (cardBackImg != null) {
        card = myCurrentPlayer == 0 ? new Card(handCardTypes.get(i),
            handCardColor.get(i), handCardParameters.get(i))
            : makeCardWithBackImg(handCardTypes.get(i),
                handCardColor.get(i), playerNumber,
                handCardParameters.get(i));
      } else {
        card = myCurrentPlayer == 0 ? new Card(handCardTypes.get(i),
            handCardColor.get(i),
            handCardParameters.get(i))
            : new Card(handCardTypes.get(i),
                handCardColor.get(i), playerNumber==2,
                handCardParameters.get(i));
      }
      if (playerNumber%2==0) { currentHandH.getChildren().add(card); }
      else { currentHandV.getChildren().add(card); }
    }
    horizontalHands.remove(playerNumber);
    verticalHands.remove(playerNumber);
    horizontalHands.add(playerNumber, currentHandH);
    verticalHands.add(playerNumber, currentHandV);
  }

  private Card makeCardWithBackImg(List<String> value, String color, int playerNumber, List<Integer> parameters) {
    return new Card(value, color, playerNumber==2, parameters, cardBackImg);
  }

  public void setCurrentPlayer(int currPlayer) {
    myCurrentPlayer = currPlayer;
  }

  public int getCurrentPlayer() {
    return myCurrentPlayer;
  }

  public int getNumberOfPlayers() {
    return myNumberOfPlayers;
  }

  public List<HBox> getHorizontalHands() {
    return horizontalHands;
  }

  public List<VBox> getVerticalHands() {
    return verticalHands;
  }

  public void setValidCards(List<Integer> validCards) {
    myValidCards = validCards;
  }

  public void setCardBackImg(String filePath) {
    cardBackImg = filePath;
  }

  public List<Integer> getValidCards() {
    return myValidCards;
  }

}
