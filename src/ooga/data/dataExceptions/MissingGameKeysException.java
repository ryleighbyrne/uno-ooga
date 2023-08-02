package ooga.data.dataExceptions;

/**
 * This exception will be raised if the json file for a game is missing required keys, like
 * "playDeck", "drawDeck", "gameType",
 *
 * @author Keith Cressman
 */
public class MissingGameKeysException extends Exception {

  /**
   * Create an instance of this exception with the given message
   *
   * @param message
   */
  public MissingGameKeysException(String message) {
    super(message);
  }


}
