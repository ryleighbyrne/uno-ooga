package ooga.view.toolbar;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import ooga.view.Config;
import ooga.view.resources.Resources;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import ooga.view.display.profileDisplay.ProfileSettings;

/**
 * This class contains methods pertaining to the ToolBar at the top of the display.
 *
 * @author Cate Schick cms168
 */
public final class Toolbar {

  /**
   * Display object used with ToolBar.
   */
  private static Display myDisplay;
  /**
   * Profile Handler
   */
  private final ProfileSettings myHandler;

  /**
   * Constructor for toolbar.
   *
   * @param d Display associated with Toolbar.
   */
  public Toolbar(Display d) {
    myDisplay = d;
    myHandler = new ProfileSettings(Config.MY_VIEW);
  }

  /**
   * Creates new ToolBar to be displayed at top of display.
   *
   * @return myToolBar.
   */
  public ToolBar createToolbar() {
    ToolBar myToolbar = new ToolBar();

    // Add buttons for UI features to ToolBar
    Button[] buttons = createButtons();
    styleButtons(myToolbar, buttons);

    // Add greeting and profile picture
    addUserContent(myToolbar);

    // Style
    myToolbar.getStyleClass().add(Config.TOOLBAR_STYLE);
    return myToolbar;
  }

  /**
   * Adds user greeting and profile picture to Toolbar.
   *
   * @param t Toolbar greeting and profile picture are being added to.
   */
  private void addUserContent(final ToolBar t) {
    // Add greeting for current user with profile picture
    Label greeting = addGreeting();

    ImageView profilePicture = myHandler.getProfilePicture(ProfileSettings.getCurrentUser());
    assert profilePicture != null;
    profilePicture.setFitHeight(Config.TOOLBAR_PIC_HEIGHT);
    profilePicture.setFitWidth(Config.TOOLBAR_PIC_WIDTH);

    t.getItems().addAll(greeting, profilePicture);
  }

  /**
   * Adds buttons to a specified ToolBar.
   */
  private Button[] createButtons() {
    assert Config.MY_RESOURCES != null;

    Button home = setButtons(Resources.getKey(Config.HOME), Config.INTRO_DISPLAY_PATH +
        Config.INTRO_DISPLAY);
    Button newGame = setButtons(Resources.getKey(Config.NEW_GAME), Config.GAME_DISPLAY);
    Button about = setButtons(Resources.getKey(Config.ABOUT), Config.ABOUT);
    Button gameSettings = setButtons(Resources.getKey(Config.GAME_SETTINGS),
        Config.GAME_SETTINGS_PATH + Config.GAME_SETTINGS);
    Button languageSettings = setButtons(Resources.getKey(Config.LANGUAGE_SETTINGS),
        Config.LANGUAGE_SETTINGS);
    Button designSettings = setButtons(Resources.getKey(Config.DESIGN_SETTINGS),
        Config.DESIGN_SETTINGS_PATH + Config.DESIGN_SETTINGS);
    Button profileSettings = setButtons(Resources.getKey(Config.PROFILE_SETTINGS),
        Config.PROFILE_DISPLAY_PATH + Config.PROFILE_SETTINGS);

    // Return buttons
    return new Button[]{home, about, newGame, gameSettings, designSettings,
        languageSettings, profileSettings};
  }

  /***
   * This method adds a given settings display to the Toolbar.
   *
   * @param label Name of display.
   * @param displayPath Path for Display.
   * @return Button that navigates to specified display.
   */
  private Button setButtons(final String label, final String displayPath) {
    return ViewButton.makeButton(label,
        e -> myDisplay.switchDisplay(displayPath));
  }

  /**
   * Adds greeting to right-hand side of toolbar.
   *
   * @return Greeting welcoming user to UNO.
   */
  private Label addGreeting() {
    Label greeting = new Label(
        Resources.getKey(Config.GREETING) + Config.SPACE
            + ProfileSettings.getCurrentUser());

    // Style with font and size from View
    greeting.getStyleClass().addAll(Config.MY_VIEW.getFont(),
        Config.GREETING_BUTTON + Config.MY_VIEW.getSize(),
        Config.GREETING_BUTTON);

    return greeting;
  }

  /**
   * Styles all ToolBar buttons.
   *
   * @param t       Toolbar object buttons are being added to.
   * @param buttons button array with all display buttons.
   */
  private void styleButtons(final ToolBar t, final Button[] buttons) {
    for (Button b : buttons) {
      b.getStyleClass().add(Config.TOOLBAR_BUTTON_STYLE);
      b.setWrapText(true);
      ViewButton.setButtonSize(b, Config.TOOLBAR_BUTTON_STYLE);

      t.getItems().add(b);
    }
  }

}
