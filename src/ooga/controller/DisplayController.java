package ooga.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;
import ooga.Config;
import ooga.model.cards.CardColor;
import ooga.model.cards.CardMaker;
import ooga.model.cards.UnoCardMaker;
import ooga.model.cards.cardcomparators.CardColorComparator;
import ooga.model.cards.cardcomparators.CardInfoArrayComparator;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.cards.cardcomponents.UnoCardInfo;
import ooga.model.players.Player;
import ooga.view.CenterDecks;
import ooga.view.PlayerHand;
import ooga.view.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class observers the generation of new displays, signaling to controller to handle game play
 * logic when notified
 *
 * @author Ryleigh Byrne, Alicia Wu
 */
public class DisplayController implements PropertyChangeListener {

  private Controller controller;
  private PlayerHand playerHand;
  private CenterDecks centerDecks;
  private PropertyChangeEvent event;
  private Logger logger = LogManager.getLogger(DisplayController.class);
  private List<List<String>> allPlayerHandCardColors = new ArrayList<>();
  private List<List<List<String>>> allPlayerHandCardTypes = new ArrayList<>();
  private List<List<List<Integer>>> allPlayerHandCardParameters = new ArrayList<>();
  private ooga.view.Card cardPlayed;
  private List<String> currentPlayersHandColors;
  private List<List<String>> currentPlayersHandTypes;
  private List<List<Integer>> currentPlayersHandParameters;
  private String pathToBackOfCard;

  /**
   * creates a new instance of DisplayController
   *
   * @param c Controller instance to reference
   */
  public DisplayController(Controller c) {
    controller = c;
  }

  /**
   * add observers for the centerDeck instances in view
   *
   * @param centerDeckAndHand CenterDecks and PlayerHand instances to add observers to
   */
  public void addGameDisplayObservers(Object[] centerDeckAndHand) {
    centerDecks = (CenterDecks) centerDeckAndHand[0];
    playerHand = (PlayerHand) centerDeckAndHand[1];
    centerDecks.addObserver(Config.CARD_DRAWN_BY_VIEW, this);
    centerDecks.addObserver(Config.CARD_SELECTED_TO_PLAY, this);
  }

  // generates list of card colors in one player's hand
  private List<String> generateListOfCardColorsInHand(List<Card> playerHand) {
    List<String> listOfColors = new ArrayList<>();
    for (ooga.model.cards.cardcomponents.Card card : playerHand) {
      listOfColors.add(card.getCardColor().toString());
    }
    return listOfColors;
  }

  // generates list of card types in one player's hand
  private List<List<String>> generateListOfCardTypesInHand(
      List<ooga.model.cards.cardcomponents.Card> playerHand) {
    List<List<String>> listOfCardTypes = new ArrayList<>();
    for (ooga.model.cards.cardcomponents.Card card : playerHand) {
      List<String> currentCardsTypes = new ArrayList<>();
      for (CardInfo cardInfo : card.getCardInfo()) {
        currentCardsTypes.add(cardInfo.getType());
      }
      listOfCardTypes.add(currentCardsTypes);
    }
    return listOfCardTypes;
  }

  // generates list of card parameters in one player's hand
  private List<List<Integer>> generateListOfCardParametersInHand(
      List<ooga.model.cards.cardcomponents.Card> playerHand) {
    List<List<Integer>> listOfCardParameters = new ArrayList<>();
    for (ooga.model.cards.cardcomponents.Card card : playerHand) {
      List<Integer> currentCardsParameters = new ArrayList<>();
      for (CardInfo cardInfo : card.getCardInfo()) {
        currentCardsParameters.add(cardInfo.getParam());
      }
      listOfCardParameters.add(currentCardsParameters);
    }
    return listOfCardParameters;
  }

  /**
   * convert a view card to a model card
   *
   * @param cardPlayed card played in the view
   * @return model card created that matches the view card
   */
  public Card convertViewCardToModelCard(ooga.view.Card cardPlayed) {
    CardMaker cardMaker = new UnoCardMaker();
    List<CardInfo> cardInfoList = new ArrayList<>();
    addCardInfoToList(cardPlayed, cardInfoList);
    CardInfo[] infos = new CardInfo[cardInfoList.size()];
    for (int j = 0; j < cardInfoList.size(); j++) {
      infos[j] = cardInfoList.get(j);
    }
    String playedCardColor = cardPlayed.getColor();
    return cardMaker.makeCard(CardColor.valueOf(playedCardColor), infos);
  }

  /**
   * find the card instance in the player's hand that matches the model card created from the view
   *
   * @param modelCard card with matching attributes to the card given in the view
   * @return matching card instance in the player's hand
   */
  public Card changeConvertedModelCardToCardInHand(Card modelCard) {
    CardColorComparator colorComparator = new CardColorComparator();
    CardInfoArrayComparator infoComparator = new CardInfoArrayComparator();
    for (Card card : controller.getCurrentPlayersModelHand()) {
      if (colorComparator.compare(card.getCardColor(), modelCard.getCardColor()) == 0 &&
          infoComparator.compare(card.getCardInfo(), modelCard.getCardInfo()) == 0) {
        return card;
      }
    }
    return null;
  }

  // helper method to parse card info
  private void addCardInfoToList(ooga.view.Card cardPlayed, List<CardInfo> list) {
    int i = 0;
    for (String cardType : cardPlayed.getValue()) {
      CardInfo cardInfo = new UnoCardInfo(cardType, cardPlayed.getParameters().get(i));
      i++;
      list.add(cardInfo);
    }
  }

