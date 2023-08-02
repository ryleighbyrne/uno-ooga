/**
 * defines a card, can get its color and its information
 */
public interface Card {

  CardColor getCardColor();

  CardInfo[] getCardInfo();

}