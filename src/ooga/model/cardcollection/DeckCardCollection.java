package ooga.model.cardcollection;

import ooga.model.cards.cardcomponents.Card;

/**
 * Defines API of a DeckCardCollection, outlines functionality unique to a deck of cards
 *
 * @author Alicia Wu
 */
public interface DeckCardCollection extends CardCollection {

  /**
   * gets the top card of this deck card collection
   *
   * @return top card of this deck card collection
   */
  Card peekTopCard();

  /**
   * gets & removes the top card of this deck card collection
   *
   * @return top card of this deck card collection
   */
  Card getTopCard();

}
