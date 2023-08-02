package ooga.data.dataExceptions;

/**
 * This class represents exceptions that will be thrown trying to access the profile of a player,
 * but the username sought after is not in the database, i.e. the user cannot be found
 *
 * @author Keith Cressman
 */
public class UserNotFoundException extends Exception {

  /**
   * create an instance of this exception with the given message
   *
   * @param message
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}
