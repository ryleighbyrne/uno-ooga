package ooga.view.display.profileDisplay;

import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import ooga.data.ProfileHandler;
import ooga.view.Config;
import ooga.view.dialog.Dialog;
import ooga.view.userAlert.UserAlert;
import ooga.view.resources.Resources;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import org.json.simple.JSONObject;

/**
 * This class allows users to switch between existing profiles in a database.
 *
 * @author Cate Schick cms168
 */
public final class SwitchProfiles extends Display {

  /**
   * User information
   */
  private final JSONObject myProfiles;
  /**
   * ProfileSettings object.
   */
  private static ProfileSettings mySettings;

  /**
   * Constructor for Display class.
   *
   * @param v View object associated with Display.
   */
  public SwitchProfiles(View v) {
    super(v);
    // Set information and settings
    myProfiles = ProfileHandler.getAllProfiles();
    mySettings = new ProfileSettings(Config.MY_VIEW);
  }

  /**
   * Creates display with other profiles.
   *
   * @return Display showing other users.
   */
  @Override
  public BorderPane createDisplay() {
    // Create display, set title and back button
    BorderPane profiles = new BorderPane();
    profiles.setLeft(makeBackButton(Config.PROFILE_DISPLAY_PATH + Config.PROFILE_SETTINGS));

    // Display users and profile pictures
    profiles.setCenter(addUsers());

    return profiles;
  }

  /**
   * This method adds a sign-in button for each user.
   *
   * @return GridPane with buttons representing users.
   */
  private GridPane addUsers() {
    // Style GridPane
    GridPane grid = new GridPane();
    grid.getStyleClass().add(Config.GRIDPANE_STYLE);

    // Add buttons for other users
    createProfileButtons(grid);

    return grid;
  }

  /**
   * This method iterates through users and sets a button for each one.
   *
   * @param grid GridPane user buttons are being added to.
   */
  private void createProfileButtons(final GridPane grid) {
    Set<?> users = myProfiles.keySet();
    int col = 0;
    int row = 0;

    for (Object user : users) {
      // Don't display Guest or current user
      if (!user.equals(Config.GUEST) && !user.equals(ProfileSettings.getCurrentUser())) {
        Button b = makeLoginButtons(user.toString());
        grid.add(b, row, col);

        // Add buttons to pane
        row++;
        if (row >= Config.SWITCH_PROFILES_MAX_BUTTONS_PER_ROW) {
          row = 0;
          col++;
        }
      }
    }
  }

  /**
   * Makes a button that, upon clicking, tries to log a user in.
   *
   * @param user User button.
   * @return Button that will try and log into user's account.
   */
  private Button makeLoginButtons(final String user) {
    Button b = new Button();
    b.setText(user);
    b.setOnAction(e -> loginUser(user));
    // Set Graphic for button equal to profile picture
    ImageView view = mySettings.getProfilePicture(user);
    assert view != null;
    view.setFitWidth(Config.LOGIN_PIC_WIDTH);
    view.setFitHeight(Config.LOGIN_PIC_HEIGHT);
    b.setGraphic(view);

    ViewButton.setButtonSize(b, Config.BUTTON_STYLE);
    b.getStyleClass().add(Config.LOGIN_BUTTON_STYLE);
    return b;
  }

  /**
   * This method checks if a user has a password saved with their account.
   *
   * @param information JSON information about selected user.
   * @return Whether the user has a password linked with their account.
   */
  private boolean hasPassword(final JSONObject information) {
    return information.containsKey(Config.PASSWORD_KEY);
  }

  /**
   * This method verifies a user through password authentication.
   *
   * @param user Selected user.
   * @return If entered password equals stored password.
   */
  private boolean confirmUser(final String user) {
    JSONObject information = (JSONObject) myProfiles.get(user);

    // Prompt user to enter password
    if (hasPassword(information)) {
      String enteredPassword = Dialog.getUserInput(String.format(
          Resources.getKey(Config.PROFILE_PROMPT), Resources.getKey(Config.PASSWORD_KEY)));

      return enteredPassword.equals(information.get(Config.PASSWORD_KEY));
    }
    return false;
  }

  /**
   * This method completes a user's login or denies them access to a user's account.
   *
   * @param user Selected user.
   */
  private void loginUser(final String user) {
    if (confirmUser(user)) {
      ProfileSettings.switchToUser(user);
      UserAlert.showMessage(String.format(Resources.getKey(Config.LOGGED_IN_MESSAGE), user),
          Resources.getKey(Config.SUCCESS));
    } else {
      UserAlert.showMessage(Resources.getKey(Config.WRONG_PASSWORD_ERROR),
          Resources.getKey(Config.CANNOT_COMPLETE));
    }
  }
}
