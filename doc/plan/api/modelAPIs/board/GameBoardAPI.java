/**
 * keeps track of discard deck & draw deck, supports discarding/drawing cards functionality,
 * resetting the draw deck when it runs out, and return true if a given card can be played on the
 * current played deck
 */
public interface GameBoard {

  Card drawCard();

  void discardCard(Card card);

  void resetDrawDeck();

  boolean ifValidToPlay(Card card);
}