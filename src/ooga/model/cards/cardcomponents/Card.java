package ooga.model.cards.cardcomponents;

import ooga.model.cards.CardColor;

/**
 * This interface defines the Card API, card must have color & information
 *
 * @author Alicia Wu
 */
public interface Card {

  /**
   * gets the card's color
   *
   * @return instance of CardColor representing the color of this card
   */
  CardColor getCardColor();

  /**
   * gets the card's information
   *
   * @return instance of CardInfo[] representing the information on this card
   */
  CardInfo[] getCardInfo();

}
