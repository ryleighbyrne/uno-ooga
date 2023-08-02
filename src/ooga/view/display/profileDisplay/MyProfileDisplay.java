package ooga.view.display.profileDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.data.ProfileHandler;
import ooga.data.dataExceptions.UserNotFoundException;
import ooga.view.Config;
import ooga.view.resources.Resources;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.dialog.Dialog;
import ooga.view.display.Display;
import ooga.view.userAlert.UserAlert;

/**
 * This class displays a user's profile information and allows them to change
 * their username, bio, profile picture, and password. Additionally, they can see their game
 * statistics and delete their profile.
 *
 * @author Cate Schick cms168
 */
public final class MyProfileDisplay extends Display {

  /**
   * Profile information from .json file.
   */
  private Map<String, String> myInfo;
  /**
   * Handles Profile Settings method.
   */
  private final ProfileSettings mySettings;
  /**
   * Pane with display content
   */
  private BorderPane myPane;
  /**
   * Current profile picture.
   */
  private Button myProfilePicture;

  /**
   * Constructor for Display class.
   *
   * @param v View object associated with Display.
   */
  public MyProfileDisplay(final View v) {
    super(v);
    mySettings = new ProfileSettings(Config.MY_VIEW);
    try {
      myInfo = ProfileHandler.getPlayerInfo(ProfileSettings.getCurrentUser());
    } catch (UserNotFoundException e) {
      UserAlert.showError(e);
    }
  }

  /**
   * Override method creates a display showing profile settings.
   *
   * @return My Profile display showing the current user's information.
   */
  @Override
  public BorderPane createDisplay() {
    myPane = new BorderPane();
    myPane.setLeft(makeBackButton(Config.PROFILE_DISPLAY_PATH + Config.PROFILE_SETTINGS));
    myPane.setCenter(createMyProfile());
    myPane.setRight(makeDeleteButton());

    return myPane;
  }

  /**
   * This method asks a user if they are sure they want to delete their profile. If they correctly
   * enter their password, they can.
   */
  private void deleteProfile() {
    String oldUser = ProfileSettings.getCurrentUser();
    // Cannot delete Guest
    if (oldUser.equals(Config.GUEST)) {
      UserAlert.showMessage(Resources.getKey(Config.DELETE_GUEST),
          Config.CAUTION);
      return;
    } else {
      // Caution user about deleting profile
      UserAlert.showMessage(Resources.getKey(Config.DELETE_PROFILE_PROMPT),
          Resources.getKey(Config.CAUTION));
    }

    // Verify password
    if (validPassword()) {
      try {
        ProfileSettings.switchToUser(Config.GUEST);
        ProfileHandler.removeProfile(oldUser);
      } catch (UserNotFoundException e) {
        UserAlert.showError(e);
        ProfileSettings.switchToUser(Config.GUEST);
      }
    }
  }

  /**
   * This method checks a user's inputted password with saved password.
   *
   * @return If entered password matches.
   */
  private boolean validPassword() {
    // Validate password before deleting
    String input = Dialog.getUserInput(Resources.getKey(Config.PASSWORD_PROMPT));
    return input.equals(myInfo.get(Config.PASSWORD_KEY));
  }

  /**
   * This method makes a button for deleting a profile.
   *
   * @return Delete Profile button.
   */
  private Button makeDeleteButton() {
    Button b = ViewButton.makeButton(Resources.getKey(Config.DELETE_PROFILE), e ->
        deleteProfile());
    ViewButton.setButtonSize(b, Config.TOOLBAR_BUTTON_STYLE);
    return b;
  }

  /**
   * This method creates myProfileDisplay content.
   */
  private BorderPane createMyProfile() {
    BorderPane content = new BorderPane();

    // Set profile picture, username, and bio at top
    content.setTop(makeTopContainer());
    content.setCenter(addGameStats());
    return content;
  }

  /**
   * This method makes the top of the myProfileDisplay, containing the user's profile picture,
   * username, and bio.
   *
   * @return VBox with user information.
   */
  private VBox makeTopContainer() {
    VBox v = new VBox();
    v.getStyleClass().add(Config.PROFILE_PICTURE_CONTAINER);

    // Add profile picture, username, and bio
    addProfilePicture();
    Button username = addUserAndBio(Config.USERNAME_KEY);
    Button bio = addUserAndBio(Config.BIO_KEY);

    v.getChildren().addAll(myProfilePicture, username, bio);
    return v;
  }

