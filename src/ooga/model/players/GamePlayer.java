package ooga.model.players;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import ooga.Config;
import ooga.model.board.CardValidator;
import ooga.model.board.UnoCardValidator;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cardcollection.hand.PlayerHand;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Abstract class implementing the Player interface, player has a hand of cards and facilitates
 * interactions with this hand of cards
 *
 * @author Alicia Wu
 */
public class GamePlayer implements Player {

  private int playerId;
  private CardCollection playerHand;
  private CardValidator cardValidator;

  /**
   * creates a new instance of Player given the player's id, player's hand starts off being empty
   *
   * @param playerId
   */
  public GamePlayer(int playerId) {
    this.playerId = playerId;
    cardValidator = new UnoCardValidator();
    playerHand = new PlayerHand(Config.EMPTY_CARD_COLLECTION);
  }

  /**
   * gets the id of this player
   *
   * @return player's id number
   */
  public int getPlayerId() {
    return playerId;
  }

  /**
   * adds a given card to the player's hand of cards
   *
   * @param card card to add to the player's hand of cards
   */
  public void addToHand(Card card) {
    playerHand.addCard(card);
  }

  /**
   * adds a list of cards to the player's hand of cards
   *
   * @param cards list of cards to add to the player's hand of cards
   */
  public void addAllToHand(List<Card> cards) {
    cards.forEach(this::addToHand);
  }

  /**
   * find the cards in a player's hand that can be played given the top card of the play deck
   *
   * @param topCardOfPlayDeck is the current top card of the play deck
   * @return an unmodifiable collection of cards in the player's hand that are valid to play
   */
  public Collection<Card> findValidCardsToPlay(Card topCardOfPlayDeck) {
    List<Card> validCards = new ArrayList<>();
    List<Card> allCards = playerHand.getAllCards();
    allCards.forEach(card -> {
      if (cardValidator.isValidToPlay(card, topCardOfPlayDeck)) {
        validCards.add(card);
      }
    });
    return Collections.unmodifiableCollection(validCards);
  }

  /**
   * find the cards in a player's hand that have the same card info as the given card
   *
   * @param info card info to match
   * @return an unmodifiable collection of cards in the player's hand that have the same card info
   * as given
   */
  public Collection<Card> findCardsWithSameCardInfo(CardInfo[] info) {
    List<Card> cardsWithSameInfo = new ArrayList<>();
    List<Card> allCards = playerHand.getAllCards();
    allCards.forEach(card -> {
      if (cardValidator.isCardInfoSame(card.getCardInfo(), info)) {
        cardsWithSameInfo.add(card);
      }
    });
    return Collections.unmodifiableCollection(cardsWithSameInfo);
  }

  /**
   * get all cards in the player's hand
   *
   * @return list of cards in the player's hand
   */
  public List<Card> getAllCards() {
    return Collections.unmodifiableList(playerHand.getAllCards());
  }

  /**
   * plays the given card by removing it from the player's hand
   *
   * @param card Card to play
   */
  public void playCard(Card card) {
    playerHand.remove(card);
  }

  /**
   * get a reference to the player's hand of cards
   *
   * @return the CardCollection instance representing the player's hand of cards
   */
  public CardCollection getPlayerHand() {
    return playerHand;
  }

}
