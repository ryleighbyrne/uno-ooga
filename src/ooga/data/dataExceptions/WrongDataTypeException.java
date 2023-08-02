package ooga.data.dataExceptions;

/**
 * The purpose of this class is to rperesent exceptions when a game's json file has improper data
 * types for a key. For instance, if a card's "param" value is not an integer, this exception will
 * be thrown
 *
 * @author Keith Cressman
 */
public class WrongDataTypeException extends Exception {

  /**
   * create an instance of this exception with the given message
   *
   * @param message
   */
  public WrongDataTypeException(String message) {
    super(message);
  }


}
