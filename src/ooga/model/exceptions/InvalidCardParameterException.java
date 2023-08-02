package ooga.model.exceptions;

/**
 * Custom exception class for invalid card parameter given
 *
 * @author Alicia Wu
 */
public class InvalidCardParameterException extends RuntimeException {

  /**
   * exception thrown when an invalid card parameter is given (ex: red number -3 card)
   */
  public InvalidCardParameterException(String message) {
    super(message);
  }
}