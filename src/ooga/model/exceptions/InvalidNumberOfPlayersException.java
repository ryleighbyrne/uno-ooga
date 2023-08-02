package ooga.model.exceptions;

/**
 * Custom exception class for invalid number of players given
 *
 * @author Alicia Wu
 */
public class InvalidNumberOfPlayersException extends RuntimeException {

  /**
   * exception thrown when an invalid number of players is given (must be 2-4 players)
   */
  public InvalidNumberOfPlayersException(String message) {
    super(message);
  }
}