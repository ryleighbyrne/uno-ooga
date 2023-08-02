package ooga.model.exceptions;

/**
 * Custom exception class for trying to get a card from an empty card collection
 *
 * @author Alicia Wu
 */
public class EmptyCardCollectionException extends RuntimeException {

  /**
   * exception thrown when trying to get a card from an empty card collection
   */
  public EmptyCardCollectionException(String message) {
    super(message);
  }
}

