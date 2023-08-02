package ooga.view.display.profileDisplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.data.GameSaver;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.view.resources.Resources;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ooga.data.ProfileHandler;
import ooga.data.dataExceptions.UserNotFoundException;
import ooga.view.Config;
import ooga.view.dialog.Dialog;
import ooga.view.userAlert.UserAlert;
import javafx.scene.layout.BorderPane;
import ooga.view.View;
import javafx.scene.control.Label;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import org.json.simple.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class shows a user profile information and allows them to edit their information, switch
 * profiles, play as a guest, or create a new profile.
 *
 * @author Cate Schick cms168
 */
public final class ProfileSettings extends Display {

  /**
   * used to log exceptions
   */
  private static final Logger LOGGER = LogManager.getLogger(ProfileSettings.class);
  /**
   * Profile information from .json file.
   */
  private static JSONObject myProfiles;
  /**
   * Current user (start as Guest).
   */
  private static String currentUser = "Guest";

  /**
   * Constructor for Profile Settings Display.
   *
   * @param v View associated with Profile Settings display.
   */
  public ProfileSettings(View v) {
    super(v);
  }

  /**
   * Override method creates a display showing profile settings.
   *
   * @return Profile Settings display.
   */
  @Override
  public BorderPane createDisplay() {
    myProfiles = ProfileHandler.getAllProfiles();

    BorderPane myPane = new BorderPane();
    super.setTitle(myPane, new Label(
        Resources.getKey(Config.PROFILE_SETTINGS_TITLE)));

    // Create buttons and add to display.
    myPane.setCenter(addButtons());

    return myPane;
  }

  /**
   * This method adds profile settings buttons to VBox.
   *
   * @return VBox with profile setting buttons.
   */
  private VBox addButtons() {
    VBox buttons = new VBox();

    // Add buttons to display
    Button editProfile = ViewButton.makeButton(Resources.getKey(Config.EDIT_PROFILE), e ->
        this.switchDisplay(Config.PROFILE_DISPLAY_PATH + Config.MY_PROFILE_DISPLAY));
    Button switchProfile = ViewButton.makeButton(Resources.getKey(Config.SWITCH_PROFILE), e ->
        this.switchDisplay(Config.PROFILE_DISPLAY_PATH + Config.SWITCH_PROFILE_DISPLAY));
    Button createProfile = ViewButton.makeButton(Resources.getKey(Config.CREATE_PROFILE), e ->
        createNewProfile());
    Button playAsGuest = ViewButton.makeButton(Resources.getKey(Config.PLAY_AS_GUEST), e ->
        playAsGuest());

    Button[] buttonArray = new Button[]{editProfile, switchProfile, createProfile,
        playAsGuest};
    ViewButton.styleButtons(buttonArray, buttons);

    return buttons;
  }

  /**
   * This method prompts a user to create a new profile.
   */
  private void createNewProfile() {
    // first, make sure max users haven't been created
    if (maxUsers()) {
      UserAlert.showMessage(String.format(Resources.getKey(Config.MAX_USERS_ERROR),
          Config.MAX_USERS), Config.CANNOT_COMPLETE);
      return;
    }

    // Prompt user to enter important information
    Map<String, String> profileInfo = new HashMap<>();

    // Ask users for username and password
    String username = Dialog.getUserInput(String.format(Resources.getKey(Config.PROFILE_PROMPT),
        Resources.getKey(Config.USERNAME_KEY)));

    // User did not enter username, cannot create user
    if (validUsername(username)) {
      String password = Dialog.getUserInput(String.format(Resources.getKey(Config.PROFILE_PROMPT),
          Resources.getKey(Config.PASSWORD_KEY)));

      profileInfo.put(Config.USERNAME_KEY, username);
      profileInfo.put(Config.PASSWORD_KEY, password);

      // Add to profile database
      addToDatabase(profileInfo, username);
    }
  }

  /**
   * This method checks if the current number of users is at the max limit.
   *
   * @return Whether JSON file contains maximum users.
   */
  private boolean maxUsers() {
    // Max Users + Guest
    return myProfiles.keySet().size() >= Config.MAX_USERS + 1;
  }

  /**
   * Checks if a username is valid.
   *
   * @param username User's selected username
   */
  private boolean validUsername(final String username) {
    // No username entered
    if (username.equals(Config.BLANK)) {
      UserAlert.showMessage(Resources.getKey(Config.NO_USERNAME_ERROR),
          Config.CANNOT_COMPLETE);
      return false;
    } else if (isDuplicate(username)) {
      UserAlert.showMessage(Resources.getKey(Config.DUPLICATE_USERNAME),
          Config.CANNOT_COMPLETE);
      return false;
    }
    return true;
  }