  /**
   * Adds a user's profile picture to the top of the display.
   */
  private void addProfilePicture() {
    // Add profile picture, tooltip, and style
    myProfilePicture = ViewButton.makeButton(null, e ->
        loadPictureOptions());
    ViewButton.addTooltip(myProfilePicture, String.format(
        Resources.getKey(Config.CHANGE_KEY), Resources.getKey(Config.PROFILE_PICTURE_KEY)));
    myProfilePicture.getStyleClass().add(Config.PROFILE_PICTURE_STYLE);

    // Generate ImageView with picture path and set
    ImageView profile = mySettings.getProfilePicture(ProfileSettings.getCurrentUser());
    assert profile != null;
    profile.setFitHeight(Config.LOGIN_PIC_HEIGHT);
    profile.setFitWidth(Config.LOGIN_PIC_WIDTH);
    myProfilePicture.setGraphic(profile);
  }

  /**
   * Adds a username and bio underneath profile picture.
   *
   * @param key Key to extract information from.
   * @return Label[] with username and bio
   */
  private Button addUserAndBio(final String key) {
    // Add key
    String keyValue = myInfo.get(key);

    // If nothing entered, enter blank
    if (keyValue == null) {
      keyValue = String.format(Config.ADD_KEY, key);
    }

    // Create button allowing users to update information
    Button button = ViewButton.makeButton(keyValue, e ->
        inputNewKey(key));

    // Add a tooltip and style
    ViewButton.addTooltip(button, (String.format(Resources.getKey(Config.CHANGE_KEY),
        Resources.getKey(key))));
    button.getStyleClass().add(key);
    return button;
  }

  /**
   * This method shows profile picture options and sets button functionality for user to save path
   * as new profile picture.
   */
  private void loadPictureOptions() {
    // CANNOT EDIT GUEST INFORMATION
    if (!ProfileSettings.getCurrentUser().equals(Config.GUEST)) {
      myPane.getChildren().clear();

      // Back button to myProfileDisplay
      myPane.setLeft(Config.MY_VIEW.getMyDisplay().makeBackButton(
          Config.PROFILE_DISPLAY_PATH + Config.MY_PROFILE_DISPLAY));

      // Add picture options
      ArrayList<String> pictures = View.listFiles(Config.PUBLIC_IMAGE_PATH);
      myPane.setCenter(showPictureOptions(pictures));
    }
  }

  /**
   * This method shows the available public profile pictures.
   *
   * @param pictures Picture options.
   * @return GridPane containing picture options.
   */
  private GridPane showPictureOptions(final List<String> pictures) {
    GridPane grid = new GridPane();
    grid.getStyleClass().add(Config.PROFILE_PICTURE_CONTAINER);

    // Add each option to the GridPane
    int row = 0;
    int col = 0;
    for (String option : pictures) {
      Button b = makePictureButton(option);
      grid.add(b, row, col);
      row++;
      if (row >= Config.MAX_PICTURES_PER_ROW) {
        col++;
        row = 0;
      }
    }
    return grid;
  }

  /**
   * This method creates a picture button for a profile picture option.
   *
   * @param picture Path for specified profile picture.
   */
  private Button makePictureButton(final String picture) {
    // Format file path name and create button
    String formatted = picture.replace(Config.DATA_PREFIX, Config.BLANK);
    Button b = ViewButton.makeButton(null, e ->
        setProfilePicture(formatted));

    // Create ImageView
    Image newPicture = new Image(formatted);
    ImageView view = new ImageView(newPicture);

    // Style
    view.setFitHeight(Config.PICTURE_OPTIONS_HEIGHT);
    view.setFitWidth(Config.PICTURE_OPTIONS_WIDTH);
    b.setGraphic(view);
    return b;
  }

  /**
   * This method sets a new profile picture and reloads myProfileDisplay.
   *
   * @param picturePath String path to picture.
   */
  private void setProfilePicture(final String picturePath) {
    // Create ImageView and style
    Image newPicture = new Image(picturePath);
    ImageView view = new ImageView(newPicture);
    ProfileSettings.updateKey(Config.PROFILE_PICTURE_KEY, picturePath);
    myProfilePicture.setGraphic(view);

    // Update scene
    Config.MY_VIEW.updateScene(Config.PROFILE_DISPLAY_PATH + Config.MY_PROFILE_DISPLAY);
  }

  /**
   * This method gets user information and displays it.
   *
   * @return HBox containing game stats and change password button.
   */
  private HBox addGameStats() {
    try {
      Map<String, String> playerInfo = ProfileHandler.getPlayerInfo(
          ProfileSettings.getCurrentUser());
      HBox h = new HBox();

      // Add game stats and password to center of pane and style
      for (String key : playerInfo.keySet()) {
        if (!skipKey(key)) {
          String label = getStatLabel(key, playerInfo);
          Button b = ViewButton.makeButton(label, e ->
              makeClickable(key));
          b.getStyleClass().add(Config.GAME_STATS_STYLE);
          h.getChildren().add(b);
        }
      }
      return h;
      // User not found; switch to Guest
    } catch (UserNotFoundException exception) {
      UserAlert.showError(exception);
      ProfileSettings.switchToUser(Config.GUEST);
      return null;
    }
  }

