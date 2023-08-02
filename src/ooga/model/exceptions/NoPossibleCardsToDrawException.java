package ooga.model.exceptions;

/**
 * Custom exception class for having no more possible cards in the game to draw
 *
 * @author Alicia Wu
 */
public class NoPossibleCardsToDrawException extends RuntimeException {

  /**
   * exception thrown when card information is missing when creating a new uno card
   */
  public NoPossibleCardsToDrawException(String message) {
    super(message);
  }
}