package ooga.view.display.introDisplay;

import javafx.scene.control.Button;
import ooga.view.resources.Resources;
import javafx.scene.layout.BorderPane;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.button.ViewButton;
import ooga.view.display.Display;

/**
 * This class handles the intro display, which gives users the option to begin settings up game
 * configurations or going to game settings.
 *
 * @author Cate Schick cms168
 */
public final class IntroDisplay extends Display {

  /**
   * Constructor for Display class.
   *
   * @param v View associated with Intro display.
   */
  public IntroDisplay(final View v) {
    super(v);
  }

  /**
   * Override method creates Intro Display allowing users to navigate to settings or start
   * gameplay.
   *
   * @return Intro display.
   */
  @Override
  public BorderPane createDisplay() {
    BorderPane intro = new BorderPane();

    // Set display style
    setDisplayStyle(Config.INTRO_DISPLAY, intro);

    // Add content
    addIntroButtons(intro);

    return intro;
  }

  /**
   * This method adds introDisplay buttons.
   *
   * @param display BorderPane for buttons to be added to.
   */
  private void addIntroButtons(final BorderPane display) {
    // Add start & settings button
    Button start = ViewButton.makeButton(Resources.getKey(Config.START_BUTTON),
        e -> {
          this.switchDisplay(Config.INTRO_DISPLAY_PATH + Config.SET_GAME_DISPLAY);
          Config.MY_VIEW.notifyObservers(
              ooga.Config.ADD_LOAD_FILE_OBSERVERS, null);
        });
    Button settings = ViewButton.makeButton(Resources.getKey(Config.SETTINGS_BUTTON),
        e -> this.switchDisplay(Config.GAME_SETTINGS_PATH + Config.GAME_SETTINGS));

    // Add style to each button
    Button[] buttons = new Button[]{start, settings};
    ViewButton.styleButtons(buttons, null);

    // Set buttons on either side of the display.
    display.setLeft(start);
    display.setRight(settings);
  }
}
