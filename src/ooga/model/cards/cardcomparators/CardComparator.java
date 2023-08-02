package ooga.model.cards.cardcomparators;

import java.util.Comparator;
import ooga.model.cards.cardcomponents.Card;

/**
 * Concrete class implementing the Comparator interface, compares two instances of Card
 *
 * @author Alicia Wu
 */
public class CardComparator implements Comparator<Card> {

  /**
   * compares two instances of Card by comparing them by color, then by card info
   *
   * @param card1 instance of Card
   * @param card2 instance of Card
   * @return negative int, zero, or a positive int if card1 is less than, equal to, or greater than
   * card2
   */
  @Override
  public int compare(Card card1, Card card2) {
    int ColorCompare = new CardColorComparator().compare(card1.getCardColor(),
        card2.getCardColor());
    int CardInfoCompare = new CardInfoArrayComparator().compare(card1.getCardInfo(),
        card2.getCardInfo());
    return (ColorCompare == 0) ? CardInfoCompare : ColorCompare;
  }
}
