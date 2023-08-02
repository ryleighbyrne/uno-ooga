package ooga.view.userAlert;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import ooga.data.GameSaver;
import ooga.view.Config;
import ooga.view.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class handles errors and exceptions on the View end of the program.
 *
 * @author Cate Schick cms168
 */
final public class UserAlert {

  /**
   * used for logging
   */
  private static final Logger LOGGER = LogManager.getLogger(GameSaver.class);

  /**
   * Private constructor for public utility class.
   */
  private UserAlert() {
  }

  /**
   * This method displays an error message to the user.
   *
   * @param e Exception being thrown.
   */
  public static void showError(final Exception e) {
    LOGGER.error(e.getMessage());
    ResourceBundle myResources = Resources.getResources(
        Resources.getPath());

    Alert alert = new Alert(Alert.AlertType.ERROR);
    styleAlert(alert);

    // If no resource file is specified or file path is invalid,
    if (myResources != null) {
      alert.setTitle(Resources.getKey(Config.ERROR_TITLE));
      alert.setContentText(Resources.getKey(Config.ERROR_MESSAGE) + Config.SPACE + e);
      alert.showAndWait();

    } else {
      // Set to english to avoid application crashing.
      Resources.setPath(Config.ENGLISH);

      // Try again with valid resource file path.
      showError(e);
    }
  }

  /**
   * This is a message to the user.
   *
   * @param message Message to be displayed to user.
   * @param header  Info Header describing issue.
   */
  public static void showMessage(final String message, final String header) {
    Alert alert = new Alert(AlertType.INFORMATION);
    styleAlert(alert);

    // Set content
    alert.setTitle(Resources.getKey(Config.INFORMATION_MESSAGE_TITLE));
    alert.setHeaderText(Resources.getKey(header));
    alert.setContentText(message);

    alert.showAndWait();
  }

  /**
   * This method styles alerts and messages to user.
   *
   * @param alert Alert beig styled.
   */
  private static void styleAlert(final Alert alert){
    DialogPane pane = alert.getDialogPane();
    pane.getStylesheets().add(String.format(
        Config.THEME_PATH, Config.MY_VIEW.getCurrentTheme()));
    pane.getStyleClass().add(Config.DIALOGUE_STYLE);
  }
}
