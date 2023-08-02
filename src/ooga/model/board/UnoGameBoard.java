package ooga.model.board;

import java.util.List;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cardcollection.deck.PlayDeck;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.exceptions.NoPossibleCardsToDrawException;

/**
 * Concrete class implementing the GameBoard interface, keeps track of the draw & play decks, and
 * handles all player/game interactions with the card decks
 *
 * @author Alicia Wu
 */
public class UnoGameBoard implements GameBoard {

  private DeckCardCollection drawDeck;
  private DeckCardCollection playDeck;
  private CardValidator cardValidator;

  /**
   * creates a new uno game board given a deck of cards to play with
   *
   * @param drawDeck collection of cards to use as the draw deck
   */
  public UnoGameBoard(DeckCardCollection drawDeck) {
    this(drawDeck, new PlayDeck(Config.EMPTY_CARD_COLLECTION));
  }

  /**
   * create a new UnoGameBoard with an existing draw deck and play deck
   *
   * @param drawDeck is a collection of cards to use as the draw deck
   * @param playDeck is a collection of cards to use as the play deck
   */
  public UnoGameBoard(DeckCardCollection drawDeck, DeckCardCollection playDeck) {
    this.drawDeck = drawDeck;
    this.playDeck = playDeck;
    cardValidator = new UnoCardValidator();
    initBoard();
  }

  private void initBoard() {
    Card firstCard = getFromDrawDeck();
    playDeck.addCard(firstCard);
  }

  /**
   * gets the top card from the draw deck
   *
   * @return top card from draw deck
   */
  @Override
  public Card getFromDrawDeck() {
    if (drawDeck.isEmpty()) {
      resetDrawDeck();
      if (drawDeck.isEmpty()) {
        ResourceBundle myResources = ResourceBundle.getBundle(
            Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
        throw new NoPossibleCardsToDrawException(
            myResources.getString(NoPossibleCardsToDrawException.class.getSimpleName()));
      }
    }
    return drawDeck.getTopCard();
  }

  /**
   * get a list of all the cards in the draw deck
   *
   * @return a list of all the cards in the draw deck
   */
  public List<Card> getAllDrawDeckCards() {
    return drawDeck.getAllCards();
  }

  /**
   * get a list of all the cards in the play deck
   *
   * @return a list of all the cards in the play deck
   */
  public List<Card> getAllPlayDeckCards() {
    return playDeck.getAllCards();
  }

  /**
   * transfers a card from the draw deck to a card collection, in this case a player's hand
   *
   * @param playerHand cards in a player's hand
   * @return card that was transferred
   */
  @Override
  public Card transferCardFromDrawDeckTo(CardCollection playerHand) {
    if (drawDeck.isEmpty()) {
      resetDrawDeck();
    }
    Card cardToTransfer = drawDeck.peekTopCard();
    drawDeck.transferCardTo(playerHand, drawDeck.peekTopCard());
    return cardToTransfer;
  }

  private void resetDrawDeck() {
    Card topCard = playDeck.getTopCard();
    playDeck.transferAll(drawDeck);
    drawDeck.reorganize();
    playDeck.addCard(topCard);
  }

  /**
   * add a card to the top of the play deck
   *
   * @param card card to add to the play deck
   */
  @Override
  public void addToPlayDeck(Card card) {
    playDeck.addCard(card);
  }

  /**
   * checks if the given card is valid to play on the top of the play deck
   *
   * @param card card to play
   * @return true if the card is valid to play
   */
  @Override
  public boolean ifValidToPlay(Card card) {
    return cardValidator.isValidToPlay(card, playDeck.getTopCard()) || playDeck.getSize() == 0;
  }

  /**
   * gives the caller a reference to the top card of the play deck
   *
   * @return card on the top of the play deck
   */
  @Override
  public Card peekPlayDeckTopCard() {
    return playDeck.peekTopCard();
  }

  /**
   * shuffle the draw deck
   */
  @Override
  public void shuffleDrawDeck() {
    drawDeck.reorganize();
  }

}
