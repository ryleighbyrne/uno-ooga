package ooga.view.display.introDisplay;

import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import ooga.view.resources.Resources;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;
import ooga.view.userAlert.UserAlert;

/**
 * This class creates a display to prompt the user to either: 1. Upload an existing game file. 2.
 * Start a basic game. 3. Upload a custom game file.
 *
 * @author Cate Schick cms168
 */
public final class GameChooserDisplay extends Display {

  /**
   * Constructor for Display class.
   *
   * @param v View associated with Intro display.
   */
  public GameChooserDisplay(final View v) {
    super(v);
    v.notifyObservers(ooga.Config.ADD_LOAD_FILE_OBSERVERS, null);
  }

  /**
   * Override method creates a display to select version of UNO.
   *
   * @return Display allowing user to select game version.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane intro = new BorderPane();
    setDisplayStyle(Config.SET_GAME_DISPLAY_STYLE, intro);

    // Add buttons
    HBox h = addGameOptions();

    intro.setCenter(h);
    intro.setLeft(Config.MY_VIEW.getMyDisplay().makeBackButton(
        Config.INTRO_DISPLAY_PATH + Config.INTRO_DISPLAY));

    return intro;
  }

  /**
   * This method adds buttons to load game display.
   *
   * @return HBox with buttons added.
   */
  private HBox addGameOptions() {
    HBox h = new HBox();

    Button loadGame = ViewButton.makeButton(Resources.getKey(Config.LOAD_GAME),
        e -> loadFileChooser());
    Button createGame = ViewButton.makeButton(Resources.getKey(Config.CREATE_GAME),
        e -> {
      this.switchDisplay(Config.GAME_SETTINGS_PATH
                  + Config.GAME_SETTINGS);
        });
    Button basicGame = ViewButton.makeButton(Resources.getKey(Config.BASIC_START),
        e -> notifyObservers(ooga.Config.LOAD_FILE_FOR_NEW_GAME, ooga.Config.DEFAULT_BASIC_GAME));

//     Style display buttons.
    Button[] buttons = new Button[]{loadGame, basicGame, createGame};
    ViewButton.styleButtons(buttons, h);

    return h;
  }

  /**
   * This method creates a file chooser to return to the user.
   */
  private void loadFileChooser() {
    FileChooser chooser = new FileChooser();

    // Set title, path, and filters
    chooser.setTitle(Config.FILE_CHOOSER_KEY);
    chooser.setInitialDirectory(new File(Config.GAME_FILE_PATH));
    chooser.getExtensionFilters().add(
        new ExtensionFilter(Config.JSON_EXTENSION, Config.JSON_EXTENSION));

    Stage stage = new Stage();
    File selectedFile = chooser.showOpenDialog(stage);

    try {
      // When user does not select a file
      if (selectedFile == null) {
        UserAlert.showMessage(Resources.getKey(Config.NULL_FILE),
            Config.CANNOT_COMPLETE);
        return;
      }

      notifyObservers(ooga.Config.LOAD_FILE_FOR_NEW_GAME, selectedFile.toString());

      // Exception when opening file
    } catch (Exception exception) {
      UserAlert.showError(exception);
    }
  }

}
