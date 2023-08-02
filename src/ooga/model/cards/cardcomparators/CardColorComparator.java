package ooga.model.cards.cardcomparators;

import java.util.Comparator;
import ooga.model.cards.CardColor;

/**
 * Concrete class implementing the Comparator interface, compares two instances of CardColor
 *
 * @author Alicia Wu
 */
public class CardColorComparator implements Comparator<CardColor> {

  /**
   * compares two instances of CardColor for ordering
   *
   * @param color1 instance of CardColor
   * @param color2 instance of CardColor
   * @return negative int, zero, or a positive int if color1 is less than, equal to, or greater than
   * color2
   */
  @Override
  public int compare(CardColor color1, CardColor color2) {
    return color1.toString().compareTo(color2.toString());
  }
}
