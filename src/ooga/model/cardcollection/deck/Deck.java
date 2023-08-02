package ooga.model.cardcollection.deck;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cardcollection.UnoCardCollection;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Abstract class extending UnoCardCollection and implementing DeckCardCollection, represents a deck
 * of cards
 *
 * @author Alicia Wu
 */
public abstract class Deck extends UnoCardCollection implements
    DeckCardCollection {

  /**
   * creates a new collection of cards using the abstract factory pattern
   *
   * @param allCardsInfo information on all cards in this game
   */
  public Deck(
      Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(allCardsInfo);
  }

  /**
   * returns & removes the top card of this deck card collection
   *
   * @return top card of this deck card collection
   */
  @Override
  public Card getTopCard() {
    return getCard(0);
  }

  /**
   * returns the top card of this deck card collection
   *
   * @return top card of this deck card collection
   */
  @Override
  public Card peekTopCard() {
    return getAllCards().get(0);
  }

  /**
   * reorganizes cards in a deck by shuffling them
   *
   * @param cards cards in a deck
   * @return shuffled cards
   */
  @Override
  protected List<Card> reorganizeCollection(List<Card> cards) {
    return shuffle(cards);
  }

  private List<Card> shuffle(List<Card> cards) {
    Collections.shuffle(cards);
    return cards;
  }
}
