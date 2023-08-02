package ooga.model.exceptions;

/**
 * Custom exception class for a card not being found
 *
 * @author Alicia Wu
 */
public class CardNotFoundException extends RuntimeException {

  /**
   * exception thrown that a card is not found
   */
  public CardNotFoundException(String message) {
    super(message);
  }
}
