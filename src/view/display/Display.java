package ooga.view.display;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ooga.PropertyChangeObservable;
import ooga.view.Config;
import ooga.view.resources.Resources;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ooga.view.View;
import ooga.view.userAlert.UserAlert;
import ooga.view.button.ViewButton;

/**
 * This class is the basic setup of all Displays in our program. It contains methods applicable to 2
 * or more displays.
 *
 * @author Cate Schick cms168
 */
public class Display extends PropertyChangeObservable {

  /**
   * Constructor for Display class.
   *
   * @param v View object associated with Display.
   */
  public Display(final View v) {
    Config.MY_RESOURCES = Resources.getResources(Resources.getPath());
    Config.MY_VIEW = v;
  }

  /**
   * Uses reflection based on button input to navigate to proper display.
   *
   * @param newDisplay The new Display sub-class representing the desired display to switch to.
   */
  private void setDisplayAs(final String newDisplay) {

    try {
      // Class name representing path to file.
      Class<?> displayClass = Class.forName(
          Config.DISPLAY_PACKAGE_PATH + newDisplay);

      // Call Constructor, create new instance, and set MY_DISPLAY as new display
      Display d = (Display) displayClass.getDeclaredConstructor(View.class)
          .newInstance(Config.MY_VIEW);

      Config.MY_VIEW.setMyDisplay(d);
      Config.MY_VIEW.getRoot().setCenter(d.createDisplay());

    } catch (Exception exception) {
      UserAlert.showError(exception);
    }
  }

  /**
   * Calls createDisplay() to build the specified display child.
   *
   * @return The new display.
   */
  public BorderPane createDisplay() {
    return Config.MY_VIEW.getMyDisplay().createDisplay();
  }

  /**
   * Allows user to switch between Displays.
   *
   * @param newDisplay The display to be switched to.
   */
  public void switchDisplay(final String newDisplay) {
    // Set MY_DISPLAY to new display and set .css style.
    setDisplayAs(newDisplay);

    // Set root's center to the content of the new display
    Config.MY_VIEW.getRoot().setCenter(
        Config.MY_VIEW.getMyDisplay().createDisplay());

  }

  /**
   * Sets the title of a display to a given String pulled from a resource file.
   *
   * @param display The display being created.
   * @param title   The Title to be set to top of display.
   */
  public void setTitle(final BorderPane display,
      final Label title) {
    HBox h = new HBox();
    h.getStyleClass().addAll(Config.MY_VIEW.getFont(),
        Config.TITLE_STYLE + Config.MY_VIEW.getSize(), Config.TITLE_STYLE);
    h.getChildren().add(title);

    display.setTop(h);
  }

  /**
   * Navigates back to specified display.
   *
   * @param display The display the back button will navigate to.
   */
  public Button makeBackButton(final String display) {
    Button button = ViewButton.makeButton(Resources.getKey(Config.BACK_BUTTON),
        e -> Config.MY_VIEW.getMyDisplay().switchDisplay(display));
    button.getStyleClass().addAll(Config.MY_VIEW.getFont(),
        Config.TOOLBAR_BUTTON_STYLE + Config.MY_VIEW.getSize(),
        Config.TOOLBAR_BUTTON_STYLE, Config.BACK_BUTTON);
    return button;
  }

  /**
   * This method sets a display's .css style class to its Java class name.
   *
   * @param className Name of Java class and .css style class.
   * @param display   Pane that holds display content.
   */
  public void setDisplayStyle(final String className, final Pane display) {
    display.getStyleClass().add(className);
  }
}
