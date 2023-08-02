package ooga.view;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import ooga.PropertyChangeObservable;
import ooga.view.display.EndDisplay;
import ooga.view.resources.Resources;

import ooga.view.display.profileDisplay.ProfileSettings;
import ooga.view.toolbar.Toolbar;
import ooga.view.display.Display;
import ooga.view.userAlert.UserAlert;


/**
 * This class contains methods necessary to the creation and updating of the View.
 *
 * @author Cate Schick cms168
 */
public class View extends PropertyChangeObservable {

  /**
   * Current Scene.
   */
  private Scene myScene;
  /**
   * Current display in program.
   **/
  private Display myDisplay;
  /**
   * Main root in application.
   **/
  private static BorderPane root;
  /**
   * Currently selected theme.
   */
  private static String currentTheme = "default";
  /**
   * Current font.
   */
  private static String currentFont = "Arial";
  /**
   * Current size
   */
  private static String currentSize = "Large";

  /**
   * View constructor.
   */
  public View() {
  }

  /**
   * When the game is over, switch to EndDisplay and show who won.
   *
   * @param winnerId ID of winner (0 is user).
   */
  public void gameIsOver(final int winnerId) {
    // Is user won, change boolean in EndDisplay
    EndDisplay.setWinner(winnerId);

    // Navigate to EndDisplay
    myDisplay.switchDisplay(Config.END_DISPLAY);
  }

  /**
   * This method alerts a user that there are no more possible cards to draw.
   */
  public void noMorePossibleCardsToDraw() {
    UserAlert.showMessage(Resources.getKey(Config.NO_MORE_CARDS), Config.ERROR_TITLE);
  }

  /**
   * Returns root node of application.
   *
   * @return root.
   */
  public BorderPane getRoot() {
    return root;
  }

  /**
   * Returns current font.
   *
   * @return Current font being used.
   */
  public String getFont() {
    return currentFont;
  }

  /**
   * Returns current theme.
   *
   * @return Theme file being used.
   */
  public String getCurrentTheme() {
    return currentTheme;
  }

  /**
   * Sets current font.
   *
   * @param newFont The new font.
   */
  public void setFont(final String newFont) {
    currentFont = newFont;

    // Update User preferences in JSON file.
    if (!ProfileSettings.getCurrentUser().equals(Config.GUEST)) {
      ProfileSettings.savePreferences();
    }

    updateScene(Config.DESIGN_SETTINGS_PATH + Config.FONT_DISPLAY);
  }

  /**
   * Allows user to change theme linked to scene.
   *
   * @param newTheme The file name of new theme.
   */
  public void setTheme(final String newTheme) {
    currentTheme = newTheme;
    // clear old files and add new theme
    myScene.getStylesheets().clear();
    myScene.getStylesheets().add(String.format(
        Config.THEME_PATH, currentTheme));

    // Update User preferences in JSON file.
    if (!ProfileSettings.getCurrentUser().equals(Config.GUEST)) {
      ProfileSettings.savePreferences();
    }

    // Update scene with new theme
    updateScene(Config.DESIGN_SETTINGS_PATH + Config.THEME_DISPLAY);
  }

  /**
   * Returns the current display the program is on.
   *
   * @return myDisplay variable.
   */
  public Display getMyDisplay() {
    return myDisplay;
  }

  /**
   * Sets current display to new display navigated to within program.
   *
   * @param newDisplay Desired display to navigate to.
   */
  public void setMyDisplay(final Display newDisplay) {
    myDisplay = newDisplay;
  }

  /**
   * Sets the initial Scene with the intro display.
   *
   * @param w The width of the application.
   * @param h The height of the application.
   * @return A new scene.
   */
  public Scene makeScene(final int w, final int h) {
    root = new BorderPane();
    Display d = new Display(this);
    Toolbar t = new Toolbar(d);

    // Add ToolBar to top of root
    ToolBar toolbar = t.createToolbar();
    root.setTop(toolbar);

    // Initialize intro display, check for errors, and set to center
    d.switchDisplay(Config.INTRO_DISPLAY_PATH + Config.INTRO_DISPLAY);

    // Reset Guest game statistics every time program is loaded
    ProfileSettings.resetGameStats(true);

    // Create scene, enable cheat codes, and add style
    myScene = new Scene(root, w, h);
    myScene.setOnKeyPressed(e -> CheatCodes.enableCheatCodes(e.getCode()));
    root.getStyleClass().addAll(currentFont, Config.ROOT_STYLE);
    myScene.getStylesheets().add(String.format(
        Config.THEME_PATH, currentTheme));
    return myScene;
  }

  /**
   * Updates scene and redraws necessary displays. Used when language is dynamically changed.
   *
   * @param currentDisplay The display being updated.
   */
  public void updateScene(final String currentDisplay) {
    // Update Display
    Display d = new Display(this);
    d.switchDisplay(currentDisplay);
    // Update ToolBar
    Toolbar t = new Toolbar(d);
    root.setTop(t.createToolbar());
    root.setCenter(d.createDisplay());
  }

  /**
   * This method list of file names in a directory.
   *
   * @param filePath The directory path.
   * @return String[] listing all files in that directory.
   */
  public static ArrayList<String> listFiles(final String filePath) {
    File folderFiles = new File(filePath);
    File[] files = folderFiles.listFiles();

    ArrayList<String> fileList = new ArrayList<>();

    // Add each file to arraylist
    assert files != null;
    for (File f : files) {
      fileList.add(f.toString());
    }

    return fileList;
  }

  /**
   * This method formats files by removing their paths and extensions.
   *
   * @param folderPath       The path where files are being opened.
   * @param additionalFilter Another filter to limit returned files.
   * @return ArrayList</ String> List of formatted file names.
   */
  public ArrayList<String> extractFileName(final String folderPath,
      final String additionalFilter) {
    ArrayList<String> list = View.listFiles(folderPath);
    ArrayList<String> formatted = new ArrayList<>();

    for (String f : list) {
      // Only use .css files
      if (f.contains(additionalFilter)) {
        // Split by '/' to remove unnecessary path
        String fileName = f.split(Config.SLASH)[3];

        // Split by '.' to extract file name
        String[] file = fileName.split(Config.PERIOD);

        // Capitalize first letter
        String name = file[0].substring(0, 1).toUpperCase() +
            file[0].substring(1);
        formatted.add(name);
      }
    }
    return formatted;
  }

  /**
   * Changes the font size of the program.
   *
   * @param newSize The size option to be added to CSS style classes.
   */
  public void setSize(final String newSize) {
    currentSize = newSize;

    // Update User preferences in JSON file.
    if (!ProfileSettings.getCurrentUser().equals(Config.GUEST)) {
      ProfileSettings.savePreferences();
    }

    updateScene(Config.DESIGN_SETTINGS_PATH + Config.SIZE_DISPLAY);
  }

  /**
   * Returns the current size of the program text.
   *
   * @return The currentSize variable for the program.
   */
  public String getSize() {
    return currentSize;
  }
}
