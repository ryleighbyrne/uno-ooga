package ooga.model.cardcollection.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.cards.UnoCardFactory;
import ooga.model.cards.CardFactory;

/**
 * Concrete class extending Hand, represents a player's hand of cards
 *
 * @author Alicia Wu
 */
public class PlayerHand extends Hand {

  /**
   * creates a new collection of cards using the abstract factory pattern
   *
   * @param allCardsInfo information on all cards in this game
   */
  public PlayerHand(Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(allCardsInfo);
  }

  /**
   * initializes the player's hand
   *
   * @return the list of cards
   */
  @Override
  protected List<Card> makeCardCollection() {
    CardFactory factory = new UnoCardFactory();
    return factory.makeCards(getAllCardsInfo());
  }
}
