package ooga.model.cardcollection.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.cards.CardColor;
import ooga.model.cards.CardFactory;
import ooga.model.cards.UnoCardFactory;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Concrete class extending Deck, represents the play deck that players will discard their cards to
 * (aka playing the card)
 *
 * @author Alicia Wu
 */
public class PlayDeck extends Deck {

  /**
   * creates a new collection of cards using the abstract factory pattern
   *
   * @param allCardsInfo information on all cards in this game
   */
  public PlayDeck(
      Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(allCardsInfo);
  }

  @Override
  protected List<Card> makeCardCollection() {

    CardFactory factory = new UnoCardFactory();
    return factory.makeCards(getAllCardsInfo());
  }

  /**
   * adding a card to the PlayDeck always adds it to the top of the deck
   *
   * @param newCard card to add to the top of the play deck
   */
  @Override
  public void addCard(Card newCard) {
    addCardToTop(newCard);
  }

}
