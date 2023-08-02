/**
 * implementing use case in which the user clicks on unplayable card when the last card was green.
 * Error message is shown suggesting they use a green card Author: Ryleigh Byrne
 */
package ooga.model.board;

import ooga.model.cardcollection.deck.Deck;
import ooga.model.cardcollection.deck.PlayDeck;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
/**
 * game board keeps track of play deck and draw deck & interfaces actions with them, validates new
 * card that a player wants to play (aka add to play deck), and displays an error message if the card is not
 * valid
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
     * if the card is valid to play, the card is played
     * if the card is not valid to play, an error message is displayed and suggested card choice is generated
     */
    public boolean ifValidToPlay(Card card) {
        return cardValidator.isValidToPlay(card, playDeck.getTopCard());
    }

}