  /**
   * set the filepath to the image to be displayed on the back of cards
   *
   * @param pathToBackOfCard
   */
  public void setPathToBackOfCard(String pathToBackOfCard) {
    playerHand.setCardBackImg(pathToBackOfCard);
  }

  /**
   * generates ArrayLists of all player's hands' colors, types, and parameters for parsing in the
   * view initializes hands after first dealing
   *
   * @param playersList list of players in the game from the model
   */
  public void parseAllPlayersHandsForView(List<Player> playersList) {
    for (Player player : playersList) {
      allPlayerHandCardColors.add(generateListOfCardColorsInHand(player.getAllCards()));
      allPlayerHandCardTypes.add(generateListOfCardTypesInHand(player.getAllCards()));
      allPlayerHandCardParameters.add(generateListOfCardParametersInHand(player.getAllCards()));
      System.out.format(Resources.getKey(Config.PLAYER_HANDS_AT_START), player.getPlayerId(),
          generateListOfCardColorsInHand(
              player.getAllCards()), generateListOfCardTypesInHand(player.getAllCards()),
          generateListOfCardParametersInHand(player.getAllCards()));
    }
    initializePlayerHandsAtStart();
  }

  // helper method to initialize player hand at start
  private void initializePlayerHandsAtStart() {
    playerHand.initializePlayerHandsAtStart(allPlayerHandCardColors, allPlayerHandCardTypes,
        allPlayerHandCardParameters);
  }

  /**
   * called after player's hand updates in the game (after draw, after playing a card, etc) updates
   * view with players updated hand
   */
  private void parseCurrentPlayersHand(List<Card> allCardsInPlayerHand) {
    currentPlayersHandColors = generateListOfCardColorsInHand(allCardsInPlayerHand);
    currentPlayersHandTypes = generateListOfCardTypesInHand(allCardsInPlayerHand);
    currentPlayersHandParameters = generateListOfCardParametersInHand(allCardsInPlayerHand);
  }

  /**
   * updates the current player's hand in the view
   *
   * @param allCardsInPlayerHand all the cards in the player's hand
   */
  public void updateCurrentPlayersHand(List<Card> allCardsInPlayerHand) {
    if (playerHand == null) {
      return;
    }
    parseCurrentPlayersHand(allCardsInPlayerHand);
    playerHand.updateCurrentPlayersHand(currentPlayersHandColors, currentPlayersHandTypes,
        currentPlayersHandParameters);
  }

  /**
   * updates the hands of all players
   *
   * @param playersList
   */
  public void updateGivenPlayersHand(List<Player> playersList) {
    parseAllPlayersHandsForView(playersList);
    centerDecks.displayPirateSwapMessage();
  }

  /**
   * convert a model card to a view card
   *
   * @param card card in the model to convert
   * @return view card created that matches the given model card
   */
  public ooga.view.Card convertModelCardToViewCard(Card card) {
    List<String> values = new ArrayList<>();
    List<Integer> parameters = new ArrayList<>();
    for (CardInfo cardInfo : card.getCardInfo()) {
      values.add(cardInfo.getType());
      parameters.add(cardInfo.getParam());
    }
    return new ooga.view.Card(values, card.getCardColor().toString(),
        parameters);
  }

  /**
   * update the valid card locations in the player's hand
   *
   * @param validCardsLocations list of locations of the valid cards
   */
  public void updateValidCardsInPlayerHand(List<Integer> validCardsLocations) {
    playerHand.setValidCards(validCardsLocations);
  }

  /**
   * update the current player's id
   *
   * @param playerId player id to update the current player in the view to
   */
  public void updateCurrentPlayerId(int playerId) {
    playerHand.setCurrentPlayer(playerId);
  }

  /**
   * updates the top card in the play deck
   *
   * @param card card instance to update to
   */
  public void updatePlayDeck(Card card) {
    System.out.format(
        Resources.getKey(Config.PLAY_DECK_TOP_CARD), card.getCardColor(),
        card.getCardInfo()[0].getType(), card.getCardInfo()[0].getParam());
    centerDecks.updateDiscardDeckTopCard(convertModelCardToViewCard(card));
    HBox newDisplay = centerDecks.updateDiscardDeckTopCard(convertModelCardToViewCard(card));
    controller.getGameDisplay().setMyCenterDecks(newDisplay);
  }

  /**
   * gets called when a property this class is observing gets changed
   *
   * @param evt PropertyChangeEvent instance that holds the name of the property that has changed
   *            and its new value
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    ReflectionMethodHandler reflectionHandler = new ReflectionMethodHandler();
    event = evt;
    try {
      reflectionHandler.handleMethod(event.getPropertyName(), Config.DISPLAYCONTROLLER_CLASSPATH)
          .invoke(DisplayController.this);
    } catch (IllegalAccessException e) {
      logger.error(e.getMessage());
    } catch (InvocationTargetException e) {
      logger.error(e.getMessage());
    }
  }

  // called with reflection when card is chosen by user to play
  private void cardSelectedToPlay() throws Exception {
    cardPlayed = (ooga.view.Card) event.getNewValue();
    Card modelCard = changeConvertedModelCardToCardInHand(
        convertViewCardToModelCard((cardPlayed)));
    controller.playRealPlayerTurn(modelCard);
  }

}
