package ooga.model.cards;

import java.util.List;
import java.util.Map;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Defines the CardFactory API, factory for making new cards
 *
 * @author Alicia Wu
 */
public interface CardFactory {

  /**
   * makes and returns a list of cards given the desired information on the cards
   *
   * @param allCardsInfo information on all cards in this game
   * @return list of Cards created
   */
  List<Card> makeCards(Map<CardColor, List<CardInfo[]>> allCardsInfo);

}
