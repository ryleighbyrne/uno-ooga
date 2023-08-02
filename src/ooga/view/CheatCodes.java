package ooga.view;

import javafx.scene.input.KeyCode;
import ooga.view.display.profileDisplay.ProfileSettings;

/**
 * This class implements "Cheat Codes" to use when playing UNO.
 */
public class CheatCodes {

  /**
   * This method enables Cheat Codes for the user.
   *
   * @param code Input key from user specifying cheat code.
   */
  public static void enableCheatCodes(KeyCode code) {
    switch (code) {
      case N:
        ProfileSettings.resetGameStats(false);
        break;
      case W:
        instantWin();
        break;
    }
  }

  /**
   * This method adds +1 to a user's numWins and redirects to the end game display.
   */
  private static void instantWin() {
    Config.MY_VIEW.gameIsOver(0);
  }
}
