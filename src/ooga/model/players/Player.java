package ooga.model.players;

import java.util.Collection;
import java.util.List;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Defines API of a player in a game, outlines interactions with a player and their hand of cards
 *
 * @author Alicia Wu
 */
public interface Player {

  /**
   * adds a given card to the player's hand of cards
   *
   * @param card Card to add to this player's hand
   */
  void addToHand(Card card);

  /**
   * adds a list of cards to the player's hand of cards
   *
   * @param cards list of Cards to add to this player's hand
   */
  void addAllToHand(List<Card> cards);

  /**
   * gets the id of this player
   *
   * @return player's id number
   */
  int getPlayerId();

  /**
   * find the cards in a player's hand that are valid to play
   *
   * @param cardToBePlayedOn card that the valid cards will be played on
   * @return an unmodifiable collection of cards in the player's hand that are valid to play
   */
  Collection<Card> findValidCardsToPlay(Card cardToBePlayedOn);

  /**
   * find the cards in a player's hand that have the same card info as the given card
   *
   * @param info card info to match
   * @return an unmodifiable collection of cards in the player's hand that have the same card info
   * as given
   */
  Collection<Card> findCardsWithSameCardInfo(CardInfo[] info);

  /**
   * get a list of all cards in the player's hand
   *
   * @return unmodifiable list of cards
   */
  List<Card> getAllCards();

  /**
   * plays the given card by removing it from the player's hand
   *
   * @param card Card to play
   */
  void playCard(Card card);

  /**
   * get a reference to the player's hand of cards
   *
   * @return the CardCollection instance representing the player's hand of cards
   */
  CardCollection getPlayerHand();
}
