package ooga.model.exceptions;

/**
 * Custom exception class for missing card information on a card
 *
 * @author Alicia Wu
 */
public class InvalidCardException extends RuntimeException {

  /**
   * exception thrown when card information is missing when creating a new uno card
   */
  public InvalidCardException(String message) {
    super(message);
  }
}