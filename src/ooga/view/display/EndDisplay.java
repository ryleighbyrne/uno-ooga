package ooga.view.display;

import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ooga.data.ProfileHandler;
import ooga.data.dataExceptions.UserNotFoundException;
import ooga.view.Config;
import ooga.view.View;
import ooga.view.display.profileDisplay.ProfileSettings;
import ooga.view.resources.Resources;
import ooga.view.button.ViewButton;
import ooga.view.userAlert.UserAlert;

/**
 * This method contains display content for end of game scenarios, where the user either wins or
 * loses to an automatic player.
 *
 * @author Cate Schick cms168
 */
public class EndDisplay extends Display {

  /**
   * User win status.
   */
  private static boolean userWin = false;
  /**
   * Player ID of winner.
   */
  private static int winner;
  /**
   * BorderPane containing display information.
   */
  private BorderPane myPane;

  /**
   * Constructor for Display class.
   *
   * @param v View object associated with Display.
   */
  public EndDisplay(final View v) {
    super(v);
    // To avoid duplicates, add to user record in constructor
    this.createDisplay();
    handleGameOutcome();
  }

  /**
   * Override method creating display for when user wins.
   *
   * @return BorderPane for end of game information.
   */
  @Override
  public BorderPane createDisplay() {
    myPane = new BorderPane();
    // Display proper title depending on win or loss
    super.setTitle(myPane, showProperTitle());

    // Allow user to play again
    myPane.setCenter(addButtons());
    return myPane;
  }

  /**
   * This method adds buttons for user to either navigate to home or game settings, or to start a
   * new basic game.
   *
   * @return HBox containing buttons.
   */
  private HBox addButtons() {
    HBox h = new HBox();

    // Add home, play again, and game settings buttons
    Button home = ViewButton.makeButton(Resources.getKey(Config.RETURN_HOME), e ->
        this.switchDisplay(Config.INTRO_DISPLAY_PATH + Config.INTRO_DISPLAY));

    Button playAgain = ViewButton.makeButton(Resources.getKey(Config.PLAY_AGAIN), e ->{
              Config.MY_VIEW.notifyObservers(ooga.Config.ADD_LOAD_FILE_OBSERVERS, null);
              notifyObservers(ooga.Config.LOAD_FILE_FOR_NEW_GAME, ooga.Config.DEFAULT_BASIC_GAME);
            }
      );
    Button settings = ViewButton.makeButton(Resources.getKey(Config.GAME_SETTINGS), e ->
        this.switchDisplay(Config.GAME_SETTINGS_PATH + Config.GAME_SETTINGS));

    Button[] array = new Button[]{home, playAgain, settings};
    ViewButton.styleButtons(array, myPane);
    h.getChildren().addAll(array);
    return h;
  }

  /**
   * Keep track of winner and determine if user won.
   *
   * @param winnerID Number of winning player.
   */
  public static void setWinner(final int winnerID) {
    winner = winnerID;
    userWin = (winnerID == 0);
  }

  /**
   * Updates whether user wins or loses and add game stats to profile.
   */
  private void handleGameOutcome() {
    if (userWin) {
      addToRecord(Config.NUM_WINS_KEY);
    } else {
      addToRecord(Config.NUM_LOSSES_KEY);
    }
    addToRecord(Config.NUM_GAMES_KEY);
  }

  /**
   * This method shows the proper title depending on if user won or lost.
   *
   * @return Label containing title for win or loss scenario.
   */
  private Label showProperTitle() {
    String message;
    if (userWin) {
      message = String.format(Resources.getKey(Config.WIN_TITLE),
          ProfileSettings.getCurrentUser());
    } else {
      message = String.format(Resources.getKey(Config.LOSS_TITLE), winner);
    }
    return new Label(message);
  }

  /**
   * This method adds one to the key of the specified value.
   *
   * @param keyToUpdate Key to increment by one.
   */
  private void addToRecord(final String keyToUpdate) {
    try {
      Map<String, String> userData = ProfileHandler.getPlayerInfo(
          ProfileSettings.getCurrentUser());

      // Increment key by one
      int data = Integer.parseInt(userData.get(keyToUpdate));
      data += 1;

      // Update in JSON file.
      ProfileSettings.updateKey(keyToUpdate, String.valueOf(data));

    } catch (UserNotFoundException e) {
      ProfileSettings.switchToUser(Config.GUEST);
      UserAlert.showError(e);
    }
  }
}
