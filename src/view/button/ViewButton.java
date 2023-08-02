package ooga.view.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import ooga.view.Config;

/**
 * This class contains methods involved with buttons and their actions.
 *
 * @author Cate Schick cms168
 */
final public class ViewButton {

  /**
   * Private constructor for public utility class.
   */
  private ViewButton() {
  }

  /**
   * Creates a javaFx Button.
   *
   * @param label   The name of the Button.
   * @param handler What happens when the Button is pressed.
   * @return New Button.
   */
  public static Button makeButton(final String label,
      final EventHandler<ActionEvent> handler) {
    Button result = new Button();
    result.setOnAction(handler);
    result.setText(label);

    // Have button display current font
    result.getStyleClass().add(Config.MY_VIEW.getFont());
    result.setWrapText(true);
    return result;
  }

  /**
   * Applies basic button styling to button arrays.
   *
   * @param buttons Array of buttons.
   * @param display Display buttons are being added to.
   */
  public static void styleButtons(final Button[] buttons, final Pane display) {
    // Style and add to display
    for (Button b : buttons) {
      setButtonSize(b, Config.BUTTON_STYLE);

      // if display provided, add button
      if (display != null) {
        display.getChildren().add(b);
      }
    }
  }

  /**
   * This method connects a button type with the current size settings.
   *
   * @param b          Button having style attached.
   * @param styleClass The name of the button's style class
   */
  public static void setButtonSize(final Button b, final String styleClass) {
    b.getStyleClass().addAll(styleClass + Config.MY_VIEW.getSize(), styleClass);
  }

  /**
   * Set a Tooltip with a message to button.
   *
   * @param b       Button Tooltip is being added to
   * @param message Message associated with tooltip.
   */
  public static void addTooltip(final Button b, final String message) {
    b.setTooltip(new Tooltip(message));
  }
}
