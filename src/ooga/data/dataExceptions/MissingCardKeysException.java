package ooga.data.dataExceptions;


/**
 * Exceptions of this class are to be thrown when trying to read in JSON code describing a card, but
 * the JSONObject is missing required keys
 *
 * @author Keith Cressman
 */
public class MissingCardKeysException extends Exception {

  /**
   * create an instance of this exception
   *
   * @param message will be the message on the Exception
   */
  public MissingCardKeysException(String message) {
    super(message);
  }

}


