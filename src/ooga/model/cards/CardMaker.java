package ooga.model.cards;

import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Defines the CardMaker API, makes a card
 *
 * @author Alicia Wu
 */
public interface CardMaker {

  /**
   * makes and returns a new Card given the desired card color and information
   *
   * @param color desired color of card
   * @param cardInfo desired information on card
   * @return new Card instance made
   */
  Card makeCard(CardColor color, CardInfo[] cardInfo);

}
