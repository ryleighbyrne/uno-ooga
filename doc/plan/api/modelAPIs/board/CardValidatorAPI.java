/**
 * validate if the two cards are a "valid combo", aka if card1 can be played on top of card 2 & vice
 * versa, given that one card is the card being put down by a user and the other is the card on the
 * top of the played deck
 */
public interface CardValidator {

  boolean isValidToPlay(Card card1, Card card2);

}