package ooga.data.dataExceptions;

/**
 * This exception will be raised if the JSONObject describing a player is missing required keys,
 * like "playerID" or "playerHand"
 *
 * @author Keith Cressman
 */
public class MissingPlayerKeysException extends Exception {

  /**
   * Create an instance of this exception with the given message
   *
   * @param message
   */
  public MissingPlayerKeysException(String message) {
    super(message);
  }


}
