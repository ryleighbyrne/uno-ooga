package ooga.model.board;

import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Defines API of a card validator, validates if two cards can be played one on top of the other,
 * (ex: red with red, skip with skip, wild with anything)
 *
 * @author Alicia Wu
 */
public interface CardValidator {

  /**
   * checks if two cards can be played one on top of the other (ex: red with red, skip with skip,
   * wild with anything)
   *
   * @param cardA a Card to compare
   * @param cardB a Card to compare
   * @return true if cardA and cardB are valid to play "together" (aka one on top of the other)
   */
  boolean isValidToPlay(Card cardA, Card cardB);

  /**
   * checks if the given card info arrays are the same
   *
   * @param cardInfoA a CardInfo[] to compare
   * @param cardInfoB a CardInfo[] to compare
   * @return true if cardInfoA and cardInfoB care the same
   */
  boolean isCardInfoSame(CardInfo[] cardInfoA, CardInfo[] cardInfoB);

}
