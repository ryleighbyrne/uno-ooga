package ooga.model.cards;

import java.util.Arrays;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.cards.cardcomparators.CardInfoComparator;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.cards.cardcomponents.UnoCard;
import ooga.model.exceptions.InvalidCardException;
import ooga.model.exceptions.WildCardNotOfColorNoneException;

/**
 * Concrete class implementing the CardMaker interface, creates a UnoCard
 *
 * @author Alicia Wu
 */
public class UnoCardMaker implements CardMaker {

  /**
   * makes and returns a new Uno Card
   *
   * @param color    desired color of card
   * @param cardInfo desired information on card
   * @return new Card instance made
   */
  @Override
  public Card makeCard(CardColor color, CardInfo[] cardInfo) {
    ResourceBundle myResources = ResourceBundle.getBundle(
        Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
    if (cardInfo == null || cardInfo.length == 0 || color == null) {
      throw new InvalidCardException(
          myResources.getString(InvalidCardException.class.getSimpleName()));
    }
    if (!ifWildCardIsNoneColor(color, cardInfo)) {
      throw new WildCardNotOfColorNoneException(
          myResources.getString(WildCardNotOfColorNoneException.class.getSimpleName()));
    }
    Arrays.sort(cardInfo, new CardInfoComparator());
    return new UnoCard(color, cardInfo);
  }

  private boolean ifWildCardIsNoneColor(CardColor color, CardInfo[] cardInfo) {
    if (color != CardColor.NONE) { // shouldn't have wild
      return !cardInfoContainsWild(cardInfo);
    }
    return cardInfoContainsWild(cardInfo);
  }

  private boolean cardInfoContainsWild(CardInfo[] cardInfo) {
    for (CardInfo info : cardInfo) {
      if (info.getType().equalsIgnoreCase(Config.WILD_CARD)) {
        return true;
      }
    }
    return false;
  }
}

