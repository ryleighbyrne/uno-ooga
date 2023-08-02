package ooga.model.exceptions;

/**
 * Custom exception class for invalid number of cards to start with
 *
 * @author Alicia Wu
 */
public class InvalidNumberOfCardsToStartException extends RuntimeException {

  /**
   * exception thrown when an invalid number of cards for each player is start with is given
   */
  public InvalidNumberOfCardsToStartException(String message) {
    super(message);
  }
}