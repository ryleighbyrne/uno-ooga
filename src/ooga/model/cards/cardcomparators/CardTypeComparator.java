package ooga.model.cards.cardcomparators;

import java.util.Comparator;
import ooga.Config;

/**
 * Concrete class implementing the Comparator interface, compares two card types (strings)
 *
 * @author Alicia Wu
 */
public class CardTypeComparator implements Comparator<String> {

  /**
   * compares two card types (strings), compares by alphabetical order, but "number" is always less
   * than any other type
   *
   * @param type1 instance of String
   * @param type2 instance of String
   * @return negative int, zero, or a positive int if type1 is less than, equal to, or greater than
   * type2
   */
  @Override
  public int compare(String type1, String type2) {
    if (type1.equals(Config.NUMBER_CARD) && !type2.equals(Config.NUMBER_CARD)) {
      return -1;
    }
    if (type2.equals(Config.NUMBER_CARD) && !type1.equals(Config.NUMBER_CARD)) {
      return 1;
    }
    return type1.compareTo(type2);
  }
}
