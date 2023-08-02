package ooga.model.cards.cardcomponents;

import ooga.model.cards.CardColor;

/**
 * Concrete immutable class implementing the Card interface, represents a card played in the game
 * UNO, no setter methods / no way to modify after creation
 *
 * @author Alicia Wu
 */
public class UnoCard implements Card {

  private CardColor color;
  private CardInfo[] cardInfo;

  /**
   * instantiates a new UnoCard
   *
   * @param color    color of card
   * @param cardInfo information on card
   */
  public UnoCard(CardColor color, CardInfo[] cardInfo) {
    this.color = color;
    this.cardInfo = cardInfo;
  }

  /**
   * gets the Uno card's color
   *
   * @return instance of CardColor representing the color of this card
   */
  @Override
  public CardColor getCardColor() {
    return color;
  }

  /**
   * gets the Uno card's information
   *
   * @return instance of CardInfo[] representing the information on this card
   */
  @Override
  public CardInfo[] getCardInfo() {
    return cardInfo;
  }

}
