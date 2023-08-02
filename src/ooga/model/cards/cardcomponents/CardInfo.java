package ooga.model.cards.cardcomponents;

/**
 * This interface defines the CardInfo API, card information must contain the card's type and its
 * action parameters
 *
 * @author Alicia Wu
 */
public interface CardInfo {

  /**
   * gets the card's type (e.g.: skip, number, wild, etc.)
   *
   * @return String representing a card's type
   */
  String getType();

  /**
   * gets the card's action parameter (i.e.: 2 in "draw 2", 1 in "skip 1", 7 in "number 7")
   *
   * @return int representing a card's parameter
   */
  int getParam();

}
