package ooga.model.cardcollection.hand;

import java.util.List;
import java.util.Map;
import ooga.model.cardcollection.UnoCardCollection;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomparators.CardComparator;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Abstract class extending UnoCardCollection, represents a hand of cards
 *
 * @author Alicia Wu
 */
public abstract class Hand extends UnoCardCollection {

  /**
   * creates a new collection of cards using the abstract factory pattern
   *
   * @param allCardsInfo information on all cards in this game
   */
  public Hand(Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(allCardsInfo);
  }

  /**
   * reorganizes cards in a hand by sorting them by card type
   *
   * @param cards cards to sort
   * @return sorted cards
   */
  @Override
  protected List<Card> reorganizeCollection(List<Card> cards) {
    return sort(cards);
  }

  private List<Card> sort(List<Card> cards) {
    cards.sort(new CardComparator());
    return cards;
  }
}
