package ooga.model.cards.cardcomparators;

import java.util.Comparator;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Concrete class implementing the Comparator interface, compares two CardInfo[]
 *
 * @author Alicia Wu
 */
public class CardInfoArrayComparator implements Comparator<CardInfo[]> {

  /**
   * compares two CardInfo[], first compares by CardInfo[] length, then compares each CardInfo
   * entry
   *
   * @param info1 a CardInfo[]
   * @param info2 a CardInfo[]
   * @return negative int, zero, or a positive int if info1 is less than, equal to, or greater than
   * info2
   */
  @Override
  public int compare(CardInfo[] info1, CardInfo[] info2) {
    int ret = Integer.compare(info1.length, info2.length);
    int i = 0;
    while (ret == 0 && i < info1.length) {
      Comparator<CardInfo> infoComparator = new CardInfoComparator();
      ret = infoComparator.compare(info1[i], info2[i]);
      i++;
    }
    return ret;
  }
}