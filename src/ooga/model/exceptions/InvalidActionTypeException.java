package ooga.model.exceptions;

/**
 * Custom exception class for an invalid card action type (skip, draw, etc.) given
 *
 * @author Alicia Wu
 */
public class InvalidActionTypeException extends RuntimeException {

  /**
   * exception thrown that an invalid card action type (skip, draw, etc.) was given
   */
  public InvalidActionTypeException(String message) {
    super(message);
  }
}

