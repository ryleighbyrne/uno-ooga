/**
 * implementing use case in which the draw deck runs out, so all the cards (except for the top one)
 * in the play deck are shuffled & become the new draw deck. the play deck is now only has 1 card
 * (its top card). Author: Alicia Wu
 */
package ooga.model.board;

import ooga.model.cardcollection.deck.Deck;
import ooga.model.cardcollection.deck.PlayDeck;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;

/**
 * game board keeps track of play deck and draw deck & interfaces actions with them, validates new
 * card that a player wants to play (aka add to play deck), resets draw deck when it runs out
 */
public class UnoGameBoard implements GameBoard {

  private Deck drawDeck;
  private Deck playDeck;
  private CardValidator cardValidator;

  public UnoGameBoard(Deck drawDeck) {
    playDeck = new PlayDeck();
    this.drawDeck = drawDeck;
    cardValidator = new UnoCardValidator();
  }

  /**
   * "saves" the top card of the play deck, transfers all the cards from the play deck to the draw
   * deck, then calls reorganize (aka shuffle) on the deck, and adds the "saved" top card back to
   * the play deck
   */
  public void resetDrawDeck() {
    Card topCard = playDeck.getTopCard();
    playDeck.transferAll(drawDeck);
    drawDeck.reorganize();
    playDeck.addCard(topCard);
  }

}
