package ooga.model.board;

import java.util.List;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cards.cardcomponents.Card;

/**
 * This interface defines the GameBoard API, handles all player/game interactions with card decks
 *
 * @author Alicia Wu
 */
public interface GameBoard {

  /**
   * get a card from the draw deck
   *
   * @return card from the top of the draw deck
   */
  Card getFromDrawDeck();

  /**
   * get a list of all the cards in the draw deck
   *
   * @return a list of all the cards in the draw deck
   */
  List<Card> getAllDrawDeckCards();

  /**
   * get a list of all the cards in the play deck
   *
   * @return a list of all the cards in the play deck
   */
  List<Card> getAllPlayDeckCards();

  /**
   * add a card to the play deck
   *
   * @param card card to add to play deck
   */
  void addToPlayDeck(Card card);

  /**
   * checks if a given card is valid to play
   *
   * @param card card to play
   * @return true if the card is valid to play
   */
  boolean ifValidToPlay(Card card);

  /**
   * get a reference to the top card in the play deck
   *
   * @return top card of the play deck
   */
  Card peekPlayDeckTopCard();

  /**
   * transfer a card from the draw deck to a card collection
   *
   * @param cardCollection collection of cards to transfer a card to
   * @return card that was transferred
   */
  Card transferCardFromDrawDeckTo(CardCollection cardCollection);

  /**
   * shuffle the draw deck
   */
  void shuffleDrawDeck();

}
