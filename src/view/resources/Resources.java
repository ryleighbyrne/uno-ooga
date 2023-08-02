package ooga.view.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import ooga.data.GameSaver;
import ooga.view.Config;
import ooga.view.userAlert.UserAlert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class handles property files, resource bundles, and path strings.
 *
 * @author Cate Schick cms168
 */
final public class Resources {

  /**
   * used for logging
   */
  private static final Logger LOGGER = LogManager.getLogger(Resources.class);

  /**
   * Default language set to English.
   */
  private static String language = "english";

  /**
   * Returns resources from the desired resource bundle.
   *
   * @param path the filepath indicating which resource bundle to access.
   * @return keys and values of resource bundle.
   */
  public static ResourceBundle getResources(final String path) {
    try {
      return ResourceBundle.getBundle(path);

      /* NOT A VALID RESOURCE FILE */
    } catch (MissingResourceException exception) {

      // Set to english and regenerate resources
      Resources.setPath(Config.ENGLISH);
      UserAlert.showMessage(Resources.getKey(Config.INVALID_RESOURCE),
          Config.CANNOT_COMPLETE);
      getResources(Resources.getPath());
    }
    return null;
  }

  /**
   * This method returns the current resource file path in the program.
   */
  public static String getPath() {
    return String.format(Config.RESOURCE_PATH, language);
  }

  /**
   * Allows user to set resource path to a selected language and redraws all displays in that
   * language.
   *
   * @param newLanguage The language to be switched to.
   */
  public static void setPath(final String newLanguage) {
    // If user accidentally capitalized language, fix this issue here
    language = newLanguage.toLowerCase();

    // Try to generate resources
    getResources(Resources.getPath());
  }

  /**
   * This method returns a key from current resource file.
   */
  public static String getKey(final String key) {
    try {
      return Config.MY_RESOURCES.getString(key);
    } /* INVALID KEY */ catch (MissingResourceException | NullPointerException e) {
      // return an empty string
      LOGGER.info(e.getMessage());
      return Config.BLANK;
    }
  }
}

