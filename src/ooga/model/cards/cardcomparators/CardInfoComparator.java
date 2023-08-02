package ooga.model.cards.cardcomparators;

import java.util.Comparator;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Concrete class implementing the Comparator interface, compares two CardInfo
 *
 * @author Alicia Wu
 */
public class CardInfoComparator implements Comparator<CardInfo> {

  /**
   * compares two CardInfo, first compares card types, and then card params if needed
   *
   * @param info1 a CardInfo
   * @param info2 a CardInfo
   * @return negative int, zero, or a positive int if info1 is less than, equal to, or greater than
   * info2
   */
  @Override
  public int compare(CardInfo info1, CardInfo info2) {
    int typeComp = new CardTypeComparator().compare(info1.getType(), info2.getType());
    if (typeComp != 0) {
      return typeComp;
    }
    return Integer.compare(info1.getParam(),
        info2.getParam()); // red number 3 goes before red number 7
  }


}