  /**
   * This method adds a user to profile database.
   *
   * @param profileInfo Map containing profile information
   * @param username    User being added to database.
   */
  private void addToDatabase(final Map<String, String> profileInfo, final String username) {
    // Initialize game stats
    addStarterData(profileInfo);

    ProfileHandler.addProfile(profileInfo);
    switchToUser(username);
    UserAlert.showMessage(Resources.getKey(Config.GREETING) + Config.SPACE +
        getCurrentUser(), Config.SUCCESS);
  }

  /**
   * This method sets the View to user preferences including font, theme, font size, and preferred
   * language.
   */
  private static void setUserPreferences() {
    String theme = getKey(Config.PREFERRED_PREFIX + Config.THEME_KEY);
    String font = getKey(Config.PREFERRED_PREFIX + Config.FONT_KEY);
    String size = getKey(Config.PREFERRED_PREFIX + Config.SIZE_KEY);
    String language = getKey(Config.PREFERRED_PREFIX + Config.LANGUAGE);

    try {
      Config.MY_VIEW.setTheme(theme);
      Config.MY_VIEW.setFont(font);
      Config.MY_VIEW.setSize(size);
      // Set language and update view
      Resources.setPath(language);
      Config.MY_VIEW.updateScene(Config.PROFILE_DISPLAY_PATH + Config.PROFILE_SETTINGS);

    } catch (NullPointerException e) {
      // Reset to english
      LOGGER.info(e.getMessage());
      Resources.setPath(Config.ENGLISH);
    }
    Config.MY_RESOURCES = Resources.getResources(Resources.getPath());
  }

  /**
   * This method saves user preferences.
   */
  public static void savePreferences() {
    // Do not change Guest default preferences
    if (!currentUser.equals(Config.GUEST)) {
      String newFont = Config.MY_VIEW.getFont();
      String newTheme = Config.MY_VIEW.getCurrentTheme();
      String newSize = Config.MY_VIEW.getSize();
      String languagePath = Config.RESOURCE_PATH.replace(Config.FORMAT_STRING, Config.BLANK);
      String newLanguage = Resources.getPath().replace(languagePath, Config.BLANK);

      // Update JSON file with new preferences
      updateKey(Config.PREFERRED_PREFIX + Config.FONT_KEY, newFont);
      updateKey(Config.PREFERRED_PREFIX + Config.THEME_KEY, newTheme);
      updateKey(Config.PREFERRED_PREFIX + Config.SIZE_KEY, newSize);
      updateKey(Config.PREFERRED_PREFIX + Config.LANGUAGE, newLanguage);
    }
  }

  /**
   * This method changes a key in the JSON file.
   *
   * @param key      Key to be altered.
   * @param newValue New value to be overwritten to that key.
   */
  public static void updateKey(final String key, final String newValue) {
    try {
      ProfileHandler.changeProfile(currentUser, key, newValue);

      // User error, switch to Guest
    } catch (UserNotFoundException e) {
      LOGGER.info(e.getMessage());
      switchToUser(Config.GUEST);

      // Bad value error
    } catch (UnrecognizedValueException e) {
      LOGGER.info(e.getMessage());
      UserAlert.showMessage(Resources.getKey(Config.NO_VALUE),
          Config.CANNOT_COMPLETE);
    }
  }

  /**
   * This method checks for current user's information.
   *
   * @param key Key to be examined.
   * @return Value of key.
   */
  private static String getKey(final String key) {
    try {
      Map<String, String> myInfo = ProfileHandler.getPlayerInfo(getCurrentUser());
      return myInfo.get(key);
      // If user cannot be found, load Guest data
    } catch (UserNotFoundException e) {
      LOGGER.info(e.getMessage());
      switchToUser(Config.GUEST);
    }
    // Return blank String otherwise
    return Config.BLANK;
  }

  /**
   * This method initialized game stat keys and profile information in the JSON file.
   *
   * @param profileInfo Map containing information about current user.
   */
  private void addStarterData(final Map<String, String> profileInfo) {
    // NEW USER, set games/wins/losses to 0
    for (String key : new String[]{Config.NUM_GAMES_KEY, Config.NUM_WINS_KEY,
        Config.NUM_LOSSES_KEY}) {
      profileInfo.put(key, Config.ZERO);
    }

    // No bio or profile picture yet
    for (String key : new String[]{Config.BIO_KEY, Config.PROFILE_PICTURE_KEY}) {
      profileInfo.put(key, Config.BLANK);
    }

    // default Preferences
    profileInfo.put(Config.PREFERRED_PREFIX + Config.THEME_KEY, Config.DEFAULT_THEME);
    profileInfo.put(Config.PREFERRED_PREFIX + Config.LANGUAGE, Config.ENGLISH);
    profileInfo.put(Config.PREFERRED_PREFIX + Config.SIZE_KEY, Config.LARGE);
    profileInfo.put(Config.PREFERRED_PREFIX + Config.FONT_KEY, Config.ARIAL);
  }