  /**
   * This method creates the label for a game stat button.
   *
   * @param key        Key being examined.
   * @param playerInfo Map containing player information.
   * @return Label for particular stat.
   */
  private String getStatLabel(final String key, final Map<String, String> playerInfo) {
    String label;
    // Don't show user's password
    if (key.equals(Config.PASSWORD_KEY)) {
      label = Resources.getKey(Config.CHANGE_PASSWORD);
    } else {
      label = Resources.getKey(key) + Config.COLON + Config.SPACE + playerInfo.get(key);
    }
    return label;
  }

  /**
   * This method calls on the data backend to alter the value of a particular key.
   *
   * @param key The key being altered.
   */
  private void inputNewKey(final String key) {
    if (!ProfileSettings.getCurrentUser().equals(Config.GUEST)) {
      String newValue = Dialog.getUserInput(String.format(Resources.getKey(Config.NEW_KEY), key));

      // Too long of an entry or no entry
      if (characterLimitReached(newValue)) {
        UserAlert.showMessage(String.format(Resources.getKey(Config.CHARACTER_LIMIT),
            Config.MAX_CHARACTERS), Config.CANNOT_COMPLETE);
        return;
      } else if (newValue.equals(Config.BLANK)) {
        return;
      }

      // If user does not change username, return
      if (key.equals(Config.USERNAME_KEY)) {
        updateUsername(newValue);
        return;
      }

      // Add new key to database
      ProfileSettings.updateKey(key, newValue);

      // Refresh View
      Config.MY_VIEW.updateScene(Config.PROFILE_DISPLAY_PATH +
          Config.MY_PROFILE_DISPLAY);
    }
  }

  /**
   * This method validates user input and makes sure it is not too many characters and properly
   * formatted.
   *
   * @param newValue User input.
   */
  private boolean characterLimitReached(final String newValue) {
    return (newValue.length() > Config.MAX_CHARACTERS);
  }

  /**
   * Deletes old username and data and creates entry with new username.
   *
   * @param newValue Username selected by user.
   */
  private void updateUsername(final String newValue) {
    // Do not create blank user
    if (!newValue.equals(Config.BLANK)) {

      // Delete old username and transfer data to new username
      ProfileSettings.updateKey(Config.USERNAME_KEY, newValue);
      String oldUsername = myInfo.get(Config.USERNAME_KEY);
      try {
        // Switch to new user and delete
        ProfileSettings.switchToUser(newValue);
        ProfileHandler.removeProfile(oldUsername);
      } catch (UserNotFoundException e) {
        UserAlert.showError(e);
      }
    }

    // Update scene
    Config.MY_VIEW.updateScene(Config.PROFILE_DISPLAY_PATH +
        Config.MY_PROFILE_DISPLAY);
  }

  /**
   * This method sets functionality only for buttons where the key can be altered.
   *
   * @param key The key being examined.
   */
  private void makeClickable(final String key) {
    // User must enter old password to change it
    if (key.equals(Config.PASSWORD_KEY)) {
      if (validPassword()) {
        inputNewKey(Config.PASSWORD_KEY);
      } else {
        UserAlert.showMessage(Resources.getKey(Config.WRONG_PASSWORD_ERROR),
            Config.CANNOT_COMPLETE);
      }
    }
    // Make sure key is editable and current user is NOT Guest
    else if (canEdit(key) && !ProfileSettings.getCurrentUser().equals(Config.GUEST)) {
      // User must enter password before changing
      Dialog.getUserInput(Resources.getKey(key));
    }
  }

  /**
   * This method makes sure keys that aren't relevant to myProfileDisplay aren't shown. These
   * include profile picture as it is already set at the top of the display, and user preferences
   * which are implemented upon login.
   *
   * @param key Key to check.
   * @return Whether key should be skipped.
   */
  private boolean skipKey(final String key) {
    // Skip username, bio, profile picture, and preferences
    return key.equals(Config.USERNAME_KEY) | key.equals(Config.BIO_KEY) |
        key.equals(Config.PROFILE_PICTURE_KEY) | key.contains(Config.PREFERRED_PREFIX);
  }

  /**
   * This method makes sure keys that aren't relevant to myProfileDisplay aren't shown. These
   * include profile picture as it is already set at the top of the display, and user preferences
   * which are implemented upon login.
   *
   * @param key Key to check.
   * @return Whether key should be skipped.
   */
  private boolean canEdit(final String key) {
    // Can edit password, bio, and username
    return key.equals(Config.PASSWORD_KEY) || key.equals(Config.BIO_KEY) || key.equals(
        Config.USERNAME_KEY);
  }
}
