package ooga.view.dialog;


import java.util.List;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import ooga.view.resources.Resources;
import javafx.scene.control.TextInputDialog;
import ooga.view.Config;

/**
 * This class handles user input through TextInputDialogs and ChoiceDialogs.
 *
 * @author Cate Schick cms168
 */
public class Dialog {

  /**
   * Returns a Text Input Dialogue asking user for information.
   *
   * @param header Dialogue message to user.
   * @return Formatted input from user.
   */
  public static String getUserInput(final String header) {
    // Create and style
    TextInputDialog dialog = new TextInputDialog();
    DialogPane pane = dialog.getDialogPane();
    styleDialog(pane);

    // Add content
    dialog.setHeaderText(header);
    String userInput = dialog.showAndWait().toString();
    String formatted = formatInput(userInput);

    // User tried to submit empty response
    if (formatted.equals(Config.EMPTY_RESPONSE)) {
      return Config.BLANK;
    }
    return formatted;
  }

  /**
   * This method removes the brackets and "Optional" from a text input dialogue and verifies that it
   * is a valid String response.
   *
   * @param input Unformatted input.
   * @return Formatted String with just entered value.
   */
  private static String formatInput(final String input) {
    // Remove Optional[] from input
    return input.replace(Config.OPTIONAL, Config.BLANK).replace(Config.LEFT_BRACKET, Config.BLANK)
        .replace(Config.RIGHT_BRACKET, Config.BLANK);
  }

  /**
   * This method creates a ChoiceDialogue for a user to select an option.
   *
   * @param information Information about what user is selecting.
   * @param options     List of Options for user to select from.
   * @param title       Whether the input from user it required or optional.
   * @return User selection from options.
   */
  public static String getUserChoice(final String information, final
  List options, final String title) {
    // Create and style
    ChoiceDialog<?> dialog = new ChoiceDialog<String>();
    DialogPane pane = dialog.getDialogPane();
    styleDialog(pane);

    // Add text and choices
    dialog.setTitle(title);
    dialog.setHeaderText(String.format(Resources.getKey(Config.SELECT), information));
    dialog.getItems().addAll(options);
    dialog.showAndWait();

    Object choice = dialog.getResult();

    // Change null to empty String
    if (choice != null) {
      return choice.toString();
    }
    return Config.BLANK;
  }

  /**
   * This method styles a DialogPane with the current theme.
   *
   * @param pane Dialog pane being styled.
   */
  private static void styleDialog(final DialogPane pane) {
    pane.getStylesheets().add(String.format(
        Config.THEME_PATH, Config.MY_VIEW.getCurrentTheme()));
    pane.getStyleClass().add(Config.DIALOGUE_STYLE);
  }
}
