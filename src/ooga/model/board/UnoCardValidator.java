package ooga.model.board;

import java.util.Comparator;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomparators.CardInfoArrayComparator;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.exceptions.InvalidCardException;

/**
 * Concrete class implementing the CardValidator interface, validates if cards can be played on top
 * of each, if they have same attributes, etc.
 *
 * @author Alicia Wu
 */
public class UnoCardValidator implements CardValidator {

  /**
   * checks if two cards can be played one on top of the other (ex: red with red, skip with skip,
   * wild with anything)
   *
   * @param cardA a Card to compare
   * @param cardB a Card to compare
   * @return true if cardA and cardB are valid to play "together" (aka one on top of the other)
   */
  @Override
  public boolean isValidToPlay(Card cardA, Card cardB) {
    checkIfAnyNull(cardA, cardB);
    if (cardA.getCardColor() == cardB.getCardColor() || isColorlessCard(cardA)
        || isColorlessCard(cardB)) {
      return true;
    }
    return isCardInfoSame(cardA.getCardInfo(), cardB.getCardInfo());
  }

  private boolean isColorlessCard(Card card) {
    return card.getCardColor() == CardColor.NONE;
  }

  /**
   * checks if the given card info arrays are the same
   *
   * @param cardInfoA a CardInfo[] to compare
   * @param cardInfoB a CardInfo[] to compare
   * @return true if cardInfoA and cardInfoB care the same
   */
  @Override
  public boolean isCardInfoSame(CardInfo[] cardInfoA, CardInfo[] cardInfoB) {
    checkIfAnyNull(cardInfoA, cardInfoB);
    Comparator<CardInfo[]> cardInfoComparator = new CardInfoArrayComparator();
    return cardInfoComparator.compare(cardInfoA, cardInfoB) == 0;
  }

  private void checkIfAnyNull(Object A, Object B) {
    if (A == null || B == null) {
      ResourceBundle myResources = ResourceBundle.getBundle(Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
      throw new InvalidCardException(myResources.getString(InvalidCardException.class.getSimpleName()));
    }
  }

}

