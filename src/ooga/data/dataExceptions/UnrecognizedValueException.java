package ooga.data.dataExceptions;

/**
 * This class represents exceptions that will be thrown when a game's json file has an unrecognized
 * value For instance, if a card had "color": "keith" this exception will be raised because "keith"
 * is not a valid color
 *
 * @author Keith Cressman
 */
public class UnrecognizedValueException extends Exception {

  /**
   * create an instance of this exception with the given message
   *
   * @param message
   */
  public UnrecognizedValueException(String message) {
    super(message);
  }
}
