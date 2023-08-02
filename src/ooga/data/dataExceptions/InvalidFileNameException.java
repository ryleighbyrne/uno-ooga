package ooga.data.dataExceptions;

/**
 * Exceptions of this class are to be thrown when trying to save a file with an invalid name, such
 * as if you wanted to save a file named ".../xasd/.fd\"
 *
 * @author Keith Cressman
 */
public class InvalidFileNameException extends Exception {

  /**
   * create an instance of this exception
   *
   * @param message will be the message on the Exception
   */
  public InvalidFileNameException(String message) {
    super(message);
  }

}
