package ooga.model.exceptions;

/**
 * Custom exception class for when a wild card created is not of color none
 *
 * @author Alicia Wu
 */
public class WildCardNotOfColorNoneException extends RuntimeException {

  /**
   * exception thrown when a wild card is not of color none
   */
  public WildCardNotOfColorNoneException(String message) {
    super(message);
  }
}