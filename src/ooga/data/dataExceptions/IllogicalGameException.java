package ooga.data.dataExceptions;

/**
 * Exceptions of this class are to be thrown when trying to create a game which logically cannot
 * exist, such as if there are more players than cards
 *
 * @author Keith Cressman
 */
public class IllogicalGameException extends Exception {

  /**
   * create an instance of this exception
   *
   * @param message will be the message on the Exception
   */
  public IllogicalGameException(String message) {
    super(message);
  }

}
