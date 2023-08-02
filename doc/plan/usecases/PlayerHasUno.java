/**
 * implementing use case in which a player has one card left in hand
 */

package ooga.model.players;

import ooga.model.cardcollection.hand.Hand;
import ooga.model.cards.cardcomponents.Card;

public abstract class Player {

    private int playerId;
    private Hand playerHand;
    private int uno;  // keeping as this in case different versions could have different uno meanings?

    public Player(int playerId) {
        this.playerId = playerId;
    }

    public void addToHand(Card card){
        playerHand.addCard(card);
    }

    public boolean unoStatus(){ return playerHand.getSize() == uno;}
}