  /**
   * This helper method determines if a username already exists in the database.
   *
   * @param username Username entered by user.
   * @return Whether username exists in program database.
   */
  private boolean isDuplicate(final String username) {
    Set<?> users = myProfiles.keySet();
    return users.contains(username);
  }

  /**
   * This method switches to a new user's profile and updates greeting display.
   *
   * @param newUser New user to switch to.
   */
  public static void switchToUser(final String newUser) {
    // Save current user preferences
    savePreferences();

    if (validUser(newUser)) {
      // Change current user
      currentUser = newUser;
      // Update new user's preferences IF NOT GUEST
      setUserPreferences();
    } else {
      switchToUser(Config.GUEST);
    }

    // Generate scene with preferences
    Config.MY_VIEW.updateScene(Config.PROFILE_DISPLAY_PATH +
        Config.PROFILE_SETTINGS);
  }

  /**
   * Checks if new user is in database.
   *
   * @param username Username being searched.
   */
  private static boolean validUser(final Object username) {
    // Update display and reload myProfiles
    Config.MY_VIEW.updateScene(Config.PROFILE_DISPLAY_PATH + Config.PROFILE_SETTINGS);
    return myProfiles.containsKey(username);
  }

  /**
   * Allows a user to play as guest by logging them out.
   */
  private void playAsGuest() {
    if (currentUser.equals(Config.GUEST)) {
      // Guest cannot switch to Guest
      UserAlert.showMessage(Resources.getKey(Config.ALREADY_GUEST),
          Config.CANNOT_COMPLETE);

    } else {
      // Set current user to Guest and update greeting
      switchToUser(Config.GUEST);
      UserAlert.showMessage(Resources.getKey(Config.NOW_GUEST), Config.SUCCESS);
    }
  }

  /**
   * This method displays current user's name.
   *
   * @return The name of the current user.
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * This method returns a user's profile picture image.
   *
   * @param user Username whose profile picture is being returned.
   * @return Imageview containing profile picture or default Guest picture.
   */
  public ImageView getProfilePicture(final Object user) {
    try {
      Map<String, String> information = ProfileHandler.getPlayerInfo(user);
      String picturePath = information.get(Config.PROFILE_PICTURE_KEY);

      // Validate picture path and set
      return handlePicturePath(picturePath);

      // ERROR CATCHING
    } catch (UserNotFoundException e) {
      // Switch to guest and call again
      LOGGER.info(e.getMessage());
      switchToUser(Config.GUEST);
      return getProfilePicture(Config.GUEST);
    }
  }

  /**
   * Validates current picture path and sets it to Guest if null or invalid.
   *
   * @param picturePath Current picture path.
   * @return ImageView containing a valid image.
   */
  private ImageView handlePicturePath(String picturePath) {
    // If NO profile picture is set, use Guest picture path
    if (picturePath == null) {
      picturePath = getGuestPath();
    }
    ImageView view = new ImageView();
    // If user has INVALID profile picture URL, user Guest Image
    try {
      assert picturePath != null;
      Image profilePicture = new Image(picturePath);
      view.setImage(profilePicture);
    } catch (IllegalArgumentException e) {
      LOGGER.info(e.getMessage());
      Image profilePicture = new Image(getGuestPath());
      view.setImage(profilePicture);
    }
    return view;
  }

  /**
   * This method returns the path for Guest's profile picture, to be used when a user does not have
   * a profile picture.
   *
   * @return Guest's profile picture path.
   */
  private String getGuestPath() {
    // Get Guest Profile Picture
    try {
      Map<String, String> guestInformation = ProfileHandler.getPlayerInfo(Config.GUEST);
      return guestInformation.get(Config.PROFILE_PICTURE_KEY);
    } catch (UserNotFoundException e) {
      LOGGER.info(e.getMessage());
      return getGuestPath();
    }
  }

  /**
   * This method resets current user's game statistics to 0.
   *
   * @param initialSetup Boolean indicating when this method is called. If false, no message is
   *                     shown to the user as it is called when Scene is being created.
   */
  public static void resetGameStats(final boolean initialSetup) {
    // Update in JSON file.
    ProfileSettings.updateKey(Config.NUM_WINS_KEY, String.valueOf(0));
    ProfileSettings.updateKey(Config.NUM_LOSSES_KEY, String.valueOf(0));
    ProfileSettings.updateKey(Config.NUM_GAMES_KEY, String.valueOf(0));

    if (!initialSetup) {
      UserAlert.showMessage(Resources.getKey(Config.GAME_STATS_CLEARED), Config.SUCCESS);
    }
  }
}
