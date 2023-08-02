/**
 * APIs for Error class used to alert user when an exception or error occurs.
 */
public interface Error {

  // Used to show a specific error to the user.
  public static void showError(final Exception exception);

  // Changes invalid resource file path to english and alerts user.
  public static void handleMissingResourceException(final Exception exception);
}
