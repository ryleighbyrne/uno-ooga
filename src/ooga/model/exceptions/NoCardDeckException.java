package ooga.model.exceptions;

/**
 * Custom exception class for if a game is instantiated with no cards to play with
 *
 * @author Alicia Wu
 */
public class NoCardDeckException extends RuntimeException {

  /**
   * exception thrown when game is instantiated with no cards to play with
   */
  public NoCardDeckException(String message) {
    super(message);
  }
}
